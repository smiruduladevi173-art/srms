package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn = null;

    // =====================================
    // CONNECT DATABASE
    // =====================================

    public static Connection connect() {

        try {

            Class.forName(
                    "org.sqlite.JDBC"
            );

            if(conn == null) {

                String path =
                        System.getProperty("user.dir")
                        + "/database/srms.db";

                System.out.println(
                        "DB PATH: " + path
                );

                String url =
                        "jdbc:sqlite:" + path;

                conn =
                        DriverManager.getConnection(url);

                System.out.println(
                        "✅ Database Connected Successfully"
                );
            }

        } catch(ClassNotFoundException e) {

            System.out.println(
                    "❌ JDBC Driver NOT found"
            );

        } catch(SQLException e) {

            System.out.println(
                    "❌ Connection Failed: "
                            + e.getMessage()
            );
        }

        return conn;
    }

    // =====================================
    // GET CONNECTION
    // =====================================

    public static Connection getConnection() {

        return connect();
    }
}