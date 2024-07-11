package admin.maintenance.backup_and_restore;

import java.io.IOException;

public class Restore {
    public static void restoreDatabase(String username, String password, String databaseName, String backupFilePath) {
        String mysqlPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql"; // Full path to mysql
        String[] executeCmd = new String[] { mysqlPath, "--user=" + username, "--password=" + password, databaseName,
                "-e", " source " + backupFilePath };
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("Database restored successfully.");
            } else {
                System.out.println("Could not restore the database.");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String username = "root";
        String password = "root";
        String databaseName = "lavega_store_db";
        String backupFilePath = "C:\\Users\\ADMIN\\OneDrive\\Desktop\\backup\\backup_lavega_store_db.sql";

        restoreDatabase(username, password, databaseName, backupFilePath);
    }
}
