package src.admin.product;

import javax.swing.*;

import src.admin.AdminMenu;
import src.cashier.CashierMenu;
import src.customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductPage extends JFrame {

    public ProductPage() {
        initComponents();
    }

    private void initComponents() {
        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);

        // Create a panel to hold the buttons and the back arrow
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

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
                System.out.println("Back button clicked");
                dispose(); // Close the current frame
                // Open Admin Menu
                new AdminMenu().setVisible(true); // Replace "admin" with actual username
            }
        });

        // Title Label
        JLabel titleLabel = new JLabel("Product");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 200, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        panel.add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Add Product", "Update Product" };
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
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(button.getText() + " button clicked");
                    dispose(); // Close the current frame
                    if (button.getText().equals("Add Product")) {
                        // Open AddProduct
                        // AddProduct addProduct = new AddProduct();
                        // addProduct.setVisible(true);
                        AddProduct.main(new String[] {});
                    } else if (button.getText().equals("Update Product")) {
                        // Open UpdateProduct
                        // Replace with appropriate functionality
                    }
                }
            });
            panel.add(button);
        }

        // Add components to the panel
        panel.add(backButton);
        panel.add(titleLabel);

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

        // Add component listener to keep buttons centered
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
        // Example usage: create an instance of ProductPage
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductPage().setVisible(true);
            }
        });
    }
}
