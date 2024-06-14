package src.admin;

import javax.swing.*;
import src.admin.product.ProductPage;
import src.admin.return_product.ReturnPage;
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
        setUndecorated(false);
        setLocationRelativeTo(null);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Admin Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(70, 30, 200, 30);
        panel.add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Product", "Inventory", "Reports", "Records", "Return", "Maintenance", "Help",
                "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 50;
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
                    System.out.println(button.getText() + " button clicked");

                    switch (button.getText()) {
                        case "Product":
                            ProductPage productPage = new ProductPage();
                            productPage.setVisible(true);
                            dispose();
                            break;
                        case "Inventory":
                            // Open Inventory Page
                            break;
                        case "Reports":
                            // Open Reports Page
                            break;
                        case "Records":
                            // Open Records Page
                            break;
                        case "Return":
                            dispose();
                            ReturnPage.main(new String[] {});
                            break;
                        case "Maintenance":
                            // Open Maintenance Page
                            break;
                        case "Help":
                            // Open Help Page
                            break;
                        case "About":
                            // Open About Page
                            break;
                        case "Logout":
                            dispose();
                            Login.main(new String[] {});
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
                new AdminMenu().setVisible(true);
            }
        });
    }
}
