package src.cashier.POS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.DatabaseUtil;
import src.customcomponents.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ScanProduct extends JFrame {
    private static DefaultTableModel productTableModel;
    private static JLabel subTotalLabel;
    private static JLabel totalLabel;
    private static Map<String, Product> productDatabase;
    private JTextField barcodeField;

    public ScanProduct() {
        // Initialize product database
        initializeProductDatabase();

        // Frame properties
        setTitle("Scan Products");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setLocationRelativeTo(null);

        // Initialize subTotalLabel and totalLabel
        subTotalLabel = new JLabel("Sub Total: 0.00");
        totalLabel = new JLabel("Total: 0.00");

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Scan Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(50, 30, 300, 40);
        titleLabel.setForeground(new Color(24, 26, 78));
        panel.add(titleLabel);

        // Back button (simulated with a label)
        JLabel backButton = new JLabel("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBounds(10, 30, 30, 30);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(backButton);

        // Barcode field
        barcodeField = new JTextField();
        barcodeField.setBounds(50, 80, 200, 30);
        barcodeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = barcodeField.getText().trim();
                if (!barcode.isEmpty()) {
                    addProduct(barcode);
                    barcodeField.setText("");
                }
            }
        });
        panel.add(barcodeField);

        // Product table
        String[] productColumns = { "PRODUCT CODE", "PRODUCT NAME", "PRICE", "QUANTITY" };
        productTableModel = new DefaultTableModel(productColumns, 0);
        JTable productTable = new JTable(productTableModel);
        productTable.getTableHeader().setBackground(new Color(30, 144, 255));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        productTable.setRowHeight(30);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBounds(50, 120, 700, 300);
        panel.add(productScrollPane);

        // Summary section
        String[] summaryColumns = { "PRODUCT NAME", "QUANTITY", "PRICE" };
        DefaultTableModel summaryTableModel = new DefaultTableModel(summaryColumns, 0);
        JTable summaryTable = new JTable(summaryTableModel);
        summaryTable.setRowHeight(30);
        JScrollPane summaryScrollPane = new JScrollPane(summaryTable);
        summaryScrollPane.setBounds(800, 100, 700, 150);
        panel.add(summaryScrollPane);

        // Total panel
        JPanel totalPanel = new JPanel(new GridLayout(3, 2));
        totalPanel.setBounds(800, 300, 700, 100);

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel discountValueLabel = new JLabel("0%");
        discountValueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel subTotalTextLabel = new JLabel("Sub Total:");
        subTotalTextLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel totalTextLabel = new JLabel("Total:");
        totalTextLabel.setFont(new Font("Arial", Font.BOLD, 16));

        totalPanel.add(discountLabel);
        totalPanel.add(discountValueLabel);
        totalPanel.add(subTotalTextLabel);
        totalPanel.add(subTotalLabel);
        totalPanel.add(totalTextLabel);
        totalPanel.add(totalLabel);

        panel.add(totalPanel);

        // Buttons
        RoundedButton discountButton = new RoundedButton("Discount");
        RoundedButton productCodeButton = new RoundedButton("Product Code");
        RoundedButton receiptButton = new RoundedButton("Receipt");

        discountButton.setBounds(50, 450, 200, 50);
        productCodeButton.setBounds(275, 450, 200, 50);
        receiptButton.setBounds(500, 450, 200, 50);

        discountButton.setBackground(new Color(30, 144, 255));
        discountButton.setForeground(Color.WHITE);
        discountButton.setFont(new Font("Arial", Font.BOLD, 16));
        discountButton.setBorder(BorderFactory.createEmptyBorder());

        productCodeButton.setBackground(new Color(30, 144, 255));
        productCodeButton.setForeground(Color.WHITE);
        productCodeButton.setFont(new Font("Arial", Font.BOLD, 16));
        productCodeButton.setBorder(BorderFactory.createEmptyBorder());

        receiptButton.setBackground(new Color(30, 144, 255));
        receiptButton.setForeground(Color.WHITE);
        receiptButton.setFont(new Font("Arial", Font.BOLD, 16));
        receiptButton.setBorder(BorderFactory.createEmptyBorder());

        panel.add(discountButton);
        panel.add(productCodeButton);
        panel.add(receiptButton);

        // Add action listeners for buttons
        discountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Discount button clicked");
            }
        });

        productCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productCode = JOptionPane.showInputDialog("Enter Product Code:");
                if (productCode != null && !productCode.isEmpty()) {
                    addProduct(productCode);
                }
            }
        });

        receiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReceiptUI();
            }
        });

        // Add panel to the frame
        getContentPane().add(panel);

        // Call updateTotals after initializing the labels
        updateTotals();

        // Add a key listener to close the application
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

    private static void initializeProductDatabase() {
        productDatabase = new HashMap<>();
        // You can add products from your SQL database here
    }

    private static void addProduct(String code) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT product_code, product_name, product_price, product_total_quantity " +
                    "FROM products JOIN inventory ON products.product_id = inventory.product_id " +
                    "WHERE barcode = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Object[] row = {
                        rs.getString("product_code"),
                        rs.getString("product_name"),
                        rs.getDouble("product_price"),
                        rs.getInt("product_total_quantity")
                };
                productTableModel.addRow(row);
                updateTotals();
            } else {
                JOptionPane.showMessageDialog(null, "Product not found for barcode: " + code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving product data from database");
        } finally {
            DatabaseUtil.close(rs);
            DatabaseUtil.close(stmt);
            DatabaseUtil.close(conn);
        }
    }

    private static void updateTotals() {
        double subtotal = 0.0;
        int rowCount = productTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            double price = (double) productTableModel.getValueAt(i, 2);
            int quantity = (int) productTableModel.getValueAt(i, 3);
            subtotal += price * quantity;
        }
        subTotalLabel.setText(String.format("Sub Total: %.2f", subtotal));
        totalLabel.setText(String.format("Total: %.2f", subtotal)); // Assuming no discount for simplicity
    }

    private static void showReceiptUI() {
        StringBuilder receipt = new StringBuilder("Receipt\n\n");
        for (int i = 0; i < productTableModel.getRowCount(); i++) {
            receipt.append(productTableModel.getValueAt(i, 1)).append(" - ");
            receipt.append(productTableModel.getValueAt(i, 3)).append(" @ ");
            receipt.append(productTableModel.getValueAt(i, 2)).append("\n");
        }
        receipt.append("\n");
        receipt.append(subTotalLabel.getText()).append("\n");
        receipt.append(totalLabel.getText()).append("\n");

        JTextArea textArea = new JTextArea(receipt.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Receipt", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScanProduct().setVisible(true);
            }
        });
    }
}
