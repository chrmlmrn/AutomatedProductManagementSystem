package admin.reports.inventory;

import database.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public List<Product> getInventory() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_code, p.product_name, c.category_name, p.product_price, "
                + "SUM(i.product_total_quantity) AS product_total_quantity, "
                + "MAX(i.critical_stock_level) AS critical_stock_level, "
                + "MAX(ps.product_inventory_status_name) AS product_inventory_status_name, "
                + "MAX(pt.product_type_name) AS product_type_name, "
                + "MAX(s.supplier_name) AS supplier_name "
                + "FROM products p "
                + "JOIN category c ON p.category_id = c.category_id "
                + "JOIN inventory i ON p.product_id = i.product_id "
                + "JOIN product_inventory_status ps ON i.product_inventory_status_id = ps.product_inventory_status_id "
                + "JOIN product_type pt ON p.product_type_id = pt.product_type_id "
                + "JOIN supplier s ON p.supplier_id = s.supplier_id "
                + "GROUP BY p.product_code, p.product_name, c.category_name, p.product_price "
                + "ORDER BY p.product_code";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductCode(rs.getString("product_code"));
                product.setProductName(rs.getString("product_name"));
                product.setCategoryName(rs.getString("category_name"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setProductTotalQuantity(rs.getInt("product_total_quantity"));
                product.setCriticalLevel(rs.getInt("critical_stock_level"));
                product.setProductStatus(rs.getString("product_inventory_status_name"));
                product.setProductType(rs.getString("product_type_name"));
                product.setSupplierName(rs.getString("supplier_name"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Remove expired products
        removeExpiredProducts();

        return products;
    }

    public List<Product> getInventoryByDate(String currentDate) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_code, p.product_name, c.category_name, p.product_price, "
                + "i.product_total_quantity, i.critical_stock_level, ps.product_inventory_status_name, "
                + "pt.product_type_name, s.supplier_name, i.last_updated "
                + "FROM products p "
                + "JOIN category c ON p.category_id = c.category_id "
                + "JOIN inventory i ON p.product_id = i.product_id "
                + "JOIN product_inventory_status ps ON i.product_inventory_status_id = ps.product_inventory_status_id "
                + "JOIN product_type pt ON p.product_type_id = pt.product_type_id "
                + "JOIN supplier s ON p.supplier_id = s.supplier_id "
                + "WHERE DATE(i.last_updated) = ? "
                + "ORDER BY p.product_code";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, currentDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductCode(rs.getString("product_code"));
                product.setProductName(rs.getString("product_name"));
                product.setCategoryName(rs.getString("category_name"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setProductTotalQuantity(rs.getInt("product_total_quantity"));
                product.setCriticalLevel(rs.getInt("critical_stock_level"));
                product.setProductStatus(rs.getString("product_inventory_status_name"));
                product.setProductType(rs.getString("product_type_name"));
                product.setSupplierName(rs.getString("supplier_name"));
                product.setLastUpdated(rs.getTimestamp("last_updated"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getNearExpirationProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_code, p.product_name, pe.product_expiration_date, i.product_total_quantity "
                + "FROM products p "
                + "JOIN product_expiration pe ON p.product_id = pe.product_id "
                + "JOIN inventory i ON p.product_id = i.product_id "
                + "WHERE pe.product_expiration_date <= CURDATE() + INTERVAL 2 DAY "
                + "AND pe.product_expiration_date > CURDATE()";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductCode(rs.getString("product_code"));
                product.setProductName(rs.getString("product_name"));
                product.setProductExpirationDate(rs.getDate("product_expiration_date"));
                product.setProductTotalQuantity(rs.getInt("product_total_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private void removeExpiredProducts() {
        String sql = "DELETE FROM inventory WHERE product_id IN (SELECT p.product_id FROM products p "
                + "JOIN product_expiration pe ON p.product_id = pe.product_id "
                + "WHERE pe.product_expiration_date <= CURDATE())";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getCriticalInventory() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_code, p.product_name, i.product_total_quantity "
                + "FROM products p "
                + "JOIN inventory i ON p.product_id = i.product_id "
                + "WHERE i.product_total_quantity <= i.critical_stock_level";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

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

    public void updateProductStock(String productCode, int quantity) {
        String sql = "UPDATE inventory i "
                + "JOIN products p ON i.product_id = p.product_id "
                + "SET i.product_total_quantity = i.product_total_quantity + ? "
                + "WHERE p.product_code = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantity);
            pstmt.setString(2, productCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFullName(String uniqueUserId) {
        String fullName = "";
        String sql = "SELECT CONCAT(user_first_name, ' ', user_last_name) AS full_name FROM users WHERE unique_user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uniqueUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    fullName = rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fullName;
    }
}
