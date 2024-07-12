package cashier.POS;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import admin.reports.sales.SalesDAO;
import admin.reports.sales.SoldProduct;
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
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import database.DatabaseUtil;
import admin.records.userlogs.UserLogUtil;
import customcomponents.RoundedButton;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Random;
import java.util.List;

public class ScanProduct extends JPanel {
    private DefaultTableModel productTableModel;
    private DefaultTableModel soldProductTableModel;
    private JLabel subTotalLabel;
    private JLabel totalLabel;
    private JLabel vatLabel;
    private JLabel discountValueLabel;
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
        centerScreen();
    }

    private void initComponents() {
        originalStockMap = new HashMap<>();
        subTotalLabel = new JLabel("Sub Total: 0.00", SwingConstants.RIGHT);
        subTotalLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Unbolded
        vatLabel = new JLabel("VAT (12%): 0.00", SwingConstants.RIGHT);
        vatLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Unbolded
        totalLabel = new JLabel("Total: 0.00", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bold
        discountValueLabel = new JLabel("Discount: 0.00", SwingConstants.RIGHT);
        discountValueLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Unbolded
        setLayout(null);
        setBackground(Color.WHITE);

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

        barcodeField = new JTextField();
        barcodeField.setBounds(50, 80, 400, 50);
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

        RoundedButton clearBarcodeButton = new RoundedButton("Clear Barcode");
        clearBarcodeButton.setBounds(460, 80, 200, 50);
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

        String[] productColumns = { "PRODUCT CODE", "PRODUCT NAME", "SIZE", "PRICE", "TOTAL QUANTITY" };
        productTableModel = new DefaultTableModel(productColumns, 0);
        JTable productTable = new JTable(productTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable.getTableHeader().setBackground(new Color(30, 144, 255));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        productTable.setRowHeight(30);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBounds(50, 150, 700, 500);
        add(productScrollPane);

        String[] summaryColumns = { "PRODUCT NAME", "QUANTITY", "PRICE", "CATEGORY", "ADJUST", "DELETE" };
        soldProductTableModel = new DefaultTableModel(summaryColumns, 0);
        summaryTable = new JTable(soldProductTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5;
            }
        };
        summaryTable.setRowHeight(30);
        summaryTable.getColumn("ADJUST").setCellRenderer(new AdjustCellRenderer());
        summaryTable.getColumn("ADJUST").setCellEditor(new AdjustCellEditor());
        summaryTable.getColumn("ADJUST").setPreferredWidth(100); // Adjust the width as needed
        summaryTable.getColumn("DELETE").setCellRenderer(new DeleteButtonRenderer());
        summaryTable.getColumn("DELETE").setPreferredWidth(100); // Adjust the width as needed
        summaryTable.getColumn("DELETE")
                .setCellEditor(new DeleteButtonEditor(new JCheckBox(), soldProductTableModel, productTableModel));
        JScrollPane summaryScrollPane = new JScrollPane(summaryTable);
        summaryScrollPane.setBounds(970, 150, 500, 300);
        add(summaryScrollPane);

        JPanel totalPanel = new JPanel(null); // Changed to null layout
        totalPanel.setBounds(970, 470, 500, 180);
        totalPanel.setBackground(Color.WHITE);

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Unbolded
        discountLabel.setBounds(0, 0, 150, 30); // Adjusted bounds
        totalPanel.add(discountLabel);

        discountValueLabel.setBounds(350, 0, 150, 30); // Adjusted bounds
        totalPanel.add(discountValueLabel);

        JLabel vatLabelText = new JLabel("VAT (12%):");
        vatLabelText.setFont(new Font("Arial", Font.PLAIN, 18)); // Unbolded
        vatLabelText.setBounds(0, 30, 150, 30); // Adjusted bounds
        totalPanel.add(vatLabelText);

        vatLabel.setBounds(350, 30, 150, 30); // Adjusted bounds
        totalPanel.add(vatLabel);

        JLabel subTotalLabelText = new JLabel("Sub Total:");
        subTotalLabelText.setFont(new Font("Arial", Font.PLAIN, 18)); // Unbolded
        subTotalLabelText.setBounds(0, 60, 150, 30); // Adjusted bounds
        totalPanel.add(subTotalLabelText);

        subTotalLabel.setBounds(350, 60, 150, 30); // Adjusted bounds
        totalPanel.add(subTotalLabel);

        // Add a separator line
        JSeparator separator = new JSeparator();
        separator.setBorder(new LineBorder(Color.BLACK, 1));
        separator.setBounds(0, 90, 450, 1); // Adjusted bounds
        totalPanel.add(separator);

        JLabel totalLabelText = new JLabel("Total:");
        totalLabelText.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabelText.setBounds(0, 100, 150, 30); // Adjusted bounds
        totalPanel.add(totalLabelText);

        totalLabel.setBounds(350, 100, 150, 30); // Adjusted bounds
        totalPanel.add(totalLabel);

        add(totalPanel);

        RoundedButton discountButton = new RoundedButton("Discount");
        RoundedButton productCodeButton = new RoundedButton("Product Code");
        RoundedButton checkoutButton = new RoundedButton("Checkout");

        discountButton.setBounds(50, 680, 200, 50);
        productCodeButton.setBounds(275, 680, 200, 50);
        checkoutButton.setBounds(970, 660, 500, 50);

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
                if (soldProductTableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No products scanned. Please scan products before checkout.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveTransaction();
                    updateInventory();
                    int response = JOptionPane.showConfirmDialog(null, "Would you like to print the receipt?",
                            "Print Receipt", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        showReceiptUI();
                        resetPOS();
                        UserLogUtil.logUserAction(uniqueUserId, "Printed a receipt");
                        UserLogUtil.logUserAction(uniqueUserId, "Transaction Done");
                    } else if (response == JOptionPane.NO_OPTION) {
                        resetPOS();
                    }
                }
            }
        });

        updateTotals();

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

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
                sql = "SELECT product_code, product_name, product_size, product_price, product_total_quantity, category_name, product_status_id "
                        +
                        "FROM products JOIN inventory ON products.product_id = inventory.product_id " +
                        "JOIN category ON products.category_id = category.category_id " +
                        "WHERE barcode = ?";
            } else {
                sql = "SELECT product_code, product_name, product_size, product_price, product_total_quantity, category_name, product_status_id "
                        +
                        "FROM products JOIN inventory ON products.product_id = inventory.product_id " +
                        "JOIN category ON products.category_id = category.category_id " +
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
                String categoryName = rs.getString("category_name");
                String productStatusId = rs.getString("product_status_id");

                if (!productStatusId.equals("ACT")) {
                    String statusMessage = "Product " + productName + " is ";
                    if (productStatusId.equals("INA")) {
                        statusMessage += "inactive";
                    } else if (productStatusId.equals("DIS")) {
                        statusMessage += "discontinued";
                    }
                    statusMessage += " and cannot be added.";
                    JOptionPane.showMessageDialog(null, statusMessage);
                    return;
                }

                if (productTotalQuantity <= 0) {
                    JOptionPane.showMessageDialog(null,
                            "Product " + productName + " is out of stock and cannot be added.");
                    return;
                }

                originalStockMap.put(productCode, productTotalQuantity);

                // Check for existing product in soldProductTableModel
                int existingRowIndex = getExistingRowIndex(productName);
                if (existingRowIndex != -1) {
                    int existingQuantity = (int) soldProductTableModel.getValueAt(existingRowIndex, 1);
                    soldProductTableModel.setValueAt(existingQuantity + 1, existingRowIndex, 1);
                } else {
                    Object[] soldRow = { productName, 1, productPrice, categoryName, "Adjust", "Delete" };
                    soldProductTableModel.addRow(soldRow);
                }

                // Update product table model
                boolean foundInProductTable = false;
                for (int i = 0; i < productTableModel.getRowCount(); i++) {
                    if (productTableModel.getValueAt(i, 0).equals(productCode)) {
                        int totalSoldQuantity = getTotalSoldQuantity(productName);
                        int newQuantity = productTotalQuantity - totalSoldQuantity;
                        productTableModel.setValueAt(newQuantity, i, 4);
                        foundInProductTable = true;
                        break;
                    }
                }

                if (!foundInProductTable) {
                    int totalSoldQuantity = getTotalSoldQuantity(productName);
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

    private int getExistingRowIndex(String productName) {
        for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
            if (soldProductTableModel.getValueAt(i, 0).equals(productName)) {
                return i;
            }
        }
        return -1;
    }

    private int getTotalSoldQuantity(String productName) {
        int totalSoldQuantity = 0;
        for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
            if (soldProductTableModel.getValueAt(i, 0).equals(productName)) {
                totalSoldQuantity = (int) soldProductTableModel.getValueAt(i, 1);
                break;
            }
        }
        return totalSoldQuantity;
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
        double discountableTotal = 0.0;
        int rowCount = soldProductTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            double price = (double) soldProductTableModel.getValueAt(i, 2);
            int quantity = (int) soldProductTableModel.getValueAt(i, 1);
            subtotal += price * quantity;

            String categoryName = (String) soldProductTableModel.getValueAt(i, 3);
            if (!categoryName.equals("Snacks") && !categoryName.equals("Other")) {
                discountableTotal += price * quantity;
            }
        }

        discountAmount = discountableTotal * (discountPercentage / 100);
        vatAmount = (subtotal - discountAmount) * 0.12;
        total = subtotal - discountAmount + vatAmount;

        subTotalLabel.setText(String.format("%.2f", subtotal));
        vatLabel.setText(String.format(" %.2f", vatAmount));
        totalLabel.setText(String.format("%.2f", total));
        discountValueLabel.setText(String.format("%.2f", discountAmount));
    }

    private void applyDiscount() {
        String[] discountOptions = { "Senior Citizen (5%)", "Person with Disability (5%)" };
        int selectedOption = JOptionPane.showOptionDialog(null,
                "Select Discount Type\nNote: Snacks and Other items will not be discounted.", "Discount",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, discountOptions, discountOptions[0]);

        switch (selectedOption) {
            case 0:
                discountPercentage = 0.05;
                break;
            case 1:
                discountPercentage = 0.05;
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
        String cashierName = getCashierFullName(uniqueUserId);

        StringBuilder receipt = new StringBuilder();
        receipt.append(centerText("LAVEGA Store") + "\n");
        receipt.append(centerText("Receipt No. " + receiptNumber) + "\n");
        receipt.append(centerText("Owner: Amelia Lavega") + "\n");
        receipt.append(centerText("Eastwind Montalban Rizal") + "\n");
        receipt.append(centerText("Cashier: " + cashierName) + "\n");

        receipt.append(String.format("\n" + "%-15s %15s\n", currentDate, currentTime));
        receipt.append("\n");

        receipt.append(String.format("%-17s %2s %10s\n", "PRODUCT NAME", "QTY", "PRICE"));

        for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
            String productName = truncateProductName((String) soldProductTableModel.getValueAt(i, 0));
            receipt.append(String.format("%-16s %2d x %10.2f\n",
                    productName,
                    (int) soldProductTableModel.getValueAt(i, 1),
                    (double) soldProductTableModel.getValueAt(i, 2)));
        }

        receipt.append("\n");
        receipt.append("---------------------------------\n");
        receipt.append(String.format("%-15s %15.2f\n", "Discount", discountAmount));
        receipt.append(String.format("%-15s %15.2f\n", "VAT (12%)", vatAmount));
        receipt.append(String.format("%-15s %15.2f\n", "Sub Total", subtotal));
        receipt.append(String.format("%-15s %15.2f\n", "Total", total));
        receipt.append("\n" + centerText("Reference: " + referenceNumber) + "\n");
        receipt.append(centerText("Thank you for shopping with us!") + "\n");
        receipt.append(centerText("THIS DOCUMENT IS NOT VALID FOR") + "\n");
        receipt.append(centerText("CLAIMING OF INPUT TAX") + "\n");

        JTextArea textArea = new JTextArea(receipt.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(220, 600));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(220, 600));

        JButton printButton = new JButton("Print");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printReceipt(receipt.toString());
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

    private String truncateProductName(String productName) {
        if (productName.length() > 14) {
            return productName.substring(0, 14) + "..";
        }
        return productName;
    }

    private void printReceipt(String receiptContent) {
        JTextArea textArea = new JTextArea(receiptContent);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 7));
        textArea.setEditable(false);

        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = pf.getPaper();

        double margin = 0;
        paper.setImageableArea(margin, margin, paper.getWidth() - 2 * margin, paper.getHeight() - 2 * margin);
        pf.setPaper(paper);

        job.setPrintable(textArea.getPrintable(null, null), pf);

        try {
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    private String getCashierFullName(String uniqueUserId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String fullName = "";

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT user_first_name, user_last_name FROM users WHERE unique_user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uniqueUserId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("user_first_name");
                String lastName = rs.getString("user_last_name");
                fullName = firstName + " " + lastName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving cashier name from the database");
        } finally {
            DatabaseUtil.close(rs);
            DatabaseUtil.close(stmt);
            DatabaseUtil.close(conn);
        }

        return fullName;
    }

    private String centerText(String text) {
        int width = 30;
        return String.format("%" + ((width - text.length()) / 2 + text.length()) + "s", text);
    }

    private String generateReceiptNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateReferenceNumber() {
        Random random = new Random();
        return String.format("%08d", random.nextInt(100000000));
    }

    private void saveTransaction() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        currentDate = dateFormat.format(now);
        currentTime = timeFormat.format(now);

        receiptNumber = generateReceiptNumber();
        referenceNumber = generateReferenceNumber();

        subtotal = 0.0;
        double discountableTotal = 0.0;
        List<SoldProduct> soldProducts = new ArrayList<>();
        for (int i = 0; i < soldProductTableModel.getRowCount(); i++) {
            double price = (double) soldProductTableModel.getValueAt(i, 2);
            int quantity = (int) soldProductTableModel.getValueAt(i, 1);
            subtotal += price * quantity;

            String categoryName = (String) soldProductTableModel.getValueAt(i, 3);
            if (!categoryName.equals("Snacks") && !categoryName.equals("Other")) {
                discountableTotal += price * quantity;
            }

            String productName = (String) soldProductTableModel.getValueAt(i, 0);
            int productId = getProductIdByName(productName);
            if (productId != -1) {
                SoldProduct soldProduct = new SoldProduct();
                soldProduct.setProductId(productId);
                soldProduct.setQuantity(quantity);
                soldProducts.add(soldProduct);
                System.out.println("Adding SoldProduct: " + productName + ", Quantity: " + quantity);
            }
        }

        discountAmount = discountableTotal * (discountPercentage / 100);
        vatAmount = (subtotal - discountAmount) * 0.12;
        total = subtotal - discountAmount + vatAmount;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "INSERT INTO transactions (receipt_number, reference_number, date, time, subtotal, discount, vat, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, receiptNumber);
            stmt.setString(2, referenceNumber);
            stmt.setDate(3, java.sql.Date.valueOf(currentDate));
            stmt.setTime(4, java.sql.Time.valueOf(currentTime));
            stmt.setDouble(5, subtotal);
            stmt.setDouble(6, discountAmount);
            stmt.setDouble(7, vatAmount);
            stmt.setDouble(8, total);

            stmt.executeUpdate();

            // Retrieve the generated transaction ID
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int transactionId = generatedKeys.getInt(1);
                System.out.println("Transaction ID: " + transactionId);

                // Insert the sold products into the sold_products table
                String soldProductsSql = "INSERT INTO sold_products (transaction_id, product_id, quantity) VALUES (?, ?, ?)";
                try (PreparedStatement soldProductsStmt = conn.prepareStatement(soldProductsSql)) {
                    for (SoldProduct soldProduct : soldProducts) {
                        soldProductsStmt.setInt(1, transactionId);
                        soldProductsStmt.setInt(2, soldProduct.getProductId());
                        soldProductsStmt.setInt(3, soldProduct.getQuantity());
                        soldProductsStmt.addBatch();
                    }
                    soldProductsStmt.executeBatch();
                }

                // Handle the new transaction to update sales summary
                Transaction transaction = new Transaction();
                transaction.setDate(java.sql.Date.valueOf(currentDate));
                transaction.setTime(java.sql.Time.valueOf(currentTime));
                transaction.setTax(vatAmount);
                transaction.setTotal(total);

                TransactionService transactionService = new TransactionService();
                transactionService.handleNewTransaction(transaction, soldProducts);

                // Update sales_summary table
                SalesDAO salesDAO = new SalesDAO();
                salesDAO.updateSalesSummary();
            }

            System.out.println("Transaction saved successfully!"); // Debugging statement
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
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving product ID from database");
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
            setLayout(new GridLayout(1, 2));
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
            panel = new JPanel(new GridLayout(1, 2));
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
                        stopCellEditing();
                        deleteRow(selectedRow);
                        updateInventoryTable();
                        updateTotals();
                        ((JTable) button.getParent().getParent()).revalidate();
                        ((JTable) button.getParent().getParent()).repaint();
                    } else {
                        cancelCellEditing();
                    }
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

    private void centerScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - mainFrame.getWidth()) / 2;
        int centerY = (screenSize.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(centerX, centerY);
    }
}
