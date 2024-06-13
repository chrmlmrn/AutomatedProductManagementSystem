package src.admin;

import javax.swing.*;
import src.admin.product.ProductPage;
import src.customcomponents.RoundedButton;
import src.login.Login;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {

    public AdminMenu() {
        initComponents();
    }

    private void initComponents() {
        // Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // Remove window borders and title bar
        setLocationRelativeTo(null); // Center the frame on the screen

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Admin Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        panel.add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Product", "Inventory", "Reports", "Records", "Return", "Maintenance", "Help",
                "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;

        for (String label : buttonLabels) {
            RoundedButton button = new RoundedButton(label);
            button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(button.getText() + " button clicked");

                    switch (button.getText()) {
                        case "Product":
                            ProductPage productPage = new ProductPage();
                            productPage.setVisible(true);
                            dispose(); // Close the current frame
                            break;
                        case "Inventory":
                            // Open Inventory Page
                            // Replace with appropriate functionality
                            break;
                        case "Reports":
                            // Open Reports Page
                            // Replace with appropriate functionality
                            break;
                        case "Records":
                            // Open Records Page
                            // Replace with appropriate functionality
                            break;
                        case "Return":
                            dispose();
                            Login.main(new String[] {}); // Open login window again
                            break;
                        case "Maintenance":
                            // Open Maintenance Page
                            // Replace with appropriate functionality
                            break;
                        case "Help":
                            // Open Help Page
                            // Replace with appropriate functionality
                            break;
                        case "About":
                            // Open About Page
                            // Replace with appropriate functionality
                            break;
                        case "Logout":
                            dispose();
                            Login.main(new String[] {}); // Open login window again
                            break;
                    }
                }
            });
            panel.add(button);
        }

        // Add panel to the frame
        getContentPane().add(panel, BorderLayout.CENTER);

        // Add a key listener to close the application
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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMenu().setVisible(true);
            }
        });
    }
}