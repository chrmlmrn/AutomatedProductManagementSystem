package admin.records.user;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.records.RecordsMainPage;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import database.DatabaseUtil;

public class UserRecords extends JPanel {
    private DefaultTableModel tableModel;
    private JTable userTable;
    private JTextField searchField;

    private JFrame mainFrame;
    private String uniqueUserId;

    public UserRecords(JFrame mainFrame, String uniqueUserId) {
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

        JLabel titleLabel = new JLabel("User Records");
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
        String[] columnNames = { "Unique User ID", "First Name", "Last Name", "Username", "Role", "Status" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 16));
        userTable.setRowHeight(30);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        userTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(userTable);
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
        searchButton.addActionListener(e -> searchUsers());
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

    private void searchUsers() {

        String searchText = searchField.getText().trim();

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query;
            PreparedStatement statement;

            if (searchText.isEmpty()) {
                query = "SELECT u.unique_user_id, u.user_first_name, u.user_last_name, u.username, l.user_role_name, s.account_status "
                        +
                        "FROM users u " +
                        "JOIN user_level_of_access l ON u.user_role_id = l.user_role_id " +
                        "JOIN user_account_status s ON u.user_account_status_id = s.user_account_status_id";
                statement = connection.prepareStatement(query);
            } else {
                query = "SELECT u.unique_user_id, u.user_first_name, u.user_last_name, u.username, l.user_role_name, s.account_status "
                        +
                        "FROM users u " +
                        "JOIN user_level_of_access l ON u.user_role_id = l.user_role_id " +
                        "JOIN user_account_status s ON u.user_account_status_id = s.user_account_status_id " +
                        "WHERE u.user_first_name LIKE ? OR u.user_last_name LIKE ? OR u.username LIKE ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, "%" + searchText + "%");
                statement.setString(2, "%" + searchText + "%");
                statement.setString(3, "%" + searchText + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String uniqueUserId = resultSet.getString("unique_user_id");
                String firstName = resultSet.getString("user_first_name");
                String lastName = resultSet.getString("user_last_name");
                String username = resultSet.getString("username");
                String roleName = resultSet.getString("user_role_name");
                String accountStatus = resultSet.getString("account_status");
                tableModel
                        .addRow(new Object[] { uniqueUserId, firstName, lastName, username, roleName, accountStatus });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Connection connection) {
        try {
            String query = "SELECT u.unique_user_id, u.user_first_name, u.user_last_name, u.username, l.user_role_name, s.account_status "
                    +
                    "FROM users u " +
                    "JOIN user_level_of_access l ON u.user_role_id = l.user_role_id " +
                    "JOIN user_account_status s ON u.user_account_status_id = s.user_account_status_id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String uniqueUserId = resultSet.getString("unique_user_id");
                String firstName = resultSet.getString("user_first_name");
                String lastName = resultSet.getString("user_last_name");
                String username = resultSet.getString("username");
                String roleName = resultSet.getString("user_role_name");
                String accountStatus = resultSet.getString("account_status");
                tableModel
                        .addRow(new Object[] { uniqueUserId, firstName, lastName, username, roleName, accountStatus });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
