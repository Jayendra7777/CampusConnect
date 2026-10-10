public class DAOTest {

    public static void main(String[] args) {

        System.out.println("===== CAMPUSCONNECT DAO TEST =====");

        // Test adding a user
        boolean userAdded = UserDAO.addUser(
                "Test Student",
                "test@gmail.com",
                "test123",
                "Student"
        );

        System.out.println(
                "User Test: " + (userAdded ? "SUCCESS" : "FAILED")
        );

        // Test adding a lost item
        boolean lostItemAdded = LostItemDAO.addLostItem(
                101,
                "Black Wallet",
                "Black leather wallet",
                "Library",
                "05-10-2026",
                "Test Student",
                "9876543210",
                "Available"
        );

        System.out.println(
                "Lost Item Test: " +
                (lostItemAdded ? "SUCCESS" : "FAILED")
        );

        // Test adding a found item
        boolean foundItemAdded = FoundItemDAO.addFoundItem(
                201,
                "Blue Bottle",
                "Blue water bottle",
                "Canteen",
                "05-10-2026",
                "Test Student",
                "9876543210",
                "Available"
        );

        System.out.println(
                "Found Item Test: " +
                (foundItemAdded ? "SUCCESS" : "FAILED")
        );

        // Test adding a claim
        boolean claimAdded = ClaimDAO.addClaim(
                301,
                101,
                "Test Student",
                "Pending"
        );

        System.out.println(
                "Claim Test: " +
                (claimAdded ? "SUCCESS" : "FAILED")
        );

        // Display database records
        System.out.println("\n===== USERS =====");
        UserDAO.findUserByEmail("test@gmail.com");

        System.out.println("\n===== LOST ITEMS =====");
        LostItemDAO.displayLostItems();

        System.out.println("\n===== FOUND ITEMS =====");
        FoundItemDAO.displayFoundItems();

        System.out.println("\n===== CLAIMS =====");
        ClaimDAO.displayClaims();
    }
}