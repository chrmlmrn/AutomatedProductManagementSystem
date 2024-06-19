package src.admin.product;

import com.toedter.calendar.JDateChooser;

import database.DatabaseUtil;
import src.admin.product.barcode.BarcodeGenerator;
import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;
import src.customcomponents.RoundedTextField;

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

public class AddProduct {
    private static JFrame frame;
    private static Map<String, String> categoryMap;
    private static Map<String, String> productTypeMap;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private static String fullBarcode;

    public static void main(String[] args) {
        // Create the frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setUndecorated(false); // Remove window borders and title bar

        // Create a main panel for centering the blue container
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

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
        JLabel barcodeLabel = new JLabel("Product Barcode");
        barcodeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        barcodeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        containerPanel.add(barcodeLabel, gbc);

        RoundedTextField barcodeTextField = new RoundedTextField(5, 20);
        barcodeTextField.setPreferredSize(new Dimension(500, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(barcodeTextField, gbc);

        JLabel productCodeLabel = new JLabel("Product Code");
        productCodeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        productCodeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        containerPanel.add(productCodeLabel, gbc);

        RoundedTextField productCodeTextField = new RoundedTextField(5, 20);
        productCodeTextField.setPreferredSize(new Dimension(500, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        containerPanel.add(productCodeTextField, gbc);

        JLabel productNameLabel = new JLabel("Product Name");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        productNameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        containerPanel.add(productNameLabel, gbc);

        RoundedTextField productNameTextField = new RoundedTextField(5, 20);
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
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

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

        RoundedTextField productPriceTextField = new RoundedTextField(5, 20);
        productPriceTextField.setPreferredSize(new Dimension(500, 30));
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

        RoundedTextField productSizeTextField = new RoundedTextField(5, 20);
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

        RoundedTextField productQuantityTextField = new RoundedTextField(5, 20);
        productQuantityTextField.setPreferredSize(new Dimension(500, 30));
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

        JDateChooser expirationDateChooser = new JDateChooser();
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
        JComboBox<String> typeComboBox = new JComboBox<>(types);

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

        RoundedTextField supplierNameTextField = new RoundedTextField(5, 20);
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
            if (validateFields(barcodeTextField, productCodeTextField, productNameTextField, productPriceTextField,
                    productSizeTextField, productQuantityTextField, expirationDateChooser, supplierNameTextField)) {
                insertProduct(barcodeTextField.getText(), productCodeTextField.getText(),
                        productNameTextField.getText(), (String) categoryComboBox.getSelectedItem(),
                        new BigDecimal(productPriceTextField.getText()), productSizeTextField.getText(),
                        Integer.parseInt(productQuantityTextField.getText()),
                        ((JTextField) expirationDateChooser.getDateEditor().getUiComponent()).getText(),
                        (String) typeComboBox.getSelectedItem(), supplierNameTextField.getText());
            }
        });

        // Add a button to generate the barcode
        RoundedButton generateButton = new RoundedButton("Generate Barcode");
        generateButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateButton.setBackground(Color.WHITE);
        generateButton.setForeground(Color.BLACK);
        generateButton.setFocusPainted(false);
        generateButton.setPreferredSize(new Dimension(300, 40));
        generateButton.addActionListener(e -> {
            // Validate all fields before generating barcode
            if (validateFields(barcodeTextField, productCodeTextField, productNameTextField,
                    productPriceTextField, productSizeTextField, productQuantityTextField,
                    expirationDateChooser, supplierNameTextField)) {
                // Calculate fullBarcode before generating the barcode image
                String barcode = barcodeTextField.getText();
                if (!barcode.isEmpty() && barcode.matches("\\d{12}")) {
                    String fullBarcode = barcode + calculateEAN13Checksum(barcode);
                    BufferedImage barcodeImage = BarcodeGenerator.generateEAN13Barcode(fullBarcode, "barcode.png");
                    String productName = productNameTextField.getText().trim();

                    // Show dialog with generated barcode image and product name
                    showBarcodeDialog(barcodeImage, productName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid 12-digit barcode.", "Error",
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
            System.out.println("Cancel button clicked");
            frame.dispose(); // Close the current frame
            // Open ProductPage frame
            // ProductPage.main(new String[] {});
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        buttonPanel.setBackground(new Color(30, 144, 255));
        gbcButton.insets = new Insets(10, 10, 10, 10);
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        buttonPanel.add(addButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(generateButton, gbcButton);

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

        // Display the frame
        frame.pack();
        frame.setVisible(true);
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
    private static boolean validateFields(JTextField barcodeTextField, JTextField productCodeTextField,
            JTextField productNameTextField, JTextField productPriceTextField,
            JTextField productSizeTextField, JTextField productQuantityTextField,
            JDateChooser expirationDateChooser, JTextField supplierNameTextField) {
        // Add your validation logic here
        // Example validation: checking if fields are empty
        if (barcodeTextField.getText().isEmpty() || productCodeTextField.getText().isEmpty() ||
                productNameTextField.getText().isEmpty() || productPriceTextField.getText().isEmpty() ||
                productSizeTextField.getText().isEmpty() || productQuantityTextField.getText().isEmpty() ||
                supplierNameTextField.getText().isEmpty() || expirationDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Ensure barcode is 12 digits long
        if (barcodeTextField.getText().length() != 12 || !barcodeTextField.getText().matches("\\d{12}")) {
            JOptionPane.showMessageDialog(null, "Barcode must be exactly 12 digits.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate price, quantity, etc., if necessary
        try {
            new BigDecimal(productPriceTextField.getText());
            Integer.parseInt(productQuantityTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numerical values for price and quantity.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private static void insertProduct(String barcode, String productCode, String productName, String category,
            BigDecimal price, String size, int quantity, String expirationDate, String type, String supplierName) {
        // Calculate the EAN 13 checksum
        String fullBarcode = barcode + calculateEAN13Checksum(barcode);

        // Get the category_id and product_type_id
        String categoryId = categoryMap.get(category);
        String typeId = productTypeMap.get(type);

        try {
            Connection conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // Enable transaction

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

            // Generate the barcode image
            BufferedImage barcodeImage = BarcodeGenerator.generateEAN13Barcode(fullBarcode, "barcode.png");

            // Save barcode image to file
            String fileName = productName + "_barcode.png"; // Example: ProductName_barcode.png
            File barcodeFile = new File(fileName);
            ImageIO.write(barcodeImage, "png", barcodeFile);

            // Convert the barcode image to a byte array and set it in the prepared
            // statement
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(barcodeImage, "png", baos);
            byte[] barcodeImageBytes = baos.toByteArray();

            // Insert the product
            String insertProductSQL = "INSERT INTO products (product_code, barcode, product_name, product_price, product_size, category_id, supplier_id, product_type_id, barcode_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmtProduct = conn.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS);
            pstmtProduct.setString(1, productCode);
            pstmtProduct.setString(2, fullBarcode);
            pstmtProduct.setString(3, productName);
            pstmtProduct.setBigDecimal(4, price);
            pstmtProduct.setString(5, size);
            pstmtProduct.setString(6, categoryId);
            pstmtProduct.setInt(7, supplierId);
            pstmtProduct.setString(8, typeId);
            pstmtProduct.setBytes(9, barcodeImageBytes); // Set the barcode image bytes

            pstmtProduct.executeUpdate();
            ResultSet rsProduct = pstmtProduct.getGeneratedKeys();
            int productId = 0;
            if (rsProduct.next()) {
                productId = rsProduct.getInt(1);
                rsProduct.close();
                pstmtProduct.close();

                // Insert into the inventory table
                String insertInventorySQL = "INSERT INTO inventory (product_id, product_total_quantity, critical_stock_level, product_status_id) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmtInventory = conn.prepareStatement(insertInventorySQL);
                pstmtInventory.setInt(1, productId);
                pstmtInventory.setInt(2, quantity);
                pstmtInventory.setInt(3, 10); // Assuming a default critical stock level
                pstmtInventory.setString(4, "ACT"); // Assuming default status as 'In Stock'
                pstmtInventory.executeUpdate();
                pstmtInventory.close();

                // Parse and format expiration date
                SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = inputFormat.parse(expirationDate);
                String formattedDate = outputFormat.format(parsedDate);

                // Insert into the product_expiration table
                String insertExpirationSQL = "INSERT INTO product_expiration (product_id, product_expiration_date, product_quantity) VALUES (?, ?, ?)";
                PreparedStatement pstmtExpiration = conn.prepareStatement(insertExpirationSQL);
                pstmtExpiration.setInt(1, productId);
                pstmtExpiration.setDate(2, java.sql.Date.valueOf(formattedDate)); // Use java.sql.Date
                pstmtExpiration.setInt(3, quantity);
                pstmtExpiration.executeUpdate();
                pstmtExpiration.close();

                conn.commit(); // Commit the transaction
            }
            conn.setAutoCommit(true);
            conn.close();
            JOptionPane.showMessageDialog(null, "Product added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | ParseException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding product to database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static char calculateEAN13Checksum(String data) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(data.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return (char) (checksum + '0');
    }

    private static void showBarcodeDialog(BufferedImage barcodeImage, String productName) {
        // Desired width and height for the image
        int imageWidth = 350;
        int imageHeight = 300;

        // Scale the barcode image to the desired size
        ImageIcon scaledIcon = new ImageIcon(
                barcodeImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH));

        // Create a dialog to display the barcode image
        JDialog dialog = new JDialog(frame, "Generated Barcode for " + productName, true);
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

        // Add a button to save the barcode
        RoundedButton saveButton = new RoundedButton("Save Barcode");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        saveButton.setBackground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.addActionListener(saveEvent -> {
            // Define the folder where the barcode image will be saved
            File saveFolder = new File("C:\\\\Users\\\\ADMIN\\\\Documents"); // Replace with your folder path

            // Check if the folder exists, create it if it doesn't
            if (!saveFolder.exists()) {
                saveFolder.mkdirs(); // Creates parent directories if necessary
            }

            // Generate the file name using the product name
            String fileName = productName.replaceAll("\\s+", "_") + ".png"; // Replace spaces with underscores
            File outputFile = new File(saveFolder, fileName);

            try {
                ImageIO.write(barcodeImage, "png", outputFile);
                JOptionPane.showMessageDialog(dialog, "Barcode saved successfully.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error saving barcode: " + ex.getMessage());
            }
        });

        // Add a cancel button to close the dialog
        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        cancelButton.addActionListener(cancelEvent -> dialog.dispose());
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Add buttons to the button panel
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the sides
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Add horizontal space between buttons
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the sides

        // Set dialog shape to rounded rectangle
        dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 30, 30));

        // Add a black border line around the dialog
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        dialog.getRootPane().setBorder(border);

        // Set the dialog to be visible
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

}