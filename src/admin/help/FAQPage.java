package admin.help;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;

public class FAQPage extends JPanel {
    private JFrame mainFrame;

    public FAQPage(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use null layout for absolute positioning
        setBackground(Color.WHITE);

        // Add back button
        RoundedButton backButton = new RoundedButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new HelpPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Add title label
        JLabel titleLabel = new JLabel("Frequently Asked Questions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 400, 30);
        add(titleLabel);

        // Create a panel for the FAQ content
        JPanel faqContentPanel = new JPanel();
        faqContentPanel.setLayout(new BoxLayout(faqContentPanel, BoxLayout.Y_AXIS));
        faqContentPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(faqContentPanel);
        scrollPane.setBounds(100, 100, 1720, 800);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        // FAQ questions and answers
        String[][] faqs = {
                { "What happens if I forgot my password?",
                        "If you forgot your password, click on 'Forgot Password' on the login page and follow the instructions to reset it." },
                { "What functions do the system have?",
                        "The system provides inventory management, sales tracking, and reporting functionalities." },
                { "How can I scan a product?",
                        "To scan a product, use the barcode scanner provided and scan the product's barcode." },
                { "How do I log out?",
                        "To log out, click on your profile icon in the top right corner and select 'Log Out'." },
                { "What actions can the admin see?",
                        "The admin can see all user activities, including login times, changes made to inventory, and sales transactions." }
        };

        int buttonWidth = 1000;
        int buttonHeight = 50;
        int gap = 20;

        for (int i = 0; i < faqs.length; i++) {
            String question = faqs[i][0];
            String answer = faqs[i][1];

            JPanel faqPanel = new RoundedPanel(20);
            faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));
            faqPanel.setBackground(Color.WHITE);
            faqPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align
            faqPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            RoundedButton faqButton = new RoundedButton(question);
            faqButton.setFont(new Font("Arial", Font.PLAIN, 18));
            faqButton.setBackground(new Color(30, 144, 255));
            faqButton.setForeground(Color.WHITE);
            faqButton.setFocusPainted(false);
            faqButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            faqButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align
            faqPanel.add(faqButton);

            // Create a panel with GridBagLayout for the answer
            JPanel answerPanel = new JPanel(new GridBagLayout());
            answerPanel.setBackground(Color.WHITE);
            answerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            answerPanel.setVisible(false);
            answerPanel.setMaximumSize(new Dimension(buttonWidth, 100)); // Maximum size constraint

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);

            JLabel answerLabel = new JLabel(
                    "<html><div style='width: 800px; text-align: center;'>" + answer + "</div></html>");
            answerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            answerLabel.setForeground(Color.BLACK);
            answerPanel.add(answerLabel, gbc);

            faqButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    answerPanel.setVisible(!answerPanel.isVisible());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            });

            faqPanel.add(answerPanel);

            faqContentPanel.add(Box.createRigidArea(new Dimension(0, gap))); // Add space
            faqContentPanel.add(faqPanel);
        }
    }
}
