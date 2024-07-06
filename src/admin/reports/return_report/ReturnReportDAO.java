package admin.reports.return_report;

import database.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReturnReportDAO {
    public List<ReturnReportEntry> getReturnReports() {
        List<ReturnReportEntry> returnReports = new ArrayList<>();
        String sql = "SELECT rp.return_date, p.product_code, p.product_name, rp.return_quantity, rr.return_reason_name "
                +
                "FROM return_products rp " +
                "JOIN products p ON rp.product_id = p.product_id " +
                "JOIN return_reason rr ON rp.return_reason_id = rr.return_reason_id";

        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ReturnReportEntry entry = new ReturnReportEntry();
                entry.setReturnDate(rs.getDate("return_date"));
                entry.setProductCode(rs.getString("product_code")); // Retrieve product_code from product_id
                entry.setProductName(rs.getString("product_name"));
                entry.setReturnQuantity(rs.getInt("return_quantity"));
                entry.setReturnReason(rs.getString("return_reason_name"));
                returnReports.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnReports;
    }

    public String getFullName(String userUniqueId) {
        return userUniqueId;
    }
}
