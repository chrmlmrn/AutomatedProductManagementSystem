package admin.product;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;
import admin.records.userlogs.UserLogUtil;
import database.DatabaseUtil;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import customcomponents.RoundedTextField;

public class UpdateProduct extends JPanel {
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JFrame mainFrame;
    private String uniqueUserId;

    public UpdateProduct(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Outer panel for padding
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));
        outerPanel.setBackground(Color.WHITE);
        add(outerPanel, BorderLayout.CENTER);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 20));

        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new ProductPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
        });
        headerPanel.add(backButton);

        JLabel titleLabel = new JLabel("Update Product");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBorder(BorderFactory.createEmptyBorder());
        headerPanel.add(titleLabel);

        outerPanel.add(headerPanel, BorderLayout.NORTH);

        // Product Table
        String[] columnNames = { "Product Code", "Name", "Category", "Price", "Stock Quantity", "Supplier", "Status",
                "Expiration Date" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        productTable.getTableHeader().setBackground(new Color(30, 144, 255));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.setFont(new Font("Arial", Font.PLAIN, 16));
        productTable.setRowHeight(30);

        // Set column widths
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outerPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBackground(Color.WHITE);

        // Load products into the table
        loadProducts();

        // Bottom Panel with centered Select button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        outerPanel.add(bottomPanel, BorderLayout.SOUTH);

        JButton selectButton = new JButton("Select");
        selectButton.setFont(new Font("Arial", Font.BOLD, 18));
        selectButton.setBackground(new Color(30, 144, 255));
        selectButton.setForeground(Color.WHITE);
        selectButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to edit.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int productId = (int) productTable.getClientProperty("productId-" + selectedRow);
            String productCode = (String) tableModel.getValueAt(selectedRow, 0);
            String productName = (String) tableModel.getValueAt(selectedRow, 1);
            String categoryName = (String) tableModel.getValueAt(selectedRow, 2);
            BigDecimal productPrice = (BigDecimal) tableModel.getValueAt(selectedRow, 3);
            int productQuantity = (int) tableModel.getValueAt(selectedRow, 4);
            String supplierName = (String) tableModel.getValueAt(selectedRow, 5);
            String productStatus = (String) tableModel.getValueAt(selectedRow, 6);
            Date expirationDate = (Date) tableModel.getValueAt(selectedRow, 7);

            openEditDialog(productId, productCode, productName, categoryName, productPrice, productQuantity,
                    supplierName, productStatus, expirationDate, selectedRow);
        });

        bottomPanel.add(selectButton);
    }

    private void loadProducts() {
        String query = "SELECT p.product_id, p.product_code, p.product_name, c.category_name, p.product_price, i.product_total_quantity, s.supplier_id, s.supplier_name, ps.product_status_name, MAX(pe.product_expiration_date) AS product_expiration_date, SUM(pe.product_quantity) AS total_quantity "
                +
                "FROM products p " +
                "JOIN category c ON p.category_id = c.category_id " +
                "JOIN inventory i ON p.product_id = i.product_id " +
                "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                "JOIN product_status ps ON p.product_status_id = ps.product_status_id " +
                "LEFT JOIN product_expiration pe ON p.product_id = pe.product_id " +
                "GROUP BY p.product_id, p.product_code, p.product_name, c.category_name, p.product_price, i.product_total_quantity, s.supplier_id, s.supplier_name, ps.product_status_name "
                +
                "ORDER BY p.product_id ASC";

        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productCode = rs.getString("product_code");
                String productName = rs.getString("product_name");
                String categoryName = rs.getString("category_name");
                BigDecimal productPrice = rs.getBigDecimal("product_price");
                int productQuantity = rs.getInt("product_total_quantity");
                String supplierId = rs.getString("supplier_id");
                String supplierName = rs.getString("supplier_name");
                String productStatus = rs.getString("product_status_name");
                Date expirationDate = rs.getDate("product_expiration_date");

                // Add rows to the table model
                tableModel.addRow(new Object[] { productCode, productName, categoryName, productPrice, productQuantity,
                        supplierName, productStatus, expirationDate });

                // Store productId and supplierId in hidden properties
                productTable.putClientProperty("productId-" + (tableModel.getRowCount() - 1), productId);
                productTable.putClientProperty("supplierId-" + (tableModel.getRowCount() - 1), supplierId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEditDialog(int productId, String productCode, String productName, String categoryName,
            BigDecimal productPrice, int productQuantity, String supplierName, String productStatus,
            Date expirationDate, int row) {

        JDialog editDialog = new JDialog(mainFrame, "Edit Product", true);
        editDialog.setUndecorated(true); // Remove window borders and title bar
        editDialog.setLayout(new BorderLayout());
        editDialog.setSize(650, 700); // Adjust the size
        editDialog.setLocationRelativeTo(mainFrame);

        RoundedPanel editPanel = new RoundedPanel(30);
        editPanel.setBackground(new Color(30, 144, 255));
        editPanel.setPreferredSize(new Dimension(650, 800));
        editPanel.setLayout(new GridBagLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;

        JLabel titleLabel = new JLabel("Edit Product Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        editPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        editPanel.add(nameLabel, gbc);

        RoundedTextField nameField = new RoundedTextField(5, 20);
        nameField.setText(productName);
        nameField.setPreferredSize(new Dimension(500, 30));
        nameField.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        editPanel.add(nameField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        editPanel.add(categoryLabel, gbc);

        JComboBox<String> categoryComboBox = new JComboBox<>();
        loadCategoriesIntoComboBox(categoryComboBox);
        categoryComboBox.setSelectedItem(categoryName);
        categoryComboBox.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        editPanel.add(categoryComboBox, gbc);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        editPanel.add(priceLabel, gbc);

        RoundedTextField priceField = new RoundedTextField(5, 20);
        priceField.setText(productPrice.toString());
        priceField.setPreferredSize(new Dimension(500, 30));
        priceField.setEnabled(false);
        ((AbstractDocument) priceField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null)
                    return;
                String newString = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (isValidPrice(newString)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null)
                    return;
                String newString = fb.getDocument().getText(0, fb.getDocument().getLength()).substring(0, offset) +
                        text + fb.getDocument().getText(0, fb.getDocument().getLength()).substring(offset + length);
                if (isValidPrice(newString)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                String newString = fb.getDocument().getText(0, fb.getDocument().getLength()).substring(0, offset) +
                        fb.getDocument().getText(0, fb.getDocument().getLength()).substring(offset + length);
                if (isValidPrice(newString)) {
                    super.remove(fb, offset, length);
                }
            }

            private boolean isValidPrice(String text) {
                try {
                    if (text.isEmpty())
                        return true;
                    BigDecimal price = new BigDecimal(text);
                    return price.signum() >= 0;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        editPanel.add(priceField, gbc);

        JLabel quantityLabel = new JLabel("Quantity (Stocks):");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        quantityLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 7;
        editPanel.add(quantityLabel, gbc);

        RoundedTextField quantityField = new RoundedTextField(5, 20);
        quantityField.setText("0");
        quantityField.setPreferredSize(new Dimension(500, 30));
        quantityField.setEnabled(false);
        ((AbstractDocument) quantityField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null)
                    return;
                String newString = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (isValidQuantity(newString)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null)
                    return;
                String newString = fb.getDocument().getText(0, fb.getDocument().getLength()).substring(0, offset) +
                        text + fb.getDocument().getText(0, fb.getDocument().getLength()).substring(offset + length);
                if (isValidQuantity(newString)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                String newString = fb.getDocument().getText(0, fb.getDocument().getLength()).substring(0, offset) +
                        fb.getDocument().getText(0, fb.getDocument().getLength()).substring(offset + length);
                if (isValidQuantity(newString)) {
                    super.remove(fb, offset, length);
                }
            }

            private boolean isValidQuantity(String text) {
                try {
                    if (text.isEmpty())
                        return true;
                    int quantity = Integer.parseInt(text);
                    return quantity >= 0;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        editPanel.add(quantityField, gbc);

        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        supplierLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 9;
        editPanel.add(supplierLabel, gbc);

        RoundedTextField supplierField = new RoundedTextField(5, 20);
        supplierField.setText(supplierName);
        supplierField.setPreferredSize(new Dimension(500, 30));
        supplierField.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        editPanel.add(supplierField, gbc);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 11;
        editPanel.add(statusLabel, gbc);

        JComboBox<String> statusComboBox = new JComboBox<>();
        loadStatusesIntoComboBox(statusComboBox);
        statusComboBox.setSelectedItem(productStatus);
        statusComboBox.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        editPanel.add(statusComboBox, gbc);

        JLabel expirationDateLabel = new JLabel("Expiration Date:");
        expirationDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        expirationDateLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 13;
        editPanel.add(expirationDateLabel, gbc);

        JDateChooser expirationDateChooser = new JDateChooser();
        expirationDateChooser.setDate(expirationDate);
        expirationDateChooser.setPreferredSize(new Dimension(500, 30));
        expirationDateChooser.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        editPanel.add(expirationDateChooser, gbc);

        RoundedButton editButton = new RoundedButton("Edit");
        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);
        editButton.setPreferredSize(new Dimension(140, 40));
        editButton.addActionListener(e -> {
            nameField.setEnabled(true);
            categoryComboBox.setEnabled(true);
            priceField.setEnabled(true);
            quantityField.setEnabled(true);
            supplierField.setEnabled(true);
            statusComboBox.setEnabled(true);
            expirationDateChooser.setEnabled(true);
        });

        RoundedButton saveButton = new RoundedButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(140, 40));
        saveButton.addActionListener(e -> {
            String updatedName = nameField.getText();
            String updatedCategory = (String) categoryComboBox.getSelectedItem();
            String updatedCategoryId = getCategoryIDByName(updatedCategory);
            BigDecimal updatedPrice = new BigDecimal(priceField.getText());
            String quantityText = quantityField.getText().trim();
            int updatedQuantity = quantityText.isEmpty() ? 0 : Integer.parseInt(quantityText); // Set to 0 if empty
            String updatedSupplier = supplierField.getText();
            String supplierId = (String) productTable.getClientProperty("supplierId-" + row);
            String updatedStatus = (String) statusComboBox.getSelectedItem();
            String updatedStatusId = getStatusIDByName(updatedStatus);
            java.util.Date updatedExpirationDate = expirationDateChooser.getDate();
            java.sql.Date sqlExpirationDate = (updatedExpirationDate != null)
                    ? new java.sql.Date(updatedExpirationDate.getTime())
                    : null;

            // Display confirmation dialog
            int confirmOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this product?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmOption == JOptionPane.YES_OPTION) {
                updateProductDetails(productId, updatedName, updatedCategoryId, updatedPrice, updatedQuantity,
                        supplierId, updatedSupplier,
                        updatedStatusId, sqlExpirationDate);
                refreshProductRow(row);
                editDialog.dispose();
            }
        });

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(140, 40));
        cancelButton.addActionListener(e -> editDialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        editPanel.add(buttonPanel, gbc);

        editDialog.add(editPanel, BorderLayout.CENTER);
        editDialog.setShape(new RoundRectangle2D.Double(0, 0, editDialog.getWidth(), editDialog.getHeight(), 30, 30));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        editDialog.getRootPane().setBorder(border);
        editDialog.setVisible(true);
    }

    private void loadCategoriesIntoComboBox(JComboBox<String> comboBox) {
        String query = "SELECT category_id, category_name FROM category";
        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                comboBox.addItem(rs.getString("category_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStatusesIntoComboBox(JComboBox<String> comboBox) {
        String query = "SELECT product_status_id, product_status_name FROM product_status";
        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                comboBox.addItem(rs.getString("product_status_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading statuses: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getCategoryIDByName(String categoryName) {
        String query = "SELECT category_id FROM category WHERE category_name = ?";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("category_id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error getting category ID: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private String getStatusIDByName(String statusName) {
        String query = "SELECT product_status_id FROM product_status WHERE product_status_name = ?";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, statusName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("product_status_id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error getting status ID: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void updateProductDetails(int productId, String updatedName, String updatedCategoryId,
            BigDecimal updatedPrice, int updatedQuantity, String supplierId, String updatedSupplier,
            String updatedStatusId, java.sql.Date sqlExpirationDate) {
        updateSupplierName(supplierId, updatedSupplier);

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE products SET product_name = ?, category_id = ?, product_price = ?, supplier_id = ?, product_status_id = ? WHERE product_id = ?")) {

            stmt.setString(1, updatedName);
            stmt.setString(2, updatedCategoryId);
            stmt.setBigDecimal(3, updatedPrice);
            stmt.setString(4, supplierId);
            stmt.setString(5, updatedStatusId);
            stmt.setInt(6, productId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Update inventory quantity by adding to the current quantity
                try (PreparedStatement updateInventoryStmt = conn.prepareStatement(
                        "UPDATE inventory SET product_total_quantity = product_total_quantity + ? WHERE product_id = ?")) {
                    updateInventoryStmt.setInt(1, updatedQuantity);
                    updateInventoryStmt.setInt(2, productId);
                    updateInventoryStmt.executeUpdate();
                }

                // Insert new expiration date if changed
                if (sqlExpirationDate != null) {
                    try (PreparedStatement insertExpirationStmt = conn.prepareStatement(
                            "INSERT INTO product_expiration (product_id, product_expiration_date, product_quantity) VALUES (?, ?, ?)")) {
                        insertExpirationStmt.setInt(1, productId);
                        insertExpirationStmt.setDate(2, sqlExpirationDate);
                        insertExpirationStmt.setInt(3, updatedQuantity);
                        insertExpirationStmt.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(this, "Product updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                UserLogUtil.logUserAction(uniqueUserId, "Updated Product");

            } else {
                JOptionPane.showMessageDialog(this, "No rows updated. Please check your data.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating product: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshProductRow(int row) {
        int productId = (int) productTable.getClientProperty("productId-" + row);
        String query = "SELECT p.product_id, p.product_code, p.product_name, c.category_name, p.product_price, i.product_total_quantity, s.supplier_name, ps.product_status_name, MAX(pe.product_expiration_date) AS product_expiration_date, SUM(pe.product_quantity) AS total_quantity "
                +
                "FROM products p " +
                "JOIN category c ON p.category_id = c.category_id " +
                "JOIN inventory i ON p.product_id = i.product_id " +
                "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                "JOIN product_status ps ON p.product_status_id = ps.product_status_id " +
                "LEFT JOIN product_expiration pe ON p.product_id = pe.product_id " +
                "WHERE p.product_id = ? " +
                "GROUP BY p.product_id, p.product_code, p.product_name, c.category_name, p.product_price, i.product_total_quantity, s.supplier_name, ps.product_status_name";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tableModel.setValueAt(rs.getString("product_code"), row, 0);
                    tableModel.setValueAt(rs.getString("product_name"), row, 1);
                    tableModel.setValueAt(rs.getString("category_name"), row, 2);
                    tableModel.setValueAt(rs.getBigDecimal("product_price"), row, 3);
                    tableModel.setValueAt(rs.getInt("product_total_quantity"), row, 4);
                    tableModel.setValueAt(rs.getString("supplier_name"), row, 5);
                    tableModel.setValueAt(rs.getString("product_status_name"), row, 6);
                    tableModel.setValueAt(rs.getDate("product_expiration_date"), row, 7);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error refreshing product row: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSupplierName(String supplierId, String newSupplierName) {
        String updateQuery = "UPDATE supplier SET supplier_name = ? WHERE supplier_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newSupplierName);
            stmt.setString(2, supplierId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Supplier name updated successfully.");
            } else {
                System.out.println("No rows updated. Supplier ID may not exist.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating supplier name: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
