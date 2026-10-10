import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClaimDAO {

    // Add a new claim to the database
    public static boolean addClaim(
            int claimId,
            int itemId,
            String studentName,
            String status) {

        String sql = "INSERT INTO Claims " +
                     "(claim_id, item_id, student_name, status) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claimId);
            ps.setInt(2, itemId);
            ps.setString(3, studentName);
            ps.setString(4, status);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error adding claim: " + e.getMessage());

            return false;
        }
    }

    // Display all claims
    public static void displayClaims() {

        String sql = "SELECT * FROM Claims";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== CLAIMS FROM DATABASE =====");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Claim ID: " +
                        rs.getInt("claim_id"));

                System.out.println("Item ID: " +
                        rs.getInt("item_id"));

                System.out.println("Student Name: " +
                        rs.getString("student_name"));

                System.out.println("Status: " +
                        rs.getString("status"));

                System.out.println("----------------------------");
            }

            if (!found) {
                System.out.println("No claims available.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error displaying claims: " + e.getMessage());
        }
    }

    // Update claim status
    public static boolean updateClaimStatus(
            int claimId,
            String newStatus) {

        String sql = "UPDATE Claims SET status = ? WHERE claim_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, claimId);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating claim: " + e.getMessage());

            return false;
        }
    }
}