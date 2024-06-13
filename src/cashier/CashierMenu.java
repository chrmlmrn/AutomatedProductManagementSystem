package src.cashier;

import javax.swing.*;

import src.customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierMenu {

    private JFrame frame;

    public CashierMenu() {
        initialize();
    }

    private void initialize() {
        // Create the frame
        frame = new JFrame("Cashier Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(1600, 900);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false); // Remove window borders and title bar
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a panel to hold the buttons
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
        int startY = 300; // Starting Y position for the first button
        int gap = 20; // Gap between buttons

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1600 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            // Add action listener for the buttons
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(button.getText() + " button clicked");

                    switch (button.getText()) {
                        case "Scan Product":
                            // Open Scan Product Page
                            openScanProductPage();
                            break;
                        case "Generate Sales":
                            // Open Generate Sales Page
                            openGenerateSalesPage();
                            break;
                        case "Help":
                            // Open Help Page
                            openHelpPage();
                            break;
                        case "About":
                            // Open About Page
                            openAboutPage();
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

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void openScanProductPage() {
        // Replace with code to open the Scan Product Page
        System.out.println("Opening Scan Product Page...");
        // Example: ScanProductPage scanProductPage = new ScanProductPage();
        // scanProductPage.setVisible(true);
    }

    private void openGenerateSalesPage() {
        // Replace with code to open the Generate Sales Page
        System.out.println("Opening Generate Sales Page...");
        // Example: GenerateSalesPage generateSalesPage = new GenerateSalesPage();
        // generateSalesPage.setVisible(true);
    }

    private void openHelpPage() {
        // Replace with code to open the Help Page
        System.out.println("Opening Help Page...");
        // Example: HelpPage helpPage = new HelpPage();
        // helpPage.setVisible(true);
    }

    private void openAboutPage() {
        // Replace with code to open the About Page
        System.out.println("Opening About Page...");
        // Example: AboutPage aboutPage = new AboutPage();
        // aboutPage.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                CashierMenu cashierMenu = new CashierMenu();
                cashierMenu.setVisible(true);
            }
        });
    }
}