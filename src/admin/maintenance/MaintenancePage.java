package admin.maintenance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import customcomponents.RoundedButton;
import admin.AdminMenu;
import admin.maintenance.backup_and_restore.SystemMaintenance;

public class MaintenancePage extends JPanel {

    private JFrame mainFrame;
    private String uniqueUserId;

    public MaintenancePage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use null layout for absolute positioning
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Maintenance Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(100, 30, 500, 30);
        add(titleLabel);

        // Back button
        RoundedButton backButton = new RoundedButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new AdminMenu(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Buttons for User Maintenance, System Maintenance
        String[] buttonLabels = { "User Maintenance", "System Maintenance" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int gap = 20; // Gap between buttons

        int totalButtonHeight = buttonLabels.length * buttonHeight + (buttonLabels.length - 1) * gap;
        int startY = (750 - totalButtonHeight) / 2; // Center vertically

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1350 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonText = button.getText();
                    // Perform action based on the button clicked
                    if (buttonText.equals("User Maintenance")) {
                        mainFrame.setContentPane(new UserMaintenance(mainFrame, uniqueUserId));
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    } else if (buttonText.equals("System Maintenance")) {
                        mainFrame.setContentPane(new SystemMaintenance(mainFrame, uniqueUserId));
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    }
                }
            });
            add(button);
        }
    }
}
