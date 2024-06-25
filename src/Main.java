import javax.swing.*;

import about.AboutMainPage;
import admin.AdminMenu;
import admin.product.AddProduct;
import cashier.CashierMenu;
import cashier.POS.ScanProduct;
import cashier.POS.ScanProduct;
import help.FAQPage;
import help.HelpPage;
import help.UserManual;
import login.Login;

import java.awt.*;

public class Main extends JFrame {
    public static final String LOGIN_PANEL = "Login";
    public static final String CASHIER_MENU_PANEL = "CashierMenu";
    public static final String SCAN_PRODUCT_PANEL = "ScanProduct";
    public static final String ADMIN_MENU_PANEL = "AdminMenu";
    public static final String ADD_PRODUCT_PANEL = "AddProduct";
    public static final String HELP_PANEL = "Help";
    public static final String FAQ_PANEL = "FAQ";
    public static final String USERMANUAL_PANEL = "User Manual";
    public static final String ABOUT_PANEL = "About";

    private CardLayout cardLayout;
    private JPanel mainPanel;

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
        Login loginPanel = new Login(this);
        CashierMenu cashierMenuPanel = new CashierMenu(this);
        ScanProduct scanProduct = new ScanProduct(this);
        AdminMenu adminMenu = new AdminMenu(this);
        // AddProduct addProduct = new AddProduct(this);
        HelpPage helpPage = new HelpPage(this);
        FAQPage faqPage = new FAQPage(this);
        UserManual userManualPage = new UserManual(this);
        AboutMainPage aboutPage = new AboutMainPage(this);

        // Add panels to mainPanel with unique names
        mainPanel.add(loginPanel, LOGIN_PANEL);

        mainPanel.add(cashierMenuPanel, CASHIER_MENU_PANEL);
        mainPanel.add(scanProduct, SCAN_PRODUCT_PANEL);

        mainPanel.add(adminMenu, ADMIN_MENU_PANEL);
        // mainPanel.add(addProduct, ADD_PRODUCT_PANEL);

        mainPanel.add(helpPage, HELP_PANEL);
        mainPanel.add(faqPage, FAQ_PANEL);
        mainPanel.add(userManualPage, USERMANUAL_PANEL);
        mainPanel.add(aboutPage, ABOUT_PANEL);

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
