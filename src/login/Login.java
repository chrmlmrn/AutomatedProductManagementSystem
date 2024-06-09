package src.login;

import javax.swing.*;

import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;
import src.register.Signup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JLabel welcomeLabel, signInLabel, usernameLabel, passwordLabel, forgotPasswordLabel, noAccountLabel,
                        registerLabel;
        private JButton signInButton;
        private JPanel mainPanel, centerPanel;

        public Login() {
                initComponents();
        }

        private void initComponents() {
                mainPanel = new JPanel(new GridBagLayout());
                centerPanel = createCenterPanel();

                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setTitle("Login Page");
                setSize(1600, 900); // Set the size of the window to 1920x1080 pixels
                setUndecorated(true); // Remove window borders and title bar
                setLocationRelativeTo(null); // Center the frame on the screen

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.CENTER;
                mainPanel.add(centerPanel, gbc);

                add(mainPanel);
                // pack(); // No need to call pack since setSize sets the size explicitly
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

                // Add mouse listener to forgotPasswordLabel to open ForgotPassword page
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
                                                                                                .addComponent(registerLabel)))
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
                // Handle sign in action
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // TODO: Implement your authentication logic here
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
        }

        private void openSignUpPage() {
                Signup signupPage = new Signup();
                signupPage.setVisible(true);
                dispose(); // Close the current login frame
        }

        private void openForgotPasswordPage() {
                ForgotPassword forgotPasswordPage = new ForgotPassword();
                forgotPasswordPage.setVisible(true);
                dispose(); // Close the current login frame
        }

        public static void main(String args[]) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new Login().setVisible(true);
                        }
                });
        }
}
