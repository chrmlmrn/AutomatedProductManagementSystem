package admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import admin.about.AboutMainPage;
import admin.help.HelpPage;
import admin.maintenance.MaintenancePage;
import admin.product.ProductPage;
import admin.records.RecordsMainPage;
import admin.reports.ReportsPage;
import admin.return_product.ReturnPage;
import customcomponents.RoundedButton;
import login.Login;

public class AdminMenu extends JPanel {
    private JFrame mainFrame;

    public AdminMenu(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Admin Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(70, 30, 200, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Product", "Inventory", "Reports", "Records", "Return", "Maintenance", "Help",
                "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 100; // Starting Y position for the first button
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
                        case "Product":
                            openProductPage();
                            break;
                        case "Inventory":
                            // Open Inventory Page
                            break;
                        case "Reports":
                            openReportsPage();
                            break;
                        case "Records":
                            openRecordsPage();
                            break;
                        case "Return":
                            openReturnPage();
                            break;
                        case "Maintenance":
                            openMaintenancePage();
                            break;
                        case "Help":
                            openHelpPage();
                            break;
                        case "About":
                            openAboutPage();
                            break;
                        case "Logout":
                            confirmLogout();
                            break;
                    }
                }
            });

            add(button);
        }

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

    private void openProductPage() {
        mainFrame.setContentPane(new ProductPage(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openReportsPage() {
        mainFrame.setContentPane(new ReportsPage(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openRecordsPage() {
        mainFrame.setContentPane(new RecordsMainPage(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openReturnPage() {
        mainFrame.setContentPane(new ReturnPage(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openMaintenancePage() {
        mainFrame.setContentPane(new MaintenancePage(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openHelpPage() {
        mainFrame.setContentPane(new HelpPage(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openAboutPage() {
        mainFrame.setContentPane(new AboutMainPage(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openLoginPage() {
        mainFrame.setContentPane(new Login(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void confirmLogout() {
        int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            openLoginPage();
        }
    }

}
