package src.cashier;

import javax.swing.*;

import src.help.HelpPage;
import src.about.AboutMainPage;
import src.cashier.POS.ScanProduct;
import src.customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierMenu {
    public static void main(String[] args) {
        // frame
        JFrame frame = new JFrame("Cashier Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false); // Remove window borders and title bar
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Cashier Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(70, 30, 200, 30); // Adjusted y-coordinate from 10 to 30
        titleLabel.setForeground(new Color(24, 26, 78));
        panel.add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Scan Product", "Generate Sales", "Help", "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 150; // Starting Y position for the first button
        int gap = 20; // Gap between buttons

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1925 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            // Add action listener for the buttons
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(button.getText() + " button clicked");
                    frame.dispose(); // Close the current frame

                    switch (button.getText()) {
                        case "Scan Product":
                            // Open Scan Product Page
                            openScanProductPage();
                            break;
                        case "Generate Sales":
                            // Open Generate Sales Page
                            // GenerateSalesPage.main(new String[] {});
                            break;
                        case "Help":
                            // Open Help Page
                            HelpPage.main(new String[] {});
                            break;
                        case "About":
                            // Open About Page
                            AboutMainPage.main(new String[] {});
                            break;
                        case "Logout":
                            // Logout and close the application
                            System.exit(0);
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

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub

    }

    private static void openScanProductPage() {
        System.out.println("Opening Scan Product Page...");
        ScanProduct scanProduct = new ScanProduct();
        scanProduct.setVisible(true);
    }
}
