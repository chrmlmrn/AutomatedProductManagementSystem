package login;

import javax.swing.*;
import database.DatabaseUtil;
import SHA256.Sha256Util;
import admin.AdminMenu;
import admin.records.userlogs.UserLogUtil;
import cashier.CashierMenu;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import register.Signup;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JPanel {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JLabel welcomeLabel, signInLabel, usernameLabel, passwordLabel, forgotPasswordLabel, noAccountLabel,
                        registerLabel;
        private JButton signInButton;
        private JPanel mainPanel, centerPanel;
        private JFrame mainFrame;

        private int loginAttempts = 0;
        private final int maxAttempts = 3;
        private Timer loginTimer;
        private JLabel timerLabel;
        private String uniqueUserId;

        public Login(JFrame mainFrame, String uniqueUserId) {
                this.mainFrame = mainFrame;
                this.uniqueUserId = uniqueUserId;

                initComponents();
                initTimer();
        }

        private void initComponents() {
                setLayout(new GridBagLayout());
                setBackground(Color.WHITE);
                centerPanel = createCenterPanel();

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.CENTER;
                add(centerPanel, gbc);
        }

        private void initTimer() {
                loginTimer = new Timer(1000, new ActionListener() {
                        int counter = 30;
                        boolean timerStarted = false;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (loginAttempts >= maxAttempts) {
                                        timerLabel.setVisible(true);
                                        if (!timerStarted) {
                                                counter = 30;
                                                timerStarted = true;
                                                loginTimer.start();
                                        }
                                        timerLabel.setText("Please try again later. Timer: " + counter + " seconds");
                                        counter--;
                                        if (counter < 0) {
                                                timerLabel.setText("Timer expired. You can now try again.");
                                                loginAttempts = 0;
                                                signInButton.setEnabled(true);
                                                timerStarted = false;
                                                loginTimer.stop();
                                        }
                                }
                        }
                });
        }

        private JPanel createCenterPanel() {
                RoundedPanel panel = new RoundedPanel(30);
                panel.setBackground(new Color(30, 144, 255));
                panel.setPreferredSize(new Dimension(400, 500));

                welcomeLabel = createLabel("Welcome!", new Font("Arial", Font.BOLD, 36));
                welcomeLabel.setForeground(Color.WHITE);
                signInLabel = createLabel("Sign In", new Font("Arial", Font.PLAIN, 14));
                signInLabel.setForeground(Color.WHITE);
                usernameLabel = createLabel("Username", new Font("Arial", Font.PLAIN, 14));
                usernameLabel.setForeground(Color.WHITE);
                passwordLabel = createLabel("Password", new Font("Arial", Font.PLAIN, 14));
                passwordLabel.setForeground(Color.WHITE);
                forgotPasswordLabel = createLabel("Forgot Password?", new Font("Arial", Font.BOLD, 12), Color.BLACK);
                noAccountLabel = createLabel("Don’t have an account?", new Font("Arial", Font.PLAIN, 14));
                noAccountLabel.setForeground(Color.WHITE);
                registerLabel = createLabel("Register Here", new Font("Arial", Font.BOLD, 14), Color.BLACK);
                registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                registerLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                openSignUpPage();
                        }
                });

                forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                forgotPasswordLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                openForgotPasswordPage();
                        }
                });

                usernameField = new JTextField();
                passwordField = new JPasswordField();
                signInButton = new RoundedButton("Sign In");

                usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
                passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
                signInButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                signInButton.setBackground(Color.WHITE);
                signInButton.setForeground(Color.BLACK);
                signInButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                signInButtonActionPerformed(evt);
                        }
                });

                timerLabel = createLabel("", new Font("Arial", Font.PLAIN, 14), Color.RED);
                timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

                layoutCenterPanel(panel);

                return panel;
        }

        private void layoutCenterPanel(JPanel panel) {
                GroupLayout layout = new GroupLayout(panel);
                panel.setLayout(layout);

                layout.setHorizontalGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(29, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(welcomeLabel)
                                                                                .addComponent(signInLabel)
                                                                                .addComponent(usernameLabel)
                                                                                .addComponent(usernameField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                342,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(passwordLabel)
                                                                                .addComponent(passwordField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                342,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(forgotPasswordLabel)
                                                                                .addComponent(signInButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                342,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(noAccountLabel)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(registerLabel))
                                                                                .addComponent(timerLabel,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                342,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addGap(29, 29, 29)));

                layout.setVerticalGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(107, Short.MAX_VALUE)
                                                                .addComponent(welcomeLabel, GroupLayout.PREFERRED_SIZE,
                                                                                28, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(signInLabel)
                                                                .addGap(36, 36, 36)
                                                                .addComponent(usernameLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(passwordLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(forgotPasswordLabel)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(signInButton, GroupLayout.PREFERRED_SIZE,
                                                                                40, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(timerLabel, GroupLayout.PREFERRED_SIZE,
                                                                                20, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(noAccountLabel)
                                                                                .addComponent(registerLabel))
                                                                .addContainerGap(107, Short.MAX_VALUE)));
        }

        private JLabel createLabel(String text, Font font) {
                JLabel label = new JLabel(text);
                label.setFont(font);
                return label;
        }

        private JLabel createLabel(String text, Font font, Color color) {
                JLabel label = new JLabel(text);
                label.setFont(font);
                label.setForeground(color);
                return label;
        }

        private void signInButtonActionPerformed(ActionEvent evt) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if username or password is empty
                if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                int authResult = authenticateUser(username, password);

                switch (authResult) {
                        case 1: // Success
                                String uniqueUserId = getUserUniqueIdByUsername(username);
                                String role = getUserRole(username);
                                if ("A".equals(role)) {
                                        openAdminPage(uniqueUserId);
                                } else if ("C".equals(role)) {
                                        openCashierPage(uniqueUserId);
                                }
                                loginAttempts = 0;
                                UserLogUtil.logUserAction(uniqueUserId, "User logged in");
                                timerLabel.setText("");
                                if (loginTimer.isRunning()) {
                                        loginTimer.stop();
                                }
                                break;
                        case 0: // Invalid credentials
                                loginAttempts++;
                                if (loginAttempts >= maxAttempts) {
                                        timerLabel.setVisible(true);
                                        if (!loginTimer.isRunning()) {
                                                loginTimer.start();
                                        }
                                        signInButton.setEnabled(false);
                                } else {
                                        JOptionPane.showMessageDialog(this, "Invalid username or password",
                                                        "Authentication Error", JOptionPane.ERROR_MESSAGE);
                                }
                                UserLogUtil.logUserAction(getUserUniqueIdByUsername(username), "User login failed");
                                break;
                        case -1: // Inactive user
                                JOptionPane.showMessageDialog(this,
                                                "User is Inactive, Please Activate it Using an Admin Account...",
                                                "Authentication Error", JOptionPane.ERROR_MESSAGE);
                                UserLogUtil.logUserAction(getUserUniqueIdByUsername(username),
                                                "Inactive user login attempt");
                                break;
                }
        }

        private int authenticateUser(String username, String password) {
                String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

                try (Connection connection = DatabaseUtil.getConnection();
                                PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, username);
                        statement.setString(2, Sha256Util.hash(password));

                        try (ResultSet resultSet = statement.executeQuery()) {
                                if (resultSet.next()) {
                                        String status = resultSet.getString("user_account_status_id");
                                        if ("INA".equals(status)) {
                                                return -1; // Inactive user
                                        }
                                        return 1; // Success
                                } else {
                                        return 0; // Invalid credentials
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        return 0; // Invalid credentials
                }
        }

        private String getUserUniqueIdByUsername(String username) {
                String query = "SELECT unique_user_id FROM users WHERE username = ?";
                try (Connection connection = DatabaseUtil.getConnection();
                                PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, username);
                        try (ResultSet resultSet = statement.executeQuery()) {
                                if (resultSet.next()) {
                                        String uniqueUserId = resultSet.getString("unique_user_id");
                                        return uniqueUserId;
                                } else {
                                        System.err.println("User not found for username: " + username);
                                        return null;
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                }
        }

        private String getUserRole(String username) {
                String query = "SELECT user_role_id FROM users WHERE username = ?";
                try (Connection connection = DatabaseUtil.getConnection();
                                PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, username);
                        try (ResultSet resultSet = statement.executeQuery()) {
                                if (resultSet.next()) {
                                        return resultSet.getString("user_role_id");
                                } else {
                                        System.err.println("User not found for username: " + username);
                                        return "";
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        return "";
                }
        }

        private void openAdminPage(String uniqueUserId) {
                mainFrame.setContentPane(new AdminMenu(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
        }

        private void openCashierPage(String uniqueUserId) {
                mainFrame.setContentPane(new CashierMenu(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
        }

        private void openSignUpPage() {
                mainFrame.setContentPane(new Signup(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
        }

        private void openForgotPasswordPage() {
                mainFrame.setContentPane(new ForgotPassword(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
        }
}
