package admin.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;

public class UserManual extends JPanel {
    private JFrame mainFrame;

    public UserManual(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use null layout for absolute positioning
        setBackground(Color.WHITE);

        // Add back button
        RoundedButton backButton = new RoundedButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new HelpPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Add title label
        JLabel titleLabel = new JLabel("User Manual");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 300, 30);
        add(titleLabel);

        // Create a panel for the User Manual content
        JPanel manualContentPanel = new JPanel();
        manualContentPanel.setLayout(new BoxLayout(manualContentPanel, BoxLayout.Y_AXIS));
        manualContentPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(manualContentPanel);
        scrollPane.setBounds(100, 100, 1720, 800);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        // Increase scroll speed
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16); // Adjust this value for desired scroll speed

        // User Manual sections
        String[][] manualSections = {
                { "How to Use the System", getHowToUseSystemContent() },
                { "Common Problems", getCommonProblemsContent() },
                { "Rules and Regulations of the Business", getRulesAndRegulationsContent() }
        };

        int buttonWidth = 1000;
        int buttonHeight = 50;
        int gap = 20;

        for (int i = 0; i < manualSections.length; i++) {
            String sectionTitle = manualSections[i][0];
            String sectionContent = manualSections[i][1];

            JPanel sectionPanel = new RoundedPanel(20);
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
            sectionPanel.setBackground(Color.WHITE);
            sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align
            sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            RoundedButton sectionButton = new RoundedButton(sectionTitle);
            sectionButton.setFont(new Font("Arial", Font.PLAIN, 18));
            sectionButton.setBackground(new Color(30, 144, 255));
            sectionButton.setForeground(Color.WHITE);
            sectionButton.setFocusPainted(false);
            sectionButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            sectionButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align
            sectionPanel.add(sectionButton);

            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            contentPanel.setVisible(false);
            contentPanel.setMaximumSize(new Dimension(buttonWidth, 400)); // Adjust maximum size as needed

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            JLabel contentLabel = new JLabel(sectionContent);
            contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            contentLabel.setForeground(Color.BLACK);
            contentPanel.add(contentLabel, gbc);

            sectionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    contentPanel.setVisible(!contentPanel.isVisible());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            });

            sectionPanel.add(contentPanel);
            manualContentPanel.add(Box.createRigidArea(new Dimension(0, gap))); // Add space
            manualContentPanel.add(sectionPanel);
        }
    }

    private static String getHowToUseSystemContent() {
        return "<html><body style='width: 800px;'>" +
                "<h2>How to Use the System</h2>" +
                "<ol>" +
                "<li><strong>Log in</strong> using your username and password.</li>" +
                "<li><strong>Navigate</strong> to the dashboard to view your main options.</li>" +
                "<li>To add a new product, go to the <strong>'Product' module</strong>:" +
                "<ul>" +
                "<li>Enter product code, unique barcode, product name, category, supplier, price, quantity, and status.</li>"
                +
                "<li>Save the product to the database.</li>" +
                "</ul></li>" +
                "<li>For sales transactions, use the <strong>'POS' module</strong>:" +
                "<ul>" +
                "<li>Scan or manually enter the product number.</li>" +
                "<li>Complete the transaction to generate a receipt.</li>" +
                "</ul></li>" +
                "<li>To view and generate reports, access the <strong>'Reports' module</strong>:" +
                "<ul>" +
                "<li>Select the type of report (e.g., sales, inventory).</li>" +
                "<li>Generate and print the report if needed.</li>" +
                "</ul></li>" +
                "<li>For help and troubleshooting, access the <strong>'Help' module</strong>:" +
                "<ul>" +
                "<li>View FAQs or the user manual.</li>" +
                "<li>Contact support if further assistance is needed.</li>" +
                "</ul></li>" +
                "</ol></body></html>";
    }

    private static String getCommonProblemsContent() {
        return "<html><body style='width: 800px;'>" +
                "<h2>Common Problems</h2>" +
                "<ol>" +
                "<li><strong>Forgot Password:</strong>" +
                "<ul>" +
                "<li>Use the 'Forgot Password' feature on the login page.</li>" +
                "<li>Answer the security question to reset your password.</li>" +
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

    private static String getRulesAndRegulationsContent() {
        return "<html><body style='width: 800px;'>" +
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
