public class Admin extends User {

    String adminId;

    Admin(String name, String email, String password, String adminId) {
        super(name, email, password);
        this.adminId = adminId;
    }

    @Override
    void displayRole() {
        System.out.println("Role: Admin");
    }

    void displayAdmin() {
        displayUser();
        displayRole();
        System.out.println("Admin ID: " + adminId);
    }
}