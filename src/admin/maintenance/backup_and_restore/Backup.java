package admin.maintenance.backup_and_restore;

import java.io.IOException;

public class Backup {
    public static void backupDatabase(String username, String password, String databaseName, String backupFilePath) {
        String mysqldumpPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump"; // Full path to mysqldump
        String executeCmd = mysqldumpPath
                + " --routines --triggers --single-transaction --add-drop-database --add-drop-table -u "
                + username + " -p" + password + " " + databaseName + " -r " + backupFilePath;
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup created successfully.");
            } else {
                System.out.println("Could not create the backup.");
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

        backupDatabase(username, password, databaseName, backupFilePath);
    }
}
