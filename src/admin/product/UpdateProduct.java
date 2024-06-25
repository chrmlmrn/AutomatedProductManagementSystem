package admin.product;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import database.DatabaseUtil;

public class UpdateProduct extends JPanel {
    private DefaultTableModel tableModel;
    private JTable productTable;
    private String[] categories;
    private String[] suppliers;
    private JFrame mainFrame;

    public UpdateProduct(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        addHeaderPanel(this);
        addContainerPanel(this);

        loadCategories(); // Load categories from the database
        loadSuppliers(); // Load suppliers from the database
        loadProducts();
    }

    private void addHeaderPanel(JPanel mainPanel) {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            // Add your logic here to go back to the previous menu if needed
        });
        headerPanel.add(backButton);

        JLabel titleLabel = new JLabel("Product Maintenance");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(24, 26, 78));
        headerPanel.add(titleLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void addContainerPanel(JPanel mainPanel) {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(new Color(30, 144, 255));

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 3:
                        return BigDecimal.class;
                    case 4:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");
        tableModel.addColumn("Stock Quantity");
        tableModel.addColumn("Supplier");
        tableModel.addColumn("Size");

        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Arial", Font.PLAIN, 16));
        productTable.setRowHeight(40);

        // Set category and supplier columns to use JComboBox
        TableColumn categoryColumn = productTable.getColumnModel().getColumn(2);
        categoryColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(categories)));

        TableColumn supplierColumn = productTable.getColumnModel().getColumn(5);
        supplierColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(suppliers)));

        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setBackground(new Color(0, 123, 255));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 40));

        JScrollPane scrollPane = new JScrollPane(productTable);
        containerPanel.add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 18));
        updateButton.addActionListener(e -> updateProduct());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(containerPanel, BorderLayout.CENTER);
    }

    private void loadProducts() {
        String query = "SELECT p.product_id, p.product_name, c.category_name, p.product_price, i.product_total_quantity, s.supplier_name, p.product_size "
                +
                "FROM products p " +
                "JOIN category c ON p.category_id = c.category_id " +
                "JOIN inventory i ON p.product_id = i.product_id " +
                "JOIN supplier s ON p.supplier_id = s.supplier_id";

        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String categoryName = rs.getString("category_name");
                BigDecimal productPrice = rs.getBigDecimal("product_price");
                int productQuantity = rs.getInt("product_total_quantity");
                String supplierName = rs.getString("supplier_name");
                String productSize = rs.getString("product_size");

                tableModel.addRow(new Object[] {
                        productId,
                        productName,
                        categoryName,
                        productPrice,
                        productQuantity,
                        supplierName,
                        productSize
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int productId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String updatedName = tableModel.getValueAt(selectedRow, 1).toString();
        String updatedCategory = tableModel.getValueAt(selectedRow, 2).toString();
        BigDecimal updatedPrice = new BigDecimal(tableModel.getValueAt(selectedRow, 3).toString());
        int updatedQuantity = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
        String updatedSupplier = tableModel.getValueAt(selectedRow, 5).toString();
        String updatedSize = tableModel.getValueAt(selectedRow, 6).toString();

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE products p " +
                                "JOIN category c ON p.category_id = c.category_id " +
                                "JOIN inventory i ON p.product_id = i.product_id " +
                                "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                                "SET p.product_name = ?, p.category_id = (SELECT category_id FROM category WHERE category_name = ? LIMIT 1), "
                                +
                                "p.product_price = ?, i.product_total_quantity = ?, p.supplier_id = (SELECT supplier_id FROM supplier WHERE supplier_name = ? LIMIT 1), "
                                +
                                "p.product_size = ? " +
                                "WHERE p.product_id = ?")) {

            stmt.setString(1, updatedName);
            stmt.setString(2, updatedCategory);
            stmt.setBigDecimal(3, updatedPrice);
            stmt.setInt(4, updatedQuantity);
            stmt.setString(5, updatedSupplier);
            stmt.setString(6, updatedSize);
            stmt.setInt(7, productId);
            stmt.executeUpdate();

            loadProducts();
            JOptionPane.showMessageDialog(this, "Product updated successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating product: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCategories() {
        String query = "SELECT category_name FROM category";
        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            java.util.List<String> categoryList = new java.util.ArrayList<>();
            while (rs.next()) {
                categoryList.add(rs.getString("category_name"));
            }
            categories = categoryList.toArray(new String[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            categories = new String[0];
        }
    }

    private void loadSuppliers() {
        String query = "SELECT supplier_name FROM supplier";
        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            java.util.List<String> supplierList = new java.util.ArrayList<>();
            while (rs.next()) {
                supplierList.add(rs.getString("supplier_name"));
            }
            suppliers = supplierList.toArray(new String[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            suppliers = new String[0];
        }
    }
}
