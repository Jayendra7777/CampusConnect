import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserDAO {
    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    // The email column stores either an email address or a contact number.
    public static boolean registerStudent(String name, String loginId, String password) {
        String checkSql = "SELECT user_id FROM Users WHERE LOWER(email)=LOWER(?) LIMIT 1";
        String insertSql = "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, 'Student')";
        try (Connection con = DatabaseConnection.getConnection()) {
            try (PreparedStatement check = con.prepareStatement(checkSql)) {
                check.setString(1, loginId.trim());
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) return false;
                }
            }
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, name.trim());
                ps.setString(2, loginId.trim());
                ps.setString(3, hashPassword(password.toCharArray()));
                return ps.executeUpdate() == 1;
            }
        } catch (Exception e) {
            System.err.println("Student registration failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean authenticateStudent(String loginId, String password) {
        String sql = "SELECT user_id, password, role FROM Users WHERE LOWER(email)=LOWER(?) LIMIT 1";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loginId.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || !"Student".equalsIgnoreCase(rs.getString("role"))) return false;
                String stored = rs.getString("password");
                if (stored == null) return false;
                if (stored.startsWith("PBKDF2$")) return verifyPassword(password.toCharArray(), stored);
                // Migrate older plain-text demo accounts to a salted hash after a successful login.
                if (stored.equals(password)) {
                    try (PreparedStatement update = con.prepareStatement("UPDATE Users SET password=? WHERE user_id=?")) {
                        update.setString(1, hashPassword(password.toCharArray()));
                        update.setInt(2, rs.getInt("user_id"));
                        update.executeUpdate();
                    }
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            System.err.println("Student sign-in failed: " + e.getMessage());
            return false;
        }
    }

    private static String hashPassword(char[] password) throws Exception {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        spec.clearPassword();
        return "PBKDF2$" + ITERATIONS + "$" + Base64.getEncoder().encodeToString(salt) + "$" + Base64.getEncoder().encodeToString(hash);
    }

    private static boolean verifyPassword(char[] password, String stored) throws Exception {
        String[] parts = stored.split("\\$");
        if (parts.length != 4) return false;
        int iterations = Integer.parseInt(parts[1]);
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        byte[] expected = Base64.getDecoder().decode(parts[3]);
        KeySpec spec = new PBEKeySpec(password, salt, iterations, expected.length * 8);
        byte[] actual = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        ((PBEKeySpec) spec).clearPassword();
        if (actual.length != expected.length) return false;
        int diff = 0;
        for (int i = 0; i < actual.length; i++) diff |= actual[i] ^ expected[i];
        return diff == 0;
    }

    // Retained for the existing DAO test utilities.
    public static boolean addUser(String name, String email, String password, String role) {
        if (role == null || !role.equalsIgnoreCase("Student")) return false;
        return registerStudent(name, email, password);
    }

    public static void findUserByEmail(String email) {
        String sql = "SELECT user_id, name, email, role FROM Users WHERE LOWER(email)=LOWER(?)";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("User ID: " + rs.getInt("user_id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Email/contact: " + rs.getString("email"));
                    System.out.println("Role: " + rs.getString("role"));
                } else System.out.println("User not found.");
            }
        } catch (SQLException e) { System.out.println("Error finding user: " + e.getMessage()); }
    }
}
