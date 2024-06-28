package admin.maintenance.backup_and_restore;

import customcomponents.RoundedButton;

import javax.swing.*;

import admin.maintenance.MaintenancePage;

import java.awt.*;

public class SystemMaintenance extends JPanel {
    private JFrame mainFrame;

    public SystemMaintenance(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use null layout for absolute positioning
        setBackground(Color.WHITE);

        int panelWidth = 1200; // Assuming the width of the panel
        int panelHeight = 800; // Assuming the height of the panel

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
            mainFrame.setContentPane(new MaintenancePage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Title Label
        JLabel titleLabel = new JLabel("System Maintenance");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 20, 500, 30);
        add(titleLabel);

        int buttonWidth = 200;
        int buttonHeight = 50;
        int gap = 30; // Gap between buttons
        int labelHeight = 40; // Height for description labels

        int totalHeight = 3 * buttonHeight + 3 * gap + 3 * labelHeight;
        int startY = (panelHeight - totalHeight) / 2 + 50; // Center vertically, adjusting for the title and back button

        int centerX = (panelWidth - buttonWidth) / 2;

        // Backup button and label
        RoundedButton backupButton = new RoundedButton("Backup");
        backupButton.setFont(new Font("Arial", Font.BOLD, 16));
        backupButton.setBorder(BorderFactory.createEmptyBorder());
        backupButton.setBackground(new Color(30, 144, 255));
        backupButton.setForeground(Color.WHITE);
        backupButton.setFocusPainted(false);
        backupButton.setBounds(centerX, startY, buttonWidth, buttonHeight);
        add(backupButton);

        backupButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to create a backup?", "Backup Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                Backup.backupDatabase("root", "root", "lavega_store_db",
                        "C:\\Users\\ismai\\OneDrive\\Documents\\SoftEng\\AutomatedProductManagementSystem\\database\\backup\\backup_lavega_store_db.sql");
            }
        });

        JLabel backupLabel = new JLabel(
                "<html>Backup: Save a copy of the current database.<br>This will help you restore data later.</html>");
        backupLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        backupLabel.setForeground(new Color(24, 26, 78));
        backupLabel.setBounds(centerX + buttonWidth + 20, startY, 400, labelHeight);
        add(backupLabel);

        // Restore button and label
        RoundedButton restoreButton = new RoundedButton("Restore");
        restoreButton.setFont(new Font("Arial", Font.BOLD, 16));
        restoreButton.setBorder(BorderFactory.createEmptyBorder());
        restoreButton.setBackground(new Color(30, 144, 255));
        restoreButton.setForeground(Color.WHITE);
        restoreButton.setFocusPainted(false);
        restoreButton.setBounds(centerX, startY + buttonHeight + gap, buttonWidth, buttonHeight);
        add(restoreButton);

        restoreButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to restore the database? This will overwrite existing data.",
                    "Restore Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                Restore.restoreDatabase("root", "root", "lavega_store_db",
                        "C:\\Users\\ismai\\OneDrive\\Documents\\SoftEng\\AutomatedProductManagementSystem\\database\\backup\\backup_lavega_store_db.sql");
            }
        });

        JLabel restoreLabel = new JLabel(
                "<html>Restore: Overwrite the current database with a saved backup.<br>Be cautious as this will replace existing data.</html>");
        restoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        restoreLabel.setForeground(new Color(24, 26, 78));
        restoreLabel.setBounds(centerX + buttonWidth + 20, startY + buttonHeight + gap, 400, labelHeight);
        add(restoreLabel);

        // Reset button and label
        RoundedButton resetButton = new RoundedButton("Reset to Default");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBorder(BorderFactory.createEmptyBorder());
        resetButton.setBackground(new Color(30, 144, 255));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setBounds(centerX, startY + 2 * (buttonHeight + gap), buttonWidth, buttonHeight);
        add(resetButton);

        resetButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to reset the database? This will delete all records.", "Reset Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                Reset.resetDatabase("root", "root", "lavega_store_db");
            }
        });

        JLabel resetLabel = new JLabel(
                "<html>Reset to Default: Clear all records from the database.<br>This action is irreversible, so proceed with caution.</html>");
        resetLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        resetLabel.setForeground(new Color(24, 26, 78));
        resetLabel.setBounds(centerX + buttonWidth + 20, startY + 2 * (buttonHeight + gap), 400, labelHeight);
        add(resetLabel);
    }
}
