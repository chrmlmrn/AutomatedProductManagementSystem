package admin.records.sales;

import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.records.RecordsMainPage;
import admin.reports.ReportsPage;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import database.DatabaseUtil;

public class SalesRecord extends JPanel {
    private DefaultTableModel tableModel;
    private JTable salesTable;
    private JTextField searchField;
    private JFrame mainFrame;

    public SalesRecord(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        setVisible(true);

        // Initialize table with data
        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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
            mainFrame.setContentPane(new RecordsMainPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JLabel titleLabel = new JLabel("Sales Records");
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
        String[] columnNames = { "Date", "Hours Open", "Hours Closed", "Products Sold", "Tax", "Discount",
                "Total Sales" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        salesTable = new JTable(tableModel);
        salesTable.setFont(new Font("Arial", Font.PLAIN, 16));
        salesTable.setRowHeight(30);
        salesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        salesTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(salesTable);
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
        searchButton.addActionListener(e -> searchSalesByDate());
        searchPanel.add(searchButton);
    }

    private void searchSalesByDate() {
        String searchText = searchField.getText().trim();

        if (!searchText.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date parsedDate = dateFormat.parse(searchText);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                try (Connection connection = DatabaseUtil.getConnection()) {
                    refreshTable(connection, sqlDate);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try (Connection connection = DatabaseUtil.getConnection()) {
                refreshTable(connection, null);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshTable(Connection connection, java.sql.Date dateFilter) {
        try {
            String query = "SELECT t.date AS sale_date, "
                    + "SUM(t.subtotal) AS total_sales, "
                    + "SUM(t.vat) AS tax, "
                    + "SUM(COALESCE((SELECT SUM(r.return_quantity * p.product_price) "
                    + "               FROM return_products r "
                    + "               JOIN products p ON r.product_id = p.product_id "
                    + "               WHERE DATE(r.return_date) = t.date), 0)) AS return_refund, "
                    + "COUNT(*) AS products_sold "
                    + "FROM transactions t ";

            if (dateFilter != null) {
                query += "WHERE t.date = ? ";
            }

            query += "GROUP BY t.date";

            PreparedStatement statement = connection.prepareStatement(query);

            if (dateFilter != null) {
                statement.setDate(1, dateFilter);
            }

            ResultSet resultSet = statement.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                Date saleDate = resultSet.getDate("sale_date");
                int productsSold = resultSet.getInt("products_sold");
                double tax = resultSet.getDouble("tax");
                double returnRefund = resultSet.getDouble("return_refund");
                double totalSales = resultSet.getDouble("total_sales");

                // Assuming fixed hours open/closed for simplicity
                tableModel.addRow(new Object[] { saleDate, 8, 16, productsSold, tax, returnRefund, totalSales });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
