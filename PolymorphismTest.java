public class PolymorphismTest {

    public static void main(String[] args) {

        User user1 = new Student(
                "Jayendra",
                "jayendra@gmail.com",
                "student123",
                "ST101",
                "B.Tech CSE"
        );

        User user2 = new Admin(
                "Campus Admin",
                "admin@gmail.com",
                "admin123",
                "AD101"
        );

        System.out.println("===== POLYMORPHISM TEST =====");

        System.out.println("\nStudent Object:");
        user1.displayRole();

        System.out.println("\nAdmin Object:");
        user2.displayRole();
    }
}