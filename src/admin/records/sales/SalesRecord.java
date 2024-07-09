package admin.records.sales;

import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import admin.records.RecordsMainPage;
import customcomponents.RoundedButton;
import customcomponents.RoundedPanel;
import database.DatabaseUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SalesRecord extends JPanel {
    private DefaultTableModel tableModel;
    private JTable salesTable;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JFrame mainFrame;
    private String uniqueUserId;
    private Timer timer;

    public SalesRecord(JFrame mainFrame, String uniqueUserId) {
        this.mainFrame = mainFrame;
        this.uniqueUserId = uniqueUserId;
        initComponents();
        setVisible(true);

        // Initialize table with data
        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection, null, null);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        startAutoRefresh();
    }

    private void initComponents() {
        setLayout(null); // Use absolute positioning
        setBackground(Color.WHITE);

        // Title and Back Button
        JButton backButton = new JButton("<");
        backButton.setFont(new Font("Arial", Font.BOLD, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(24, 26, 78));
        backButton.setFocusPainted(false);
        backButton.setBounds(20, 20, 50, 50);
        add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.setContentPane(new RecordsMainPage(mainFrame, uniqueUserId));
            mainFrame.revalidate();
            mainFrame.repaint();
            stopAutoRefresh(); // Stop auto-refresh when navigating away
        });

        JLabel titleLabel = new JLabel("Sales Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(24, 26, 78));
        titleLabel.setBounds(100, 30, 500, 30);
        add(titleLabel);

        // Rounded Blue Panel
        RoundedPanel bluePanel = new RoundedPanel(30);
        bluePanel.setBackground(new Color(30, 144, 255));
        bluePanel.setBounds(100, 120, 1200, 600);
        bluePanel.setLayout(null); // Use absolute positioning within the panel
        add(bluePanel);

        // Table Setup
        String[] columnNames = { "Date", "Hours Open", "Hours Closed", "Tax", "Total Discount", "Total Sales" };
        Object[][] data = {}; // Sample data

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        salesTable = new JTable(tableModel);
        salesTable.setFont(new Font("Arial", Font.PLAIN, 16));
        salesTable.setRowHeight(30);
        salesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        salesTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBounds(50, 50, 1100, 400);
        bluePanel.add(scrollPane);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(30, 144, 255));
        searchPanel.setLayout(null);
        searchPanel.setBounds(280, 470, 600, 80); // Center the search panel horizontally
        bluePanel.add(searchPanel);

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        startDateLabel.setForeground(Color.WHITE);
        startDateLabel.setBounds(50, 10, 80, 30);
        searchPanel.add(startDateLabel);

        startDateChooser = new JDateChooser();
        startDateChooser.setDateFormatString("yyyy-MM-dd");
        startDateChooser.setBounds(140, 10, 150, 30);
        searchPanel.add(startDateChooser);

        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        endDateLabel.setForeground(Color.WHITE);
        endDateLabel.setBounds(300, 10, 80, 30);
        searchPanel.add(endDateLabel);

        endDateChooser = new JDateChooser();
        endDateChooser.setDateFormatString("yyyy-MM-dd");
        endDateChooser.setBounds(390, 10, 150, 30);
        searchPanel.add(endDateChooser);

        RoundedButton searchButton = new RoundedButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.setBounds(250, 50, 150, 30); // Adjust the position of the search button within the search panel
        searchButton.addActionListener(e -> searchSalesByDate());
        searchPanel.add(searchButton);
    }

    private void searchSalesByDate() {
        stopAutoRefresh(); // Stop auto-refresh when navigating away

        java.util.Date startDate = startDateChooser.getDate();
        java.util.Date endDate = endDateChooser.getDate();

        java.sql.Date sqlStartDate = null;
        java.sql.Date sqlEndDate = null;

        if (startDate != null) {
            sqlStartDate = new java.sql.Date(startDate.getTime());
        }
        if (endDate != null) {
            sqlEndDate = new java.sql.Date(endDate.getTime());
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            refreshTable(connection, sqlStartDate, sqlEndDate);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Connection connection, java.sql.Date startDate, java.sql.Date endDate) {
        try {
            String query = "SELECT sale_date, hours_open, hours_closed, tax, total_discount, total_sales "
                    + "FROM sales_summary ";

            if (startDate != null && endDate != null) {
                query += "WHERE sale_date BETWEEN ? AND ? ";
            } else if (startDate != null) {
                query += "WHERE sale_date >= ? ";
            } else if (endDate != null) {
                query += "WHERE sale_date <= ? ";
            }

            PreparedStatement statement = connection.prepareStatement(query);

            if (startDate != null && endDate != null) {
                statement.setDate(1, startDate);
                statement.setDate(2, endDate);
            } else if (startDate != null) {
                statement.setDate(1, startDate);
            } else if (endDate != null) {
                statement.setDate(1, endDate);
            }

            ResultSet resultSet = statement.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            DecimalFormat df = new DecimalFormat("0.00");

            while (resultSet.next()) {
                Date saleDate = resultSet.getDate("sale_date");
                int hoursOpen = resultSet.getInt("hours_open");
                int hoursClosed = resultSet.getInt("hours_closed");
                double tax = resultSet.getDouble("tax");
                double totalDiscount = resultSet.getDouble("total_discount");
                double totalSales = resultSet.getDouble("total_sales");

                tableModel.addRow(new Object[] { saleDate, hoursOpen, hoursClosed, df.format(tax),
                        df.format(totalDiscount), df.format(totalSales) });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startAutoRefresh() {
        timer = new Timer(true); // Run timer as a daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    try (Connection connection = DatabaseUtil.getConnection()) {
                        refreshTable(connection, null, null);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        }, 0, 5000); // Refresh every 5 seconds
    }

    private void stopAutoRefresh() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
