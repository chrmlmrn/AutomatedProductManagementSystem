package cashier.help;

import customcomponents.RoundedButton;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserManual extends JPanel {
    private JFrame mainFrame;
    private static final int BUTTON_WIDTH = 1000;
    private static final int BUTTON_HEIGHT = 50;
    private static final int GAP = 20;
    private String uniqueUserId;
    private static final String PDF_PATH = "assets/file/USER MANUAL.pdf";

    public UserManual(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        addTopPanel();
        addPDFContentPanel();

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

    private void addPDFContentPanel() {
        JPanel pdfPanel = new JPanel(new GridBagLayout());
        pdfPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(GAP, 0, GAP, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        try {
            PDDocument document = PDDocument.load(new File(PDF_PATH));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();

            for (int i = 0; i < pageCount; i++) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 150, ImageType.RGB);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
                pdfPanel.add(imageLabel, gbc);
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Failed to load the PDF document.");
            errorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            errorLabel.setForeground(Color.RED);
            pdfPanel.setLayout(new BorderLayout());
            pdfPanel.add(errorLabel, BorderLayout.CENTER);
        }

        JScrollPane scrollPane = new JScrollPane(pdfPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }
}