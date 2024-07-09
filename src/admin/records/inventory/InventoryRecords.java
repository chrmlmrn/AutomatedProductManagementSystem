package admin.records.inventory;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.records.RecordsMainPage;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import database.DatabaseUtil;

import java.util.Timer;
import java.util.TimerTask;

public class InventoryRecords extends JPanel {
    private DefaultTableModel tableModel;
    private JTable inventoryTable;
    private JTextField searchField;

    private JFrame mainFrame;
    private String uniqueUserId;
    private Timer timer;

    public InventoryRecords(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
        setVisible(true);

        // Initialize table with data
        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        startAutoRefresh();
    }

    private void initComponents() {
        setLayout(null); // Use absolute positioning
        setBackground(Color.WHITE);

        // Title and Back Button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new RecordsMainPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
            stopAutoRefresh(); // Stop auto-refresh when navigating away
        });

        JLabel titleLabel = new JLabel("Inventory Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(100, 30, 500, 30);
        add(titleLabel);

        // Rounded Blue Panel
        RoundedPanel bluePanel = new RoundedPanel(30);
        bluePanel.setBackground(new Color(30, 144, 255));
        bluePanel.setBounds(100, 120, 1200, 600);
        bluePanel.setLayout(null); // Use absolute positioning within the panel
        add(bluePanel);

        // Table Setup
        String[] columnNames = { "Product Code", "Product Name", "Supplier Name", "Product Type",
                "Critical Stock Level", "Stock Quantity", "Product Status", "Last Updated" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        inventoryTable = new JTable(tableModel);
        inventoryTable.setFont(new Font("Arial", Font.PLAIN, 16));
        inventoryTable.setRowHeight(30);
        inventoryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        inventoryTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBounds(50, 50, 1100, 400);
        bluePanel.add(scrollPane);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(30, 144, 255));
        searchPanel.setLayout(null);
        searchPanel.setBounds(350, 470, 600, 60); // Center the search panel horizontally
        bluePanel.add(searchPanel);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBounds(50, 10, 300, 40); // Center the search field within the search panel
        searchPanel.add(searchField);

        RoundedButton searchButton = new RoundedButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.setBounds(370, 10, 150, 40); // Adjust the position of the search button within the search panel
        searchButton.addActionListener(e -> searchInventory());
        searchPanel.add(searchButton);

        // Initialize table with data
        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchInventory() {
        stopAutoRefresh(); // Stop auto-refresh when navigating away
        String searchText = searchField.getText().trim();

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query;
            PreparedStatement statement;

            if (searchText.isEmpty()) {
                query = "SELECT p.product_code, p.product_name, s.supplier_name, pt.product_type_name AS product_type, i.critical_stock_level, i.product_total_quantity AS stock_quantity, ps.product_inventory_status_name AS product_status, i.last_updated "
                        +
                        "FROM inventory i " +
                        "JOIN products p ON i.product_id = p.product_id " +
                        "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                        "JOIN product_type pt ON p.product_type_id = pt.product_type_id " +
                        "JOIN product_inventory_status ps ON i.product_inventory_status_id = ps.product_inventory_status_id "
                        +
                        "ORDER BY p.product_code DESC";
                statement = connection.prepareStatement(query);
            } else {
                query = "SELECT p.product_code, p.product_name, s.supplier_name, pt.product_type_name AS product_type, i.critical_stock_level, i.product_total_quantity AS stock_quantity, ps.product_inventory_status_name AS product_status, i.last_updated "
                        +
                        "FROM inventory i " +
                        "JOIN products p ON i.product_id = p.product_id " +
                        "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                        "JOIN product_type pt ON p.product_type_id = pt.product_type_id " +
                        "JOIN product_inventory_status ps ON i.product_inventory_status_id = ps.product_inventory_status_id "
                        +
                        "WHERE p.product_name LIKE ? OR p.product_code LIKE ? OR s.supplier_name LIKE ? " +
                        "ORDER BY p.product_code DESC";
                statement = connection.prepareStatement(query);
                statement.setString(1, "%" + searchText + "%");
                statement.setString(2, "%" + searchText + "%");
                statement.setString(3, "%" + searchText + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");
                String productName = resultSet.getString("product_name");
                String supplierName = resultSet.getString("supplier_name");
                String productType = resultSet.getString("product_type");
                int criticalStockLevel = resultSet.getInt("critical_stock_level");
                int stockQuantity = resultSet.getInt("stock_quantity");
                String productStatus = determineProductStatus(stockQuantity, criticalStockLevel);
                Timestamp lastUpdated = resultSet.getTimestamp("last_updated");
                tableModel.addRow(new Object[] { productCode, productName, supplierName, productType,
                        criticalStockLevel, stockQuantity, productStatus, lastUpdated });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Connection connection) {
        try {
            String query = "SELECT p.product_code, p.product_name, s.supplier_name, pt.product_type_name AS product_type, i.critical_stock_level, i.product_total_quantity AS stock_quantity, ps.product_inventory_status_name AS product_status, i.last_updated "
                    +
                    "FROM inventory i " +
                    "JOIN products p ON i.product_id = p.product_id " +
                    "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                    "JOIN product_type pt ON p.product_type_id = pt.product_type_id " +
                    "JOIN product_inventory_status ps ON i.product_inventory_status_id = ps.product_inventory_status_id "
                    +
                    "ORDER BY p.product_code DESC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");
                String productName = resultSet.getString("product_name");
                String supplierName = resultSet.getString("supplier_name");
                String productType = resultSet.getString("product_type");
                int criticalStockLevel = resultSet.getInt("critical_stock_level");
                int stockQuantity = resultSet.getInt("stock_quantity");
                String productStatus = determineProductStatus(stockQuantity, criticalStockLevel);
                Timestamp lastUpdated = resultSet.getTimestamp("last_updated");
                tableModel.addRow(new Object[] { productCode, productName, supplierName, productType,
                        criticalStockLevel, stockQuantity, productStatus, lastUpdated });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String determineProductStatus(int stockQuantity, int criticalStockLevel) {
        if (stockQuantity == 0) {
            return "Out of Stock";
        } else if (stockQuantity <= criticalStockLevel) {
            return "Re-ordering";
        } else {
            return "In Stock";
        }
    }

    private void startAutoRefresh() {
        timer = new Timer(true); // Run timer as a daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    try (Connection connection = DatabaseUtil.getConnection()) {
                        refreshTable(connection);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        }, 0, 5000); // Refresh every 5 seconds
    }

    private void stopAutoRefresh() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
