package src.admin.maintenance.userlogs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseUtil;

public class UserLogUtil {
    public static void logUserAction(int userId, String action) {
        String query = "INSERT INTO user_logs (user_id, user_action) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, action);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
