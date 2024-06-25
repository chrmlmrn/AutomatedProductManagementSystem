package admin.reports.sales;

import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import customcomponents.RoundedTextField;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import admin.reports.ReportsPage;
import cashier.POS.ScanProduct;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SalesReport extends JPanel {

    private DefaultTableModel model;
    private SalesDAO salesDAO;
    private RoundedTextField startDateField;
    private RoundedTextField endDateField;

    private JFrame mainFrame;

    public SalesReport(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        salesDAO = new SalesDAO();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

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
        mainPanel.add(backButton);

        // Title Label
        JLabel titleLabel = new JLabel("Sales Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(80, 30, 300, 30);
        titleLabel.setForeground(new Color(24, 26, 78));
        mainPanel.add(titleLabel);

        // Date Input Panel
        RoundedPanel datePanel = new RoundedPanel(30);
        datePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        datePanel.setBackground(new Color(30, 144, 255)); // Blue background
        datePanel.setBounds(50, 100, 1800, 100);

        JLabel startDateLabel = new JLabel("Enter Date Start");
        startDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        startDateLabel.setForeground(Color.WHITE);

        startDateField = new RoundedTextField(20, 10);

        JLabel endDateLabel = new JLabel("Enter Date End");
        endDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endDateLabel.setForeground(Color.WHITE);

        endDateField = new RoundedTextField(20, 10);

        RoundedButton fetchButton = new RoundedButton("Fetch Data");
        fetchButton.setFont(new Font("Arial", Font.BOLD, 20));
        fetchButton.setBackground(Color.WHITE);
        fetchButton.setForeground(new Color(30, 144, 255));
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchData();
            }
        });

        datePanel.add(startDateLabel);
        datePanel.add(startDateField);
        datePanel.add(endDateLabel);
        datePanel.add(endDateField);
        datePanel.add(fetchButton);

        mainPanel.add(datePanel);

        // Table Data
        String[] columnNames = { "Date", "Hours Open", "Hours Closed", "Products Sold", "Tax", "Return/Refund",
                "Total Sales" };
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 144, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 250, 1800, 800);
        mainPanel.add(tableScrollPane);

        // Print Button
        JButton printButton = new JButton("Print");
        printButton.setBounds(1750, 220, 100, 30);
        printButton.setBackground(new Color(30, 144, 255));
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.setFont(new Font("Arial", Font.BOLD, 16));
        printButton.setBorder(BorderFactory.createEmptyBorder());
        printButton.addActionListener(e -> {
            try {
                table.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat("Sales Report"), null);
            } catch (PrinterException pe) {
                pe.printStackTrace();
            }
        });
        mainPanel.add(printButton);

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private void fetchData() {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both start and end dates.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Sale> sales = salesDAO.getSales(startDate, endDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.setRowCount(0); // Clear existing rows

        for (Sale sale : sales) {
            model.addRow(new Object[] {
                    dateFormat.format(sale.getSaleDate()),
                    sale.getHoursOpen(),
                    sale.getHoursClosed(),
                    sale.getProductsSold(),
                    sale.getTax(),
                    sale.getReturnRefund(),
                    sale.getTotalSales()
            });
        }

        if (sales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No sales data found for the given date range.", "No Data",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
