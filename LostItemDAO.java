import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LostItemDAO {

    // Backward-compatible method for callers that do not attach a photo.
    public static boolean addLostItem(
            int itemId, String itemName, String description, String location,
            String date, String reporterName, String contactNumber, String status) {
        return addLostItem(itemId, itemName, description, location,
                date, reporterName, contactNumber, status, null);
    }

    // Save item details and optional photo path.
    public static boolean addLostItem(
            int itemId, String itemName, String description, String location,
            String date, String reporterName, String contactNumber, String status, String photoPath) {
        String ensureSql = "ALTER TABLE LostItems ADD COLUMN photo_path VARCHAR(500) NULL";
        String sql = "INSERT INTO LostItems (item_id, item_name, description, location, date, " +
                     "reporter_name, contact_number, status, photo_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection()) {
            try (java.sql.Statement st = con.createStatement()) {
                try { st.executeUpdate(ensureSql); } catch (SQLException ignored) { /* column already exists */ }
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, itemId); ps.setString(2, itemName); ps.setString(3, description);
                ps.setString(4, location); ps.setString(5, date); ps.setString(6, reporterName);
                ps.setString(7, contactNumber); ps.setString(8, status); ps.setString(9, photoPath);
                ps.executeUpdate(); return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding lost item: " + e.getMessage());
            return false;
        }
    }

    // Display all lost items
    public static void displayLostItems() {

        String sql = "SELECT * FROM LostItems";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== LOST ITEMS FROM DATABASE =====");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Item ID: " +
                        rs.getInt("item_id"));

                System.out.println("Item Name: " +
                        rs.getString("item_name"));

                System.out.println("Description: " +
                        rs.getString("description"));

                System.out.println("Location: " +
                        rs.getString("location"));

                System.out.println("Date: " +
                        rs.getString("date"));

                System.out.println("Reported By: " +
                        rs.getString("reporter_name"));

                System.out.println("Contact Number: " +
                        rs.getString("contact_number"));

                System.out.println("Status: " +
                        rs.getString("status"));

                System.out.println("----------------------------");
            }

            if (!found) {
                System.out.println("No lost items found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error displaying lost items: " + e.getMessage());
        }
    }
}