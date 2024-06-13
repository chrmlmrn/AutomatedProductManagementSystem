package src.admin.product;

import com.toedter.calendar.JDateChooser;

import src.admin.product.barcode.BarcodeGenerator;
import src.customcomponents.RoundedButton;
import src.customcomponents.RoundedPanel;
import src.customcomponents.RoundedTextField;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class AddProduct {
    private static JFrame frame;

    public static void main(String[] args) {
        // Create the frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(1600, 900);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setUndecorated(false); // Remove window borders and title bar

        // Create a main panel for centering the blue container
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.setContentPane(mainPanel);

        // Create the blue container panel with rounded corners
        RoundedPanel containerPanel = new RoundedPanel(30); // Adjust the radius as needed
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setPreferredSize(new Dimension(650, 800)); // Increased width and height size
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Create constraints for layout inside the blue container
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Reduced spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Add the title label
        JLabel titleLabel = new JLabel("Enter Product Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(titleLabel, gbc);

        // Reset grid width
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Create labels and text fields for the form
        String[] labels = {
                "Product Barcode", "Product Code", "Product Name", "Product Category",
                "Product Price", "Product Size", "Product Quantity", "Product Expiration Date",
                "Product Type", "Supplier Name"
        };

        // Create option lists for the combo boxes
        String[] categories = { "Category 1", "Category 2", "Category 3" };
        String[] statuses = { "Available", "Out of Stock", "On Hold", "Discontinued" };
        String[] types = { "Fast", "Slow" };

        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem("Available"); // Set default value to "Available"
        statusComboBox.setEnabled(false); // Disable the combo box so the user can't change it
        JComboBox<String> typeComboBox = new JComboBox<>(types);

        // Create the date picker
        JDateChooser expirationDateChooser = new JDateChooser();
        expirationDateChooser.setPreferredSize(new Dimension(200, 30));

        List<JTextField> textFields = new ArrayList<>();
        RoundedTextField barcodeTextField = null;

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = i * 2 + 1;
            gbc.gridwidth = 1;
            containerPanel.add(label, gbc);

            if (i == 3 || i == 8) { // Check if it's one of the option fields
                JComboBox<String> comboBox = (i == 3) ? categoryComboBox : typeComboBox;
                gbc.gridx = 0;
                gbc.gridy = i * 2 + 2;
                gbc.gridwidth = 2;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                containerPanel.add(comboBox, gbc);
            } else if (i == 7) { // Product Expiration Date
                gbc.gridx = 0;
                gbc.gridy = i * 2 + 2;
                gbc.gridwidth = 2;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                containerPanel.add(expirationDateChooser, gbc);
            } else if (i == 10) { // Supplier Name
                JTextField textField = new JTextField(); // Text field for supplier name
                textField.setPreferredSize(new Dimension(500, 30)); // Adjusted width and height to fit new container
                textFields.add(textField);
                gbc.gridx = 0;
                gbc.gridy = i * 2 + 2;
                gbc.gridwidth = 2;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                containerPanel.add(textField, gbc);
            } else {
                RoundedTextField textField = new RoundedTextField(5, 20); // Adjust the radius and columns as needed
                textField.setPreferredSize(new Dimension(500, 30)); // Adjusted width and height to fit new container
                textFields.add(textField);
                if (i == 0) {
                    barcodeTextField = textField;
                }
                gbc.gridx = 0;
                gbc.gridy = i * 2 + 2;
                gbc.gridwidth = 2;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                containerPanel.add(textField, gbc);
            }
        }

        // Add buttons
        RoundedButton addButton = new RoundedButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(300, 40));
        addButton.addActionListener(e -> {
            if (validateFields(textFields)) {
                System.out.println("Add button clicked");
            }
        });

        // Barcode Generation Button
        RoundedButton generateBarcodeButton = new RoundedButton("Generate Barcode");
        generateBarcodeButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateBarcodeButton.setBackground(Color.WHITE);
        generateBarcodeButton.setForeground(Color.BLACK);
        generateBarcodeButton.setFocusPainted(false);
        generateBarcodeButton.setPreferredSize(new Dimension(300, 40));
        RoundedTextField finalBarcodeTextField = barcodeTextField;
        generateBarcodeButton.addActionListener(e -> {
            if (finalBarcodeTextField != null) {
                String barcodeData = finalBarcodeTextField.getText();
                if (barcodeData.length() == 12 && barcodeData.matches("\\d+")) {
                    // Generate barcode
                    char checksum = BarcodeGenerator.calculateChecksum(barcodeData);
                    String fullBarcodeData = barcodeData + checksum;

                    // Define file path for the barcode image
                    String barcodeFilePath = "C:\\Users\\ADMIN\\Documents\\SampleBarcode\\generated_barcode.png";

                    // Generate barcode image
                    BarcodeGenerator.generateEAN13Barcode(fullBarcodeData, barcodeFilePath);

                    // Load generated image
                    try {
                        BufferedImage barcodeImage = ImageIO.read(new File(barcodeFilePath));
                        showBarcodeDialog(barcodeImage);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error loading barcode image: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid 12-digit barcode.");
                }
            }
        });

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(300, 40));
        cancelButton.addActionListener(e -> {
            System.out.println("Cancel button clicked");
            frame.dispose(); // Close the current frame
            // Open ProductPage frame
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
        buttonPanel.add(addButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(generateBarcodeButton, gbcButton);

        gbcButton.gridx = 2;
        buttonPanel.add(cancelButton, gbcButton);

        // Add the button panel to the container
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing around button panel
        gbc.gridx = 0;
        gbc.gridy = labels.length * 2 + 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Ensure the panel does not stretch
        containerPanel.add(buttonPanel, gbc);

        // Add the container panel to the main panel
        mainPanel.add(containerPanel);

        // Display the frame
        frame.setVisible(true);
    }

    private static boolean validateFields(List<JTextField> textFields) {
        for (JTextField textField : textFields) {
            if (textField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private static void showBarcodeDialog(BufferedImage barcodeImage) {
        // Desired width and height for the image
        int imageWidth = 350;
        int imageHeight = 300;

        // Scale the barcode image to the desired size
        ImageIcon scaledIcon = new ImageIcon(
                barcodeImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH));

        // Create a dialog to display the barcode image
        JDialog dialog = new JDialog(frame, "Generated Barcode", true);
        dialog.setSize(500, 500); // Adjusted height to accommodate label and spacing
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS
        // alignment
        dialog.setUndecorated(true); // Remove window borders and title bar
        dialog.getContentPane().setBackground(new Color(30, 144, 255)); // Set background color for the entire dialog

        // Create a label for the title
        JLabel titleLabel = new JLabel("Generated Barcode");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Add padding
        dialog.add(titleLabel);

        // Create a panel to hold the barcode image label and center it
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(new Color(30, 144, 255)); // Set background color for the image panel
        JLabel barcodeLabel = new JLabel(scaledIcon); // Use the scaled image icon
        imagePanel.add(barcodeLabel);
        dialog.add(imagePanel);

        // Add a button panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // Use BoxLayout with X_AXIS alignment
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add padding
        buttonPanel.setBackground(new Color(30, 144, 255)); // Set background color for the button panel
        dialog.add(buttonPanel);

        // Add space between the image and the buttons
        dialog.add(Box.createVerticalStrut(10)); // Add vertical space

        // Add a button to save the barcode
        RoundedButton saveButton = new RoundedButton("Save Barcode");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        saveButton.setBackground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(dialog);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(barcodeImage, "png", file);
                    JOptionPane.showMessageDialog(dialog, "Barcode saved successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Error saving barcode: " + ex.getMessage());
                }
            }
        });

        // Add a cancel button to close the dialog
        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        cancelButton.addActionListener(e -> dialog.dispose());
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Add buttons to the button panel
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the sides
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Add horizontal space between buttons
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the sides

        // Set dialog shape to rounded rectangle
        dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 30, 30));

        // Add a black border line around the dialog
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        dialog.getRootPane().setBorder(border);

        // Set the dialog to be visible
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
