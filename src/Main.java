import javax.swing.*;

import admin.AdminMenu;
import cashier.CashierMenu;
import login.Login;
import admin.maintenance.MaintenancePage;

import java.awt.*;

public class Main extends JFrame {
    public static final String LOGIN_PANEL = "Login";
    public static final String CASHIER_MENU_PANEL = "CashierMenu";
    public static final String ADMIN_MENU_PANEL = "AdminMenu";

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String uniqueUserId;

    public Main() {
        setTitle("Lavega Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true); // Remove window decorations
        setResizable(false); // Disable resizing
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize your panels
        Login loginPanel = new Login(this, uniqueUserId);
        CashierMenu cashierMenuPanel = new CashierMenu(this, uniqueUserId);
        AdminMenu adminMenu = new AdminMenu(this, uniqueUserId);

        // Add panels to mainPanel with unique names
        mainPanel.add(loginPanel, LOGIN_PANEL);
        mainPanel.add(cashierMenuPanel, CASHIER_MENU_PANEL);
        mainPanel.add(adminMenu, ADMIN_MENU_PANEL);

        // Set initial panel to Login
        cardLayout.show(mainPanel, LOGIN_PANEL);

        add(mainPanel);
        setVisible(true);
    }

    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
