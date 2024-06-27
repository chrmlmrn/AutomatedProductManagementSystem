package admin.product;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.*;
import database.DatabaseUtil;

public class UpdateProduct extends JPanel {
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JFrame mainFrame;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> statusComboBox;

    public UpdateProduct(JFrame mainFrame) {
        this.mainFrame = mainFrame;
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
        JPanel headerPanel = new JPanel(); // Adjust the radius as needed
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 20)); // Remove the border

        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new ProductPage(mainFrame));
            mainFrame.revalidate();
        });
        headerPanel.add(backButton);

        JLabel titleLabel = new JLabel("Update Product");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBorder(BorderFactory.createEmptyBorder()); // Remove the border
        headerPanel.add(titleLabel);

        outerPanel.add(headerPanel, BorderLayout.NORTH);

        // Product Table
        String[] columnNames = { "Product Code", "Name", "Category", "Price", "Stock Quantity", "Supplier", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make only Product Code non-editable
            }
        };
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        productTable.getTableHeader().setBackground(new Color(30, 144, 255)); // Match button color
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.setRowHeight(25);

        // Set column widths
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for the scroll pane
        outerPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBackground(Color.WHITE);

        // Load products into the table
        loadProducts();

        // Load categories into combo box
        categoryComboBox = new JComboBox<>();
        loadCategoriesIntoComboBox();

        // Load statuses into combo box
        statusComboBox = new JComboBox<>();
        loadStatusesIntoComboBox();

        // Set cell editors for Category and Status columns
        productTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(categoryComboBox));
        productTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(statusComboBox));

        // Add Cell Editor Listener to capture changes when editing stops
        productTable.getDefaultEditor(Object.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                int row = productTable.getSelectedRow();
                int column = productTable.getSelectedColumn();
                if (column == 2 || column == 6) {
                    updateProduct();
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                // Do nothing
            }
        });

        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 18));
        updateButton.setBackground(new Color(30, 144, 255)); // Match button color
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(e -> updateProduct());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(updateButton);

        outerPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        String query = "SELECT p.product_id, p.product_code, p.product_name, c.category_name, p.product_price, i.product_total_quantity, s.supplier_id, s.supplier_name, ps.product_status_name "
                +
                "FROM products p " +
                "JOIN category c ON p.category_id = c.category_id " +
                "JOIN inventory i ON p.product_id = i.product_id " +
                "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                "JOIN product_status ps ON p.product_status_id = ps.product_status_id " +
                "ORDER BY p.product_id ASC"; // Add ORDER BY clause;

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

                // Add rows to the table model
                tableModel.addRow(new Object[] { productCode, productName, categoryName, productPrice, productQuantity,
                        supplierName, productStatus });

                // Store productId and supplierId in hidden columns (not displayed)
                productTable.putClientProperty("productId-" + (tableModel.getRowCount() - 1), productId);
                productTable.putClientProperty("supplierId-" + (tableModel.getRowCount() - 1), supplierId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCategoriesIntoComboBox() {
        String query = "SELECT category_id, category_name FROM category";
        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categoryComboBox.addItem(rs.getString("category_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStatusesIntoComboBox() {
        String query = "SELECT product_status_id, product_status_name FROM product_status";
        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                statusComboBox.addItem(rs.getString("product_status_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading statuses: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to update.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int productId = (int) productTable.getClientProperty("productId-" + selectedRow);
        String updatedName = (String) tableModel.getValueAt(selectedRow, 1);
        String updatedCategory = (String) tableModel.getValueAt(selectedRow, 2);
        String updatedCategoryId = getCategoryIDByName(updatedCategory); // Get category ID by name
        BigDecimal updatedPrice = new BigDecimal(tableModel.getValueAt(selectedRow, 3).toString());
        int updatedQuantity = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
        String updatedSupplier = (String) tableModel.getValueAt(selectedRow, 5);
        String supplierId = (String) productTable.getClientProperty("supplierId-" + selectedRow);
        String updatedStatus = (String) tableModel.getValueAt(selectedRow, 6);
        String updatedStatusId = getStatusIDByName(updatedStatus); // Get status ID by name

        // Debugging prints to check values before update
        System.out.println("Updating Product ID: " + productId);
        System.out.println("Updated Name: " + updatedName);
        System.out.println("Updated Category ID: " + updatedCategoryId);
        System.out.println("Updated Price: " + updatedPrice);
        System.out.println("Updated Quantity: " + updatedQuantity);
        System.out.println("Updated Supplier: " + updatedSupplier);
        System.out.println("Supplier ID: " + supplierId);
        System.out.println("Updated Status ID: " + updatedStatusId);

        // Update supplier name
        updateSupplierName(supplierId, updatedSupplier);

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE products p " +
                                "JOIN inventory i ON p.product_id = i.product_id " +
                                "SET p.product_name = ?, p.category_id = ?, p.product_price = ?, i.product_total_quantity = ?, p.supplier_id = ?, p.product_status_id = ? "
                                +
                                "WHERE p.product_id = ?")) {

            stmt.setString(1, updatedName);
            stmt.setString(2, updatedCategoryId);
            stmt.setBigDecimal(3, updatedPrice);
            stmt.setInt(4, updatedQuantity);
            stmt.setString(5, supplierId);
            stmt.setString(6, updatedStatusId);
            stmt.setInt(7, productId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Product updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshProductRow(selectedRow); // Refresh the product row after updating
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

    private void refreshProductRow(int row) {
        int productId = (int) productTable.getClientProperty("productId-" + row);
        String query = "SELECT p.product_id, p.product_code, p.product_name, c.category_name, p.product_price, i.product_total_quantity, s.supplier_name, ps.product_status_name "
                +
                "FROM products p " +
                "JOIN category c ON p.category_id = c.category_id " +
                "JOIN inventory i ON p.product_id = i.product_id " +
                "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                "JOIN product_status ps ON p.product_status_id = ps.product_status_id " +
                "WHERE p.product_id = ?";

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