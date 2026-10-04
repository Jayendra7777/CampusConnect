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
}