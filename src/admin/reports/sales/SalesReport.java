package admin.reports.sales;

import customcomponents.RoundedPanel;
import customcomponents.RoundedTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.AdminMenu;
import admin.reports.ReportsPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesReport extends JPanel {
    private JFrame mainFrame;

    public SalesReport(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Back arrow button
        JButton backButton = new JButton("<");
        backButton.setBounds(20, 30, 50, 30);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(new ReportsPage(mainFrame));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Sales Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 300, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        add(titleLabel);

        // Date Input Panel
        RoundedPanel datePanel = new RoundedPanel(30);
        datePanel.setLayout(null); // Use absolute layout to control bounds manually
        datePanel.setBackground(new Color(30, 144, 255)); // Blue background
        datePanel.setBounds(50, 100, getWidth() - 100, 100);

        int padding = 50;
        int labelWidth = 150;
        int fieldWidth = 200;
        int fieldHeight = 30;

        JLabel startDateLabel = new JLabel("Enter Date Start");
        startDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        startDateLabel.setForeground(Color.WHITE);
        startDateLabel.setBounds(padding, padding, labelWidth, fieldHeight);

        RoundedTextField startDateField = new RoundedTextField(20, 10);
        startDateField.setBounds(padding + labelWidth + 10, padding, fieldWidth, fieldHeight);

        JLabel endDateLabel = new JLabel("Enter Date End");
        endDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endDateLabel.setForeground(Color.WHITE);
        endDateLabel.setBounds(padding + labelWidth + fieldWidth + 30, padding, labelWidth, fieldHeight);

        RoundedTextField endDateField = new RoundedTextField(20, 10);
        endDateField.setBounds(padding + 2 * labelWidth + fieldWidth + 40, padding, fieldWidth, fieldHeight);

        datePanel.add(startDateLabel);
        datePanel.add(startDateField);
        datePanel.add(endDateLabel);
        datePanel.add(endDateField);

        add(datePanel);

        // Table Data
        String[] columnNames = { "Date", "Hours Open", "Hours Closed", "Products Sold", "Tax", "Return/Refund",
                "Total Sales That Day" };
        Object[][] data = {
                // {"11/25/23", "8:00 AM", "10:00 PM", 50, 100.00, 0, "P6,500.00"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 144, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 220, getWidth() - 100, getHeight() - 300);
        add(tableScrollPane);

        // Add component listener to keep elements centered and resized properly
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                datePanel.setBounds(50, 100, frameWidth - 100, 100);
                tableScrollPane.setBounds(50, 220, frameWidth - 100, frameHeight - 300);
            }
        });
    }

}
