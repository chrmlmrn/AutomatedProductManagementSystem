package admin.maintenance;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.DatabaseUtil;
import admin.records.userlogs.UserLogUtil;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;

public class UserMaintenance extends JPanel {
    private static DefaultTableModel tableModel;
    private static JTable userTable;
    private JFrame mainFrame;
    private String uniqueUserId;

    public UserMaintenance(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents(); // Initialize components
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(null); // Use absolute positioning

        // Title and Back Button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new MaintenancePage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JLabel titleLabel = new JLabel("User Maintenance");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(100, 30, 500, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Rounded Blue Panel
        RoundedPanel bluePanel = new RoundedPanel(30);
        bluePanel.setBackground(new Color(30, 144, 255));
        bluePanel.setBounds(180, 120, 1000, 500);
        bluePanel.setLayout(null); // Use absolute positioning within the panel
        add(bluePanel);

        // Table Setup
        String[] columnNames = { "First Name", "Last Name", "Username", "Role", "Status" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all columns non-editable
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 16));
        userTable.setRowHeight(30);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        userTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(50, 50, 900, 300);
        bluePanel.add(scrollPane);

        // Add a mouse listener to open a new window on row click
        userTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && userTable.getSelectedRow() != -1) {
                    int selectedRow = userTable.getSelectedRow();
                    String firstName = (String) userTable.getValueAt(selectedRow, 0);
                    String lastName = (String) userTable.getValueAt(selectedRow, 1);
                    String username = (String) userTable.getValueAt(selectedRow, 2);
                    String status = (String) userTable.getValueAt(selectedRow, 4);

                    // Open a new window with the user details
                    openUserDetailsWindow(firstName, lastName, username, status);
                }
            }
        });

        // Button Panel
        RoundedButton selectButton = new RoundedButton("Select");
        selectButton.setFont(new Font("Arial", Font.BOLD, 16));
        selectButton.setBackground(Color.WHITE);
        selectButton.setForeground(Color.BLACK);
        selectButton.setFocusPainted(false);
        selectButton.setBounds(200, 400, 150, 40);
        selectButton.addActionListener(e -> {
            if (userTable.getSelectedRow() != -1) {
                int selectedRow = userTable.getSelectedRow();
                String firstName = (String) userTable.getValueAt(selectedRow, 0);
                String lastName = (String) userTable.getValueAt(selectedRow, 1);
                String username = (String) userTable.getValueAt(selectedRow, 2);
                String status = (String) userTable.getValueAt(selectedRow, 4);

                // Open a new window with the user details
                openUserDetailsWindow(firstName, lastName, username, status);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a user to update.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        bluePanel.add(selectButton);

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(650, 400, 150, 40);
        cancelButton.addActionListener(e -> {
            mainFrame.setContentPane(new MaintenancePage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        bluePanel.add(cancelButton);

        // Initialize table with data
        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openUserDetailsWindow(String firstName, String lastName, String username, String status) {
        JDialog userDetailsDialog = new JDialog(mainFrame, "User Details", true);
        userDetailsDialog.setSize(400, 250); // Make the dialog smaller
        userDetailsDialog.setLocationRelativeTo(mainFrame); // Center on the screen

        // Remove the 'X' button
        userDetailsDialog.setUndecorated(true);
        userDetailsDialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        // Blue panel for the dialog
        RoundedPanel dialogPanel = new RoundedPanel(30);
        dialogPanel.setBackground(new Color(30, 144, 255));
        dialogPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        dialogPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        JTextField firstNameField = new JTextField(firstName, 20);
        firstNameField.setEditable(false);
        dialogPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        JTextField lastNameField = new JTextField(lastName, 20);
        lastNameField.setEditable(false);
        dialogPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialogPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "Active", "Inactive" });
        statusComboBox.setSelectedItem(status);
        dialogPanel.add(statusComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(30, 144, 255)); // Set the background color to match the dialog panel
        RoundedButton saveButton = new RoundedButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLACK);
        saveButton.addActionListener(e -> {
            String updatedFirstName = firstNameField.getText().trim();
            String updatedLastName = lastNameField.getText().trim();
            String updatedStatus = (String) statusComboBox.getSelectedItem();
            updateUserDetails(username, updatedFirstName, updatedLastName, updatedStatus);
            userDetailsDialog.dispose();
        });
        buttonPanel.add(saveButton);

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(e -> userDetailsDialog.dispose());
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialogPanel.add(buttonPanel, gbc);

        userDetailsDialog.add(dialogPanel);
        userDetailsDialog.setVisible(true);
    }

    private void updateUserDetails(String username, String firstName, String lastName, String status) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Get user ID from database based on username
            String userIdQuery = "SELECT user_id FROM users WHERE username = ?";
            PreparedStatement userIdStatement = connection.prepareStatement(userIdQuery);
            userIdStatement.setString(1, username);
            ResultSet userIdResult = userIdStatement.executeQuery();

            String userId = null;

            if (userIdResult.next()) {
                userId = userIdResult.getString("user_id");
            } else {
                JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get status_id from database based on status string
            String statusIdQuery = "SELECT user_account_status_id FROM user_account_status WHERE account_status = ?";
            PreparedStatement statusIdStatement = connection.prepareStatement(statusIdQuery);
            statusIdStatement.setString(1, status);
            ResultSet statusIdResult = statusIdStatement.executeQuery();

            String userAccountStatusId = null;

            if (statusIdResult.next()) {
                userAccountStatusId = statusIdResult.getString("user_account_status_id");
            }

            String updateQuery = "UPDATE users SET user_first_name = ?, user_last_name = ?, user_account_status_id = ? WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, userAccountStatusId);
            preparedStatement.setString(4, userId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "User updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                try (Connection conn = DatabaseUtil.getConnection()) {
                    refreshTable(conn); // Refresh table after update
                }
                // Log user action
                UserLogUtil.logUserAction(uniqueUserId, "Updated user: " + username);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Connection connection) {
        try {
            String query = "SELECT u.user_first_name, u.user_last_name, u.username, r.user_role_name, s.account_status "
                    + "FROM users u "
                    + "INNER JOIN user_level_of_access r ON u.user_role_id = r.user_role_id "
                    + "INNER JOIN user_account_status s ON u.user_account_status_id = s.user_account_status_id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String firstName = resultSet.getString("user_first_name");
                String lastName = resultSet.getString("user_last_name");
                String username = resultSet.getString("username");
                String role = resultSet.getString("user_role_name");
                String status = resultSet.getString("account_status");
                tableModel.addRow(new Object[] { firstName, lastName, username, role, status });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
