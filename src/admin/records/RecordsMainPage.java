package admin.records;

import admin.AdminMenu;
import admin.records.inventory.InventoryRecords;
import admin.records.product.ProductRecords;
import admin.records.return_records.ReturnRecords;
import admin.records.sales.SalesRecord;
import admin.records.user.UserRecords; // Replace with your actual AdminMenu class
import admin.records.userlogs.UserLog;
import customcomponents.RoundedButton;
import java.awt.*; // Replace with your actual ProductRecords class
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // Replace with your actual UserRecords class
import javax.swing.*;

public class RecordsMainPage extends JPanel {

    private JFrame mainFrame;
    private String uniqueUserId;

    public RecordsMainPage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBounds(0, 0, getWidth(), 100);
        add(headerPanel);

        // Back button
        RoundedButton backButton = new RoundedButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new AdminMenu(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        headerPanel.add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(100, 30, 200, 30);
        headerPanel.add(titleLabel);

        add(headerPanel);

        // Buttons
        String[] buttonLabels = { "Product", "User", "Inventory", "Sales", "Return", "User Logs" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int gap = 20; // Gap between buttons

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(buttonLabels.length, 1, 0, gap));
        buttonsPanel.setOpaque(false);

        for (String label : buttonLabels) {
            RoundedButton button = new RoundedButton(label);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (button.getText()) {
                        case "Product":
                            openProductRecords();
                            break;
                        case "User":
                            openUserRecords();
                            break;
                        case "Inventory":
                            openInventoryRecords();
                            break;
                        case "Sales":
                            openSalesRecords();
                            break;
                        case "Return":
                            openReturnRecords();
                            break;
                        case "User Logs":
                            openUserlogs();
                            break;
                    }
                }
            });

            buttonsPanel.add(button);
        }

        add(buttonsPanel);
        buttonsPanel.setBounds((getWidth() - buttonWidth) / 2,
                (getHeight() - (buttonHeight * buttonLabels.length + gap * (buttonLabels.length - 1))) / 2, buttonWidth,
                buttonHeight * buttonLabels.length + gap * (buttonLabels.length - 1));

        // Add component listener to dynamically adjust button positions on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                int newCenterX = (frameWidth - buttonWidth) / 2;
                int newCenterY = (frameHeight - (buttonHeight * buttonLabels.length + gap * (buttonLabels.length - 1)))
                        / 2;

                buttonsPanel.setBounds(newCenterX, newCenterY, buttonWidth,
                        buttonHeight * buttonLabels.length + gap * (buttonLabels.length - 1));
                headerPanel.setBounds(0, 0, frameWidth, 100);
            }
        });
    }

    private void openProductRecords() {
        mainFrame.setContentPane(new ProductRecords(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openUserRecords() {
        mainFrame.setContentPane(new UserRecords(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openUserlogs() {
        mainFrame.setContentPane(new UserLog(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openSalesRecords() {
        mainFrame.setContentPane(new SalesRecord(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openInventoryRecords() {
        mainFrame.setContentPane(new InventoryRecords(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openReturnRecords() {
        mainFrame.setContentPane(new ReturnRecords(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}