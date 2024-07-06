package cashier;

import javax.swing.*;

import admin.records.userlogs.UserLogUtil;
import login.Login;
import cashier.POS.ScanProduct;
import cashier.about.AboutMainPage;
import cashier.help.HelpPage;
import customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierMenu extends JPanel {

    private JFrame mainFrame;
    private String uniqueUserId;

    public CashierMenu(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponent();
    }

    private void initComponent() {
        // panel settings
        setLayout(null);
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Cashier Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(70, 30, 200, 30); // Adjusted y-coordinate from 10 to 30
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Scan Product", "Help", "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 150; // Starting Y position for the first button
        int gap = 20; // Gap between buttons

        // Calculate the initial center position for the buttons horizontally
        int panelWidth = getWidth();
        int centerX = (panelWidth - buttonWidth) / 2;

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);

            int y = startY + (buttonHeight + gap) * i;

            button.setBounds(centerX, y, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            // Add action listener for the buttons
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (button.getText()) {
                        case "Scan Product":
                            // Open Scan Product Page
                            openScanProductPage();
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
                            // Logout and open login panel
                            confirmLogout();
                            break;
                    }
                }
            });

            add(button);
        }

        // Add a key listener to close the application
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

        // Add component listener to dynamically adjust button positions on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int newCenterX = (frameWidth - buttonWidth) / 2;
                for (Component component : getComponents()) {
                    if (component instanceof RoundedButton) {
                        RoundedButton button = (RoundedButton) component;
                        button.setBounds(newCenterX, button.getY(), buttonWidth, buttonHeight);
                    }
                }
            }
        });
    }

    private void openScanProductPage() {
        mainFrame.setContentPane(new ScanProduct(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openHelpPage() {
        mainFrame.setContentPane(new HelpPage(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openAboutPage() {
        mainFrame.setContentPane(new AboutMainPage(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openLoginPage() {
        mainFrame.setContentPane(new Login(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void confirmLogout() {
        int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            UserLogUtil.logUserAction(uniqueUserId, "User logged out");
            openLoginPage();
        }
    }

}
