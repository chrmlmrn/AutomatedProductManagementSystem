package login;

import javax.swing.*;

import database.DatabaseUtil;
import SHA256.Sha256Util;
import admin.records.userlogs.UserLogUtil;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassword extends JPanel {

    private JPanel mainPanel, formPanel;
    private JLabel forgotPasswordLabel, usernameLabel, questionLabel, answerLabel, newPasswordLabel,
            confirmPasswordLabel, backToLoginLabel;
    private JTextField usernameField, answerField;
    private JPasswordField newPasswordField, confirmPasswordField;
    private JLabel questionTextLabel;
    private JButton resetButton, fetchQuestionButton;

    private JFrame mainFrame;
    private String uniqueUserId;

    public ForgotPassword(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        // Initialize main panel
        mainPanel = new JPanel(new GridBagLayout());
        formPanel = createFormPanel();
        mainPanel.setBackground(Color.WHITE);
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(100, 0, 0, 0); // Add space at the top
        mainPanel.add(formPanel, gbc);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        RoundedPanel panel = new RoundedPanel(30);
        panel.setBackground(new Color(30, 144, 255));
        panel.setPreferredSize(new Dimension(400, 600));

        // Create labels and fields
        forgotPasswordLabel = createLabel("Forgot Password", new Font("Arial", Font.BOLD, 30));
        forgotPasswordLabel.setForeground(Color.WHITE);
        usernameLabel = createLabel("Username", new Font("Arial", Font.PLAIN, 12));
        usernameLabel.setForeground(Color.WHITE);
        questionLabel = createLabel("Your Security Question", new Font("Arial", Font.PLAIN, 12));
        questionLabel.setForeground(Color.WHITE);
        answerLabel = createLabel("Answer", new Font("Arial", Font.PLAIN, 12));
        answerLabel.setForeground(Color.WHITE);
        newPasswordLabel = createLabel("New Password", new Font("Arial", Font.PLAIN, 12));
        newPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel = createLabel("Confirm Password", new Font("Arial", Font.PLAIN, 12));
        confirmPasswordLabel.setForeground(Color.WHITE);

        usernameField = new JTextField();
        questionTextLabel = createLabel("", new Font("Arial", Font.PLAIN, 12));
        questionTextLabel.setForeground(Color.WHITE);
        answerField = new JTextField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        // Create fetch question button
        fetchQuestionButton = new RoundedButton("Verify");
        fetchQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchSecurityQuestion();
            }
        });

        // Create reset button
        resetButton = new RoundedButton("Reset Password");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleResetPassword();
            }
        });

        // Setup back to login label to navigate back to login page
        backToLoginLabel = createLabel("Back to Login", new Font("Arial", Font.BOLD, 12), new Color(0, 102, 204));
        backToLoginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goToLoginPage();
            }
        });

        // Layout the form panel
        layoutFormPanel(panel);

        return panel;
    }

    private void layoutFormPanel(JPanel panel) {
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(forgotPasswordLabel)
                                        .addComponent(usernameLabel)
                                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(fetchQuestionButton, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(questionLabel)
                                        .addComponent(questionTextLabel)
                                        .addComponent(answerLabel)
                                        .addComponent(answerField, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newPasswordLabel)
                                        .addComponent(newPasswordField, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmPasswordLabel)
                                        .addComponent(confirmPasswordField, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resetButton, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(backToLoginLabel))
                                .addContainerGap(25, Short.MAX_VALUE)));

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20) // Adjust this gap for more space
                                .addComponent(forgotPasswordLabel)
                                .addGap(18, 18, 18)
                                .addComponent(usernameLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(fetchQuestionButton, GroupLayout.PREFERRED_SIZE, 37,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(questionLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(questionTextLabel)
                                .addGap(18, 18, 18)
                                .addComponent(answerLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(answerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(newPasswordLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(confirmPasswordLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(confirmPasswordField, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(resetButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(backToLoginLabel)
                                .addContainerGap(25, Short.MAX_VALUE)));
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

    private void fetchSecurityQuestion() {
        String username = usernameField.getText();
        String query = "SELECT sq.security_question FROM security_question sq " +
                "JOIN security_answer sa ON sq.security_question_id = sa.security_question_id " +
                "JOIN users u ON sa.user_id = u.user_id WHERE u.username = ?";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String securityQuestion = resultSet.getString("security_question");
                questionTextLabel.setText(securityQuestion);
            } else {
                questionTextLabel.setText("No security question found for this username.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            questionTextLabel.setText("Error fetching security question.");
        }
    }

    private void handleResetPassword() {
        String username = usernameField.getText();
        String answer = answerField.getText();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Hash the provided security answer
        String hashedAnswer = Sha256Util.hash(answer);

        // Validate the security question answer
        boolean isAnswerCorrect = validateAnswer(username, questionTextLabel.getText(), hashedAnswer);

        if (!isAnswerCorrect) {
            JOptionPane.showMessageDialog(this, "Security answer incorrect. Please try again.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
            return;
        }

        // Reset the password
        if (resetPassword(username, newPassword)) {
            // Log password reset attempt
            try {
                UserLogUtil.logUserAction(uniqueUserId, "Password reset successful");
            } catch (IllegalArgumentException ex) {
                System.err.println("Error logging user action: " + ex.getMessage());
            }

            JOptionPane.showMessageDialog(this, "Password has been reset successfully!");
            goToLoginPage();
        } else {

            // Log failed password reset attempt
            try {
                UserLogUtil.logUserAction(uniqueUserId, "Password reset failed");
            } catch (IllegalArgumentException ex) {
                System.err.println("Error logging user action: " + ex.getMessage());
            }

            JOptionPane.showMessageDialog(this, "Failed to reset password. Please try again later.");
        }
    }

    private boolean validateAnswer(String username, String question, String answer) {
        // Query the database to fetch the stored answer for the provided username and
        // question
        String query = "SELECT sa.security_answer_hash FROM security_answer sa " +
                "JOIN users u ON sa.user_id = u.user_id " +
                "JOIN security_question sq ON sa.security_question_id = sq.security_question_id " +
                "WHERE u.username = ? AND sq.security_question = ?";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, question);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String correctAnswer = resultSet.getString("security_answer_hash");
                // Compare the provided answer with the stored answer (case-insensitive)
                return correctAnswer.equalsIgnoreCase(answer);
            } else {
                // No matching record found, answer is incorrect
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resetPassword(String username, String newPassword) {
        String hashedPassword = Sha256Util.hash(newPassword);
        String query = "UPDATE users SET password_hash = ? WHERE username = ?";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hashedPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getUserIdByUsername(String username) {
        String query = "SELECT user_id FROM users WHERE username = ?";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                } else {
                    System.err.println("User not found for username: " + username);
                    return -1; // Return -1 indicating user not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 indicating an error occurred
        }
    }

    private void goToLoginPage() {
        mainFrame.setContentPane(new Login(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
