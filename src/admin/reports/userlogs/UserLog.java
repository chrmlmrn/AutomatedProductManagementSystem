package src.admin.reports.userlogs;

import database.DatabaseUtil;
import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserLog {
    private static DefaultTableModel tableModel;
    private static JTable logTable;

    public static void main(String[] args) {
        // Create and set up the frame
        JFrame frame = new JFrame("User Logs");
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

        JLabel titleLabel = new JLabel("User Logs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(24, 26, 78));
        headerPanel.add(titleLabel);

        RoundedPanel containerPanel = new RoundedPanel(30);
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(1200, 600));
        containerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        String[] columnNames = { "Log ID", "User ID", "Username", "Action", "Timestamp" };
        tableModel = new DefaultTableModel(columnNames, 0);

        logTable = new JTable(tableModel);
        logTable.setFont(new Font("Arial", Font.PLAIN, 16));
        logTable.setRowHeight(30);
        logTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        logTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(logTable);
        scrollPane.setPreferredSize(new Dimension(1100, 400));

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 50, 20, 50);
        containerPanel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

        RoundedButton refreshButton = new RoundedButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(150, 40));
        refreshButton.addActionListener(e -> fetchData());

        RoundedButton closeButton = new RoundedButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setBackground(Color.WHITE);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(150, 40));
        closeButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 50, 20, 50);
        containerPanel.add(buttonPanel, gbc);

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

        // Fetch data from database
        fetchData();
    }

    private static void fetchData() {
        String query = "SELECT ul.user_log_id, ul.user_id, u.username, ul.user_action, ul.action_timestamp " +
                "FROM user_logs ul " +
                "JOIN users u ON ul.user_id = u.user_id";

        try (Connection connection = DatabaseUtil.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            tableModel.setRowCount(0); // Clear existing data

            while (resultSet.next()) {
                int logId = resultSet.getInt("user_log_id");
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String action = resultSet.getString("user_action");
                java.sql.Timestamp timestamp = resultSet.getTimestamp("action_timestamp");

                // Add row to table model
                tableModel.addRow(new Object[] { logId, userId, username, action, timestamp });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from database: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}