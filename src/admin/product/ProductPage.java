package src.admin.product;

// ProductPage.java
import javax.swing.*;

import src.admin.AdminMenu;
import src.customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductPage {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900); // Set frame size to 1600 x 900
        frame.setUndecorated(true); // Remove window borders and title bar
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a panel to hold the buttons and the back arrow
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Back arrow button
        JButton backButton = new JButton(" < ");
        backButton.setBounds(20, 30, 50, 30);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back button clicked");
                frame.dispose(); // Close the current frame
                // Open Admin Menu
                AdminMenu.main(new String[] {});
            }
        });

        // Title Label
        JLabel titleLabel = new JLabel("Product");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 200, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        panel.add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Add Product", "Update Product" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = (900 - (buttonLabels.length * (buttonHeight + 10))) / 2; // Center the buttons vertically
        int gap = 20; // Gap between buttons

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1600 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(button.getText() + " button clicked");
                    frame.dispose(); // Close the current frame
                    if (button.getText().equals("Add Product")) {
                        // Open AddProduct
                        AddProduct.main(new String[] {});
                    } else if (button.getText().equals("Update Product")) {
                        // Open UpdateProduct
                        UpdateProduct.main(new String[] {});
                    }
                }
            });
            panel.add(button);
        }

        // Add components to the panel
        panel.add(backButton);
        panel.add(titleLabel);

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
