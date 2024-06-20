package src.admin.records.product;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import database.DatabaseUtil;
import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;

public class ProductRecords {
    private static DefaultTableModel tableModel;
    private static JTable productTable;
    private static JTextField searchField;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Records");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        headerPanel.add(backButton);

        JLabel titleLabel = new JLabel("Product Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(24, 26, 78));
        headerPanel.add(titleLabel);

        RoundedPanel containerPanel = new RoundedPanel(30);
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(1200, 600));
        containerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        String[] columnNames = { "Product ID", "Product Code", "Barcode", "Product Name", "Price", "Size",
                "Category ID", "Supplier ID" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Arial", Font.PLAIN, 16));
        productTable.setRowHeight(30);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        productTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(1100, 400));

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 50, 20, 50);
        containerPanel.add(scrollPane, gbc);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(30, 144, 255));
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));

        RoundedButton searchButton = new RoundedButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new Dimension(150, 40));
        searchButton.addActionListener(e -> searchProducts());

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 50, 20, 50);
        containerPanel.add(searchPanel, gbc);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.NORTHWEST;
        mainGbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(headerPanel, mainGbc);

        mainGbc.gridy = 1;
        mainGbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(containerPanel, mainGbc);

        frame.setVisible(true);

        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

        // Initialize table with data
        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void searchProducts() {
        String searchText = searchField.getText().trim();

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query;
            PreparedStatement statement;

            if (searchText.isEmpty()) {
                query = "SELECT p.product_id, p.product_code, p.barcode, p.product_name, p.product_price, p.product_size, p.category_id, p.supplier_id "
                        +
                        "FROM products p";
                statement = connection.prepareStatement(query);
            } else {
                query = "SELECT p.product_id, p.product_code, p.barcode, p.product_name, p.product_price, p.product_size, p.category_id, p.supplier_id "
                        +
                        "FROM products p " +
                        "WHERE p.product_name LIKE ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, "%" + searchText + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productCode = resultSet.getString("product_code");
                String barcode = resultSet.getString("barcode");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");
                String size = resultSet.getString("product_size");
                String categoryId = resultSet.getString("category_id");
                int supplierId = resultSet.getInt("supplier_id");
                tableModel.addRow(new Object[] { productId, productCode, barcode, productName, price, size, categoryId,
                        supplierId });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void refreshTable(Connection connection) {
        try {
            String query = "SELECT p.product_id, p.product_code, p.barcode, p.product_name, p.product_price, p.product_size, p.category_id, p.supplier_id "
                    +
                    "FROM products p";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productCode = resultSet.getString("product_code");
                String barcode = resultSet.getString("barcode");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");
                String size = resultSet.getString("product_size");
                String categoryId = resultSet.getString("category_id");
                int supplierId = resultSet.getInt("supplier_id");
                tableModel.addRow(new Object[] { productId, productCode, barcode, productName, price, size, categoryId,
                        supplierId });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
