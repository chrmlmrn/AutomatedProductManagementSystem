package src.admin.maintenance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;
import src.customcomponents.RoundedTextField;

import java.awt.*;

public class UserMaintenance {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Maintenance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(1600, 900);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setLayout(new BorderLayout());

        // Create a main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

        // Create the header panel with FlowLayout aligned to the left
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Add back arrow button
        JButton backButton = new JButton("\u2190");
        backButton.setFont(new Font("Arial", Font.PLAIN, 24));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        headerPanel.add(backButton);

        // Add title label
        JLabel titleLabel = new JLabel("Maintenance");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(24, 26, 78));
        headerPanel.add(titleLabel);

        // Create the blue container panel
        RoundedPanel containerPanel = new RoundedPanel(30);
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(1200, 600)); // Adjusted container size
        containerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        // Add the search label
        JLabel searchLabel = new JLabel("Enter username to update");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 18));
        searchLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(searchLabel, gbc);

        // Add the search text field
        RoundedTextField searchField = new RoundedTextField(20, 20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
        containerPanel.add(searchField, gbc);

        // Create a table to display user information
        String[] columnNames = { "Full name", "Email", "Contact no.", "Age", "Status", "Member since" };
        Object[][] data = {}; // No sample data

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable userTable = new JTable(model);
        userTable.setFont(new Font("Arial", Font.PLAIN, 16));
        userTable.setRowHeight(30);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(1100, 100)); // Adjusted table size

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 50, 20, 50);
        containerPanel.add(scrollPane, gbc);

        // Add buttons
        RoundedButton updateButton = new RoundedButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(Color.BLACK);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(150, 40));
        updateButton.addActionListener(e -> System.out.println("Update button clicked"));

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> System.out.println("Cancel button clicked"));

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 50, 20, 50);
        containerPanel.add(buttonPanel, gbc);

        // Add header and container to main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.anchor = GridBagConstraints.NORTHWEST; // Move header to the top left
        mainGbc.insets = new Insets(10, 10, 10, 10); // Adjust padding
        mainPanel.add(headerPanel, mainGbc);

        mainGbc.gridy = 1;
        mainGbc.anchor = GridBagConstraints.CENTER; // Center the container panel
        mainPanel.add(containerPanel, mainGbc);

        // Set up the frame layout
        frame.setVisible(true);

        // Add a key listener to close the application
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }
}