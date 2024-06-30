package cashier.about;

import javax.swing.*;

import cashier.CashierMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import customcomponents.RoundedButton;

public class AboutMainPage extends JPanel {
    private JFrame mainFrame;
    private int buttonWidth = 300;
    private int buttonHeight = 50;
    private int gap = 20;
    private int totalButtonHeight;
    private int buttonCount;
    private String uniqueUserId;

    public AboutMainPage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponent();
    }

    private void initComponent() {
        // Panel settings
        setLayout(null);
        setBackground(Color.WHITE);

        // Add back button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new CashierMenu(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Add title label
        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(70, 30, 100, 30);
        add(titleLabel);

        // Buttons for About The System, About the Developers
        String[] buttonLabels = { "About The System", "About the Developers" };
        buttonCount = buttonLabels.length;
        totalButtonHeight = buttonCount * buttonHeight + (buttonCount - 1) * gap;
        int startY = (getHeight() - totalButtonHeight) / 2; // Center vertically

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
                    // Perform action based on the button clicked
                    if (buttonText.equals("About The System")) {
                        mainFrame.setContentPane(new AboutTheSystemPage(mainFrame, uniqueUserId));
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    } else if (buttonText.equals("About the Developers")) {
                        mainFrame.setContentPane(new AboutTheDevelopersPage(mainFrame, uniqueUserId));
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    }

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
    }

}