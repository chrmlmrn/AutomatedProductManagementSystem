package admin.reports;

import javax.swing.*;

import admin.AdminMenu;
import admin.reports.sales.SalesReport;
import customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsPage extends JPanel {
    private JFrame mainFrame;

    public ReportsPage(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

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
                mainFrame.setContentPane(new AdminMenu(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 200, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Inventory", "Sales", "Return" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 300; // Center the buttons vertically
        int gap = 20; // Gap between buttons

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((getWidth() - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth,
                    buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (button.getText().equals("Inventory")) {
                        // Open Inventory
                        // InventoryPage inventoryPage = new InventoryPage();
                        // inventoryPage.setVisible(true);
                    } else if (button.getText().equals("Sales")) {
                        mainFrame.setContentPane(new SalesReport(mainFrame));
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    } else if (button.getText().equals("Return")) {
                        // Open Return
                        // ReturnPage returnPage = new ReturnPage();
                        // returnPage.setVisible(true);
                    }
                }
            });
            add(button);
        }

        // Add component listener to keep buttons centered
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                for (Component component : getComponents()) {
                    if (component instanceof RoundedButton) {
                        RoundedButton button = (RoundedButton) component;
                        int x = (frameWidth - buttonWidth) / 2;
                        button.setBounds(x, button.getY(), buttonWidth, buttonHeight);
                    }
                }
            }
        });
    }

}
