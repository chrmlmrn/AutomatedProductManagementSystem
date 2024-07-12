import javax.swing.*;

import java.awt.*;

public class Main extends JFrame {
    public static final String WELCOME_PANEL = "Welcome";

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

        WelcomePage welcome = new WelcomePage(this, uniqueUserId);

        mainPanel.add(welcome, WELCOME_PANEL);

        // Set initial panel to Login
        cardLayout.show(mainPanel, WELCOME_PANEL);

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
