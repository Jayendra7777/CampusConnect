public class Claim {

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
}