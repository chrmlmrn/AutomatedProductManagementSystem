package admin.inventory;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.Timer;
import java.util.TimerTask;

import admin.reports.inventory.InventoryDAO;
import admin.reports.inventory.Product;
import admin.AdminMenu;

public class InventoryPage extends JPanel {

    private DefaultTableModel model;
    private InventoryDAO inventoryDAO;
    private JFrame mainFrame;
    private String uniqueUserId;
    private JTable table;
    private Timer timer;

    public InventoryPage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        inventoryDAO = new InventoryDAO();

        initComponents();
        fetchData();

        // Start the auto-refresh timer
        startAutoRefresh();
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
            mainFrame.setContentPane(new AdminMenu(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
            stopAutoRefresh();
        });
        add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Inventory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 300, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Table Data
        String[] columnNames = { "Product Code", "Product Name", "Supplier Name", "Product Type",
                "Critical Stock Level", "Stock Quantity", "Product Status" };
        Object[][] data = {};
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        table = new JTable(model);
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

        // Add component listener to keep elements centered and resized properly
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                tableScrollPane.setBounds(50, 100, frameWidth - 100, frameHeight - 200);
            }
        });
    }

    private void fetchData() {
        List<Product> products = inventoryDAO.getInventory();
        model.setRowCount(0); // Clear existing data
        for (Product product : products) {
            String status = "In Stock";
            if (product.getProductTotalQuantity() <= 0) {
                status = "Out of Stock";
            } else if (product.getProductTotalQuantity() <= product.getCriticalLevel()) {
                status = "Re-ordering";
            }

            model.addRow(new Object[] {
                    product.getProductCode(),
                    product.getProductName(),
                    product.getSupplierName(),
                    product.getProductType(),
                    product.getCriticalLevel(),
                    product.getProductTotalQuantity(),
                    status
            });
        }
    }

    private void startAutoRefresh() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> fetchData());
            }
        }, 0, 5000); // Refresh every 5 seconds
    }

    private void stopAutoRefresh() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
