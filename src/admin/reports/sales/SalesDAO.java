package admin.reports.sales;

import database.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    public List<Sale> getSales(String startDate, String endDate) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT t.date AS sale_date, " +
                "SUM(t.subtotal) AS total_sales, " +
                "SUM(t.vat) AS tax, " +
                "SUM(t.discount) AS total_discount " +
                "FROM transactions t " +
                "WHERE t.date BETWEEN ? AND ? " +
                "GROUP BY t.date";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleDate(rs.getDate("sale_date"));
                    sale.setTax(rs.getDouble("tax"));
                    sale.setTotalDiscount(rs.getDouble("total_discount"));
                    sale.setTotalSales(rs.getDouble("total_sales"));
                    // Assuming fixed hours open/closed for simplicity
                    sale.setHoursOpen(8);
                    sale.setHoursClosed(16);
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    public void updateSalesSummary() {
        String sqlSelect = "SELECT date AS sale_date, "
                + "SUM(subtotal) AS total_sales, "
                + "SUM(vat) AS tax, "
                + "SUM(discount) AS total_discount "
                + "FROM transactions "
                + "GROUP BY date";

        String sqlInsert = "INSERT INTO sales_summary (sale_date, hours_open, hours_closed, tax, total_discount, total_sales) "
                + "VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "tax = VALUES(tax), "
                + "total_discount = VALUES(total_discount), "
                + "total_sales = VALUES(total_sales)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsertOrUpdate = conn.prepareStatement(sqlInsert);
                ResultSet rs = pstmtSelect.executeQuery()) {

            while (rs.next()) {
                java.sql.Date saleDate = rs.getDate("sale_date");
                double tax = rs.getDouble("tax");
                double totalDiscount = rs.getDouble("total_discount");
                double totalSales = rs.getDouble("total_sales");

                pstmtInsertOrUpdate.setDate(1, saleDate);
                pstmtInsertOrUpdate.setInt(2, 8); // Example hours_open value
                pstmtInsertOrUpdate.setInt(3, 16); // Example hours_closed value
                pstmtInsertOrUpdate.setDouble(4, tax);
                pstmtInsertOrUpdate.setDouble(5, totalDiscount);
                pstmtInsertOrUpdate.setDouble(6, totalSales);

                pstmtInsertOrUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
