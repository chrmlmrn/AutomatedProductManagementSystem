package admin.help;

import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import java.awt.*;
import javax.swing.*;

public class UserManual extends JPanel {
    private JFrame mainFrame;
    private int buttonWidth = 1000;
    private int buttonHeight = 50;
    private int gap = 20;
    private String uniqueUserId;

    public UserManual(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel manualContentPanel = new JPanel();
        manualContentPanel.setLayout(new BoxLayout(manualContentPanel, BoxLayout.Y_AXIS));
        manualContentPanel.setBackground(Color.WHITE);

        populateContent(manualContentPanel);

        JScrollPane scrollPane = new JScrollPane(manualContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Adjusting the size and location of the scroll pane dynamically
        scrollPane.setPreferredSize(new Dimension(buttonWidth + 40, 600)); // Set preferred size to manage the visible
                                                                           // area
        revalidate();
        repaint();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        headerPanel.setBackground(Color.WHITE);

        RoundedButton backButton = new RoundedButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new HelpPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });
        headerPanel.add(backButton);

        JLabel titleLabel = new JLabel("User Manual");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private void populateContent(JPanel manualContentPanel) {
        String[][] manualSections = {
                { "How to Use the System", getHowToUseSystemContent() },
                { "Common Problems", getCommonProblemsContent() },
                { "Rules and Regulations of the Business", getRulesAndRegulationsContent() }
        };

        for (String[] section : manualSections) {
            JPanel sectionPanel = new RoundedPanel(20);
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
            sectionPanel.setBackground(Color.WHITE);
            sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            RoundedButton sectionButton = new RoundedButton(section[0]);
            sectionButton.setFont(new Font("Arial", Font.PLAIN, 18));
            sectionButton.setBackground(new Color(30, 144, 255));
            sectionButton.setForeground(Color.WHITE);
            sectionButton.setFocusPainted(false);
            sectionButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            sectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            sectionButton.addActionListener(e -> toggleContentVisibility(sectionPanel));
            sectionPanel.add(sectionButton);

            JLabel contentLabel = new JLabel(section[1]);
            contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            contentLabel.setForeground(Color.BLACK);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(contentLabel, BorderLayout.NORTH);
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            contentPanel.setVisible(false);

            sectionPanel.add(contentPanel);
            manualContentPanel.add(Box.createRigidArea(new Dimension(0, gap)));
            manualContentPanel.add(sectionPanel);
        }
    }

    private void toggleContentVisibility(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && comp != panel.getComponent(0)) { // Skip the button
                comp.setVisible(!comp.isVisible());
            }
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private String getHowToUseSystemContent() {
        return "<html><body>" +
                "<h2>How to Use the System</h2>" +
                "<ol>" +
                "<li><strong>Log in</strong> using your username and password.</li>" +
                "<br><li><strong>Navigate</strong> to the dashboard to view your main options.</li>" +
                "<img src='file:///C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/howtouse/USERMANUALLogin.png' width='600' height= '350'/>"
                +
                "<li>To add a new product, go to the <strong>'Product' module</strong>:" +
                "<ul>" +
                "<li>Enter product code, unique barcode, product name, category, supplier, price, quantity, and status.</li>"
                +
                "<img src='assets/images/usermanual/howtouse/USERMANUALProduct.png' width='500' height='600'/>"
                +
                "<li>Save the product to the database.</li>" +
                "</ul></li>" +
                "<li>For sales transactions, use the <strong>'POS' module</strong>:" +
                "<ul>" +
                "<li>Scan or manually enter the product number.</li>" +
                "<img src='file:///C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/howtouse/USERMANUALPOS.png' width='295' height='130'/>"
                +
                "<br> OR" +
                "<br><br><img src='file:///C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/howtouse/USERMANUALPOS-1.png' width='295' height='100'/>"
                +
                "<li>Complete the transaction to generate a receipt.</li>" +
                "</ul></li>" +
                "<li>To view and generate reports, access the <strong>'Reports' module</strong>:" +
                "<ul>" +
                "<li>Select the type of report (e.g., sales, inventory, return).</li>" +
                "<img src='file:///C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/howtouse/USERMANUALReports.png' width='600' height='350'/>"
                +
                "<li>Generate and print the report if needed.</li>" +
                "</ul></li>" +
                "<li>For help and troubleshooting, access the <strong>'Help' module</strong>:" +
                "<ul>" +
                "<li>View FAQs or the user manual.</li>" +
                "<img src='file:///C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/howtouse/USERMANUALHelp.png' width='600' height='350'/>"
                +
                "<li>Contact support if further assistance is needed.</li>" +
                "</ul></li>" +
                "</body></html>";
    }

    private String getCommonProblemsContent() {
        return "<html><body>" +
                "<h2>Common Problems</h2>" +
                "<ol>" +
                "<li><strong>Forgot Password:</strong>" +
                "<ul>" +
                "<li>Use the 'Forgot Password' feature on the login page.</li>" +
                "<img src='file:///C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/commonproblem/USERMANUALForgotPassword.png' width='300' height='340'/>"
                +
                "<li>Answer the security question to reset your password.</li>" +
                "<img src='file:///C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/commonproblem/USERMANUALSecurityQuestion.png' width='475' height='580'/>"
                +
                "</ul></li>" +
                "<li><strong>Cannot Add Product:</strong>" +
                "<ul>" +
                "<li>Ensure all required fields are filled out correctly.</li>" +
                "<li>Verify that the product code and barcode are unique.</li>" +
                "</ul></li>" +
                "<li><strong>System is Slow or Unresponsive:</strong>" +
                "<ul>" +
                "<li>Check your internet connection.</li>" +
                "<li>Contact the admin to check for system maintenance.</li>" +
                "</ul></li>" +
                "<li><strong>Report Generation Issues:</strong>" +
                "<ul>" +
                "<li>Ensure the date range and report type are correctly selected.</li>" +
                "<li>Contact support if the problem persists.</li>" +
                "</ul></li>" +
                "</ol></body></html>";
    }

    private String getRulesAndRegulationsContent() {
        return "<html><body>" +
                "<h2>Rules and Regulations of the Business</h2>" +
                "<ol>" +
                "<li><strong>User Access Levels:</strong>" +
                "<ul>" +
                "<li>Admins have full access to all modules and can add, update, or delete products and users.</li>" +
                "<li>Cashiers have access to the POS module and can process sales transactions.</li>" +
                "</ul></li>" +
                "<li><strong>Password Policy:</strong>" +
                "<ul>" +
                "<li>Passwords must be 8-12 characters long, containing numbers and special characters.</li>" +
                "<li>Passwords must be changed every 90 days.</li>" +
                "</ul></li>" +
                "<li><strong>Data Privacy:</strong>" +
                "<ul>" +
                "<li>User data must be kept confidential and secure.</li>" +
                "<li>Only authorized personnel can access sensitive data.</li>" +
                "</ul></li>" +
                "<li><strong>Inventory Management:</strong>" +
                "<ul>" +
                "<li>Regularly update inventory levels to reflect sales and returns.</li>" +
                "<li>Discontinued products must be marked as such in the system.</li>" +
                "</ul></li>" +
                "<li><strong>Customer Returns:</strong>" +
                "<ul>" +
                "<li>Follow the return policy for processing refunds or exchanges.</li>" +
                "<li>Update the inventory in real-time to reflect returns.</li>" +
                "</ul></li>" +
                "</ol></body></html>";
    }

}
