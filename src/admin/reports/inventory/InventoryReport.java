package admin.reports.inventory;

import admin.records.userlogs.UserLogUtil;
import admin.reports.ReportsPage;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InventoryReport extends JPanel {

    private DefaultTableModel model;
    private InventoryDAO inventoryDAO;
    private JFrame mainFrame;
    private String uniqueUserId;

    public InventoryReport(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        inventoryDAO = new InventoryDAO();
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
        JLabel titleLabel = new JLabel("Inventory Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 300, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Table Data
        String[] columnNames = { "Product Code", "Product Name", "Supplier Name", "Product Type",
                "Critical Stock Level", "Stock Quantity", "Product Status" };
        Object[][] data = {};
        model = new DefaultTableModel(data, columnNames);

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 144, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Center the text in all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(table);
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
        List<Product> products = inventoryDAO.getInventory();
        for (Product product : products) {
            model.addRow(new Object[] {
                    product.getProductCode(),
                    product.getProductName(),
                    product.getSupplierName(),
                    product.getProductType(),
                    product.getCriticalLevel(),
                    product.getProductTotalQuantity(),
                    product.getProductStatus()
            });
        }
    }

    private void generatePDF() {
        try (PDDocument document = new PDDocument()) {
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
            String reportType = "Inventory Report";
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
                    contentStream.showText(model.getValueAt(row, col).toString());
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
            String generatedBy = "Generated by: " + uniqueUserId;
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
            String filePath = "C:/Users/ismai/OneDrive/Documents/SoftEng/AutomatedProductManagementSystem/generated_reports/inventory/"
                    + uniqueUserId + "_"
                    + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_INVENTORY_REPORT.pdf";
            document.save(new File(filePath));
            JOptionPane.showMessageDialog(this, "Inventory report generated successfully!");

            System.out.println("User ID for logging: " + uniqueUserId); // Debugging log
            UserLogUtil.logUserAction(uniqueUserId, "Generated Inventory Report");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
