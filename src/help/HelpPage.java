package src.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.cashier.CashierMenu;
import src.customcomponents.RoundedButton;

public class HelpPage extends JFrame {
    private JPanel mainPanel;
    private JButton backButton;
    private JLabel titleLabel;

    public HelpPage() {
        initComponents();
    }

    private void initComponents() {
        getContentPane().removeAll();
        repaint();

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        addBackButton();
        addTitleLabel();
        addButtons();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setVisible(true);

        addEscapeKeyListener();
    }

    private void addBackButton() {
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
                dispose();
                CashierMenu.main(new String[] {});
            }
        });
        mainPanel.add(backButton);
    }

    private void addTitleLabel() {
        titleLabel = new JLabel("Help");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 100, 30);
        mainPanel.add(titleLabel);
    }

    private void addButtons() {
        String[] buttonLabels = { "Frequently Asked Questions", "User Manual" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int gap = 20;
        int totalButtonHeight = buttonLabels.length * buttonHeight + (buttonLabels.length - 1) * gap;
        int startY = (750 - totalButtonHeight) / 2;

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
                    String buttonText = button.getText();
                    if (buttonText.equals("Frequently Asked Questions")) {
                        new FAQPage(HelpPage.this);
                    } else if (buttonText.equals("User Manual")) {
                        new UserManual(HelpPage.this);
                    }
                }
            });

            mainPanel.add(button);
        }
    }

    private void addEscapeKeyListener() {
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HelpPage helpPage = new HelpPage();
                helpPage.setTitle("Help Main Page");
            }
        });
    }
}
