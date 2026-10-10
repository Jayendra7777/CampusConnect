public class ClaimService {

    // Synchronized method to safely process a claim
    public synchronized void processClaim(int claimId, String studentName) {

        System.out.println(
                Thread.currentThread().getName() +
                " is processing Claim ID: " + claimId
        );

        System.out.println(
                "Student: " + studentName
        );

        try {
            // Simulate claim verification
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Claim processing interrupted.");
            Thread.currentThread().interrupt();
        }

        System.out.println(
                "Claim ID " + claimId +
                " processed successfully."
        );

        System.out.println("----------------------------");
    }

    public static void main(String[] args) {

        ClaimService service = new ClaimService();

        Thread student1 = new Thread(() -> {
            service.processClaim(301, "Student A");
        }, "Student Thread 1");

        Thread student2 = new Thread(() -> {
            service.processClaim(301, "Student B");
        }, "Student Thread 2");

        System.out.println("===== MULTITHREADING TEST =====");

        student1.start();
        student2.start();
    }
}