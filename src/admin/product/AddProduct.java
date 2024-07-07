package admin.product;

import com.toedter.calendar.JDateChooser;

import database.DatabaseUtil;
import admin.product.barcode.BarcodeGenerator;
import admin.records.userlogs.UserLogUtil;
import cashier.CashierMenu;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import customcomponents.RoundedTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class AddProduct extends JPanel {
    private Map<String, String> categoryMap;
    private Map<String, String> productTypeMap;

    private RoundedTextField productCodeTextField;

    private RoundedTextField barcodeTextField;
    private RoundedTextField productNameTextField;
    private JComboBox<String> categoryComboBox;
    private RoundedTextField productPriceTextField;
    private RoundedTextField productSizeTextField;
    private RoundedTextField productQuantityTextField;
    private JDateChooser expirationDateChooser;
    private JComboBox<String> typeComboBox;
    private RoundedTextField supplierNameTextField;

    private JFrame mainFrame;
    private String uniqueUserId;

    public AddProduct(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {

        // Create a main panel for centering the blue container
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        // Create the blue container panel with rounded corners
        RoundedPanel containerPanel = new RoundedPanel(30); // Adjust the radius as needed
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(650, 800)); // Increased width and height size
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Create constraints for layout inside the blue container
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Reduced spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Add the title label
        JLabel titleLabel = new JLabel("Enter Product Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(titleLabel, gbc);

        // Reset grid width
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Create labels and text fields for the form
        JLabel productCodeLabel = new JLabel("Product Code");
        productCodeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        productCodeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        containerPanel.add(productCodeLabel, gbc);

        productCodeTextField = new RoundedTextField(5, 20);
        productCodeTextField.setPreferredSize(new Dimension(500, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        productCodeTextField.setEditable(false); // Set the text field to be non-editable

        generateAndSetProductCode();

        containerPanel.add(productCodeTextField, gbc);

        JLabel barcodeLabel = new JLabel("Barcode");
        barcodeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        barcodeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        containerPanel.add(barcodeLabel, gbc);

        barcodeTextField = new RoundedTextField(5, 20);
        barcodeTextField.setPreferredSize(new Dimension(500, 30));
        ((AbstractDocument) barcodeTextField.getDocument()).setDocumentFilter(new IntegerFilter());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(barcodeTextField, gbc);

        JLabel productNameLabel = new JLabel("Product Name");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        productNameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        containerPanel.add(productNameLabel, gbc);

        productNameTextField = new RoundedTextField(5, 20);
        productNameTextField.setPreferredSize(new Dimension(500, 30));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(productNameTextField, gbc);

        JLabel categoryLabel = new JLabel("Product Category");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        containerPanel.add(categoryLabel, gbc);

        categoryMap = fetchCategories();
        String[] categories = categoryMap.keySet().toArray(new String[0]);
        categoryComboBox = new JComboBox<>(categories);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(categoryComboBox, gbc);

        JLabel priceLabel = new JLabel("Product Price");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        containerPanel.add(priceLabel, gbc);

        productPriceTextField = new RoundedTextField(5, 20);
        productPriceTextField.setPreferredSize(new Dimension(500, 30));
        ((AbstractDocument) productPriceTextField.getDocument()).setDocumentFilter(new PositiveNumberFilter());

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(productPriceTextField, gbc);

        JLabel sizeLabel = new JLabel("Product Size");
        sizeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        sizeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        containerPanel.add(sizeLabel, gbc);

        productSizeTextField = new RoundedTextField(5, 20);
        productSizeTextField.setPreferredSize(new Dimension(500, 30));
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(productSizeTextField, gbc);

        JLabel quantityLabel = new JLabel("Product Quantity");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        quantityLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 1;
        containerPanel.add(quantityLabel, gbc);

        productQuantityTextField = new RoundedTextField(5, 20);
        productQuantityTextField.setPreferredSize(new Dimension(500, 30));
        ((AbstractDocument) productQuantityTextField.getDocument()).setDocumentFilter(new IntegerFilter());
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(productQuantityTextField, gbc);

        JLabel expirationDateLabel = new JLabel("Product Expiration Date");
        expirationDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        expirationDateLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 1;
        containerPanel.add(expirationDateLabel, gbc);

        expirationDateChooser = new JDateChooser();
        expirationDateChooser.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(expirationDateChooser, gbc);

        JLabel typeLabel = new JLabel("Product Type");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        typeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.gridwidth = 1;
        containerPanel.add(typeLabel, gbc);

        productTypeMap = fetchProductTypes();
        String[] types = productTypeMap.keySet().toArray(new String[0]);
        typeComboBox = new JComboBox<>(types);

        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(typeComboBox, gbc);

        JLabel supplierNameLabel = new JLabel("Supplier Name");
        supplierNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        supplierNameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 19;
        gbc.gridwidth = 1;
        containerPanel.add(supplierNameLabel, gbc);

        supplierNameTextField = new RoundedTextField(5, 20);
        supplierNameTextField.setPreferredSize(new Dimension(500, 30));
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(supplierNameTextField, gbc);

        // Add buttons
        RoundedButton addButton = new RoundedButton("Add Product");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(300, 40));
        addButton.addActionListener(e -> {
            try {
                if (validateFields(productCodeTextField, barcodeTextField, productNameTextField, productPriceTextField,
                        productQuantityTextField, supplierNameTextField)) {
                    String newProductCode = generateNewProductCode();
                    productCodeTextField.setText(newProductCode); // Set the new product code in the text field

                    insertProduct(newProductCode, barcodeTextField.getText(),
                            productNameTextField.getText(), (String) categoryComboBox.getSelectedItem(),
                            productPriceTextField.getText(), productSizeTextField.getText(),
                            Integer.parseInt(productQuantityTextField.getText()),
                            ((JTextField) expirationDateChooser.getDateEditor().getUiComponent()).getText(),
                            (String) typeComboBox.getSelectedItem(), supplierNameTextField.getText());

                    clearFields(barcodeTextField, productNameTextField, productPriceTextField,
                            productSizeTextField, productQuantityTextField, expirationDateChooser,
                            supplierNameTextField);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error generating product code: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Add a button to generate the barcode
        RoundedButton displayBarButton = new RoundedButton("Display Barcode");
        displayBarButton.setFont(new Font("Arial", Font.BOLD, 16));
        displayBarButton.setBackground(Color.WHITE);
        displayBarButton.setForeground(Color.BLACK);
        displayBarButton.setFocusPainted(false);
        displayBarButton.setPreferredSize(new Dimension(300, 40));
        displayBarButton.addActionListener(e -> {
            // Validate all fields before generating barcode
            if (validateFields(productCodeTextField, barcodeTextField, productNameTextField,
                    productPriceTextField, productQuantityTextField,
                    supplierNameTextField)) {
                // Calculate fullBarcode before generating the barcode image
                // Calculate fullBarcode before generating the barcode image
                String barcode = barcodeTextField.getText();
                BufferedImage barcodeImage = null;
                if (barcode.matches("\\d{12}")) {
                    // Generate EAN-13 barcode with checksum
                    String fullBarcode = barcode + calculateEAN13Checksum(barcode);
                    barcodeImage = BarcodeGenerator.generateEAN13Barcode(fullBarcode, "barcode.png");
                } else if (barcode.matches("\\d{13}")) {
                    // Generate Code 128 barcode
                    barcodeImage = BarcodeGenerator.generateEAN13Barcode(barcode, "barcode.png");
                } else if (barcode.matches("\\d{14}")) {
                    // Generate Code 128 barcode
                    barcodeImage = BarcodeGenerator.generateEAN14Barcode(barcode, "barcode.png");
                }

                if (barcodeImage != null) {
                    String productName = productNameTextField.getText().trim();
                    // Show dialog with generated barcode image and product name
                    showBarcodeDialog(barcodeImage, productName);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid 13-digit or 14-digit barcode.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Cancel Button
        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(300, 40));
        cancelButton.addActionListener(e -> {
            mainFrame.setContentPane(new ProductPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        buttonPanel.setBackground(new Color(30, 144, 255));
        gbcButton.insets = new Insets(10, 10, 10, 10);
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        buttonPanel.add(addButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(displayBarButton, gbcButton);

        gbcButton.gridx = 2;
        buttonPanel.add(cancelButton, gbcButton);

        gbc.gridx = 0;
        gbc.gridy = 21;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        containerPanel.add(buttonPanel, gbc);

        // Add the blue container to the main panel
        GridBagConstraints gbcMainPanel = new GridBagConstraints();
        gbcMainPanel.gridx = 0;
        gbcMainPanel.gridy = 0;
        gbcMainPanel.weightx = 1;
        gbcMainPanel.weighty = 1;
        gbcMainPanel.fill = GridBagConstraints.CENTER;
        mainPanel.add(containerPanel, gbcMainPanel);

        mainFrame.setContentPane(mainPanel);
        // mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame
        mainFrame.setVisible(true);
    }

    // IntegerFilter for quantity
    static class IntegerFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }

    // PositiveNumberFilter for price
    static class PositiveNumberFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()), string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()), text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isValidInput(String currentText, String newText) {
            String combinedText = currentText + newText;
            return combinedText.matches("\\d*\\.?\\d*");
        }
    }

    private void generateAndSetProductCode() {
        try {
            String newProductCode = generateNewProductCode();
            productCodeTextField.setText(newProductCode);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error generating product code: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static Map<String, String> fetchCategories() {
        Map<String, String> categoryMap = new HashMap<>();
        try {
            Connection conn = DatabaseUtil.getConnection(); // Update with your
                                                            // database path
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT category_id, category_name FROM category");

            while (rs.next()) {
                categoryMap.put(rs.getString("category_name"), rs.getString("category_id"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryMap;
    }

    private static Map<String, String> fetchProductTypes() {
        Map<String, String> typeMap = new HashMap<>();
        try {
            Connection conn = DatabaseUtil.getConnection(); // Update with your
                                                            // database path
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT product_type_id, product_type_name FROM product_type");

            while (rs.next()) {
                typeMap.put(rs.getString("product_type_name"), rs.getString("product_type_id"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeMap;
    }

    // Method to validate input fields
    private boolean validateFields(JTextField productCodeTextField, JTextField barcodeTextField,
            JTextField productNameTextField, JTextField productPriceTextField, JTextField productQuantityTextField,
            JTextField supplierNameTextField) {
        // Add your validation logic hereW
        // Example validation: checking if fields are empty
        if (productCodeTextField.getText().isEmpty() || barcodeTextField.getText().isEmpty() ||
                productNameTextField.getText().isEmpty() || productPriceTextField.getText().isEmpty()
                || productQuantityTextField.getText().isEmpty() ||
                supplierNameTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields (if applicable).", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Ensure barcode is 12 or 14 digits long
        if (!barcodeTextField.getText().matches("\\d{12}|\\d{13}|\\d{14}")) {
            JOptionPane.showMessageDialog(null, "Barcode must be exactly 12 or 14 digits.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void clearFields(RoundedTextField barcodeTextField,
            RoundedTextField productNameTextField, RoundedTextField productPriceTextField,
            RoundedTextField productSizeTextField, RoundedTextField productQuantityTextField,
            JDateChooser expirationDateChooser, RoundedTextField supplierNameTextField) {
        barcodeTextField.setText("");
        productNameTextField.setText("");
        productPriceTextField.setText("");
        productSizeTextField.setText("");
        productQuantityTextField.setText("");
        expirationDateChooser.setDate(null);
        supplierNameTextField.setText("");

        generateAndSetProductCode();

    }

    private String generateNewProductCode() throws SQLException {
        String latestProductCode = "";
        String query = "SELECT product_code FROM products ORDER BY product_code DESC LIMIT 1";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                latestProductCode = rs.getString("product_code");
            }
        }

        // Extract the numeric part and increment it
        int nextCodeNumber = 1; // Default starting number
        if (!latestProductCode.isEmpty()) {
            String numericPart = latestProductCode.replaceAll("[^\\d]", "");
            if (!numericPart.isEmpty()) {
                nextCodeNumber = Integer.parseInt(numericPart) + 1;
            }
        }

        // Format the new product code as PRODXXXX
        return String.format("PROD%04d", nextCodeNumber);
    }

    private void insertProduct(String productCode, String barcode, String productName, String category,
            String price, String size, int quantity, String expirationDate, String type, String supplierName) {
        // Calculate the EAN 13 checksum
        String fullBarcode = barcode.matches("\\d{12}") ? barcode + calculateEAN13Checksum(barcode) : barcode;

        // Get the category_id and product_type_id
        String categoryId = categoryMap.get(category);
        String typeId = productTypeMap.get(type);

        try {
            Connection conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // Enable transaction

            // Fetch the latest product code and generate a new one
            String newProductCode = generateNewProductCode();
            productCodeTextField.setText(newProductCode);

            // Insert the supplier first and retrieve its generated ID
            String insertSupplierSQL = "INSERT INTO supplier (supplier_name) VALUES (?)";
            PreparedStatement pstmtSupplier = conn.prepareStatement(insertSupplierSQL, Statement.RETURN_GENERATED_KEYS);
            pstmtSupplier.setString(1, supplierName);
            pstmtSupplier.executeUpdate();
            ResultSet rsSupplier = pstmtSupplier.getGeneratedKeys();
            int supplierId = 0;
            if (rsSupplier.next()) {
                supplierId = rsSupplier.getInt(1);
                rsSupplier.close();
                pstmtSupplier.close();
            }

            // Determine critical stock level based on product type
            int criticalStockLevel = 10; // Default
            if ("F".equals(typeId)) {
                criticalStockLevel = 15;
            }
            // Generate the barcode image
            BufferedImage barcodeImage = null;
            if (barcode.matches("\\d{12}")) {
                fullBarcode = barcode + calculateEAN13Checksum(barcode);
                barcodeImage = BarcodeGenerator.generateEAN13Barcode(fullBarcode, "barcode.png");
            } else if (barcode.matches("\\d{13}")) {
                barcodeImage = BarcodeGenerator.generateEAN13Barcode(barcode, "barcode.png");
            } else if (barcode.matches("\\d{14}")) {
                barcodeImage = BarcodeGenerator.generateEAN14Barcode(barcode, "barcode.png");
            }

            // Save barcode image to file
            String folderPath = "barcode/"; // Replace
            // with
            // your
            // folder
            // path
            String fileName = productName + "_barcode.png"; // Example: ProductName_barcode.png
            File barcodeFile = new File(folderPath + fileName);
            ImageIO.write(barcodeImage, "png", barcodeFile);

            // Convert the barcode image to a byte array and set it in the prepared
            // statement
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(barcodeImage, "png", baos);
            byte[] barcodeImageBytes = baos.toByteArray();

            // Parse the price and format it
            BigDecimal parsedPrice;
            if (price.contains(".")) {
                parsedPrice = new BigDecimal(price);
            } else {
                parsedPrice = new BigDecimal(price + ".00");
            }
            // Insert the product
            String insertProductSQL = "INSERT INTO products (product_code, barcode, product_name, product_price, product_size, category_id, supplier_id, product_type_id, barcode_image, product_status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmtProduct = conn.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS);
            pstmtProduct.setString(1, productCode);
            pstmtProduct.setString(2, fullBarcode);
            pstmtProduct.setString(3, productName);
            pstmtProduct.setBigDecimal(4, parsedPrice);
            if (size != null && !size.isEmpty()) {
                pstmtProduct.setString(5, size);
            } else {
                pstmtProduct.setNull(5, java.sql.Types.VARCHAR);
            }
            pstmtProduct.setString(6, categoryId);
            pstmtProduct.setInt(7, supplierId);
            pstmtProduct.setString(8, typeId);
            pstmtProduct.setBytes(9, barcodeImageBytes); // Set the barcode image bytes
            pstmtProduct.setString(10, "ACT");

            pstmtProduct.executeUpdate();
            ResultSet rsProduct = pstmtProduct.getGeneratedKeys();
            int productId = 0;
            if (rsProduct.next()) {
                productId = rsProduct.getInt(1);
                rsProduct.close();
                pstmtProduct.close();

                // Insert into the inventory table
                String insertInventorySQL = "INSERT INTO inventory (product_id, product_total_quantity, critical_stock_level, product_inventory_status_id) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmtInventory = conn.prepareStatement(insertInventorySQL);
                pstmtInventory.setInt(1, productId);
                pstmtInventory.setInt(2, quantity);
                pstmtInventory.setInt(3, criticalStockLevel); // Assuming a default critical stock level
                pstmtInventory.setString(4, "INS"); // Assuming a default critical stock level
                pstmtInventory.executeUpdate();
                pstmtInventory.close();

                // Insert into the product_expiration table
                String insertExpirationSQL = "INSERT INTO product_expiration (product_id, product_expiration_date, product_quantity) VALUES (?, ?, ?)";
                PreparedStatement pstmtExpiration = conn.prepareStatement(insertExpirationSQL);
                pstmtExpiration.setInt(1, productId);
                if (expirationDate != null && !expirationDate.isEmpty()) {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = inputFormat.parse(expirationDate);
                    String formattedDate = outputFormat.format(parsedDate);
                    pstmtExpiration.setDate(2, java.sql.Date.valueOf(formattedDate));
                } else {
                    pstmtExpiration.setNull(2, java.sql.Types.DATE);
                }
                pstmtExpiration.setInt(3, quantity);
                pstmtExpiration.executeUpdate();
                pstmtExpiration.close();

                conn.commit(); // Commit the transaction
            }
            conn.setAutoCommit(true);
            conn.close();
            JOptionPane.showMessageDialog(null, "Product added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            UserLogUtil.logUserAction(uniqueUserId, "Added Product: " + productCode);
        } catch (SQLException | ParseException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding product to database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private char calculateEAN13Checksum(String data) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(data.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return (char) (checksum + '0');
    }

    private void showBarcodeDialog(BufferedImage barcodeImage, String productName) {

        int imageWidth = 350;
        int imageHeight = 300;

        // Scale the barcode image to the desired size
        ImageIcon scaledIcon = new ImageIcon(
                barcodeImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH));

        // Create a dialog to display the barcode image
        JDialog dialog = new JDialog(mainFrame, "Generated Barcode for " + productName, true);
        dialog.setSize(500, 500); // Adjusted height to accommodate label and spacing
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS
        dialog.setUndecorated(true); // Remove window borders and title bar
        dialog.getContentPane().setBackground(new Color(30, 144, 255)); // Set background color for the entire dialog

        // Create a label for the title
        JLabel titleLabel = new JLabel("Generated Barcode for " + productName);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Add padding
        dialog.add(titleLabel);

        // Create a panel to hold the barcode image label and center it
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(new Color(30, 144, 255)); // Set background color for the image panel
        JLabel barcodeLabel = new JLabel(scaledIcon); // Use the scaled image icon
        imagePanel.add(barcodeLabel);
        dialog.add(imagePanel);

        // Add a button panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // Use BoxLayout with X_AXIS alignment
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add padding
        buttonPanel.setBackground(new Color(30, 144, 255)); // Set background color for the button panel
        dialog.add(buttonPanel);

        // Add space between the image and the buttons
        dialog.add(Box.createVerticalStrut(10)); // Add vertical space

        // Add a cancel button to close the dialog
        RoundedButton cancelButton = new RoundedButton("Close");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        cancelButton.addActionListener(cancelEvent -> dialog.dispose());
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Add buttons to the button panel
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the sides
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the sides

        // Set dialog shape to rounded rectangle
        dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 30, 30));

        // Add a black border line around the dialog
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        dialog.getRootPane().setBorder(border);

        // Set the dialog to be visible
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }
}
