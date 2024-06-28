package admin.reports.sales;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import admin.reports.ReportsPage;
import database.DatabaseUtil;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.MessageFormat;
import java.awt.print.PrinterException;

public class SalesReport extends JPanel {
    private DefaultTableModel tableModel;
    private JTable salesTable;
    private JFrame mainFrame;

    public SalesReport(JFrame mainFrame) {
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

        // Back Button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new ReportsPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Title Label
        JLabel titleLabel = new JLabel("Sales Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(100, 30, 500, 30);
        add(titleLabel);

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
        salesTable.getTableHeader().setBackground(new Color(30, 144, 255));
        salesTable.getTableHeader().setForeground(Color.WHITE);
        salesTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBounds(50, 80, 1300, 500);
        add(scrollPane);

        // Print Button
        JButton printButton = new JButton("Print");
        printButton.setBounds(1250, 30, 100, 30); // Adjusted position to top-right corner
        printButton.setBackground(new Color(30, 144, 255));
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.setFont(new Font("Arial", Font.BOLD, 16));
        printButton.setBorder(BorderFactory.createEmptyBorder());
        printButton.addActionListener(e -> {
            try {
                salesTable.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat("Sales Report"), null);
            } catch (PrinterException pe) {
                pe.printStackTrace();
            }
        });
        add(printButton);

        // Initialize table with data
        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Connection connection) {
        try {
            String query = "SELECT transaction_id, receipt_number, reference_number, date, subtotal, discount, vat, total "
                    + "FROM transactions";
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
