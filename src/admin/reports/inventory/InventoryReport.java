package admin.reports.inventory;

import java.awt.*;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import admin.reports.ReportsPage;

public class InventoryReport extends JPanel {

    private DefaultTableModel model;
    private InventoryDAO inventoryDAO;
    private JFrame mainFrame;

    public InventoryReport(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        inventoryDAO = new InventoryDAO();
        initComponents();
        fetchData();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Back arrow button
        JButton backButton = new JButton("<");
        backButton.setBounds(20, 30, 50, 30);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new ReportsPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Inventory Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 300, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Table Data
        String[] columnNames = { "Product Code", "Product Name", "Product Type", "Stock Quantity", "Product Status" };
        model = new DefaultTableModel(new Object[0][0], columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 144, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Center the text in all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 100, 1300, 500);
        add(tableScrollPane);

        // Print Button
        JButton printButton = new JButton("Print");
        printButton.setBounds(1300, 30, 100, 30);
        printButton.setBackground(new Color(30, 144, 255));
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.setFont(new Font("Arial", Font.BOLD, 16));
        printButton.setBorder(BorderFactory.createEmptyBorder());
        printButton.addActionListener(e -> {
            try {
                table.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat("Inventory Report"), null);
            } catch (PrinterException pe) {
                pe.printStackTrace();
            }
        });
        add(printButton);

        // Add component listener to keep elements centered and resized properly
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                tableScrollPane.setBounds(50, 100, frameWidth - 100, frameHeight - 200);
                printButton.setBounds(frameWidth - 150, 30, 100, 30);
            }
        });
    }

    private void fetchData() {
        List<Product> products = inventoryDAO.getInventory();
        for (Product product : products) {
            model.addRow(new Object[] {
                    product.getProductCode(),
                    product.getProductName(),
                    product.getProductType(),
                    product.getProductTotalQuantity(),
                    product.getProductStatus()
            });
        }
    }

}