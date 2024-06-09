package src.login;

import javax.swing.*;

import src.customcomponents.RoundedButton;


import src.customcomponents.RoundedPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ForgotPassword extends JFrame {

    private JPanel mainPanel, formPanel;
    private JLabel forgotPasswordLabel, usernameLabel, questionLabel, answerLabel, newPasswordLabel,
            confirmPasswordLabel, backToLoginLabel;
    private JTextField usernameField, answerField;
    private JPasswordField newPasswordField, confirmPasswordField;
    private JComboBox<String> questionComboBox;
    private JButton resetButton;

    public ForgotPassword() {
        initComponents();
    }

    private void initComponents() {
        // Initialize main panel
        mainPanel = new JPanel(new GridBagLayout());
        formPanel = createFormPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Forgot Password Page");
        setSize(1600, 900); // Set the size of the window to 1600x900 pixels
        setUndecorated(true); // Remove window borders and title bar
        setLocationRelativeTo(null); // Center the frame on the screen

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
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
        questionLabel = createLabel("Select a Question", new Font("Arial", Font.PLAIN, 12));
        questionLabel.setForeground(Color.WHITE);
        answerLabel = createLabel("Answer", new Font("Arial", Font.PLAIN, 12));
        answerLabel.setForeground(Color.WHITE);
        newPasswordLabel = createLabel("New Password", new Font("Arial", Font.PLAIN, 12));
        newPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel = createLabel("Confirm Password", new Font("Arial", Font.PLAIN, 12));
        confirmPasswordLabel.setForeground(Color.WHITE);

        usernameField = new JTextField();
        questionComboBox = new JComboBox<>(new String[] {
                "What was your first pet's name?",
                "What is your mother's maiden name?",
                "What was the name of your first school?"
        });
        answerField = new JTextField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

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
        backToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(forgotPasswordLabel)
                                        .addComponent(usernameLabel)
                                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(questionLabel)
                                        .addComponent(questionComboBox, GroupLayout.PREFERRED_SIZE, 350,
                                                GroupLayout.PREFERRED_SIZE)
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
                                .addGap(56, 56, 56)
                                .addComponent(forgotPasswordLabel)
                                .addGap(18, 18, 18)
                                .addComponent(usernameLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(questionLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(questionComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
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

    private void handleResetPassword() {
        String username = usernameField.getText();
        String selectedQuestion = (String) questionComboBox.getSelectedItem();
        String answer = answerField.getText();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate the security question answer
        boolean isAnswerCorrect = validateAnswer(username, selectedQuestion, answer);

        if (!isAnswerCorrect) {
            JOptionPane.showMessageDialog(this, "Security answer incorrect. Please try again.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
            return;
        }

        // Implement your logic to reset the password
        // This is just a placeholder for demonstration purposes
        JOptionPane.showMessageDialog(this, "Password has been reset successfully!");
        goToLoginPage();
    }

    private boolean validateAnswer(String username, String question, String answer) {
        // Implement your logic to validate the security answer
        // This is just a placeholder for demonstration purposes
        return "correctAnswer".equalsIgnoreCase(answer);
    }

    private void goToLoginPage() {
        Login loginPage = new Login();
        loginPage.setVisible(true);
        dispose(); // Close the current frame
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForgotPassword().setVisible(true);
            }
        });
    }
}
