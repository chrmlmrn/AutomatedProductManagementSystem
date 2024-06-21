package src.admin.records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.admin.AdminMenu;
import src.admin.records.product.ProductRecords;
import src.admin.records.user.UserRecords;
import src.customcomponents.RoundedButton;

public class RecordsMainPage extends JFrame {
    public RecordsMainPage() {
        initComponents();
        setTitle("Records Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Center the frame
        setUndecorated(false); // Keep window borders and title bar
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

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
        mainPanel.add(backButton);

        JLabel titleLabel = new JLabel("Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 150, 30);
        mainPanel.add(titleLabel);

        String[] buttonLabels = { "Product", "User", "Sales", "Return" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int gap = 20;
        int totalButtonHeight = buttonLabels.length * buttonHeight + (buttonLabels.length - 1) * gap;
        int startY = (750 - totalButtonHeight) / 2;

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1600 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonText = button.getText();
                    if (buttonText.equals("Product")) {
                        // Open Product page
                        ProductRecords productRecords = new ProductRecords();
                        productRecords.setVisible(true);
                    } else if (buttonText.equals("User")) {
                        // Open User page
                        UserRecords userRecords = new UserRecords();
                        userRecords.setVisible(true);
                    } else if (buttonText.equals("Sales")) {
                        // Open Sales page
                        // new SalesPage(); // Replace with actual class
                    } else if (buttonText.equals("Return")) {
                        // Open Return page
                        // new ReturnPage(); // Replace with actual class
                    }
                }
            });
            mainPanel.add(button);
        }

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordsMainPage());
    }
}
