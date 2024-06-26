package admin.reports.inventory;

import database.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public List<Product> getInventory() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_code, p.product_name, c.category_name, p.product_price, i.product_total_quantity, "
                +
                "i.critical_stock_level, ps.product_inventory_status_name, pe.product_expiration_date, pt.product_type_name, s.supplier_name "
                +
                "FROM products p " +
                "JOIN category c ON p.category_id = c.category_id " +
                "JOIN inventory i ON p.product_id = i.product_id " +
                "JOIN product_inventory_status ps ON i.product_inventory_status_id = ps.product_inventory_status_id " +
                "LEFT JOIN product_expiration pe ON p.product_id = pe.product_id " +
                "JOIN product_type pt ON p.product_type_id = pt.product_type_id " +
                "JOIN supplier s ON p.supplier_id = s.supplier_id " +
                "ORDER BY p.product_name";

        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductCode(rs.getString("product_code"));
                product.setProductName(rs.getString("product_name"));
                product.setCategoryName(rs.getString("category_name"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setProductTotalQuantity(rs.getInt("product_total_quantity"));
                product.setCriticalLevel(rs.getInt("critical_stock_level"));
                product.setProductStatus(rs.getString("product_inventory_status_name"));
                product.setProductExpirationDate(rs.getDate("product_expiration_date"));
                product.setProductType(rs.getString("product_type_name"));
                product.setSupplierName(rs.getString("supplier_name")); // Set supplier name
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getCriticalInventory() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_code, p.product_name, i.product_total_quantity "
                + "FROM products p "
                + "JOIN inventory i ON p.product_id = i.product_id "
                + "WHERE i.product_total_quantity <= i.critical_stock_level";

        try (Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductCode(rs.getString("product_code"));
                product.setProductName(rs.getString("product_name"));
                product.setProductTotalQuantity(rs.getInt("product_total_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProductStatusToReordering(String productCode) {
        String sql = "UPDATE inventory i "
                + "JOIN products p ON i.product_id = p.product_id "
                + "SET i.product_inventory_status_id = 'REO' "
                + "WHERE p.product_code = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
