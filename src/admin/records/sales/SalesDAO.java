package admin.records.sales;

import database.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT t.date AS sale_date, " +
                "SUM(t.subtotal) AS total_sales, " +
                "SUM(t.vat) AS tax, " +
                "SUM(COALESCE((SELECT SUM(r.return_quantity * p.product_price) " +
                "               FROM return_products r " +
                "               JOIN products p ON r.product_id = p.product_id " +
                "               WHERE DATE(r.return_date) = t.date), 0)) AS return_refund, " +
                "COUNT(*) AS products_sold " +
                "FROM transactions t " +
                "GROUP BY t.date";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSaleDate(rs.getDate("sale_date"));
                sale.setProductsSold(rs.getInt("products_sold"));
                sale.setTax(rs.getDouble("tax"));
                sale.setReturnRefund(rs.getDouble("return_refund"));
                sale.setTotalSales(rs.getDouble("total_sales"));
                sale.setHoursOpen(8); // Assuming fixed hours open/closed for simplicity
                sale.setHoursClosed(16);
                sales.add(sale);
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
                + "SUM(discount) AS return_refund, "
                + "COUNT(*) AS products_sold "
                + "FROM transactions "
                + "GROUP BY date";

        String sqlInsert = "INSERT INTO sales_summary (sale_date, hours_open, hours_closed, products_sold, tax, return_refund, total_sales) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "products_sold = VALUES(products_sold), "
                + "tax = VALUES(tax), "
                + "return_refund = VALUES(return_refund), "
                + "total_sales = VALUES(total_sales)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsertOrUpdate = conn.prepareStatement(sqlInsert);
                ResultSet rs = pstmtSelect.executeQuery()) {

            while (rs.next()) {
                java.sql.Date saleDate = rs.getDate("sale_date");
                int productsSold = rs.getInt("products_sold");
                double tax = rs.getDouble("tax");
                double returnRefund = rs.getDouble("return_refund");
                double totalSales = rs.getDouble("total_sales");

                pstmtInsertOrUpdate.setDate(1, saleDate);
                pstmtInsertOrUpdate.setInt(2, 8); // Example hours_open value
                pstmtInsertOrUpdate.setInt(3, 16); // Example hours_closed value
                pstmtInsertOrUpdate.setInt(4, productsSold);
                pstmtInsertOrUpdate.setDouble(5, tax);
                pstmtInsertOrUpdate.setDouble(6, returnRefund);
                pstmtInsertOrUpdate.setDouble(7, totalSales);

                pstmtInsertOrUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
