package admin.records.return_records;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.records.RecordsMainPage;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import database.DatabaseUtil;

import java.util.Timer;
import java.util.TimerTask;

public class ReturnRecords extends JPanel {
    private DefaultTableModel tableModel;
    private JTable returnTable;
    private JTextField searchField;

    private JFrame mainFrame;
    private String uniqueUserId;
    private Timer timer;

    public ReturnRecords(JFrame mainFrame, String uniqueUserId) {
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

        JLabel titleLabel = new JLabel("Return Records");
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
        String[] columnNames = { "Product Name", "Return Quantity", "Return Reason", "Return Date" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        returnTable = new JTable(tableModel);
        returnTable.setFont(new Font("Arial", Font.PLAIN, 16));
        returnTable.setRowHeight(30);
        returnTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        returnTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(returnTable);
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
        searchButton.addActionListener(e -> searchReturns());
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

    private void searchReturns() {
        stopAutoRefresh(); // Stop auto-refresh when navigating away

        String searchText = searchField.getText().trim();

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query;
            PreparedStatement statement;

            if (searchText.isEmpty()) {
                query = "SELECT p.product_name, r.return_quantity, rr.return_reason_name AS return_reason, r.return_date "
                        +
                        "FROM return_products r " +
                        "JOIN products p ON r.product_id = p.product_id " +
                        "JOIN return_reason rr ON r.return_reason_id = rr.return_reason_id";
                statement = connection.prepareStatement(query);
            } else {
                query = "SELECT p.product_name, r.return_quantity, rr.return_reason_name AS return_reason, r.return_date "
                        +
                        "FROM return_products r " +
                        "JOIN products p ON r.product_id = p.product_id " +
                        "JOIN return_reason rr ON r.return_reason_id = rr.return_reason_id " +
                        "WHERE p.product_name LIKE ? OR rr.return_reason_name LIKE ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, "%" + searchText + "%");
                statement.setString(2, "%" + searchText + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                int returnQuantity = resultSet.getInt("return_quantity");
                String returnReason = resultSet.getString("return_reason");
                Date returnDate = resultSet.getDate("return_date");
                tableModel.addRow(new Object[] { productName, returnQuantity, returnReason, returnDate });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Connection connection) {
        try {
            String query = "SELECT p.product_name, r.return_quantity, rr.return_reason_name AS return_reason, r.return_date "
                    +
                    "FROM return_products r " +
                    "JOIN products p ON r.product_id = p.product_id " +
                    "JOIN return_reason rr ON r.return_reason_id = rr.return_reason_id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                int returnQuantity = resultSet.getInt("return_quantity");
                String returnReason = resultSet.getString("return_reason");
                Date returnDate = resultSet.getDate("return_date");
                tableModel.addRow(new Object[] { productName, returnQuantity, returnReason, returnDate });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
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
