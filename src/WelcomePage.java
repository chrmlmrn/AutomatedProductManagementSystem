import javax.swing.*;
import customcomponents.RoundedButton;
import login.Login;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JPanel {

    private JFrame mainFrame;
    private String uniqueUserId;

    public WelcomePage(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponents();
    }

    private void initComponents() {
        // Set up the panel with a vertical BoxLayout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        // Load the image
        ImageIcon imageIcon = new ImageIcon("assets/images/Lavega.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(CENTER_ALIGNMENT); // Center the image horizontally
        add(imageLabel);

        // Add some space between the image and the button
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Create the start button
        RoundedButton startButton = new RoundedButton("Start");
        Dimension buttonSize = new Dimension(300, 50);
        startButton.setPreferredSize(buttonSize);
        startButton.setMinimumSize(buttonSize);
        startButton.setMaximumSize(buttonSize);
        startButton.setBackground(new Color(30, 144, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setAlignmentX(CENTER_ALIGNMENT); // Center the button horizontally
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new Login(mainFrame, uniqueUserId));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        // Add the button to the panel
        add(startButton);
    }

}
