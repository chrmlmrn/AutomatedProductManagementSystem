package src.admin.maintenance;

import javax.swing.*;

import src.about.AboutMainPage;
import src.admin.AdminMenu;
import src.admin.maintenance.MaintenancePage;
import src.admin.product.ProductPage;
import src.admin.return_product.ReturnPage;
import src.customcomponents.RoundedButton;
import src.help.HelpPage;
import src.login.Login;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaintenancePage extends JFrame {

    public MaintenancePage() {
        initComponents();
    }

    private void initComponents() {
        // Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Maintenance Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(70, 30, 500, 30);
        panel.add(titleLabel);

        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        backButton.addActionListener(e -> {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.setVisible(true);
        });
        panel.add(backButton);

        // Buttons
        String[] buttonLabels = { "User Maintenance", "System Maintenance" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 300;
        int gap = 20;

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((getWidth() - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth,
                    buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    switch (button.getText()) {
                        case "User Maintenance":
                            UserMaintenance userMaintenance = new UserMaintenance();
                            userMaintenance.setVisible(true);
                            dispose();
                            break;
                        case "System Maintenance":
                            // Open Maintenance Page
                            // SystemMaintenance systemMaintenance = new SystemMaintenance();
                            // systemMaintenance.setVisible(true);
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

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                for (Component component : panel.getComponents()) {
                    if (component instanceof RoundedButton) {
                        RoundedButton button = (RoundedButton) component;
                        int x = (frameWidth - buttonWidth) / 2;
                        button.setBounds(x, button.getY(), buttonWidth, buttonHeight);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MaintenancePage().setVisible(true);
            }
        });
    }
}
