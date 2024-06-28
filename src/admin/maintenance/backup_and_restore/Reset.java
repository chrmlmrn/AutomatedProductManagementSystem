package admin.maintenance.backup_and_restore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Reset {
    public static void resetDatabase(String username, String password, String databaseName) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/" + databaseName;
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(jdbcDriver);
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            Statement stmt = conn.createStatement();

            // Disable foreign key checks
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");

            // Get all table names
            ResultSet rs = stmt.executeQuery("SHOW TABLES;");
            List<String> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            rs.close();

            // Truncate all tables
            for (String table : tables) {
                stmt.executeUpdate("TRUNCATE TABLE " + table + ";");
            }

            // Enable foreign key checks
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");

            stmt.close();
            conn.close();
            System.out.println("Database reset to default successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to reset database.");
        }
    }

    public static void main(String[] args) {
        resetDatabase("root", "root", "lavega_store_db");
    }
}
