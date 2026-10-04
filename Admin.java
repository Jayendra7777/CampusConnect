public class Admin extends User {

    String adminId;

    Admin(String name, String email, String password, String adminId) {
        super(name, email, password);
        this.adminId = adminId;
    }

    void displayAdmin() {
        displayUser();
        System.out.println("Admin ID: " + adminId);
    }
}