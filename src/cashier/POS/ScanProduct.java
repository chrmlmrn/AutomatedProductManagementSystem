package cashier.POS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cashier.CashierMenu;
import customcomponents.RoundedButton;
import database.DatabaseUtil;
import help.HelpPage;
import login.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ScanProduct extends JPanel {
    private DefaultTableModel productTableModel;
    private DefaultTableModel soldProductTableModel;
    private JLabel subTotalLabel;
    private JLabel totalLabel;
    private Map<String, Product> productDatabase;
    private JTextField barcodeField;
    private JFrame mainFrame;

    public ScanProduct(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        initializeProductDatabase();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Scan Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 300, 30);
        add(titleLabel);

        // Back button (simulated with a label)
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
            mainFrame.setContentPane(new CashierMenu(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Barcode field
        barcodeField = new JTextField();
        barcodeField.setBounds(50, 80, 200, 30);
        barcodeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = barcodeField.getText().trim();
                if (!barcode.isEmpty()) {
                    addProduct(barcode);
                }
            }
        });
        add(barcodeField);

        // Product table (Left table for products in database)
        String[] productColumns = { "PRODUCT CODE", "PRODUCT NAME", "SIZE", "PRICE", "QUANTITY" };
        productTableModel = new DefaultTableModel(productColumns, 0);
        JTable productTable = new JTable(productTableModel);
        productTable.getTableHeader().setBackground(new Color(30, 144, 255));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        productTable.setRowHeight(30);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBounds(50, 120, 700, 300);
        add(productScrollPane);

        // Summary table (Right table for products being sold)
        String[] summaryColumns = { "PRODUCT NAME", "QUANTITY", "PRICE" };
        soldProductTableModel = new DefaultTableModel(summaryColumns, 0);
        JTable summaryTable = new JTable(soldProductTableModel);
        summaryTable.setRowHeight(30);
        JScrollPane summaryScrollPane = new JScrollPane(summaryTable);
        summaryScrollPane.setBounds(800, 100, 500, 300);
        add(summaryScrollPane);

        // Total panel
        JPanel totalPanel = new JPanel(new GridLayout(3, 2));
        totalPanel.setBounds(800, 420, 500, 100);

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
        subTotalLabel = new JLabel("Sub Total: 0.00");
        subTotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(subTotalLabel);
        totalPanel.add(totalTextLabel);
        totalLabel = new JLabel("Total: 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);

        add(totalPanel);

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

        add(discountButton);
        add(productCodeButton);
        add(receiptButton);

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

    private void initializeProductDatabase() {
        productDatabase = new HashMap<>();
        // You can add products from your SQL database here
    }

    private void addProduct(String code) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT product_code, product_name, product_size, product_price, product_total_quantity " +
                    "FROM products JOIN inventory ON products.product_id = inventory.product_id " +
                    "WHERE barcode = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Object[] row = {
                        rs.getString("product_code"),
                        rs.getString("product_name"),
                        rs.getString("product_size"),
                        rs.getDouble("product_price"),
                        rs.getInt("product_total_quantity")
                };
                productTableModel.addRow(row);

                // Add to sold products table
                Object[] soldRow = {
                        rs.getString("product_name"),
                        1, // Initial quantity sold is 1
                        rs.getDouble("product_price")
                };
                soldProductTableModel.addRow(soldRow);

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

    private void updateTotals() {
        double subtotal = 0.0;
        int rowCount = soldProductTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            double price = (double) soldProductTableModel.getValueAt(i, 2);
            int quantity = (int) soldProductTableModel.getValueAt(i, 1);
            subtotal += price * quantity;
        }
        subTotalLabel.setText(String.format("Sub Total: %.2f", subtotal));
        totalLabel.setText(String.format("Total: %.2f", subtotal)); // Assuming no discount for simplicity
    }

    private void showReceiptUI() {
        StringBuilder receipt = new StringBuilder("Receipt\n\n");
        for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
            receipt.append(soldProductTableModel.getValueAt(i, 0)).append(" - ");
            receipt.append(soldProductTableModel.getValueAt(i, 1)).append(" @ ");
            receipt.append(soldProductTableModel.getValueAt(i, 2)).append("\n");
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
}