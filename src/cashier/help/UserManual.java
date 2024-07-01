package cashier.help;

import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import java.awt.*;
import javax.swing.*;

public class UserManual extends JPanel {
    private JFrame mainFrame;
    private static final int BUTTON_WIDTH = 1000;
    private static final int BUTTON_HEIGHT = 50;
    private static final int GAP = 20;
    private static final String BASE_IMAGE_PATH = "C:/Users/ADMIN/OneDrive/Documents/AutomatedProductManagementSystem/assets/images/usermanual/";

    private String uniqueUserId;

    public UserManual(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        addTopPanel();
        addManualContentPanel();

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        RoundedButton backButton = new RoundedButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new HelpPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JLabel titleLabel = new JLabel("User Manual");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        topPanel.add(backButton);
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);
    }

    private void addManualContentPanel() {
        JPanel manualContentPanel = new JPanel();
        manualContentPanel.setLayout(new BoxLayout(manualContentPanel, BoxLayout.Y_AXIS));
        manualContentPanel.setBackground(Color.WHITE);
        manualContentPanel.setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollPane = new JScrollPane(manualContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        String[][] manualSections = {
                { "How to Use the System", getHowToUseSystemContent() },
                { "Common Problems", getCommonProblemsContent() },
                { "Rules and Regulations of the Business", getRulesAndRegulationsContent() }
        };

        for (String[] section : manualSections) {
            addSection(manualContentPanel, section[0], section[1]);
        }

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    private void addSection(JPanel manualContentPanel, String sectionTitle, String sectionContent) {
        JPanel sectionPanel = new RoundedPanel(20);
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder());

        RoundedButton sectionButton = new RoundedButton(sectionTitle);
        sectionButton.setFont(new Font("Arial", Font.PLAIN, 18));
        sectionButton.setBackground(new Color(30, 144, 255));
        sectionButton.setForeground(Color.WHITE);
        sectionButton.setFocusPainted(false);
        sectionButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionPanel.add(sectionButton);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setVisible(false);
        contentPanel.setMaximumSize(new Dimension(BUTTON_WIDTH, 400));

        JEditorPane contentEditorPane = new JEditorPane("text/html", sectionContent);
        contentEditorPane.setFont(new Font("Arial", Font.PLAIN, 16));
        contentEditorPane.setForeground(Color.BLACK);
        contentEditorPane.setEditable(false);

        JScrollPane contentScrollPane = new JScrollPane(contentEditorPane);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);

        sectionButton.addActionListener(e -> {
            contentPanel.setVisible(!contentPanel.isVisible());
            revalidate();
            repaint();
        });

        sectionPanel.add(contentPanel);
        manualContentPanel.add(Box.createRigidArea(new Dimension(0, GAP)));
        manualContentPanel.add(sectionPanel);
    }

    private static String getImageHtml(String relativeImagePath) {
        String imagePath = BASE_IMAGE_PATH + relativeImagePath;
        return "<p><img src='file:///" + imagePath + "' width='600' height='300'/></p>";
    }

    private static String getHowToUseSystemContent() {
        return "<html><body style='width: 800px; font-family: Arial; font-size: 13px; color: black;'>" +
                "<h2>How to Use the System</h2>" +
                "<ol>" +
                "<li><strong>Log in</strong> using your username and password.</li>" +
                getImageHtml("howtouse/USERMANUALLogin.png") +
                "<li>To add a new product, go to the <strong>'Product' module</strong>:" +
                "<ul>" +
                "<li>Enter product code, unique barcode, product name, category, supplier, price, quantity, and status.</li>"
                +
                getImageHtml("howtouse/USERMANUALProduct.png") +
                "<li>Save the product to the database.</li>" +
                "</ul></li>" +
                "<li>For sales transactions, use the <strong>'POS' module</strong>:" +
                "<ul>" +
                "<li>Scan or manually enter the product number.</li>" +
                "<br><br>" +
                getImageHtml("howtouse/USERMANUALPOS-1.png") +
                "<li>Complete the transaction to generate a receipt.</li>" +
                "</ul></li>" +
                "<li>To view and generate reports, access the <strong>'Reports' module</strong>:" +
                "<ul>" +
                "<li>Select the type of report (e.g., sales, inventory, return).</li>" +
                getImageHtml("howtouse/USERMANUALReports.png") +
                "<li>Generate and print the report if needed.</li>" +
                "</ul></li>" +
                "<li>For help and troubleshooting, access the <strong>'Help' module</strong>:" +
                "<ul>" +
                "<li>View FAQs or the user manual.</li>" +
                getImageHtml("howtouse/USERMANUALHelp.png") +
                "<li>Contact support if further assistance is needed.</li>" +
                "</ul></li>" +
                "</ol></body></html>";
    }

    private static String getCommonProblemsContent() {
        return "<html><body style='width: 800px; font-family: Arial; font-size: 13px; color: black;'>" +
                "<h2>Common Problems</h2>" +
                "<ol>" +
                "<li><strong>Forgot Password:</strong>" +
                getImageHtml("commonproblem/USERMANUALForgotPassword.png") +
                "<li>Answer the security question to reset your password.</li>" +
                getImageHtml("commonproblem/USERMANUALSecurityQuestion.png") +
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
                "</ol></body></html>";
    }

    private static String getRulesAndRegulationsContent() {
        return "<html><body style='width: 800px; font-family: Arial; font-size: 13px; color: black;'>" +
                "<h2>Rules and Regulations of the Business</h2>" +
                "<ol>" +
                "<li><strong>User Roles:</strong>" +
                "<ul>" +
                "<li>Admin has access to all modules and users.</li>" +
                "<li>Cashiers have access to the POS module and can process sales transactions.</li>" +
                "</ul></li>" +
                "<li><strong>Password Policy:</strong>" +
                "<ul>" +
                "<li>Passwords must be 8-12 characters long, containing numbers and special characters.</li>" +
                "<li>Change passwords every 90 days.</li>" +
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