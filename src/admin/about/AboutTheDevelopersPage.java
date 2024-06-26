package admin.about;

import javax.swing.*;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutTheDevelopersPage extends JPanel {
    private JFrame mainFrame;
    private int contentPanelWidth = 700;
    private int contentPanelHeight = 400;
    private int contentPanelX;
    private int contentPanelY;

    public AboutTheDevelopersPage(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponent();
    }

    private void initComponent() {
        // Panel settings
        setLayout(null);
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
                mainFrame.setContentPane(new AboutMainPage(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Add title label
        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(70, 30, 100, 30);
        add(titleLabel);

        // Content panel
        RoundedPanel contentPanel = new RoundedPanel(15);
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(30, 144, 255));
        contentPanelX = (getWidth() - contentPanelWidth) / 2;
        contentPanelY = (getHeight() - contentPanelHeight) / 2;
        contentPanel.setBounds(contentPanelX, contentPanelY, contentPanelWidth, contentPanelHeight);
        add(contentPanel);

        // Add About Us title inside content panel
        JLabel aboutUsLabel = new JLabel("About the Developers");
        aboutUsLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Thicker font for emphasis
        aboutUsLabel.setForeground(Color.WHITE);
        aboutUsLabel.setBounds(30, 20, 640, 30); // Left justified
        contentPanel.add(aboutUsLabel);

        // Add text area for content
        JTextArea textArea = new JTextArea();
        textArea.setText("We are a dynamic team of 4 computer science students currently pursuing our Bachelor of "
                + "Science in Computer Science (BSCS), and we're thrilled to introduce you to our innovative software project. "
                + "As third-year students, we are passionate about applying our theoretical knowledge to real-world scenarios, "
                + "and our latest endeavor is a testament to that commitment.\n\n"
                + "Team Members:\n"
                + "- Celis John Carlos E.\n"
                + "- Mariano Charimel C.\n"
                + "- Molina Gabriel S.\n"
                + "- Regis Mark Manuelle C.");
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 144, 255));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 60, 640, 270); // Adjust size and position as needed
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        // Add return button inside content panel
        JButton returnButton = new RoundedButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 16));
        returnButton.setBounds(570, 340, 100, 30); // Right justified and properly inside the blue box
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(new Color(24, 26, 78));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new AboutMainPage(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        contentPanel.add(returnButton);

        // Add component listener to dynamically adjust component positions on resize
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

}