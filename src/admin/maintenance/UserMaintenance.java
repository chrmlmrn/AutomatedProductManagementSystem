package src.admin.maintenance;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import database.DatabaseUtil;
import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;

public class UserMaintenance extends JFrame {
    private static DefaultTableModel tableModel;
    private static JTable userTable;
    private static JComboBox<String> roleComboBox;
    private static JComboBox<String> statusComboBox;

    public UserMaintenance() {
        initComponents(); // Initialize components
    }

    private void initComponents() {
        setTitle("Maintenance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

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

        JLabel titleLabel = new JLabel("Maintenance");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(24, 26, 78));
        headerPanel.add(titleLabel);

        RoundedPanel containerPanel = new RoundedPanel(30);
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(1200, 600));
        containerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        String[] columnNames = { "First Name", "Last Name", "Username", "Role", "Status" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 3 || column == 4; // Allow editing for First Name, Last
                                                                                 // Name, Role, and Status
            }
        };

        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 16));
        userTable.setRowHeight(30);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        userTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(1100, 400));

        // Add JComboBox for Role and Status
        roleComboBox = new JComboBox<>(new String[] { "Admin", "Cashier" });
        statusComboBox = new JComboBox<>(new String[] { "Active", "Inactive" });

        TableColumn roleColumn = userTable.getColumnModel().getColumn(3);
        roleColumn.setCellEditor(new DefaultCellEditor(roleComboBox));

        TableColumn statusColumn = userTable.getColumnModel().getColumn(4);
        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 50, 20, 50);
        containerPanel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

        RoundedButton updateButton = new RoundedButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(Color.BLACK);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(150, 40));
        updateButton.addActionListener(e -> updateUser());

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> {
            dispose();
            MaintenancePage maintenancePage = new MaintenancePage();
            maintenancePage.setVisible(true);
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

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

        addKeyListener(new java.awt.event.KeyAdapter() {
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

    private static void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = (String) userTable.getValueAt(selectedRow, 2);
        String firstName = (String) userTable.getValueAt(selectedRow, 0);
        String lastName = (String) userTable.getValueAt(selectedRow, 1);
        String role = (String) userTable.getValueAt(selectedRow, 3);
        String status = (String) userTable.getValueAt(selectedRow, 4);

        System.out.println("Selected user: " + username);

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

            // Get role_id and status_id from database based on role and status strings
            String roleIdQuery = "SELECT user_role_id FROM user_level_of_access WHERE user_role_name = ?";
            PreparedStatement roleIdStatement = connection.prepareStatement(roleIdQuery);
            roleIdStatement.setString(1, role);
            ResultSet roleIdResult = roleIdStatement.executeQuery();

            String statusIdQuery = "SELECT user_account_status_id FROM user_account_status WHERE account_status = ?";
            PreparedStatement statusIdStatement = connection.prepareStatement(statusIdQuery);
            statusIdStatement.setString(1, status);
            ResultSet statusIdResult = statusIdStatement.executeQuery();

            String userRoleId = null;
            String userAccountStatusId = null;

            if (roleIdResult.next()) {
                userRoleId = roleIdResult.getString("user_role_id");
            }

            if (statusIdResult.next()) {
                userAccountStatusId = statusIdResult.getString("user_account_status_id");
            }

            System.out.println("Role ID: " + userRoleId + ", Status ID: " + userAccountStatusId);

            String updateQuery = "UPDATE users SET user_first_name = ?, user_last_name = ?, user_role_id = ?, user_account_status_id = ? WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, userRoleId);
            preparedStatement.setString(4, userAccountStatusId);
            preparedStatement.setString(5, userId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "User updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshTable(connection); // Refresh table after update
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void refreshTable(Connection connection) {
        try {
            String query = "SELECT u.user_first_name, u.user_last_name, u.username, r.user_role_name, s.account_status "
                    +
                    "FROM users u " +
                    "INNER JOIN user_level_of_access r ON u.user_role_id = r.user_role_id " +
                    "INNER JOIN user_account_status s ON u.user_account_status_id = s.user_account_status_id";
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserMaintenance frame = new UserMaintenance();
            frame.setVisible(true);
        });
    }
}
