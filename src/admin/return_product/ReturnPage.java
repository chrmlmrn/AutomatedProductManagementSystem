package admin.return_product;

import admin.AdminMenu;
import database.DatabaseUtil;
import admin.records.userlogs.UserLogUtil;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnPage extends JPanel {
    private JTextField productCodeField;
    private JTextField quantityField;
    private JComboBox<String> reasonComboBox;
    private ReturnDAO returnDAO;
    private JFrame mainFrame;
    private String uniqueUserId;

    public ReturnPage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        returnDAO = new ReturnDAO();
        this.uniqueUserId = uniqueUserId;

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(null); // Use absolute positioning
        setBackground(Color.WHITE);

        // Title and Back Button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new AdminMenu(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JLabel titleLabel = new JLabel("Return");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(100, 30, 500, 30);
        add(titleLabel);

        // Rounded Blue Panel
        RoundedPanel bluePanel = new RoundedPanel(30);
        bluePanel.setBackground(new Color(30, 144, 255));
        bluePanel.setBounds(380, 160, 600, 350); // Increased the height to fit the Return Policy button
        bluePanel.setLayout(null); // Use absolute positioning within the panel
        add(bluePanel);

        // Labels and text fields for product code and quantity
        JLabel productCodeLabel = new JLabel("Enter Product Code");
        productCodeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        productCodeLabel.setForeground(Color.WHITE);
        productCodeLabel.setBounds(50, 30, 200, 30);
        bluePanel.add(productCodeLabel);

        productCodeField = new JTextField("", 15);
        productCodeField.setBounds(250, 30, 200, 30);
        bluePanel.add(productCodeField);

        RoundedButton verifyButton = new RoundedButton("Verify");
        verifyButton.setFont(new Font("Arial", Font.BOLD, 14));
        verifyButton.setBackground(Color.WHITE);
        verifyButton.setForeground(Color.BLACK);
        verifyButton.setBounds(460, 30, 90, 30);
        verifyButton.setFocusPainted(false);
        verifyButton.addActionListener(e -> verifyProduct());
        bluePanel.add(verifyButton);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setBounds(50, 80, 200, 30);
        bluePanel.add(quantityLabel);

        quantityField = new JTextField("", 5);
        quantityField.setBounds(250, 80, 40, 30);
        bluePanel.add(quantityField);

        JLabel reasonLabel = new JLabel("Return Reason");
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reasonLabel.setForeground(Color.WHITE);
        reasonLabel.setBounds(50, 130, 200, 30);
        bluePanel.add(reasonLabel);

        // Combo box for return reasons
        String[] reasons = { "Wrong Item", "Damaged Item", "Expired Item" };
        reasonComboBox = new JComboBox<>(reasons);
        reasonComboBox.setBounds(250, 130, 200, 30);
        bluePanel.add(reasonComboBox);

        // Confirm and Cancel buttons
        RoundedButton confirmButton = new RoundedButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(Color.WHITE);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setBounds(100, 200, 150, 40);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(e -> processReturn());
        bluePanel.add(confirmButton);

        RoundedButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBounds(300, 200, 150, 40);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> {
            mainFrame.setContentPane(new AdminMenu(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        bluePanel.add(cancelButton);

        // Add Return Policy button inside the blue panel
        RoundedButton returnPolicyButton = new RoundedButton("Return Policy");
        returnPolicyButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnPolicyButton.setBackground(Color.WHITE);
        returnPolicyButton.setForeground(Color.BLACK);
        returnPolicyButton.setBounds(200, 270, 150, 40);
        returnPolicyButton.setFocusPainted(false);
        returnPolicyButton.addActionListener(e -> {
            mainFrame.setContentPane(new ReturnPolicyPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        bluePanel.add(returnPolicyButton);
    }

    private void verifyProduct() {
        String productCode = productCodeField.getText().trim();
        if (productCode.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter the product code.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String productId = returnDAO.getProductIdByCode(productCode);
        if (productId != null) {
            JOptionPane.showMessageDialog(mainFrame, "Product verified successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Product does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processReturn() {
        String productCode = productCodeField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        int quantity;

        if (productCode.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter both product code and quantity.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid quantity. Please enter a number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String reason = (String) reasonComboBox.getSelectedItem();
        String productId = returnDAO.getProductIdByCode(productCode);

        if (productId != null) {
            if (returnDAO.processReturn(productId, quantity, reason)) {
                JOptionPane.showMessageDialog(mainFrame, "Return processed successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                UserLogUtil.logUserAction(uniqueUserId, "Returned a Product" + productCode);

            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to process return.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Product does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ReturnDAO class to handle database operations
    class ReturnDAO {
        public String getProductIdByCode(String productCode) {
            String sql = "SELECT product_id FROM products WHERE product_code = ?";
            try (Connection conn = DatabaseUtil.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, productCode);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("product_id");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public boolean processReturn(String productId, int quantity, String reason) {
            String sqlUpdateInventory = "UPDATE inventory SET product_total_quantity = product_total_quantity + ? WHERE product_id = ?";
            String sqlInsertReturn = "INSERT INTO return_products (product_id, return_quantity, return_reason_id, return_date) VALUES (?, ?, ?, NOW())";
            String reasonId = getReasonId(reason);

            try (Connection conn = DatabaseUtil.getConnection();
                    PreparedStatement stmtUpdateInventory = conn.prepareStatement(sqlUpdateInventory);
                    PreparedStatement stmtInsertReturn = conn.prepareStatement(sqlInsertReturn)) {

                conn.setAutoCommit(false); // Start transaction

                if (reason.equals("Wrong Item")) {
                    // Update inventory only if the reason is "Wrong Item"
                    stmtUpdateInventory.setInt(1, quantity);
                    stmtUpdateInventory.setString(2, productId);
                    stmtUpdateInventory.executeUpdate();
                }

                // Insert return record
                stmtInsertReturn.setString(1, productId);
                stmtInsertReturn.setInt(2, quantity);
                stmtInsertReturn.setString(3, reasonId);
                stmtInsertReturn.executeUpdate();

                conn.commit(); // Commit transaction

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                try (Connection conn = DatabaseUtil.getConnection()) {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        }

        private String getReasonId(String reason) {
            switch (reason) {
                case "Wrong Item":
                    return "WRO";
                case "Damaged Item":
                    return "DEF";
                case "Expired Item":
                    return "EXP";
                default:
                    return null;
            }
        }
    }
}
