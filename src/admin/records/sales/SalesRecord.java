package admin.records.sales;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.records.RecordsMainPage;
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
            refreshTable(connection);
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
        bluePanel.setBounds(200, 100, 1200, 600);
        bluePanel.setLayout(null); // Use absolute positioning within the panel
        add(bluePanel);

        // Table Setup
        String[] columnNames = { "Transaction ID", "Receipt Number", "Reference Number", "Date", "Subtotal", "Discount",
                "VAT", "Total" };
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
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchPanel.setBounds(150, 470, 900, 60); // Center the search panel horizontally
        bluePanel.add(searchPanel);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(300, 40)); // Center the search field within the search panel
        searchPanel.add(searchField);

        RoundedButton searchButton = new RoundedButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new Dimension(150, 40)); // Adjust the position of the search button within the
                                                               // search panel
        searchButton.addActionListener(e -> searchSales());
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

    private void searchSales() {
        String searchText = searchField.getText().trim();

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query;
            PreparedStatement statement;

            if (searchText.isEmpty()) {
                query = "SELECT transaction_id, receipt_number, reference_number, date, subtotal, discount, vat, total "
                        +
                        "FROM transactions";
                statement = connection.prepareStatement(query);
            } else {
                query = "SELECT transaction_id, receipt_number, reference_number, date, subtotal, discount, vat, total "
                        +
                        "FROM transactions " +
                        "WHERE date = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, searchText);
            }

            ResultSet resultSet = statement.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                String receiptNumber = resultSet.getString("receipt_number");
                String referenceNumber = resultSet.getString("reference_number");
                Date saleDate = resultSet.getDate("date");
                double subtotal = resultSet.getDouble("subtotal");
                double discount = resultSet.getDouble("discount");
                double vat = resultSet.getDouble("vat");
                double total = resultSet.getDouble("total");
                tableModel.addRow(
                        new Object[] { transactionId, receiptNumber, referenceNumber, saleDate, subtotal, discount, vat,
                                total });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Connection connection) {
        try {
            String query = "SELECT transaction_id, receipt_number, reference_number, date, subtotal, discount, vat, total "
                    +
                    "FROM transactions";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                String receiptNumber = resultSet.getString("receipt_number");
                String referenceNumber = resultSet.getString("reference_number");
                Date saleDate = resultSet.getDate("date");
                double subtotal = resultSet.getDouble("subtotal");
                double discount = resultSet.getDouble("discount");
                double vat = resultSet.getDouble("vat");
                double total = resultSet.getDouble("total");
                tableModel.addRow(
                        new Object[] { transactionId, receiptNumber, referenceNumber, saleDate, subtotal, discount, vat,
                                total });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
