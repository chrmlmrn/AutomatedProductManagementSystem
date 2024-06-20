package src.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.cashier.CashierMenu;
import src.cashier.POS.ScanProduct;
import src.customcomponents.RoundedButton;

public class HelpPage {
    public HelpPage(JFrame frame) {
        // Clear the frame
        frame.getContentPane().removeAll();
        frame.repaint();

        // Create a main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

        // Add back button (optional)
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of the current frame
                frame.dispose();
                // Open the CashierMenu.java
                CashierMenu.main(new String[] {});
            }
        });
        mainPanel.add(backButton);

        // Add title label
        JLabel titleLabel = new JLabel("Help");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 100, 30);
        mainPanel.add(titleLabel);

        // Buttons for FAQ, User Manual
        String[] buttonLabels = { "Frequently Asked Questions", "User Manual" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int gap = 20; // Gap between buttons

        int totalButtonHeight = buttonLabels.length * buttonHeight + (buttonLabels.length - 1) * gap;
        int startY = (750 - totalButtonHeight) / 2; // Center vertically

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1925 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonText = button.getText();
                    // Perform action based on the button clicked
                    if (buttonText.equals("Frequently Asked Questions")) {
                        new FAQPage(frame);
                    } else if (buttonText.equals("User Manual")) {
                        new UserManual(frame);
                    }
                }
            });
            mainPanel.add(button);
        }

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

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Help Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setUndecorated(false); // Keep window borders and title bar

        new HelpPage(frame);
    }
}
