package admin.help;

import javax.swing.*;

import admin.AdminMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import customcomponents.RoundedButton;

public class HelpPage extends JPanel {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton backButton;
    private JLabel titleLabel;
    private int buttonWidth = 300;
    private int buttonHeight = 50;
    private int gap = 20;
    private int totalButtonHeight;
    private int startY;
    private String uniqueUserId;

    public HelpPage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        // Panel settings
        setLayout(null);
        setBackground(Color.WHITE);

        // Add back button
        backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new AdminMenu(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Add title label
        titleLabel = new JLabel("Help");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(70, 30, 100, 30);
        add(titleLabel);

        // Buttons for Frequently Asked Questions and User Manual
        String[] buttonLabels = { "Frequently Asked Questions", "User Manual" };
        totalButtonHeight = buttonLabels.length * buttonHeight + (buttonLabels.length - 1) * gap;
        startY = (getHeight() - totalButtonHeight) / 2;

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
                    String buttonText = button.getText();
                    if (buttonText.equals("Frequently Asked Questions")) {
                        mainFrame.setContentPane(new FAQPage(mainFrame, uniqueUserId));
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    } else if (buttonText.equals("User Manual")) {
                        mainFrame.setContentPane(new UserManual(mainFrame, uniqueUserId));
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    }
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            });
            add(button);
        }

        // Add component listener to dynamically adjust button positions on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int newCenterX = (frameWidth - buttonWidth) / 2;
                int newStartY = (getHeight() - totalButtonHeight) / 2; // Recalculate vertical center
                Component[] components = getComponents();
                int buttonIndex = 0;
                for (Component component : components) {
                    if (component instanceof RoundedButton) {
                        RoundedButton button = (RoundedButton) component;
                        button.setBounds(newCenterX, newStartY + (buttonHeight + gap) * buttonIndex, buttonWidth,
                                buttonHeight);
                        buttonIndex++;
                    }
                }
            }
        });

        // Add key listener to close the application with ESC key
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

}