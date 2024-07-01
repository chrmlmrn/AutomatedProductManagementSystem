package cashier.POS;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import admin.reports.sales.Transaction;
import admin.reports.sales.TransactionService;
import cashier.CashierMenu;

import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import database.DatabaseUtil;
import admin.records.userlogs.UserLogUtil;
import customcomponents.RoundedButton;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Random;

public class ScanProduct extends JPanel {
    private DefaultTableModel productTableModel;
    private DefaultTableModel soldProductTableModel;
    private JLabel subTotalLabel;
    private JLabel totalLabel;
    private JLabel discountValueLabel; // New label for discount value
    private JTextField barcodeField;
    private JTable summaryTable;
    private Map<String, Integer> originalStockMap;
    private double discountPercentage = 0.0;
    private String receiptNumber;
    private String referenceNumber;
    private String currentDate;
    private String currentTime;
    private double subtotal;
    private double discountAmount;
    private double vatAmount;
    private double total;

    private JFrame mainFrame;
    private String uniqueUserId;

    public ScanProduct(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        originalStockMap = new HashMap<>();

        // Initialize subTotalLabel, totalLabel, and discountValueLabel
        subTotalLabel = new JLabel("Sub Total: 0.00");
        subTotalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        totalLabel = new JLabel("Total: 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        discountValueLabel = new JLabel("0%"); // Initialize discount value label
        discountValueLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Set panel properties
        setLayout(null);
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Scan Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(100, 30, 300, 40);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        JButton backButton = new JButton(" < ");
        backButton.setBounds(20, 30, 50, 30);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new CashierMenu(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Barcode field
        barcodeField = new JTextField();
        barcodeField.setBounds(50, 80, 300, 50);
        barcodeField.setFont(new Font("Arial", Font.PLAIN, 30));
        barcodeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = barcodeField.getText().trim();
                if (!code.isEmpty()) {
                    addProduct(code, "barcode");
                }
            }
        });
        add(barcodeField);

