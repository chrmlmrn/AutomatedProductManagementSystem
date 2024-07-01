package admin.return_product;

import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import javax.swing.*;

import admin.AdminMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnPolicyPage extends JPanel {
    private JFrame mainFrame;
    private String uniqueUserId;

    public ReturnPolicyPage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Back button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new ReturnPage(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Title label
        JLabel titleLabel = new JLabel("Return Policy");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 200, 30);
        add(titleLabel);

        // Content panel
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(30, 144, 255));
        contentPanel.setBounds((1400 - 800) / 2, (800 - 600) / 2, 800, 600); // Center the panel
        add(contentPanel);

        // Return policy label inside content panel
        JLabel returnPolicyLabel = new JLabel("Return Policy");
        returnPolicyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        returnPolicyLabel.setForeground(Color.WHITE);
        returnPolicyLabel.setBounds(30, 20, 740, 30); // Left justified
        contentPanel.add(returnPolicyLabel);

        // Text area for return policy content
        JTextArea textArea = new JTextArea();
        textArea.setText("1. General Return Guidelines\n"
                + "- Items must be returned within 30 days of receipt.\n"
                + "- Proof of purchase (receipt or order number) is required for all returns.\n\n"
                + "2. Valid Reasons for Return\n"
                + "- If you receive a damaged, expired, or wrong item, please return it to the Lavega Store immediately.\n"
                + "- The store will arrange for a replacement or refund at no additional cost.\n"
                + "\t- Damaged Item: If the product is damaged upon receipt.\n"
                + "\t- Expired Item: If the product is expired or has an expiration date that has passed.\n"
                + "\t- Wrong Item: If the product received is not the item that was ordered.\n\n"
                + "3. Non-Returnable Items\n"
                + "- Perishable Goods: Food, flowers, newspapers, magazines, etc..\n"
                + "- Personal Care Items: Intimate or sanitary goods, hazardous materials, or flammable liquids or gases.\n\n"
                + "4. Return Process\n"
                + "- Step 1: Visit Lavega Store\n"
                + "\t- Return the item to the Lavega Store where it was purchased.\n"
                + "\t- Provide proof of purchase (receipt or order number) and a valid reason for return (damaged, expired, or wrong item).\n"
                + "- Step 2: Store Inspection\n"
                + "\t- The store staff will inspect the item to verify the reason for return.\n"
                + "- Step 3: Resolution\n"
                + "\t- If the return is approved, you can choose to receive a replacement or a refund.\n"
                + "\t- Refunds will be processed to the original payment method. Allow 5-10 business days for the refund to be processed.\n\n"
                + "5. Refunds\n"
                + "- Refunds will be processed to the original payment method.\n"
                + "- Allow 5-10 business days for the refund to be processed after we receive your return.\n"
                + "- Shipping Costs: Original shipping costs are non-refundable. Return shipping costs are the responsibility of the customer unless the return is due to our error (e.g., wrong item sent).\n\n"
                + "6. Exchanges\n"
                + "- To exchange an item, visit the Lavega Store where you purchased the item and follow the return process.\n"
                + "- Exchanges are subject to product availability.");
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 144, 255));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 60, 740, 500); // Adjust size and position as needed
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Make the scroll bar invisible but still functional
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        contentPanel.add(scrollPane);

        // Return button inside content panel
        JButton returnButton = new RoundedButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnButton.setBounds(670, 570, 100, 30); // Right justified and properly inside the blue box
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(new Color(24, 26, 78));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new ReturnPage(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        contentPanel.add(returnButton);
    }

}
