public class Student extends User {

    String studentId;
    String course;

    Student(String name, String email, String password,
            String studentId, String course) {

        super(name, email, password);
        this.studentId = studentId;
        this.course = course;
    }

    @Override
    void displayRole() {
        System.out.println("Role: Student");
    }

    void displayStudent() {
        displayUser();
        displayRole();
        System.out.println("Student ID: " + studentId);
        System.out.println("Course: " + course);
    }
}