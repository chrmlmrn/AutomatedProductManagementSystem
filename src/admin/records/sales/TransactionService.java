package admin.records.sales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.DatabaseUtil;

public class TransactionService {
    private SalesDAO salesDAO;

    public TransactionService() {
        salesDAO = new SalesDAO();
    }

    public void handleNewTransaction(Transaction transaction) {
        // Logic to record the transaction...
        recordTransaction(transaction);

        // Update sales summary
        salesDAO.updateSalesSummary();
    }

    private void recordTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (receipt_number, reference_number, date, time, subtotal, discount, vat, total, product_id) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, transaction.getReceiptNumber());
            pstmt.setString(2, transaction.getReferenceNumber());
            pstmt.setDate(3, new java.sql.Date(transaction.getDate().getTime()));
            pstmt.setTime(4, new java.sql.Time(transaction.getTime().getTime()));
            pstmt.setDouble(5, transaction.getSubtotal());
            pstmt.setDouble(6, transaction.getDiscount());
            pstmt.setDouble(7, transaction.getVat());
            pstmt.setDouble(8, transaction.getTotal());
            pstmt.setInt(9, transaction.getProductId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods related to transactions...
}