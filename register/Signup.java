package register;

import javax.swing.*;

import customcomponents.RoundedPanel;
import customcomponents.RoundedButton;

import login.Login;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Signup extends JFrame {

        private JPanel mainPanel, formPanel;
        private JLabel registerLabel, detailsLabel, firstNameLabel, lastNameLabel, usernameLabel, passwordLabel,
                        termsLabel, haveAccountLabel, clickHereLabel;
        private JTextField firstNameField, lastNameField, usernameField;
        private JPasswordField passwordField;
        private JButton cashierButton, signInButton, adminButton;
        private JCheckBox termsCheckBox;

        public Signup() {
                initComponents();
        }

        private void initComponents() {
                // Initialize main panel
                mainPanel = new JPanel(new GridBagLayout());
                formPanel = createFormPanel();

                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setTitle("Signup Page");
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
                panel.setPreferredSize(new Dimension(400, 500));

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
                termsLabel = createLabel("I agree to the Terms and Conditions", new Font("Arial", Font.PLAIN, 12));
                termsLabel.setForeground(Color.WHITE);

                haveAccountLabel = createLabel("Already have an account?", new Font("Arial", Font.PLAIN, 12));
                haveAccountLabel.setForeground(Color.WHITE);
                clickHereLabel = createLabel("Click Here", new Font("Arial", Font.BOLD, 12), new Color(0, 102, 204));
                clickHereLabel.setForeground(Color.BLACK);

                firstNameField = new JTextField();
                lastNameField = new JTextField();
                usernameField = new JTextField();
                passwordField = new JPasswordField();
                termsCheckBox = new JCheckBox("I accept the Terms and Conditions");

                // Create buttons
                cashierButton = new RoundedButton("Cashier");
                adminButton = new RoundedButton("Admin");
                signInButton = new RoundedButton("Sign Up");
                signInButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                goToLoginPage();
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
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(registerLabel)
                                                                                .addComponent(detailsLabel)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                false)
                                                                                                                .addComponent(firstNameField)
                                                                                                                .addComponent(firstNameLabel,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(lastNameLabel)
                                                                                                                .addComponent(lastNameField,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                368,
                                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                                .addComponent(usernameLabel)
                                                                                .addComponent(usernameField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                368,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(passwordLabel)
                                                                                .addComponent(passwordField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                368,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(termsLabel)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(adminButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                160,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(cashierButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                160,
                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(signInButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                326,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(termsCheckBox)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(haveAccountLabel)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(clickHereLabel)))
                                                                .addContainerGap(25, Short.MAX_VALUE)));

                layout.setVerticalGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(56, 56, 56)
                                                                .addComponent(registerLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(detailsLabel)
                                                                .addGap(18, 18, 18)
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
                                                                .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
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
                                                                .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(termsLabel)
                                                                .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(adminButton)
                                                                                .addComponent(cashierButton))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(termsCheckBox)
                                                                .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(haveAccountLabel)
                                                                                .addComponent(clickHereLabel))
                                                                .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(signInButton, GroupLayout.PREFERRED_SIZE,
                                                                                37, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(38, 38, 38)));
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

        private JButton createButton(String text) {
                JButton button = new JButton(text);
                button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(0, 102, 204));
                return button;
        }

        private void goToLoginPage() {
                Login loginPage = new Login();
                loginPage.setVisible(true);
                dispose(); // Close the current frame
        }

        public static void main(String args[]) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new Signup().setVisible(true);
                        }
                });
        }
}
