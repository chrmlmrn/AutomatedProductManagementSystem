package admin.reports.sales;

import admin.records.userlogs.UserLogUtil;
import admin.reports.ReportsPage;
import database.DatabaseUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SalesReport extends JPanel {

    private DefaultTableModel model;
    private JTable salesTable;
    private JFrame mainFrame;
    private String uniqueUserId;
    private Timer timer;

    public SalesReport(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponents();
        fetchData();
        startAutoRefresh();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Back arrow button
        JButton backButton = new JButton("<");
        backButton.setBounds(20, 30, 50, 30);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new ReportsPage(mainFrame, uniqueUserId)); // Pass userUniqueId
            mainFrame.revalidate();
            mainFrame.repaint();
            stopAutoRefresh(); // Stop auto-refresh when navigating away
        });
        add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Sales Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 300, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Table Data
        String[] columnNames = { "Receipt Number", "Reference Number", "Date", "Subtotal", "Discount", "VAT", "Total" };
        Object[][] data = {};
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        salesTable = new JTable(model);
        salesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        salesTable.getTableHeader().setBackground(new Color(30, 144, 255));
        salesTable.getTableHeader().setForeground(Color.WHITE);
        salesTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        salesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        salesTable.setRowHeight(30); // Set a minimum row height

        // Center the text in all cells and wrap long text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < salesTable.getColumnCount(); i++) {
            salesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Custom cell renderer to wrap text and adjust row height
        salesTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private final JTextArea textArea = new JTextArea();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                textArea.setText(value == null ? "" : value.toString());
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setFont(table.getFont());
                textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding

                int fontHeight = textArea.getFontMetrics(textArea.getFont()).getHeight();
                int textLength = textArea.getText().length();
                int lines = (int) Math
                        .ceil((double) textLength / table.getColumnModel().getColumn(column).getWidth() * 1.5);
                int rowHeight = fontHeight * (lines + 1);
                table.setRowHeight(row, Math.max(rowHeight, 30)); // Set minimum row height

                return textArea;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(salesTable);
        tableScrollPane.setBounds(50, 100, 1300, 500);
        add(tableScrollPane);

        // Generate PDF Button
        JButton pdfButton = new JButton("Print");
        pdfButton.setBounds(1300, 30, 150, 30);
        pdfButton.setBackground(new Color(30, 144, 255));
        pdfButton.setForeground(Color.WHITE);
        pdfButton.setFocusPainted(false);
        pdfButton.setFont(new Font("Arial", Font.BOLD, 16));
        pdfButton.setBorder(BorderFactory.createEmptyBorder());
        pdfButton.addActionListener(e -> generatePDF());
        add(pdfButton);

        // Add component listener to keep elements centered and resized properly
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                tableScrollPane.setBounds(50, 100, frameWidth - 100, frameHeight - 200);
                pdfButton.setBounds(frameWidth - 200, 30, 150, 30);
            }
        });
    }

    private void fetchData() {
        DecimalFormat df = new DecimalFormat("0.00");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT receipt_number, reference_number, date, subtotal, discount, vat, total FROM transactions WHERE DATE(date) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currentDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            model.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String receiptNumber = resultSet.getString("receipt_number");
                String referenceNumber = resultSet.getString("reference_number");
                Date saleDate = resultSet.getDate("date");
                double subtotal = resultSet.getDouble("subtotal");
                double discount = resultSet.getDouble("discount");
                double vat = resultSet.getDouble("vat");
                double total = resultSet.getDouble("total");

                model.addRow(new Object[] { receiptNumber, referenceNumber, saleDate, df.format(subtotal),
                        df.format(discount), df.format(vat), df.format(total) });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startAutoRefresh() {
        timer = new Timer(true); // Run timer as a daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    fetchData();
                });
            }
        }, 0, 5000); // Refresh every 5 seconds
    }

    private void stopAutoRefresh() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void generatePDF() {
        PDDocument document = new PDDocument();
        try {
            PDPage page = new PDPage(PDRectangle.A4);
            page.setRotation(90); // Rotate page to landscape
            document.addPage(page);

            float margin = 50;
            float yStart = page.getMediaBox().getWidth() - 60; // Use width as height because of rotation
            float tableWidth = page.getMediaBox().getHeight() - 2 * margin;
            float yPosition = yStart;
            float cellMargin = 5f;

            int numRows = model.getRowCount();
            int numCols = model.getColumnCount();
            float tableTopY = yPosition - 60; // Adjust starting position of the table

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.transform(new Matrix(0, 1, -1, 0, page.getMediaBox().getWidth(), 0)); // Rotate content

            // Company and report details
            String companyName = "Lavega Store";
            String companyAddress = "Eastwind Montalban Rizal";
            String companyContact = "+639756497239";
            String reportType = "Sales Report";
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            centerText(contentStream, companyName, PDType1Font.HELVETICA_BOLD, 12, page.getMediaBox().getHeight(),
                    yStart + 30);
            contentStream.endText();

            contentStream.beginText();
            centerText(contentStream, companyAddress, PDType1Font.HELVETICA_BOLD, 12, page.getMediaBox().getHeight(),
                    yStart + 15);
            contentStream.endText();

            contentStream.beginText();
            centerText(contentStream, companyContact, PDType1Font.HELVETICA_BOLD, 12, page.getMediaBox().getHeight(),
                    yStart);
            contentStream.endText();

            contentStream.beginText();
            centerText(contentStream, reportType, PDType1Font.HELVETICA_BOLD, 12, page.getMediaBox().getHeight(),
                    yStart - 30);
            contentStream.endText();

            // Move down to start table
            yPosition = tableTopY;

            // Draw table header
            float rowHeight = drawTableHeader(contentStream, margin, yPosition, tableWidth, numCols, cellMargin);
            yPosition -= rowHeight;

            contentStream.setFont(PDType1Font.HELVETICA, 8);
            double totalTotal = 0;

            for (int row = 0; row < numRows; row++) {
                float maxHeight = rowHeight; // Default row height
                // Check if a new page is needed
                if (yPosition - maxHeight < margin + 50) { // Adjust margin to ensure footer space
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    page.setRotation(90);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.transform(new Matrix(0, 1, -1, 0, page.getMediaBox().getWidth(), 0)); // Rotate
                                                                                                        // content
                    yPosition = yStart;
                    rowHeight = drawTableHeader(contentStream, margin, yPosition, tableWidth, numCols, cellMargin);
                    yPosition -= rowHeight;
                }

                // Draw table rows
                for (int col = 0; col < numCols; col++) {
                    float cellWidth = tableWidth / numCols;
                    String cellText = model.getValueAt(row, col).toString();
                    float textHeight = drawCellText(contentStream, cellText, margin + col * cellWidth, yPosition - 15,
                            cellWidth, cellMargin, PDType1Font.HELVETICA, 8);
                    maxHeight = Math.max(maxHeight, textHeight); // Update row height if necessary

                    // Calculate total
                    if (col == 6)
                        totalTotal += Double.parseDouble(cellText);
                }
                yPosition -= maxHeight;

                // Draw table borders for row
                for (int col = 0; col <= numCols; col++) {
                    float cellWidth = tableWidth / numCols;
                    contentStream.moveTo(margin + col * cellWidth, yPosition + maxHeight);
                    contentStream.lineTo(margin + col * cellWidth, yPosition);
                    contentStream.stroke();
                }
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();
            }

            // Add totals row inside the table
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            float maxHeight = rowHeight; // Default row height for totals row

            // Draw totals row cells and calculate the height
            for (int col = 0; col < numCols; col++) {
                float cellWidth = tableWidth / numCols;
                String cellText = (col == 0) ? "Total" : (col == 6) ? String.format("%.2f", totalTotal) : "";
                float textHeight = drawCellText(contentStream, cellText, margin + col * cellWidth, yPosition - 15,
                        cellWidth, cellMargin, PDType1Font.HELVETICA_BOLD, 8);
                maxHeight = Math.max(maxHeight, textHeight); // Update row height if necessary
            }
            yPosition -= maxHeight;

            // Draw table borders for totals row
            for (int col = 0; col <= numCols; col++) {
                float cellWidth = tableWidth / numCols;
                contentStream.moveTo(margin + col * cellWidth, yPosition + maxHeight);
                contentStream.lineTo(margin + col * cellWidth, yPosition);
                contentStream.stroke();
            }
            contentStream.moveTo(margin, yPosition);
            contentStream.lineTo(margin + tableWidth, yPosition);
            contentStream.stroke();

            // Draw the generated by and date below the table on the last page
            drawFooter(contentStream, margin, yPosition - 40, page.getMediaBox().getHeight(), uniqueUserId); // Adjust
                                                                                                             // to
                                                                                                             // ensure
                                                                                                             // the
                                                                                                             // footer
                                                                                                             // is
                                                                                                             // directly
                                                                                                             // under
                                                                                                             // the
                                                                                                             // table
            contentStream.close();

            // Save the document with the specified filename format
            String fileName = uniqueUserId + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date())
                    + "_SALES_REPORT.pdf";
            String filePath = "generated_reports/sales/" + fileName;
            document.save(new File(filePath));
            JOptionPane.showMessageDialog(this, "Sales report generated successfully!");

            UserLogUtil.logUserAction(uniqueUserId, "Generated Sales Report");

            // Store the PDF file in the database
            storePDFInDatabase(filePath, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to draw the table header
    private float drawTableHeader(PDPageContentStream contentStream, float margin, float yPosition, float tableWidth,
            int numCols, float cellMargin) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
        contentStream.setLineWidth(0.5f);

        float rowHeight = 20; // Default row height

        // Draw table header text
        for (int col = 0; col < numCols; col++) {
            float cellWidth = tableWidth / numCols;
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + col * cellWidth + cellMargin, yPosition - 12);
            contentStream.showText(model.getColumnName(col));
            contentStream.endText();
        }

        // Draw table header borders
        contentStream.moveTo(margin, yPosition);
        contentStream.lineTo(margin + tableWidth, yPosition);
        contentStream.stroke();
        contentStream.moveTo(margin, yPosition - rowHeight);
        contentStream.lineTo(margin + tableWidth, yPosition - rowHeight);
        contentStream.stroke();
        for (int col = 0; col <= numCols; col++) {
            float cellWidth = tableWidth / numCols;
            contentStream.moveTo(margin + col * cellWidth, yPosition);
            contentStream.lineTo(margin + col * cellWidth, yPosition - rowHeight);
            contentStream.stroke();
        }
        return rowHeight;
    }

    private float drawCellText(PDPageContentStream contentStream, String text, float x, float y, float cellWidth,
            float cellMargin, PDType1Font font, int fontSize) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        float maxHeight = 20; // Default row height
        if (textWidth < cellWidth - 2 * cellMargin) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x + cellMargin, y);
            contentStream.showText(text);
            contentStream.endText();
        } else {
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();
            float lineHeight = fontSize + 2;
            for (String word : words) {
                if (font.getStringWidth(line + " " + word) / 1000 * fontSize < cellWidth - 2 * cellMargin) {
                    if (line.length() > 0) {
                        line.append(" ");
                    }
                    line.append(word);
                } else {
                    contentStream.beginText();
                    contentStream.setFont(font, fontSize);
                    contentStream.newLineAtOffset(x + cellMargin, y);
                    contentStream.showText(line.toString());
                    contentStream.endText();
                    y -= lineHeight;
                    maxHeight += lineHeight;
                    line = new StringBuilder(word);
                }
            }
            if (line.length() > 0) {
                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                contentStream.newLineAtOffset(x + cellMargin, y);
                contentStream.showText(line.toString());
                contentStream.endText();
            }
        }
        return maxHeight;
    }

    // Centered text utility function
    private void centerText(PDPageContentStream contentStream, String text, PDType1Font font, int fontSize,
            float pageWidth, float yPosition) throws IOException {
        float x = getCenterX(text, font, fontSize, pageWidth);
        contentStream.newLineAtOffset(x, yPosition);
        contentStream.showText(text);
    }

    private float getCenterX(String text, PDType1Font font, int fontSize, float pageWidth) throws IOException {
        float stringWidth = font.getStringWidth(text) / 1000 * fontSize;
        return (pageWidth - stringWidth) / 2;
    }

    private void drawFooter(PDPageContentStream contentStream, float margin, float yPosition, float pageWidth,
            String uniqueUserId) throws IOException {
        String generatedBy = "Generated by: " + uniqueUserId;
        String generatedOn = "Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(generatedBy);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(pageWidth - margin - getStringWidth(generatedOn, PDType1Font.HELVETICA, 10),
                yPosition);
        contentStream.showText(generatedOn);
        contentStream.endText();
    }

    private void storePDFInDatabase(String filePath, String fileName) {
        String insertSQL = "INSERT INTO reports (report_type_id, report_date, unique_user_id, file_name, file_data) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                FileInputStream fis = new FileInputStream(new File(filePath))) {

            pstmt.setInt(1, getReportTypeId("Sales Report")); // Assuming you have a method to get the report_type_id
            pstmt.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            pstmt.setString(3, uniqueUserId); // Assuming uniqueUserId is an integer
            pstmt.setString(4, fileName);
            pstmt.setBinaryStream(5, fis, (int) new File(filePath).length());

            pstmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private int getReportTypeId(String reportType) {
        return 1; // Replace with actual logic to get report type ID
    }

    private float getStringWidth(String text, PDType1Font font, int fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }

}
