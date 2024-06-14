package src.cashier;

import javax.swing.*;
import src.cashier.POS.ScanProduct;
import src.customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierMenu {

    private JFrame frame;

    public CashierMenu() {
        initialize();
    }

    private void initialize() {
        // Create the frame
        frame = new JFrame("Cashier Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setLocationRelativeTo(null);

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Cashier Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(70, 30, 200, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        panel.add(titleLabel);

        // Buttons
        String[] buttonLabels = { "Scan Product", "Generate Sales", "Help", "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 200;
        int gap = 20;

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((frame.getWidth() - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth,
                    buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            // Add action listener for the buttons
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(button.getText() + " button clicked");

                    switch (button.getText()) {
                        case "Scan Product":
                            openScanProductPage();
                            break;
                        case "Generate Sales":
                            openGenerateSalesPage();
                            break;
                        case "Help":
                            openHelpPage();
                            break;
                        case "About":
                            openAboutPage();
                            break;
                        case "Logout":
                            System.exit(0);
                            break;
                    }
                }
            });

            panel.add(button);
        }

        // Add panel to the frame
        frame.getContentPane().add(panel);

        // Add a key listener to close the application
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = frame.getWidth();
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

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void openScanProductPage() {
        System.out.println("Opening Scan Product Page...");
        ScanProduct scanProduct = new ScanProduct();
        scanProduct.setVisible(true);
    }

    private void openGenerateSalesPage() {
        System.out.println("Opening Generate Sales Page...");
    }

    private void openHelpPage() {
        System.out.println("Opening Help Page...");
    }

    private void openAboutPage() {
        System.out.println("Opening About Page...");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                CashierMenu cashierMenu = new CashierMenu();
                cashierMenu.setVisible(true);
            }
        });
    }
}
