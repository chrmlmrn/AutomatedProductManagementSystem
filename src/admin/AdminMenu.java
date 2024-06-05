package src.admin;

import javax.swing.*;

import src.admin.product.ProductPage;
import src.customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu {
    public static void main(String[] args) {
        // frame
        JFrame frame = new JFrame("Admin Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900); // Set frame size to 1600 x 900
        frame.setUndecorated(true); // Remove window borders and title bar
        frame.setLocationRelativeTo(null); // Center the frame on the screen

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
                            // Open Scan Product Page
                            // ScanProductPage.main(new String[] {});
                            frame.dispose(); // Close the current frame
                            // Open Product Page
                            ProductPage.main(new String[] {});
                            break;
                        case "Inventory":
                            // Open Generate Sales Page
                            // GenerateSalesPage.main(new String[] {});
                            break;
                        case "Reports":
                            // Open Help Page
                            // HelpPage.main(new String[] {});
                            break;
                        case "Records":
                            // Open About Page
                            // AboutPage.main(new String[] {});
                            break;
                        case "Return":
                            // Logout and close the application
                            break;
                        case "Maintenance":
                            break;
                        case "Help":
                            break;
                        case "About":
                            break;
                        case "Logout":
                            frame.dispose();
                            break;
                    }

                }
            });
            panel.add(button);
        }

        // Add panel to the frame
        frame.getContentPane().add(panel);

        // Make the frame visible
        frame.setVisible(true);

        // Add a key listener to close the application
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }
}
