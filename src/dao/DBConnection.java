/**
 * @author  Melinda Teed   GitHub: https://github.com/teedinmt
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    ///for MS Access database connection 
    private static final String DB_URL = "jdbc:ucanaccess://" + LocalCustomization.PATHTODB;
    public static Connection conn;

    public static void makeDBConnection() throws ClassNotFoundException, SQLException, Exception {
        try {
            conn = (Connection) DriverManager.getConnection(DB_URL);
            System.out.println("Database Connection Made Successfully using DB_URL");

        } catch (Exception ex) {
            System.out.println("Could not make db connection" + ex);

        }
    }

    public static void closeDBConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("\nProgram closed. \n Database connection closed!");

    }

}
