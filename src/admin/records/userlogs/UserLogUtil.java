package admin.records.userlogs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseUtil;

public class UserLogUtil {
    public static void logUserAction(String uniqueUserId, String action) {
        String query = "INSERT INTO user_logs (unique_user_id, user_action) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("Logging action for user unique ID: " + uniqueUserId); // Debugging log
            statement.setString(1, uniqueUserId);
            statement.setString(2, action);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
