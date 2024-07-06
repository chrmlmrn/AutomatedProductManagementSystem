package admin.about;

import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AboutTheDevelopersPage extends JPanel {
    private JFrame mainFrame;
    private int contentPanelWidth = 1262;
    private int contentPanelHeight = 768;
    private int contentPanelX;
    private int contentPanelY;

    private String uniqueUserId;

    public AboutTheDevelopersPage(JFrame mainFrame, String uniqueStringId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponent();
    }

    private void initComponent() {
        // Panel settings
        setLayout(null);
        setBackground(new Color(240, 240, 240)); // light gray background

        // Content panel
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(30, 144, 255)); // deep blue background
        contentPanelX = (getWidth() - contentPanelWidth) / 2;
        contentPanelY = (getHeight() - contentPanelHeight) / 2;
        contentPanel.setBounds(contentPanelX, contentPanelY, contentPanelWidth, contentPanelHeight);
        add(contentPanel);

        // Add title label
        JLabel titleLabel = new JLabel("About the Developers");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40)); // larger font size
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(50, 20, 500, 50);
        contentPanel.add(titleLabel);

        // Developer description text
        JTextArea descriptionText = new JTextArea(
                "We are a dynamic team of four computer science students currently pursuing our Bachelor of " +
                        "Science in Computer Science (BSCS) and we're thrilled to introduce you to our innovative " +
                        "software project. As third-year students, we are passionate about applying our theoretical " +
                        "knowledge to real-world scenarios, and our latest endeavor is a testament to that commitment. "
                        +
                        "Our diverse skill sets range from front-end design to back-end system architecture, enabling us "
                        +
                        "to tackle complex challenges and innovate effectively. Together, we share a vision of creating technology "
                        +
                        "that not only meets current demands but also anticipates future needs.");
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 20));
        descriptionText.setForeground(Color.WHITE);
        descriptionText.setBackground(new Color(30, 144, 255));
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(descriptionText);
        scrollPane.setBounds(50, 80, 1150, 150); // Adjusted for more height to avoid scrolling
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        // Developer details and images
        int baseY = 350; // Adjust baseY position based on new description area
        addDeveloperDetail(contentPanel, "John Carlos E. Celis",
                "assets\\images\\developers\\john.jpg",
                100, baseY);
        addDeveloperDetail(contentPanel, "Charimel C. Mariano",
                "assets\\images\\developers\\charimel.jpg",
                400, baseY);
        addDeveloperDetail(contentPanel, " Gabriel S. Molina",
                "assets\\images\\developers\\gab.png",
                700, baseY);
        addDeveloperDetail(contentPanel, "Mark Manuelle C. Regis",
                "assets\\images\\developers\\mark.png",
                1000, baseY);

        // Add return button inside content panel
        JButton returnButton = new RoundedButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 30));
        returnButton.setBounds(560, 700, 150, 40); // Centered at the bottom
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(new Color(24, 26, 78));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new AboutMainPage(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        contentPanel.add(returnButton);

        // Adjust component position on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                contentPanelX = (frameWidth - contentPanelWidth) / 2;
                contentPanelY = (frameHeight - contentPanelHeight) / 2;
                contentPanel.setBounds(contentPanelX, contentPanelY, contentPanelWidth, contentPanelHeight);
            }
        });
    }

    private void addDeveloperDetail(RoundedPanel panel, String name, String imagePath, int x, int y) {
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(x, y + 150, 250, 50); // Adjust the y position slightly if images are larger
        panel.add(nameLabel);

        ImageIcon icon = new ImageIcon(imagePath);
        // Change these values to adjust the size of the images
        int imageWidth = 150; // New width of the image
        int imageHeight = 150; // New height of the image
        Image scaledImage = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(x, y, imageWidth, imageHeight); // Adjust bounds to new image size
        panel.add(imageLabel);
    }

}