        // Clear Barcode Button
        RoundedButton clearBarcodeButton = new RoundedButton("Clear Barcode");
        clearBarcodeButton.setBounds(360, 80, 200, 50);
        clearBarcodeButton.setBackground(new Color(30, 144, 255));
        clearBarcodeButton.setForeground(Color.WHITE);
        clearBarcodeButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearBarcodeButton.setBorder(BorderFactory.createEmptyBorder());
        clearBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                barcodeField.setText("");
                barcodeField.requestFocus();
            }
        });
        add(clearBarcodeButton);

        // Product table (Left table for products being scanned)
        String[] productColumns = { "PRODUCT CODE", "PRODUCT NAME", "SIZE", "PRICE", "TOTAL QUANTITY" };
        productTableModel = new DefaultTableModel(productColumns, 0);
        JTable productTable = new JTable(productTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the left table non-editable
            }
        };
        productTable.getTableHeader().setBackground(new Color(30, 144, 255));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        productTable.setRowHeight(30);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBounds(50, 150, 700, 500);
        add(productScrollPane);

        // Summary table (Right table for products being sold)
        String[] summaryColumns = { "PRODUCT NAME", "QUANTITY", "PRICE", "ADJUST", "DELETE" };
        soldProductTableModel = new DefaultTableModel(summaryColumns, 0);
        summaryTable = new JTable(soldProductTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Only the "ADJUST" and "DELETE" columns should be editable
            }
        };
        summaryTable.setRowHeight(30);
        summaryTable.getColumn("ADJUST").setCellRenderer(new AdjustCellRenderer());
        summaryTable.getColumn("ADJUST").setCellEditor(new AdjustCellEditor());
        summaryTable.getColumn("DELETE").setCellRenderer(new DeleteButtonRenderer());
        summaryTable.getColumn("DELETE")
                .setCellEditor(new DeleteButtonEditor(new JCheckBox(), soldProductTableModel, productTableModel));
        JScrollPane summaryScrollPane = new JScrollPane(summaryTable);
        summaryScrollPane.setBounds(800, 150, 500, 300);
        add(summaryScrollPane);

        // Total panel
        JPanel totalPanel = new JPanel(new GridLayout(3, 2));
        totalPanel.setBounds(800, 450, 500, 100);
        totalPanel.setBackground(Color.WHITE);

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalPanel.add(discountLabel);
        totalPanel.add(discountValueLabel); // Add the discount value label to the panel
        totalPanel.add(new JLabel("Sub Total:"));
        totalPanel.add(subTotalLabel);
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(totalLabel);

        add(totalPanel);

        // Buttons
        RoundedButton discountButton = new RoundedButton("Discount");
        RoundedButton productCodeButton = new RoundedButton("Product Code");
        RoundedButton checkoutButton = new RoundedButton("Checkout");

        discountButton.setBounds(50, 680, 200, 50);
        productCodeButton.setBounds(275, 680, 200, 50);
        checkoutButton.setBounds(800, 600, 480, 50); // Expand the checkout button to take over void button's space

        discountButton.setBackground(new Color(30, 144, 255));
        discountButton.setForeground(Color.WHITE);
        discountButton.setFont(new Font("Arial", Font.BOLD, 16));
        discountButton.setBorder(BorderFactory.createEmptyBorder());

        productCodeButton.setBackground(new Color(30, 144, 255));
        productCodeButton.setForeground(Color.WHITE);
        productCodeButton.setFont(new Font("Arial", Font.BOLD, 16));
        productCodeButton.setBorder(BorderFactory.createEmptyBorder());

        checkoutButton.setBackground(new Color(30, 144, 255));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutButton.setBorder(BorderFactory.createEmptyBorder());

        add(discountButton);
        add(productCodeButton);
        add(checkoutButton);

        // Add action listeners for buttons
        discountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyDiscount();
            }
        });

        productCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productCode = JOptionPane.showInputDialog("Enter Product Code:");
                if (productCode != null && !productCode.isEmpty()) {
                    addProduct(productCode, "product_code");
                    barcodeField.setText("");
                }
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTransaction();
                updateInventory(); // Update the inventory after saving the transaction
                int response = JOptionPane.showConfirmDialog(null, "Would you like to print the receipt?",
                        "Print Receipt", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    showReceiptUI();
                    resetPOS();
                    UserLogUtil.logUserAction(uniqueUserId, "Printed a receipt");
                } else if (response == JOptionPane.NO_OPTION) {
                    resetPOS();
                }
                // No action is needed if the response is JOptionPane.CLOSED_OPTION
            }
        });

        // Call updateTotals after initializing the labels
        updateTotals();

        // Add a key listener to close the application
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

        // Add a table model listener to update the inventory when quantity changes
        soldProductTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    updateInventoryTable();
                    updateTotals();
                }
            }
        });
    }

    private void addProduct(String code, String type) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            String sql;
            if (type.equals("barcode")) {
                sql = "SELECT product_code, product_name, product_size, product_price, product_total_quantity " +
                        "FROM products JOIN inventory ON products.product_id = inventory.product_id " +
                        "WHERE barcode = ?";
            } else {
                sql = "SELECT product_code, product_name, product_size, product_price, product_total_quantity " +
                        "FROM products JOIN inventory ON products.product_id = inventory.product_id " +
                        "WHERE product_code = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String productCode = rs.getString("product_code");
                String productName = rs.getString("product_name");
                String productSize = rs.getString("product_size");
                double productPrice = rs.getDouble("product_price");
                int productTotalQuantity = rs.getInt("product_total_quantity");

                originalStockMap.put(productCode, productTotalQuantity);

                boolean foundInSoldProducts = false;

                for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
                    if (soldProductTableModel.getValueAt(i, 0).equals(productName)) {
                        int quantity = (int) soldProductTableModel.getValueAt(i, 1);
                        soldProductTableModel.setValueAt(quantity + 1, i, 1);
                        foundInSoldProducts = true;
                        break;
                    }
                }

                if (!foundInSoldProducts) {
                    Object[] soldRow = { productName, 1, productPrice, "Adjust", "Delete" };
                    soldProductTableModel.addRow(soldRow);
                }

                boolean foundInProductTable = false;

                for (int i = 0; i < productTableModel.getRowCount(); i++) {
                    if (productTableModel.getValueAt(i, 0).equals(productCode)) {
                        int totalSoldQuantity = 0;
                        for (int j = 0; j < soldProductTableModel.getRowCount(); j++) {
                            if (soldProductTableModel.getValueAt(j, 0).equals(productName)) {
                                totalSoldQuantity = (int) soldProductTableModel.getValueAt(j, 1);
                                break;
                            }
                        }
                        int newQuantity = productTotalQuantity - totalSoldQuantity;
                        productTableModel.setValueAt(newQuantity, i, 4);
                        foundInProductTable = true;
                        break;
                    }
                }

                if (!foundInProductTable) {
                    int totalSoldQuantity = 0;
                    for (int j = 0; j < soldProductTableModel.getRowCount(); j++) {
                        if (soldProductTableModel.getValueAt(j, 0).equals(productName)) {
                            totalSoldQuantity = (int) soldProductTableModel.getValueAt(j, 1);
                            break;
                        }
                    }
                    Object[] productRow = { productCode, productName, productSize, productPrice,
                            productTotalQuantity - totalSoldQuantity };
                    productTableModel.addRow(productRow);
                }

                updateTotals();
            } else {
                JOptionPane.showMessageDialog(null, "Product not found for " + type + ": " + code);
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

    private void updateProductTable(int row, int difference) {
        String productName = (String) soldProductTableModel.getValueAt(row, 0);
        for (int i = 0; i < productTableModel.getRowCount(); i++) {
            if (productTableModel.getValueAt(i, 1).equals(productName)) {
                String productCode = (String) productTableModel.getValueAt(i, 0);
                int originalQuantity = originalStockMap.get(productCode);
                int soldQuantity = (int) soldProductTableModel.getValueAt(row, 1);
                int newQuantity = originalQuantity - soldQuantity;
                productTableModel.setValueAt(newQuantity, i, 4);
                break;
            }
        }
    }

    private void updateTotals() {
        subtotal = 0.0;
        int rowCount = soldProductTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            double price = (double) soldProductTableModel.getValueAt(i, 2);
            int quantity = (int) soldProductTableModel.getValueAt(i, 1);
            subtotal += price * quantity;
        }
        subTotalLabel.setText(String.format("Sub Total: %.2f", subtotal));
        total = subtotal * (1 - discountPercentage / 100);
        totalLabel.setText(String.format("Total: %.2f", total));
        discountValueLabel.setText(String.format("%.0f%%", discountPercentage)); // Update discount value label
    }

    private void applyDiscount() {
        String[] discountOptions = { "Senior Citizen (20%)", "Person with Disability (20%)" };
        int selectedOption = JOptionPane.showOptionDialog(null, "Select Discount Type", "Discount",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, discountOptions, discountOptions[0]);

        switch (selectedOption) {
            case 0:
                discountPercentage = 20.0;
                break;
            case 1:
                discountPercentage = 20.0;
                break;
            default:
                discountPercentage = 0.0;
                break;
        }
        updateTotals();
    }

    private void updateInventory() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
                String productName = (String) soldProductTableModel.getValueAt(i, 0);
                int quantitySold = (int) soldProductTableModel.getValueAt(i, 1);

                String sql = "UPDATE inventory SET product_total_quantity = product_total_quantity - ? " +
                        "WHERE product_id = (SELECT product_id FROM products WHERE product_name = ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, quantitySold);
                stmt.setString(2, productName);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating inventory in the database");
        } finally {
            DatabaseUtil.close(stmt);
            DatabaseUtil.close(conn);
        }
    }

    private void updateInventoryTable() {
        for (int i = 0; i < productTableModel.getRowCount(); i++) {
            String productCode = (String) productTableModel.getValueAt(i, 0);
            if (originalStockMap.containsKey(productCode)) {
                int originalQuantity = originalStockMap.get(productCode);
                for (int j = 0; j < soldProductTableModel.getRowCount(); j++) {
                    if (productTableModel.getValueAt(i, 1).equals(soldProductTableModel.getValueAt(j, 0))) {
                        int soldQuantity = (int) soldProductTableModel.getValueAt(j, 1);
                        int newQuantity = originalQuantity - soldQuantity;
                        productTableModel.setValueAt(newQuantity, i, 4);
                        break;
                    }
                }
            }
        }
    }

    private void showReceiptUI() {
        // Generate receipt content
        StringBuilder receipt = new StringBuilder();
        receipt.append(centerText("LAVEGA Store") + "\n");
        receipt.append("Receipt No. " + receiptNumber + "\n");
        receipt.append("Store Owner: Amelia Lavega" + "\n");
        receipt.append("Address: Eastwind Montalban Rizal" + "\n\n");

        receipt.append(String.format("%-15s %15s\n", currentDate, currentTime));
        receipt.append("\n");

        for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
            receipt.append(String.format("%-15s %5d x %10.2f\n",
                    soldProductTableModel.getValueAt(i, 0),
                    (int) soldProductTableModel.getValueAt(i, 1),
                    (double) soldProductTableModel.getValueAt(i, 2)));
        }

        receipt.append("\n");
        receipt.append("---------------------------------\n");
        receipt.append(String.format("%-15s %15.0f%%\n", "Discount", discountPercentage));
        receipt.append(String.format("%-15s %15.2f\n", "VAT (12%)", vatAmount));
        receipt.append(String.format("%-15s %15.2f\n", "Total", total));
        receipt.append("\n" + centerText("Reference: " + referenceNumber) + "\n");
        receipt.append(centerText("Thank you for shopping with us!") + "\n");

        JTextArea textArea = new JTextArea(receipt.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(220, 600)); // Adjusted dimensions for receipt paper width
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(220, 600)); // Adjusted dimensions for receipt paper width

        JButton printButton = new JButton("Print");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea.print();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.add(scrollPane, BorderLayout.CENTER);
        receiptPanel.add(printButton, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(null, receiptPanel, "Receipt", JOptionPane.PLAIN_MESSAGE);
    }

    // Center text method
    private String centerText(String text) {
        int width = 30; // Adjust this width as necessary
        return String.format("%" + ((width - text.length()) / 2 + text.length()) + "s", text);
    }

    // Method to generate a unique receipt number
    private String generateReceiptNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Method to generate a unique reference number
    private String generateReferenceNumber() {
        Random random = new Random();
        return String.format("%08d", random.nextInt(100000000));
    }

    // Method to save the transaction to the database
    private void saveTransaction() {
        // Get the current date and time
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        currentDate = dateFormat.format(now);
        currentTime = timeFormat.format(now);

        // Generate unique receipt number and reference number
        receiptNumber = generateReceiptNumber();
        referenceNumber = generateReferenceNumber();

        // Calculate totals
        subtotal = 0.0;
        for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
            double price = (double) soldProductTableModel.getValueAt(i, 2);
            int quantity = (int) soldProductTableModel.getValueAt(i, 1);
            subtotal += price * quantity;
        }
        discountAmount = subtotal * (discountPercentage / 100);
        vatAmount = (subtotal - discountAmount) * 0.12; // VAT is 12%
        total = subtotal - discountAmount + vatAmount;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "INSERT INTO transactions (receipt_number, reference_number, date, time, subtotal, discount, vat, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, receiptNumber);
            stmt.setString(2, referenceNumber);
            stmt.setDate(3, java.sql.Date.valueOf(currentDate)); // Using java.sql.Date for date
            stmt.setTime(4, java.sql.Time.valueOf(currentTime)); // Using java.sql.Time for time
            stmt.setDouble(5, subtotal);
            stmt.setDouble(6, discountAmount);
            stmt.setDouble(7, vatAmount);
            stmt.setDouble(8, total);

            System.out.println("Executing query: " + stmt.toString()); // Debugging statement
            stmt.executeUpdate();
            System.out.println("Transaction saved successfully!"); // Debugging statement

            // Handle the new transaction to update sales summary
            Transaction transaction = new Transaction();
            transaction.setDate(java.sql.Date.valueOf(currentDate));
            transaction.setTime(java.sql.Time.valueOf(currentTime));
            transaction.setProductsSold(soldProductTableModel.getRowCount());
            transaction.setTax(vatAmount);
            transaction.setTotal(total);

            TransactionService transactionService = new TransactionService();
            transactionService.handleNewTransaction(transaction);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving transaction to the database: " + e.getMessage());
        } finally {
            DatabaseUtil.close(stmt);
            DatabaseUtil.close(conn);
        }
    }

    private int getProductIdByName(String productName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int productId = -1;

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT product_id FROM products WHERE product_name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, productName);
            rs = stmt.executeQuery();

            if (rs.next()) {
                productId = rs.getInt("product_id");
            }
            System.out.println("Product ID for " + productName + " is " + productId); // Debugging statement
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs);
            DatabaseUtil.close(stmt);
            DatabaseUtil.close(conn);
        }

        return productId;
    }

    private void resetPOS() {
        productTableModel.setRowCount(0);
        soldProductTableModel.setRowCount(0);
        originalStockMap.clear();
        discountPercentage = 0.0;
        updateTotals();
        barcodeField.requestFocusInWindow();
    }

    class AdjustCellRenderer extends JPanel implements TableCellRenderer {
        private JButton plusButton;
        private JButton minusButton;

        public AdjustCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            plusButton = new JButton("+");
            minusButton = new JButton("-");
            add(plusButton);
            add(minusButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return this;
        }
    }

    class AdjustCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JPanel panel;
        private JButton plusButton;
        private JButton minusButton;
        private int row;

        public AdjustCellEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            plusButton = new JButton("+");
            minusButton = new JButton("-");
            plusButton.addActionListener(this);
            minusButton.addActionListener(this);
            panel.add(plusButton);
            panel.add(minusButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String productName = (String) soldProductTableModel.getValueAt(row, 0);
            int quantity = (int) soldProductTableModel.getValueAt(row, 1);

            // Find the corresponding product in the left table to check its total quantity
            int totalQuantity = 0;
            for (int i = 0; i < productTableModel.getRowCount(); i++) {
                if (productTableModel.getValueAt(i, 1).equals(productName)) {
                    totalQuantity = (int) productTableModel.getValueAt(i, 4);
                    break;
                }
            }

            if (e.getSource() == plusButton) {
                if (totalQuantity > 0) {
                    soldProductTableModel.setValueAt(quantity + 1, row, 1);
                    updateProductTable(row, 1);
                } else {
                    JOptionPane.showMessageDialog(null, "No more stock available for " + productName);
                }
            } else if (e.getSource() == minusButton) {
                if (quantity > 1) {
                    soldProductTableModel.setValueAt(quantity - 1, row, 1);
                    updateProductTable(row, -1);
                }
            }
            stopCellEditing();
            updateTotals();
        }
    }

    class DeleteButtonRenderer extends JButton implements TableCellRenderer {
        public DeleteButtonRenderer() {
            setText("Delete");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return this;
        }
    }

    class DeleteButtonEditor extends DefaultCellEditor {
        private JButton button;
        private DefaultTableModel soldProductTableModel;
        private DefaultTableModel productTableModel;
        private int selectedRow;

        public DeleteButtonEditor(JCheckBox checkBox, DefaultTableModel soldProductTableModel,
                DefaultTableModel productTableModel) {
            super(checkBox);
            this.button = new JButton("Delete");
            this.soldProductTableModel = soldProductTableModel;
            this.productTableModel = productTableModel;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?",
                            "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        deleteRow(selectedRow);
                    }
                    stopCellEditing();
                    updateInventoryTable();
                    updateTotals();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.selectedRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        private void deleteRow(int row) {
            String productName = (String) soldProductTableModel.getValueAt(row, 0);
            for (int i = 0; i < productTableModel.getRowCount(); i++) {
                if (productTableModel.getValueAt(i, 1).equals(productName)) {
                    productTableModel.removeRow(i);
                    break;
                }
            }
            soldProductTableModel.removeRow(row);
        }
    }
}
