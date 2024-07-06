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
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesReport extends JPanel {

    private DefaultTableModel model;
    private JTable salesTable;
    private JFrame mainFrame;
    private String uniqueUserId;

    public SalesReport(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponents();
        fetchData();
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
        });
        add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Sales Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 300, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Table Data
        String[] columnNames = { "Receipt Number", "Reference Number", "Date", "Subtotal", "Discount",
                "VAT", "Total" };
        Object[][] data = {};
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        salesTable = new JTable(model);
        salesTable.setRowHeight(30);
        salesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        salesTable.getTableHeader().setBackground(new Color(30, 144, 255));
        salesTable.getTableHeader().setForeground(Color.WHITE);
        salesTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        salesTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Center the text in all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < salesTable.getColumnCount(); i++) {
            salesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT receipt_number, reference_number, date, subtotal, discount, vat, total FROM transactions";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            model.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String receiptNumber = resultSet.getString("receipt_number");
                String referenceNumber = resultSet.getString("reference_number");
                Date saleDate = resultSet.getDate("date");
                double subtotal = resultSet.getDouble("subtotal");
                double discount = resultSet.getDouble("discount");
                double vat = resultSet.getDouble("vat");
                double total = resultSet.getDouble("total");
                model.addRow(new Object[] { receiptNumber, referenceNumber, saleDate,
                        df.format(subtotal), df.format(discount), df.format(vat), df.format(total) });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
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
            float rowHeight = 20; // Increase row height for better spacing
            float cellMargin = 5f;

            int rowsPerPage = (int) ((yPosition - margin) / rowHeight) - 4; // Adjusted for spacing
            int numRows = model.getRowCount();
            int numCols = model.getColumnCount();
            float tableTopY = yStart - 60; // Adjust starting position of the table

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

            // Draw the table header
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.setLineWidth(0.5f);

            // Draw table header
            for (int col = 0; col < numCols; col++) {
                float cellWidth = tableWidth / numCols;
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + col * cellWidth + cellMargin, yPosition - 12);
                contentStream.showText(model.getColumnName(col));
                contentStream.endText();
            }
            yPosition -= rowHeight;

            // Draw table borders for header
            for (int col = 0; col <= numCols; col++) {
                float cellWidth = tableWidth / numCols;
                contentStream.moveTo(margin + col * cellWidth, yPosition + rowHeight);
                contentStream.lineTo(margin + col * cellWidth, yPosition);
                contentStream.stroke();
            }
            contentStream.moveTo(margin, yPosition + rowHeight);
            contentStream.lineTo(margin + tableWidth, yPosition + rowHeight);
            contentStream.stroke();
            contentStream.moveTo(margin, yPosition);
            contentStream.lineTo(margin + tableWidth, yPosition);
            contentStream.stroke();

            contentStream.setFont(PDType1Font.HELVETICA, 8);
            int rowCount = 0;
            for (int row = 0; row < numRows; row++) {
                if (rowCount >= rowsPerPage) {
                    rowCount = 0;
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    page.setRotation(90);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.transform(new Matrix(0, 1, -1, 0, page.getMediaBox().getWidth(), 0)); // Rotate
                                                                                                        // content
                    yPosition = tableTopY;

                    // Draw the table header on the new page
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    yPosition -= rowHeight;
                    for (int col = 0; col < numCols; col++) {
                        float cellWidth = tableWidth / numCols;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + col * cellWidth + cellMargin, yPosition - 5);
                        contentStream.showText(model.getColumnName(col));
                        contentStream.endText();
                    }
                    yPosition -= rowHeight;

                    // Draw table borders for header
                    for (int col = 0; col <= numCols; col++) {
                        float cellWidth = tableWidth / numCols;
                        contentStream.moveTo(margin + col * cellWidth, yPosition + rowHeight);
                        contentStream.lineTo(margin + col * cellWidth, yPosition);
                        contentStream.stroke();
                    }
                    contentStream.moveTo(margin, yPosition + rowHeight);
                    contentStream.lineTo(margin + tableWidth, yPosition + rowHeight);
                    contentStream.stroke();
                    contentStream.moveTo(margin, yPosition);
                    contentStream.lineTo(margin + tableWidth, yPosition);
                    contentStream.stroke();
                }

                // Draw table rows
                for (int col = 0; col < numCols; col++) {
                    float cellWidth = tableWidth / numCols;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + col * cellWidth + cellMargin, yPosition - 15);
                    Object cellValue = model.getValueAt(row, col);
                    if (cellValue != null) {
                        contentStream.showText(cellValue.toString());
                    } else {
                        contentStream.showText("");
                    }
                    contentStream.endText();
                }
                yPosition -= rowHeight;

                // Draw table borders for row
                for (int col = 0; col <= numCols; col++) {
                    float cellWidth = tableWidth / numCols;
                    contentStream.moveTo(margin + col * cellWidth, yPosition + rowHeight);
                    contentStream.lineTo(margin + col * cellWidth, yPosition);
                    contentStream.stroke();
                }
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();

                rowCount++;
            }

            // Draw the generated by and date below the table
            yPosition -= 20; // Adjust the position below the table
            String generatedBy = "Generated by: " + uniqueUserId; // Using userUniqueId as the placeholder for the
                                                                  // generator
            String generatedOn = "Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText(generatedBy);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset(
                    page.getMediaBox().getHeight() - margin - getStringWidth(generatedOn, PDType1Font.HELVETICA, 10),
                    yPosition);
            contentStream.showText(generatedOn);
            contentStream.endText();

            contentStream.close();

            // Save the document with the specified filename format
            String fileName = uniqueUserId + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date())
                    + "_SALES_REPORT.pdf";
            String filePath = "generated_reports/sales/"
                    + fileName;
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
        // Implement this method to retrieve the report_type_id from the database
        // For simplicity, return a hardcoded value if known, e.g., 1 for Sales Report
        return 1;
    }

    private float getStringWidth(String text, PDType1Font font, int fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }

    // Centered text utility function
    private float getCenterX(String text, PDType1Font font, int fontSize, float pageWidth) throws IOException {
        float stringWidth = font.getStringWidth(text) / 1000 * fontSize;
        return (pageWidth - stringWidth) / 2;
    }

    private void centerText(PDPageContentStream contentStream, String text, PDType1Font font, int fontSize,
            float pageWidth, float yPosition) throws IOException {
        float x = getCenterX(text, font, fontSize, pageWidth);
        contentStream.newLineAtOffset(x, yPosition);
        contentStream.showText(text);
    }
}
