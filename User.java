public class User {

    String name;
    String email;
    String password;

    User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    void displayUser() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }

    // Method that can be overridden by child classes
    void displayRole() {
        System.out.println("Role: User");
    }
}