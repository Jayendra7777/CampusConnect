public class Claim implements Claimable {

    int claimId;
    int itemId;
    String studentName;
    String status;

    Claim(int claimId, int itemId, String studentName) {

        this.claimId = claimId;
        this.itemId = itemId;
        this.studentName = studentName;
        this.status = "Pending";
    }

    void displayClaim() {

        System.out.println("Claim ID: " + claimId);
        System.out.println("Item ID: " + itemId);
        System.out.println("Student Name: " + studentName);
        System.out.println("Status: " + status);
    }

    @Override
    public void submitClaim(int itemId, String studentName) {

        this.itemId = itemId;
        this.studentName = studentName;
        this.status = "Pending";

        System.out.println("\n===== CLAIM SUBMITTED =====");
        System.out.println("Item ID: " + this.itemId);
        System.out.println("Student Name: " + this.studentName);
        System.out.println("Status: " + this.status);
    }
}