package admin.product;

import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class UpdateProduct {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setUndecorated(true); // Remove window borders and title bar

        // Create a main panel for centering the blue container
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

        // Create the blue container panel
        RoundedPanel containerPanel = new RoundedPanel(30);
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(800, 800)); // Increased width size
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 20)); // Padding

        // Create constraints for layout inside the blue container
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Reduced spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add the title label
        JLabel titleLabel = new JLabel("Search for a product to update");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(titleLabel, gbc);

        // Create a panel for the search bar and icon
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(30, 144, 255)); // Match container panel background

        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(0, 0, 0, 0); // No padding inside search panel

        // Add search field with reduced left padding
        JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(10, 30));
        searchField.setMargin(new Insets(0, 5, 0, 100)); // Reduced left margin
        searchGbc.gridx = 1;
        searchGbc.gridy = 0;
        searchPanel.add(searchField, searchGbc);

        // Add search panel to container panel
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        containerPanel.add(searchPanel, gbc);

        // Reset grid width
        gbc.gridwidth = 2;

        // Create labels and text fields for the current product details
        String[] currentLabels = {
                "Current Product Number", "Current Product Name", "Current Product Category",
                "Current Product Price", "Current Product Stocks", "Current Product Expiration Date",
                "Current Product Status", "Current Supplier Name"
        };

        JTextField[] currentTextFields = new JTextField[currentLabels.length];

        for (int i = 0; i < currentLabels.length; i++) {
            JLabel label = new JLabel(currentLabels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = i * 2 + 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.LINE_START;

            containerPanel.add(label, gbc);

            currentTextFields[i] = new JTextField(20);
            currentTextFields[i].setPreferredSize(new Dimension(250, 30));
            gbc.gridx = 0;
            gbc.gridy = i * 2 + 2;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            containerPanel.add(currentTextFields[i], gbc);
        }

        // Create labels and text fields for the updated product details
        String[] updateLabels = {
                "Update Product Number", "Update Name", "Update Category",
                "Update Product Price", "Update Product Stocks", "Update Product Expiration Date",
                "Update Product Status", "Update Supplier Name"
        };

        JTextField[] updateTextFields = new JTextField[updateLabels.length];

        for (int i = 0; i < updateLabels.length; i++) {
            JLabel label = new JLabel(updateLabels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = i * 2 + 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            containerPanel.add(label, gbc);

            updateTextFields[i] = new JTextField(20);
            updateTextFields[i].setPreferredSize(new Dimension(250, 30));
            gbc.gridx = 1;
            gbc.gridy = i * 2 + 2;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            containerPanel.add(updateTextFields[i], gbc);
        }

        // Add buttons
        RoundedButton updateButton = new RoundedButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(Color.WHITE);
        updateButton.setForeground(Color.BLACK);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(300, 40));
        updateButton.addActionListener(e -> System.out.println("Update button clicked"));

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(300, 40));
        cancelButton.addActionListener(e -> {
            System.out.println("Cancel button clicked");
            frame.dispose(); // Close the current frame
            // Open ProductPage
            ProductPage.main(new String[] {});
        });

        // Create button panel with GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(30, 144, 255));
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(10, 5, 10, 5);
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        gbcButton.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(updateButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(cancelButton, gbcButton);

        // Add the button panel to the container
        gbc.gridx = 0;
        gbc.gridy = currentLabels.length * 2 + 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(buttonPanel, gbc);

        // Add the blue container panel to the main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.anchor = GridBagConstraints.CENTER;
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
