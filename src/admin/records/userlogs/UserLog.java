package admin.records.userlogs;

import database.DatabaseUtil;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.records.RecordsMainPage;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserLog extends JPanel {
    private DefaultTableModel tableModel;
    private JTable logTable;

    private JFrame mainFrame;
    private String uniqueUserId;

    public UserLog(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
        setVisible(true);

        // Initialize table with data
        fetchData();
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
        });

        JLabel titleLabel = new JLabel("User Logs");
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
        String[] columnNames = { "Unique User ID", "Username", "Action", "Timestamp" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        logTable = new JTable(tableModel);
        logTable.setFont(new Font("Arial", Font.PLAIN, 16));
        logTable.setRowHeight(30);
        logTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        logTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(logTable);
        scrollPane.setBounds(50, 50, 1100, 400);
        bluePanel.add(scrollPane);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(450, 470, 300, 60); // Center the button panel horizontally
        bluePanel.add(buttonPanel);

        RoundedButton refreshButton = new RoundedButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFocusPainted(false);
        refreshButton.setBounds(20, 10, 120, 40); // Adjust the position of the refresh button within the button panel
        refreshButton.addActionListener(e -> fetchData());
        buttonPanel.add(refreshButton);

        RoundedButton closeButton = new RoundedButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setBackground(Color.WHITE);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setBounds(160, 10, 120, 40); // Adjust the position of the close button within the button panel
        closeButton.addActionListener(e -> {
            mainFrame.setContentPane(new RecordsMainPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        buttonPanel.add(closeButton);
    }

    private void fetchData() {
        String query = "SELECT u.unique_user_id, u.username, ul.user_action, ul.action_timestamp " +
                "FROM user_logs ul " +
                "JOIN users u ON ul.unique_user_id = u.unique_user_id " +
                "ORDER BY ul.action_timestamp DESC"; // Order by timestamp in descending order

        try (Connection connection = DatabaseUtil.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            tableModel.setRowCount(0); // Clear existing data

            while (resultSet.next()) {
                String uniqueUserId = resultSet.getString("unique_user_id");
                String username = resultSet.getString("username");
                String action = resultSet.getString("user_action");
                java.sql.Timestamp timestamp = resultSet.getTimestamp("action_timestamp");

                // Add row to table model
                tableModel.addRow(new Object[] { uniqueUserId, username, action, timestamp });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
