package admin.product;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

import admin.AdminMenu;
import customcomponents.RoundedPanel;
import database.DatabaseUtil;

public class UpdateProduct extends JPanel {
    private DefaultTableModel tableModel;
    private JTable productTable;
    private String[] categories;
    private String[] suppliers;

    private RoundedPanel containerPanel;
    private JTextField searchField;
    private JTextField[] currentTextFields;
    private JTextField[] updateTextFields;
    private JFrame mainFrame;

    public UpdateProduct(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new ProductPage(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        headerPanel.add(backButton);

        JLabel titleLabel = new JLabel("Product Maintenance");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(24, 26, 78));
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Container Panel
        containerPanel = new RoundedPanel(30);
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        searchField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerPanel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        containerPanel.add(searchField, gbc);

        // Create text fields for current and updated values
        currentTextFields = new JTextField[6];
        updateTextFields = new JTextField[6];
        String[] labels = { "Product ID", "Name", "Category", "Price", "Stock Quantity", "Supplier" };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            containerPanel.add(new JLabel(labels[i]), gbc);

            currentTextFields[i] = new JTextField(20);
            updateTextFields[i] = new JTextField(20);

            gbc.gridx = 1;
            containerPanel.add(currentTextFields[i], gbc);
            gbc.gridx = 2;
            containerPanel.add(updateTextFields[i], gbc);
        }

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 18));
        updateButton.addActionListener(e -> updateProduct());

        gbc.gridx = 1;
        gbc.gridy = labels.length + 1;
        containerPanel.add(updateButton, gbc);

        add(containerPanel, BorderLayout.CENTER);

        // Load products into the table
        loadProducts();

        setVisible(true);
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

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String categoryName = rs.getString("category_name");
                BigDecimal productPrice = rs.getBigDecimal("product_price");
                int productQuantity = rs.getInt("product_total_quantity");
                String supplierName = rs.getString("supplier_name");
                String productSize = rs.getString("product_size");

                // Populate text fields with product details
                currentTextFields[0].setText(String.valueOf(productId));
                currentTextFields[1].setText(productName);
                currentTextFields[2].setText(categoryName);
                currentTextFields[3].setText(productPrice.toString());
                currentTextFields[4].setText(String.valueOf(productQuantity));
                currentTextFields[5].setText(supplierName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        int productId = Integer.parseInt(currentTextFields[0].getText());
        String updatedName = updateTextFields[1].getText();
        String updatedCategory = updateTextFields[2].getText();
        BigDecimal updatedPrice = new BigDecimal(updateTextFields[3].getText());
        int updatedQuantity = Integer.parseInt(updateTextFields[4].getText());
        String updatedSupplier = updateTextFields[5].getText();

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
            stmt.setString(6, updateTextFields[5].getText());
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
