package cashier;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import admin.records.userlogs.UserLogUtil;
import login.Login;
import cashier.POS.ScanProduct;
import cashier.about.AboutMainPage;
import cashier.help.HelpPage;
import customcomponents.RoundedButton;

import admin.reports.inventory.InventoryDAO;
import admin.reports.inventory.Product;

public class CashierMenu extends JPanel {

    private JFrame mainFrame;
    private String uniqueUserId;
    private RoundedButton notificationButton;

    public CashierMenu(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponent();
        showCriticalInventory(); // Call this method to show notifications on startup
    }

    private void initComponent() {
        // panel settings
        setLayout(null);
        setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Cashier Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(70, 30, 200, 30); // Adjusted y-coordinate from 10 to 30
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Notification Button
        notificationButton = new RoundedButton("No Critical Inventory");
        notificationButton.setBounds(getWidth() - 250, 30, 200, 30);
        notificationButton.setBackground(new Color(144, 238, 144)); // Green for no notifications
        notificationButton.setForeground(Color.WHITE);
        notificationButton.setFont(new Font("Arial", Font.BOLD, 14));
        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayNotificationDetails();
            }
        });
        add(notificationButton);

        // Buttons
        String[] buttonLabels = { "Scan Product", "Help", "About", "Logout" };
        int buttonWidth = 300;
        int buttonHeight = 50;
        int startY = 150; // Starting Y position for the first button
        int gap = 20; // Gap between buttons

        // Calculate the initial center position for the buttons horizontally
        int panelWidth = getWidth();
        int centerX = (panelWidth - buttonWidth) / 2;

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = new RoundedButton(buttonLabels[i]);

            int y = startY + (buttonHeight + gap) * i;

            button.setBounds(centerX, y, buttonWidth, buttonHeight);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBorder(BorderFactory.createEmptyBorder());

            // Add action listener for the buttons
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (button.getText()) {
                        case "Scan Product":
                            // Open Scan Product Page
                            openScanProductPage();
                            break;
                        case "Help":
                            // Open Help Page
                            openHelpPage();
                            break;
                        case "About":
                            // Open About Page
                            openAboutPage();
                            break;
                        case "Logout":
                            // Logout and open login panel
                            confirmLogout();
                            break;
                    }
                }
            });

            add(button);
        }

        // Add component listener to dynamically adjust button positions on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int newCenterX = (frameWidth - buttonWidth) / 2;
                for (Component component : getComponents()) {
                    if (component instanceof RoundedButton) {
                        RoundedButton button = (RoundedButton) component;
                        button.setBounds(newCenterX, button.getY(), buttonWidth, buttonHeight);
                    }
                }
                notificationButton.setBounds(getWidth() - 250, 30, 200, 30); // Adjust notification button width and
                                                                             // position
            }
        });
    }

    private void openScanProductPage() {
        mainFrame.setContentPane(new ScanProduct(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openHelpPage() {
        mainFrame.setContentPane(new HelpPage(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openAboutPage() {
        mainFrame.setContentPane(new AboutMainPage(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void openLoginPage() {
        mainFrame.setContentPane(new Login(mainFrame, uniqueUserId));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void confirmLogout() {
        int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            UserLogUtil.logUserAction(uniqueUserId, "User logged out");
            openLoginPage();
        }
    }

    private void showCriticalInventory() {
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Product> criticalProducts = inventoryDAO.getCriticalInventory();
        List<Product> nearExpirationProducts = inventoryDAO.getNearExpirationProducts();

        if (!criticalProducts.isEmpty() || !nearExpirationProducts.isEmpty()) {
            notificationButton.setBackground(new Color(255, 69, 0)); // Red for notifications
            notificationButton.setText("Inventory Alerts!");
        } else {
            notificationButton.setBackground(new Color(144, 238, 144)); // Green for no notifications
            notificationButton.setText("No Critical Inventory");
        }
    }

    private void displayNotificationDetails() {
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Product> criticalProducts = inventoryDAO.getCriticalInventory();
        List<Product> nearExpirationProducts = inventoryDAO.getNearExpirationProducts();

        // Create custom popup dialog with tabs
        JDialog dialog = new JDialog(mainFrame, "Inventory Alerts", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Critical Level Alerts Tab
        JPanel criticalPanel = new JPanel(new BorderLayout());
        criticalPanel.setBackground(new Color(245, 245, 245));
        String[] criticalColumnNames = { "Product Code", "Product Name", "Stock Quantity" };
        DefaultTableModel criticalModel = new DefaultTableModel(criticalColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells not editable
            }
        };
        JTable criticalTable = new JTable(criticalModel);
        criticalTable.setRowHeight(30);
        criticalTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        criticalTable.getTableHeader().setBackground(new Color(30, 144, 255));
        criticalTable.getTableHeader().setForeground(Color.WHITE);
        criticalTable.setFont(new Font("Arial", Font.PLAIN, 14));
        criticalTable.setBackground(Color.WHITE);
        criticalTable.setForeground(Color.BLACK);
        criticalTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < criticalTable.getColumnCount(); i++) {
            criticalTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane criticalScrollPane = new JScrollPane(criticalTable);
        criticalPanel.add(criticalScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Critical Level Products", criticalPanel);

        // Nearly Expired Products Tab
        JPanel nearExpirationPanel = new JPanel(new BorderLayout());
        nearExpirationPanel.setBackground(new Color(245, 245, 245));
        String[] nearExpirationColumnNames = { "Product Code", "Product Name", "Expiration Date" };
        DefaultTableModel nearExpirationModel = new DefaultTableModel(nearExpirationColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells not editable
            }
        };
        JTable nearExpirationTable = new JTable(nearExpirationModel);
        nearExpirationTable.setRowHeight(30);
        nearExpirationTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        nearExpirationTable.getTableHeader().setBackground(new Color(30, 144, 255));
        nearExpirationTable.getTableHeader().setForeground(Color.WHITE);
        nearExpirationTable.setFont(new Font("Arial", Font.PLAIN, 14));
        nearExpirationTable.setBackground(Color.WHITE);
        nearExpirationTable.setForeground(Color.BLACK);
        nearExpirationTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        for (int i = 0; i < nearExpirationTable.getColumnCount(); i++) {
            nearExpirationTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane nearExpirationScrollPane = new JScrollPane(nearExpirationTable);
        nearExpirationPanel.add(nearExpirationScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Nearly Expired Products", nearExpirationPanel);

        dialog.add(tabbedPane, BorderLayout.CENTER);

        // Custom buttons and message
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        RoundedButton okButton = new RoundedButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(new Color(30, 144, 255));
        okButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        buttonPanel.add(okButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Populate tables
        for (Product product : criticalProducts) {
            criticalModel.addRow(new Object[] { product.getProductCode(), product.getProductName(),
                    product.getProductTotalQuantity() });
        }
        for (Product product : nearExpirationProducts) {
            nearExpirationModel.addRow(new Object[] { product.getProductCode(), product.getProductName(),
                    product.getProductExpirationDate().toString() });
        }

        dialog.setVisible(true);
    }
}
