package cashier.about;

import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class AboutTheDevelopersPage extends JPanel {
    private JFrame mainFrame;
    private String uniqueUserId;

    public AboutTheDevelopersPage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;

        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use null layout for absolute positioning
        setBackground(Color.WHITE);

        // Add back button
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
                mainFrame.setContentPane(new AboutMainPage(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Add title label
        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(90, 30, 100, 30);
        add(titleLabel);

        // Content panel
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(30, 144, 255));
        contentPanel.setBounds(0, 0, 900, 600); // Increased width, will be centered later
        add(contentPanel);

        // Add About the Developers title inside content panel
        JLabel aboutDevelopersLabel = new JLabel("About the Developers");
        aboutDevelopersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        aboutDevelopersLabel.setForeground(Color.WHITE);
        aboutDevelopersLabel.setBounds(30, 20, 840, 30);
        contentPanel.add(aboutDevelopersLabel);

        // Add text area for content
        JTextArea textArea = new JTextArea();
        textArea.setText(
                "We are a dynamic team of four computer science students currently pursuing our Bachelor of Science in Computer Science (BSCS). "
                        + "We are thrilled to introduce you to our innovative software project. As third-year students, we are passionate about applying our theoretical knowledge to real-world scenarios. "
                        + "Our diverse skill sets range from front-end design to back-end system architecture, enabling us to tackle complex challenges and innovate effectively. "
                        + "Together, we share a vision of creating technology that not only meets current demands but also anticipates future needs.");
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 144, 255));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 60, 840, 150);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        // Developer details and images
        int baseY = 230; // Adjust baseY position based on new description area
        addDeveloperDetail(contentPanel, "   John Carlos E. Celis",
                "assets/images/developers/john.jpg", 50, baseY);
        addDeveloperDetail(contentPanel, "   Charimel C. Mariano",
                "assets/images/developers/charimel.jpg", 250, baseY);
        addDeveloperDetail(contentPanel, "     Gabriel S. Molina",
                "assets/images/developers/gab.png", 450, baseY);
        addDeveloperDetail(contentPanel, "   Mark Manuelle C. Regis",
                "assets/images/developers/mark.png", 650, baseY);

        // Add return button inside content panel
        JButton returnButton = new RoundedButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnButton.setBounds(740, 540, 100, 30);
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

        // Add resize listener to center content panel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getSize();
                contentPanel.setBounds((size.width - 900) / 2, (size.height - 600) / 2, 900, 600);
            }
        });
    }

    private void addDeveloperDetail(RoundedPanel panel, String name, String imagePath, int x, int y) {
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(x - 25, y + 160, 200, 30); // Adjust the y position for name and increase width
        panel.add(nameLabel);

        ImageIcon icon = new ImageIcon(imagePath);
        // Change these values to adjust the size of the images
        int imageWidth = 150; // Width of the image
        int imageHeight = 150; // Height of the image
        Image scaledImage = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(x, y, imageWidth, imageHeight); // Adjust bounds to image size
        panel.add(imageLabel);
    }
}