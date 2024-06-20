package src.about;

import javax.swing.*;
import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutTheSystemPage {
    public AboutTheSystemPage(JFrame frame) {
        // Clear the frame
        frame.getContentPane().removeAll();
        frame.repaint();

        // Create a main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

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
                // Dispose of the current frame
                frame.dispose();
                // Open the AboutMainPage.java
                AboutMainPage.main(new String[] {});
            }
        });
        mainPanel.add(backButton);

        // Add title label
        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 100, 30);
        mainPanel.add(titleLabel);

        // Content panel
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(30, 144, 255));
        contentPanel.setBounds((1925 - 700) / 2, (1000 - 400) / 2, 700, 400); // Center the panel
        mainPanel.add(contentPanel);

        // Add About Us title inside content panel
        JLabel aboutUsLabel = new JLabel("About the System");
        aboutUsLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Thicker font for emphasis
        aboutUsLabel.setForeground(Color.WHITE);
        aboutUsLabel.setBounds(30, 20, 640, 30); // Left justified
        contentPanel.add(aboutUsLabel);

        // Add text area for content
        JTextArea textArea = new JTextArea();
        textArea.setText("The Automated Product Management System for Lavega Store utilizes barcode technology to "
                + "streamline product management and sales processes. The system provides real-time inventory "
                + "updates, sales automation, and comprehensive reporting tools to enhance operational efficiency "
                + "and accuracy. With robust security features, the system ensures data integrity and secure access.");
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 144, 255));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 60, 640, 270); // Adjust size and position as needed
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        // Add return button inside content panel
        JButton returnButton = new RoundedButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnButton.setBounds(570, 340, 100, 30); // Right justified and properly inside the blue box
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(new Color(24, 26, 78));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutMainPage(frame);
            }
        });
        contentPanel.add(returnButton);

        frame.setVisible(true);
    }
}
