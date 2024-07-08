package register;

import javax.swing.*;

import admin.records.userlogs.UserLogUtil;
import database.DatabaseUtil;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import login.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Signup extends JPanel {
        private JPanel mainPanel, formPanel;
        private JLabel registerLabel, detailsLabel, firstNameLabel, lastNameLabel, usernameLabel, passwordLabel,
                        confirmPasswordLabel, termsLabel, securityQuestionLabel, securityAnswerLabel, haveAccountLabel,
                        clickHereLabel;
        private JTextField firstNameField, lastNameField, usernameField, securityAnswerField;
        private JPasswordField passwordField, confirmPasswordField;
        private JButton cashierButton, signInButton, adminButton;
        private JCheckBox termsCheckBox;
        private JComboBox<String> securityQuestionComboBox;
        private boolean isAdminSelected = false;
        private boolean isCashierSelected = false;
        private JFrame mainFrame;
        private String uniqueUserId;

        public Signup(JFrame mainFrame, String uniqueUserId) {
                this.mainFrame = mainFrame;
                this.uniqueUserId = uniqueUserId;

                initComponents();
        }

        private void initComponents() {
                // Initialize main panel with GridBagLayout
                mainPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                setBackground(Color.WHITE);

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(0, 0, 0, 0); // Remove extra space at the top

                formPanel = createFormPanel();
                mainPanel.add(formPanel, gbc);

                // Add mainPanel to the Signup panel
                setLayout(new GridBagLayout());
                GridBagConstraints mainGbc = new GridBagConstraints();
                mainGbc.gridx = 0;
                mainGbc.gridy = 0;
                mainGbc.anchor = GridBagConstraints.CENTER;
                add(mainPanel, mainGbc);
        }

        private JPanel createFormPanel() {
                RoundedPanel panel = new RoundedPanel(30);
                panel.setBackground(new Color(30, 144, 255));
                panel.setPreferredSize(new Dimension(400, 600));

                // Create labels and fields
                registerLabel = createLabel("Register", new Font("Arial", Font.BOLD, 30));
                registerLabel.setForeground(Color.WHITE);
                detailsLabel = createLabel("Enter your details", new Font("Arial", Font.PLAIN, 12));
                detailsLabel.setForeground(Color.WHITE);
                firstNameLabel = createLabel("First Name", new Font("Arial", Font.PLAIN, 12));
                firstNameLabel.setForeground(Color.WHITE);
                lastNameLabel = createLabel("Last Name", new Font("Arial", Font.PLAIN, 12));
                lastNameLabel.setForeground(Color.WHITE);
                usernameLabel = createLabel("Username", new Font("Arial", Font.PLAIN, 12));
                usernameLabel.setForeground(Color.WHITE);
                passwordLabel = createLabel("Password", new Font("Arial", Font.PLAIN, 12));
                passwordLabel.setForeground(Color.WHITE);
                confirmPasswordLabel = createLabel("Confirm Password", new Font("Arial", Font.PLAIN, 12));
                confirmPasswordLabel.setForeground(Color.WHITE);
                securityQuestionLabel = createLabel("Security Question", new Font("Arial", Font.PLAIN, 12));
                securityQuestionLabel.setForeground(Color.WHITE);
                securityAnswerLabel = createLabel("Answer", new Font("Arial", Font.PLAIN, 12));
                securityAnswerLabel.setForeground(Color.WHITE);

                haveAccountLabel = createLabel("Already have an account?", new Font("Arial", Font.PLAIN, 12));
                haveAccountLabel.setForeground(Color.WHITE);
                clickHereLabel = createLabel("Click Here", new Font("Arial", Font.BOLD, 12), new Color(0, 102, 204));
                clickHereLabel.setForeground(Color.BLACK);

                firstNameField = new JTextField();
                lastNameField = new JTextField();
                usernameField = new JTextField();
                passwordField = new JPasswordField();
                confirmPasswordField = new JPasswordField();
                securityAnswerField = new JTextField();
                termsCheckBox = new JCheckBox("I accept the");
                termsCheckBox.setOpaque(false); // Make the checkbox non-opaque
                termsCheckBox.setContentAreaFilled(false);
                termsCheckBox.setBorderPainted(false);
                termsCheckBox.setFocusPainted(false);
                termsCheckBox.setForeground(Color.WHITE); // Set the foreground color to white to match the text
                termsLabel = createLabel("Terms and Conditions", new Font("Arial", Font.PLAIN, 12));
                termsLabel.setForeground(Color.BLUE);

                // Add mouse listener to the terms link label
                termsLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                showTermsAndConditions();
                        }
                });
                securityQuestionComboBox = new JComboBox<>(new String[] {
                                "What is your mother's maiden name?",
                                "What is the name of your first pet?",
                                "In what city were you born?",
                                "What is your favorite movie?",
                                "In what year did you graduate from high school?",
                                "What was the name of your first school?"
                });

                // Create buttons
                cashierButton = new RoundedButton("Cashier");
                adminButton = new RoundedButton("Admin");

                // Add action listeners to change button color
                cashierButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                isAdminSelected = false;
                                isCashierSelected = true;
                                cashierButton.setBackground(new Color(169, 169, 169)); // Set darker color
                                cashierButton.setForeground(Color.WHITE);
                                adminButton.setBackground(new Color(240, 240, 240)); // Reset other button color
                                adminButton.setForeground(Color.BLACK);
                        }
                });

                adminButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                isAdminSelected = true;
                                isCashierSelected = false;
                                adminButton.setBackground(new Color(169, 169, 169)); // Set darker color
                                adminButton.setForeground(Color.WHITE);
                                cashierButton.setBackground(new Color(240, 240, 240)); // Reset other button color
                                cashierButton.setForeground(Color.BLACK);
                        }
                });

                signInButton = new RoundedButton("Sign Up");
                signInButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                saveUserDetails();
                        }
                });

                // Setup click here label to navigate to login page
                clickHereLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                clickHereLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                goToLoginPage();
                        }
                });

                // Add new label above admin button
                JLabel roleSelectionLabel = createLabel("Select Your Role", new Font("Arial", Font.PLAIN, 12));
                roleSelectionLabel.setForeground(Color.WHITE);

                // Layout the form panel
                layoutFormPanel(panel, roleSelectionLabel);

                return panel;
        }

        private void layoutFormPanel(JPanel panel, JLabel roleSelectionLabel) {
                GroupLayout layout = new GroupLayout(panel);
                panel.setLayout(layout);

                layout.setHorizontalGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(registerLabel)
                                                                                .addComponent(detailsLabel)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                false)
                                                                                                                .addComponent(firstNameLabel,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(firstNameField,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                170,
                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(lastNameLabel)
                                                                                                                .addComponent(lastNameField,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                168,
                                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                                .addComponent(usernameLabel)
                                                                                .addComponent(usernameField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                350,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(passwordLabel)
                                                                                .addComponent(passwordField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                350,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(confirmPasswordLabel)
                                                                                .addComponent(confirmPasswordField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                350,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(securityQuestionLabel)
                                                                                .addComponent(securityQuestionComboBox,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                350,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(securityAnswerLabel)
                                                                                .addComponent(securityAnswerField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                350,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(roleSelectionLabel)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(adminButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                165,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(cashierButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                165,
                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(termsCheckBox)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(termsLabel))
                                                                                .addComponent(signInButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                350,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(haveAccountLabel)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(clickHereLabel)))
                                                                .addContainerGap(25, Short.MAX_VALUE)));

                layout.setVerticalGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addComponent(registerLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(detailsLabel)
                                                                .addGap(10, 10, 10)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(firstNameLabel)
                                                                                .addComponent(lastNameLabel))
                                                                .addGap(4, 4, 4)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(firstNameField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(lastNameField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(usernameLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(passwordLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(confirmPasswordLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(confirmPasswordField,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(securityQuestionLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(securityQuestionComboBox,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(securityAnswerLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(securityAnswerField,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(roleSelectionLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(adminButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                40,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cashierButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                40,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(termsCheckBox)
                                                                                .addComponent(termsLabel))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(signInButton, GroupLayout.PREFERRED_SIZE,
                                                                                40, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(haveAccountLabel)
                                                                                .addComponent(clickHereLabel))
                                                                .addContainerGap(15, Short.MAX_VALUE)));
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

        private void showTermsAndConditions() {
                String terms = "Terms and Conditions:\n\n"
                                + "1. User must provide accurate and truthful information.\n"
                                + "2. Password must be 8 to 12 characters long and contain numbers, and be case-sensitive.\n"
                                + "3. User is responsible for maintaining the confidentiality of their password.\n"
                                + "4. The use of this application is subject to the approval of the administrator.\n"
                                + "5. Any misuse of the system will face severe consequences from the Business Owner.\n"
                                + "6. User agrees to comply with all applicable laws and regulations.\n"
                                + "7. The administrator reserves the right to update the terms and conditions at any time.\n"
                                + "8. User must not share their account with others.\n"
                                + "9. User should report any suspicious activity immediately.\n"
                                + "10. User agrees to the data privacy policy outlined by the system.";

                JOptionPane.showMessageDialog(this, terms, "Terms and Conditions", JOptionPane.INFORMATION_MESSAGE);
        }

        private void goToLoginPage() {
                mainFrame.setContentPane(new Login(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
        }

        private String generateEmployeeID() {
                String prefix = isAdminSelected ? "ADM-" : "CAS-";
                int count = getEmployeeCount() + 1;
                return String.format("%s%04d", prefix, count);
        }

        // Method to get the current count of employees
        private static int getEmployeeCount() {
                String query = "SELECT COUNT(*) FROM users"; // Modify the table name as per your schema
                int count = 0;

                try (Connection connection = DatabaseUtil.getConnection();
                                PreparedStatement stmt = connection.prepareStatement(query);
                                ResultSet rs = stmt.executeQuery()) {

                        if (rs.next()) {
                                count = rs.getInt(1);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return count;
        }

        private void saveUserDetails() {

                // Validation checks
                if (firstNameField.getText().trim().isEmpty() ||
                                lastNameField.getText().trim().isEmpty() ||
                                usernameField.getText().trim().isEmpty() ||
                                new String(passwordField.getPassword()).trim().isEmpty() ||
                                new String(confirmPasswordField.getPassword()).trim().isEmpty() ||
                                securityAnswerField.getText().trim().isEmpty() ||
                                !termsCheckBox.isSelected() ||
                                (!isAdminSelected && !isCashierSelected)) {

                        JOptionPane.showMessageDialog(this,
                                        "Please fill in all required fields and accept the terms and conditions",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method if any required field is missing
                }

                String uniqueUserId = generateEmployeeID();
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
                String securityAnswer = securityAnswerField.getText().trim();
                String userRoleId = isAdminSelected ? "A" : "C"; // Assuming "A" for Admin and "C" for Cashier

                // Check username length
                if (username.length() < 5) {
                        JOptionPane.showMessageDialog(this, "Username must be at least 5 characters long.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Check password length and complexity
                if (password.length() < 8 || password.length() > 12 || !isPasswordComplex(password)) {
                        JOptionPane.showMessageDialog(this,
                                        "Password must be 8 to 12 characters long and contain numbers, and be case-sensitive.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Encrypt password using SHA-256
                String encryptedPassword = hashSHA256(password);
                System.out.println("Password encrypted.");

                // Encrypt security answer using SHA-256
                String encryptedSecurityAnswer = hashSHA256(securityAnswer);
                System.out.println("Security answer encrypted.");

                // SQL query to insert user details into the database
                String insertUserQuery = "INSERT INTO users (unique_user_id, user_role_id, user_first_name, user_last_name, username, password_hash, user_account_status_id) "
                                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

                // SQL query to insert security answer into the database
                String insertSecurityAnswerQuery = "INSERT INTO security_answer (user_id, security_question_id, security_answer_hash) "
                                + "VALUES (?, ?, ?)";

                // Get the selected security question
                String selectedSecurityQuestion = (String) securityQuestionComboBox.getSelectedItem();
                int securityQuestionId = getSecurityQuestionId(selectedSecurityQuestion);

                try {
                        // Establish database connection
                        Connection connection = DatabaseUtil.getConnection();
                        if (connection != null) {
                                // Insert user details
                                PreparedStatement insertUserStatement = connection.prepareStatement(insertUserQuery,
                                                Statement.RETURN_GENERATED_KEYS);
                                insertUserStatement.setString(1, uniqueUserId); // user_id
                                insertUserStatement.setString(2, userRoleId); // user_role_id
                                insertUserStatement.setString(3, firstName); // user_first_name
                                insertUserStatement.setString(4, lastName); // user_last_name
                                insertUserStatement.setString(5, username); // username
                                insertUserStatement.setString(6, encryptedPassword); // password_hash
                                insertUserStatement.setString(7, "ACT"); // Set the default value for
                                // user_account_status_id

                                insertUserStatement.executeUpdate();

                                // Get the generated user ID
                                ResultSet generatedKeys = insertUserStatement.getGeneratedKeys();
                                int userId = -1;
                                if (generatedKeys.next()) {
                                        userId = generatedKeys.getInt(1);
                                }

                                // Log user registration action
                                UserLogUtil.logUserAction(uniqueUserId, "User registered");

                                // Insert security answer
                                PreparedStatement insertSecurityAnswerStatement = connection
                                                .prepareStatement(insertSecurityAnswerQuery);
                                insertSecurityAnswerStatement.setInt(1, userId);
                                insertSecurityAnswerStatement.setInt(2, securityQuestionId);
                                insertSecurityAnswerStatement.setString(3, encryptedSecurityAnswer);
                                insertSecurityAnswerStatement.executeUpdate();

                                JOptionPane.showMessageDialog(this, "User registered successfully!", "Success",
                                                JOptionPane.INFORMATION_MESSAGE);

                                // Close database connection
                                connection.close();

                                // Navigate to login page
                                goToLoginPage();
                        } else {
                                JOptionPane.showMessageDialog(this, "Failed to connect to the database", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error occurred while registering user", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private String hashSHA256(String input) {
                try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
                        for (byte b : encodedHash) {
                                String hex = Integer.toHexString(0xff & b);
                                if (hex.length() == 1) {
                                        hexString.append('0');
                                }
                                hexString.append(hex);
                        }
                        return hexString.toString();
                } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        return null;
                }
        }

        private int getSecurityQuestionId(String question) {
                // This method would retrieve the ID of the selected security question from the
                // database
                // For demonstration purposes, let's assume a simple mapping
                if (question.equals("What is your mother's maiden name?")) {
                        return 1;
                } else if (question.equals("What is the name of your first pet?")) {
                        return 2;
                } else if (question.equals("In what city were you born?")) {
                        return 3;
                } else if (question.equals("What is your favorite movie?")) {
                        return 4;
                } else if (question.equals("In what year did you graduate from high school?")) {
                        return 5;
                } else if (question.equals("What was the name of your first school?")) {
                        return 6;
                } else {
                        return -1; // Invalid question
                }
        }

        private boolean isPasswordComplex(String password) {
                // Check if password length is between 8 and 12 characters
                if (password.length() < 8 || password.length() > 12) {
                        System.out.println("Password length check failed.");
                        return false;
                }

                // Check if password contains at least one digit
                boolean containsDigit = password.matches(".*\\d.*");
                System.out.println("Contains digit: " + containsDigit);

                // Check if password contains both lowercase and uppercase letters
                boolean containsLowerCase = !password.equals(password.toUpperCase());
                boolean containsUpperCase = !password.equals(password.toLowerCase());
                System.out.println("Contains lowercase: " + containsLowerCase);
                System.out.println("Contains uppercase: " + containsUpperCase);

                return containsDigit && containsLowerCase && containsUpperCase;
        }
}
