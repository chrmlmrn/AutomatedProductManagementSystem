package help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cashier.CashierMenu;
import customcomponents.RoundedButton;
import login.Login;

public class HelpPage extends JPanel {
    private JFrame mainFrame;

    public HelpPage(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use null layout for absolute positioning
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Help");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 300, 30);
        add(titleLabel);

        // Back button
        RoundedButton backButton = new RoundedButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        backButton.addActionListener(e -> navigateToCashierMenu());
        add(backButton);

        // Buttons for FAQ and User Manual
        String[] buttonLabels = { "Frequently Asked Questions", "User Manual" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int gap = 20; // Gap between buttons

        int totalButtonHeight = buttonLabels.length * buttonHeight + (buttonLabels.length - 1) * gap;
        int startY = (750 - totalButtonHeight) / 2; // Center vertically

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);
            button.setBounds((1925 - buttonWidth) / 2, startY + (buttonHeight + gap) * i, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    navigateToPage(button.getText());
                }
            });

            add(button);
        }
    }

    private void navigateToCashierMenu() {
        mainFrame.setContentPane(new CashierMenu(mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void navigateToPage(String buttonText) {
        if (buttonText.equals("Frequently Asked Questions")) {
            mainFrame.setContentPane(new FAQPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        } else if (buttonText.equals("User Manual")) {
            mainFrame.setContentPane(new UserManual(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        }

    }
}
