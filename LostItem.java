public class LostItem {

    int itemId;
    String itemName;
    String description;
    String location;
    String date;
    String reporterName;
    String contactNumber;
    String status;

    LostItem(int itemId, String itemName, String description,
             String location, String date,
             String reporterName, String contactNumber) {

        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.location = location;
        this.date = date;
        this.reporterName = reporterName;
        this.contactNumber = contactNumber;
        this.status = "Available";
    }

    void displayLostItem() {

        System.out.println("Item ID: " + itemId);
        System.out.println("Item Name: " + itemName);
        System.out.println("Description: " + description);
        System.out.println("Location: " + location);
        System.out.println("Date: " + date);
        System.out.println("Reported By: " + reporterName);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Status: " + status);
    }
}