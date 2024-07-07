package cashier;

import javax.swing.*;
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

        if (!criticalProducts.isEmpty()) {
            notificationButton.setBackground(new Color(255, 69, 0)); // Red for notifications
            notificationButton.setText("Ask Admin to Re-stock!");
        } else {
            notificationButton.setBackground(new Color(144, 238, 144)); // Green for no notifications
            notificationButton.setText("No Critical Inventory");
        }
    }

    private void displayNotificationDetails() {
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<Product> criticalProducts = inventoryDAO.getCriticalInventory();

        // Create custom popup dialog
        JDialog dialog = new JDialog(mainFrame, "Critical Inventory Details", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setLayout(new BorderLayout());

        // Styled panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 144, 255));
        panel.setLayout(new BorderLayout());

        // Table for critical products
        String[] columnNames = { "Product Code", "Product Name", "Stock Quantity" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells not editable
            }
        };
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(24, 26, 78));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(new Color(30, 144, 255));
        table.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        dialog.add(panel, BorderLayout.CENTER);

        // Custom buttons and message
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 144, 255));
        buttonPanel.setLayout(new BorderLayout());

        RoundedButton okButton = new RoundedButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setForeground(new Color(24, 26, 78));
        okButton.setBackground(Color.WHITE);
        okButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel okButtonPanel = new JPanel();
        okButtonPanel.setBackground(new Color(30, 144, 255));
        okButtonPanel.add(okButton);

        if (!criticalProducts.isEmpty()) {
            JLabel messageLabel = new JLabel("Ask admin to re-stock immediately!", JLabel.CENTER);
            messageLabel.setForeground(Color.RED);
            messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
            buttonPanel.add(messageLabel, BorderLayout.NORTH);
            for (Product product : criticalProducts) {
                model.addRow(new Object[] { product.getProductCode(), product.getProductName(),
                        product.getProductTotalQuantity() });
            }
        } else {
            JLabel noCriticalLabel = new JLabel("No products in critical level", JLabel.CENTER);
            noCriticalLabel.setForeground(Color.WHITE);
            noCriticalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            buttonPanel.add(noCriticalLabel, BorderLayout.NORTH);
        }

        buttonPanel.add(okButtonPanel, BorderLayout.SOUTH);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
