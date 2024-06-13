package src.admin;

import javax.swing.*;

import src.admin.product.ProductPage;
import src.customcomponents.RoundedButton;
import src.login.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {

    public AdminMenu() {
        initComponents();
    }

    private void initComponents() {
        // frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(1600, 900);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setUndecorated(false); // Remove window borders and title bar
        setLocationRelativeTo(null); // Center the frame on the screen

        // a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Admin Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(70, 30, 200, 30); // Adjusted y-coordinate from 10 to 30
        titleLabel.setForeground(new Color(24, 26, 78));
        panel.add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Product", "Inventory", "Reports", "Records", "Return", "Maintenance", "Help",
                "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 150; // Starting Y position for the first button
        int gap = 20; // Gap between buttons

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1600 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(button.getText() + " button clicked");

                    switch (button.getText()) {
                        case "Product":
                            // Open Product Page
                            ProductPage productPage = new ProductPage();
                            productPage.setVisible(true);
                            dispose(); // Close the current frame
                            break;
                        case "Inventory":
                            // Open Inventory Page
                            // Replace with appropriate functionality
                            break;
                        case "Reports":
                            // Open Reports Page
                            // Replace with appropriate functionality
                            break;
                        case "Records":
                            // Open Records Page
                            // Replace with appropriate functionality
                            break;
                        case "Return":
                            // Logout and close the application
                            dispose();
                            Login.main(new String[] {}); // Open login window again
                            break;
                        case "Maintenance":
                            // Open Maintenance Page
                            // Replace with appropriate functionality
                            break;
                        case "Help":
                            // Open Help Page
                            // Replace with appropriate functionality
                            break;
                        case "About":
                            // Open About Page
                            // Replace with appropriate functionality
                            break;
                        case "Logout":
                            // Logout and close the application
                            dispose();
                            Login.main(new String[] {}); // Open login window again
                            break;
                    }

                }
            });
            panel.add(button);
        }

        // Add panel to the frame
        getContentPane().add(panel);

        // Add a key listener to close the application
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMenu().setVisible(true);
            }
        });
    }
}