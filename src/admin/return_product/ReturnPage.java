package src.admin.return_product;

import javax.swing.*;
import java.awt.*;

public class ReturnPage {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null); // Center the frame

        // Create a main panel for centering the blue container
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

        // Create the blue container panel
        JPanel containerPanel = new JPanel();
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(400, 200)); // Adjusted size to match the image
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Create constraints for layout inside the blue container
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adjust spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add back button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> System.out.println("Back button clicked"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(backButton, gbc);

        // Add title label
        JLabel returnLabel = new JLabel("Return");
        returnLabel.setFont(new Font("Arial", Font.BOLD, 24));
        returnLabel.setForeground(new Color(24, 26, 78));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(returnLabel, gbc);

        // Create labels and text fields for the product barcode and quantity
        JLabel barcodeLabel = new JLabel("Enter product barcode");
        barcodeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        barcodeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        containerPanel.add(barcodeLabel, gbc);

        JTextField barcodeField = new JTextField(15);
        barcodeField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        containerPanel.add(barcodeField, gbc);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        quantityLabel.setForeground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        containerPanel.add(quantityLabel, gbc);

        // Create a panel for quantity with minus and plus buttons
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        quantityPanel.setBackground(new Color(30, 144, 255));

        JTextField quantityField = new JTextField("0", 3);
        quantityField.setPreferredSize(new Dimension(50, 30));
        quantityField.setHorizontalAlignment(JTextField.CENTER);

        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("Arial", Font.BOLD, 16));
        minusButton.setPreferredSize(new Dimension(45, 30));
        minusButton.setFocusPainted(false);
        minusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > 0) {
                quantityField.setText(String.valueOf(quantity - 1));
            }
        });

        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Arial", Font.BOLD, 16));
        plusButton.setPreferredSize(new Dimension(45, 30));
        plusButton.setFocusPainted(false);
        plusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityField.getText());
            quantityField.setText(String.valueOf(quantity + 1));
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityField);
        quantityPanel.add(plusButton);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        containerPanel.add(quantityPanel, gbc);

        // Add confirm and cancel buttons
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(Color.WHITE);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFocusPainted(false);
        confirmButton.setPreferredSize(new Dimension(150, 40));
        confirmButton.addActionListener(e -> System.out.println("Confirm button clicked"));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.addActionListener(e -> {
            System.out.println("Cancel button clicked");
            frame.dispose(); // Close the current frame
        });

        // Create button panel with horizontal box layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // Add the button panel to the container
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(buttonPanel, gbc);

        // Add the blue container panel to the main panel
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 1;
        mainGbc.gridwidth = 5;
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
