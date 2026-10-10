
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3307/CampusConnectDB";

    public static Connection getConnection() throws SQLException {

        String user = System.getenv("CAMPUSCONNECT_DB_USER");
        String password = System.getenv("CAMPUSCONNECT_DB_PASSWORD");

        if (user == null || password == null ||
                user.isBlank() || password.isBlank()) {
            throw new SQLException(
                    "Database credentials are not configured."
            );
        }

        return DriverManager.getConnection(URL, user, password);
    }

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {

            System.out.println(
                    "CampusConnect Database Connected Successfully!"
            );

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");
            System.out.println(e.getMessage());
        }
    }
}
