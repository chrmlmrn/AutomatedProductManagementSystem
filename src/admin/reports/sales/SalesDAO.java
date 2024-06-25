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
        String sql = "SELECT sale_date, hours_open, hours_closed, products_sold, tax, return_refund, total_sales " +
                "FROM sales_summary " +
                "WHERE sale_date BETWEEN ? AND ? " +
                "ORDER BY sale_date";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleDate(rs.getDate("sale_date"));
                    sale.setHoursOpen(rs.getInt("hours_open"));
                    sale.setHoursClosed(rs.getInt("hours_closed"));
                    sale.setProductsSold(rs.getInt("products_sold"));
                    sale.setTax(rs.getDouble("tax"));
                    sale.setReturnRefund(rs.getDouble("return_refund"));
                    sale.setTotalSales(rs.getDouble("total_sales"));
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}