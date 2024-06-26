package admin.return_product;

import database.DatabaseUtil;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import admin.AdminMenu;

public class ReturnPage extends JPanel {
    private JFrame mainFrame;
    private JTextField productIdField;
    private JTextField quantityField;
    private JComboBox<String> reasonComboBox;
    private ReturnDAO returnDAO;

    public ReturnPage(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        returnDAO = new ReturnDAO();
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Back arrow button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new AdminMenu(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(backButton, gbc);

        // Title Label
        JLabel returnLabel = new JLabel("Return");
        returnLabel.setFont(new Font("Arial", Font.BOLD, 24));
        returnLabel.setForeground(new Color(24, 26, 78));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(returnLabel, gbc);

        // Blue container panel
        JPanel containerPanel = new JPanel();
        containerPanel.setBackground(new Color(30, 144, 255));
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        GridBagConstraints cpc = new GridBagConstraints();
        cpc.insets = new Insets(10, 10, 10, 10);
        cpc.fill = GridBagConstraints.HORIZONTAL;

        // Add product ID label and field
        JLabel productIdLabel = new JLabel("Enter Product ID");
        productIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        productIdLabel.setForeground(Color.WHITE);
        cpc.gridx = 0;
        cpc.gridy = 0;
        containerPanel.add(productIdLabel, cpc);

        productIdField = new JTextField(15);
        cpc.gridx = 1;
        cpc.gridwidth = 1;
        containerPanel.add(productIdField, cpc);

        // Add verify button next to product ID field
        JButton verifyButton = new JButton("Verify");
        verifyButton.setFont(new Font("Arial", Font.BOLD, 14));
        verifyButton.setBackground(Color.WHITE);
        verifyButton.setForeground(Color.BLACK);
        verifyButton.setFocusPainted(false);
        verifyButton.addActionListener(e -> verifyProduct());
        cpc.gridx = 2;
        cpc.gridwidth = 1;
        containerPanel.add(verifyButton, cpc);

        // Add quantity label and field
        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        quantityLabel.setForeground(Color.WHITE);
        cpc.gridx = 0;
        cpc.gridy = 1;
        cpc.gridwidth = 1;
        containerPanel.add(quantityLabel, cpc);

        quantityField = new JTextField(5);
        cpc.gridx = 1;
        cpc.gridwidth = 2;
        containerPanel.add(quantityField, cpc);

        // Add reason label and combo box
        JLabel reasonLabel = new JLabel("Return Reason");
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reasonLabel.setForeground(Color.WHITE);
        cpc.gridx = 0;
        cpc.gridy = 2;
        cpc.gridwidth = 1;
        containerPanel.add(reasonLabel, cpc);

        String[] reasons = { "Wrong Item", "Damaged Item", "Expired Item" };
        reasonComboBox = new JComboBox<>(reasons);
        cpc.gridx = 1;
        cpc.gridwidth = 2;
        containerPanel.add(reasonComboBox, cpc);

        // Add confirm and cancel buttons
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(Color.WHITE);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(e -> processReturn());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> {
            mainFrame.setContentPane(new AdminMenu(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        cpc.gridx = 0;
        cpc.gridy = 3;
        cpc.gridwidth = 3;
        cpc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(buttonPanel, cpc);

        // Create and add the rounded "Return Policy" button on the right side
        JButton returnPolicyButton = new JButton("Return Policy") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 144, 255));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(new Color(30, 144, 255));
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        returnPolicyButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnPolicyButton.setForeground(Color.WHITE);
        returnPolicyButton.setContentAreaFilled(false);
        returnPolicyButton.setFocusPainted(false);
        returnPolicyButton.setPreferredSize(new Dimension(150, 40));
        returnPolicyButton.addActionListener(e -> {
            mainFrame.setContentPane(new ReturnPolicyPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(containerPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(returnPolicyButton, gbc);

        // Add a key listener to close the application
        mainFrame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

    private void verifyProduct() {
        String productId = productIdField.getText().trim();
        if (productId.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a product ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (returnDAO.isProductExists(productId)) {
            JOptionPane.showMessageDialog(mainFrame, "Product exists. You can proceed with the return.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Product does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processReturn() {
        String productId = productIdField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        int quantity;

        if (productId.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter both product ID and quantity.", "Error",
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

        if (returnDAO.isProductExists(productId)) {
            if (returnDAO.processReturn(productId, quantity, reason)) {
                JOptionPane.showMessageDialog(mainFrame, "Return processed successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
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
        public boolean isProductExists(String productId) {
            String sql = "SELECT COUNT(*) FROM products WHERE product_id = ?";
            try (Connection conn = DatabaseUtil.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, productId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public boolean processReturn(String productId, int quantity, String reason) {
            String sqlUpdateInventory = "UPDATE inventory SET product_total_quantity = product_total_quantity + ? WHERE product_id = ?";
            String sqlInsertReturn = "INSERT INTO return_products (product_id, return_quantity, return_reason_id, return_date, return_status_id) VALUES (?, ?, ?, NOW(), 'PRO')";
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
