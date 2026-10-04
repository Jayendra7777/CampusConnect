import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static int readInt(Scanner sc) {

    while (true) {

        try {

            return sc.nextInt();   // ✅ CORRECT

        } catch (InputMismatchException e) {

            System.out.println("\nInvalid input!");
            System.out.println("Please enter a number.");

            sc.nextLine();

            System.out.print("Enter again: ");
        }
    }
}

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

ArrayList<LostItem> lostItems = new ArrayList<>();
ArrayList<FoundItem> foundItems = new ArrayList<>();
ArrayList<Claim> claims = new ArrayList<>();


Admin admin = new Admin(
    "Admin",
    "admin@gmail.com",
    "admin123",
    "AD101"
);

while (true) {


        System.out.println("===== CAMPUSCONNECT =====");
        System.out.println("Campus Lost and Found Management System");

        System.out.println("\n1. Student Portal");
        System.out.println("2. Admin Login");
        System.out.println("3. Exit");

        System.out.print("\nEnter your choice: ");
        int choice = readInt(sc);

        if (choice == 1) {

    sc.nextLine();

    System.out.println("\n===== STUDENT PORTAL =====");

    System.out.print("Enter Student Name: ");
    String currentStudentName = sc.nextLine();

    System.out.println("\nWelcome, " + currentStudentName + "!");

    while (true) {

        System.out.println("\n===== STUDENT MENU =====");
        System.out.println("1. Report Lost Item");
        System.out.println("2. Report Found Item");
        System.out.println("3. Search Item");
        System.out.println("4. Submit Claim");
        System.out.println("5. Back");

        System.out.print("\nEnter your choice: ");
        int studentChoice = readInt(sc);

            if (studentChoice == 1) {

    System.out.println("\n===== REPORT LOST ITEM =====");

    System.out.print("Enter Item ID: ");
int itemId = readInt(sc);

boolean idExists = false;

for (LostItem item : lostItems) {
    if (item.itemId == itemId) {
        idExists = true;
    }
}

for (FoundItem item : foundItems) {
    if (item.itemId == itemId) {
        idExists = true;
    }
}

if (idExists) {
    System.out.println("\nItem ID already exists!");
    continue;
}

sc.nextLine();

    System.out.print("Enter Item Name: ");
    String itemName = sc.nextLine();


    System.out.print("Enter Description: ");
    String description = sc.nextLine();

    System.out.print("Enter Location: ");
    String location = sc.nextLine();

    System.out.print("Enter Date: ");
String date = sc.nextLine();

System.out.print("Enter Your Name: ");
String reporterName = sc.nextLine();

System.out.print("Enter Contact Number: ");
String contactNumber = sc.nextLine();

LostItem lostItem = new LostItem(
    itemId,
    itemName,
    description,
    location,
    date,
    reporterName,
    contactNumber
);
    lostItems.add(lostItem);

    System.out.println("\nLost item reported successfully!");

    lostItem.displayLostItem();
}
           else if (studentChoice == 2) {

    System.out.println("\n===== REPORT FOUND ITEM =====");

    System.out.print("Enter Item ID: ");
int itemId = readInt(sc);

boolean idExists = false;

for (LostItem item : lostItems) {
    if (item.itemId == itemId) {
        idExists = true;
    }
}

for (FoundItem item : foundItems) {
    if (item.itemId == itemId) {
        idExists = true;
    }
}

if (idExists) {
    System.out.println("\nItem ID already exists!");
    continue;
}

sc.nextLine();

    System.out.print("Enter Item Name: ");
    String itemName = sc.nextLine();

    System.out.print("Enter Description: ");
    String description = sc.nextLine();

    System.out.print("Enter Location: ");
    String location = sc.nextLine();

    System.out.print("Enter Date: ");
String date = sc.nextLine();

System.out.print("Enter Your Name: ");
String reporterName = sc.nextLine();

System.out.print("Enter Contact Number: ");
String contactNumber = sc.nextLine();

FoundItem foundItem = new FoundItem(
    itemId,
    itemName,
    description,
    location,
    date,
    reporterName,
    contactNumber
);
    foundItems.add(foundItem);

    System.out.println("\nFound item reported successfully!");

    foundItem.displayFoundItem();
}
            else if (studentChoice == 3) {

    System.out.print("\nEnter Item Name to search: ");
sc.nextLine();
String searchName = sc.nextLine();

boolean found = false;

    for (LostItem item : lostItems) {
         if (item.itemName.toLowerCase().contains(searchName.toLowerCase())) {
            System.out.println("\n===== LOST ITEM FOUND =====");
            item.displayLostItem();
            found = true;
        }
    }

    for (FoundItem item : foundItems) {
      if (item.itemName.toLowerCase().contains(searchName.toLowerCase())) {
            System.out.println("\n===== FOUND ITEM FOUND =====");
            item.displayFoundItem();
            found = true;
        }
    }

    if (!found) {
        System.out.println("\nItem not found.");
    }
}
         else if (studentChoice == 4) {

    System.out.print("\nEnter Item ID to claim: ");
    int claimItemId = readInt(sc);

    boolean itemFound = false;
    String itemStatus = "";

    // Check Lost Items
    for (LostItem item : lostItems) {

        if (item.itemId == claimItemId) {

            itemFound = true;
            itemStatus = item.status;
            break;
        }
    }

    // Check Found Items
    if (!itemFound) {

        for (FoundItem item : foundItems) {

            if (item.itemId == claimItemId) {

                itemFound = true;
                itemStatus = item.status;
                break;
            }
        }
    }

    if (!itemFound) {

        System.out.println("\nItem not found.");
        System.out.println("Claim cannot be submitted.");

    } else if (!itemStatus.equals("Available")) {

        System.out.println(
            "\nThis item is already " + itemStatus + "."
        );
        System.out.println("Claim cannot be submitted.");

    } else {

        // Check whether a claim already exists for this item
        boolean claimExists = false;

        for (Claim c : claims) {

            if (c.itemId == claimItemId &&
                (c.status.equals("Pending") ||
                 c.status.equals("Approved"))) {

                claimExists = true;
                break;
            }
        }

        if (claimExists) {

            System.out.println(
                "\nA claim already exists for this item."
            );
            System.out.println("You cannot submit another claim.");

        } else {

            System.out.print("Enter Claim ID: ");
            int claimId = readInt(sc);

            boolean claimIdExists = false;

            // Check duplicate Claim ID
            for (Claim c : claims) {

                if (c.claimId == claimId) {

                    claimIdExists = true;
                    break;
                }
            }

            if (claimIdExists) {

                System.out.println("\nClaim ID already exists.");

            } else {

              System.out.print("Enter Student Name: ");
                sc.nextLine();
              String studentName = sc.nextLine();

             Claim claim = new Claim(
             claimId,
             claimItemId,
             currentStudentName
             );

                claims.add(claim);

                System.out.println(
                    "\nClaim submitted successfully!"
                );

                claim.displayClaim();
            }
        }
    }
}
        
            else if (studentChoice == 5) {
    System.out.println("\nReturning to main menu...");
    break;
}
else {
    System.out.println("\nInvalid choice!");
}
        }

 
        }
        else if (choice == 2) {
            System.out.print("\nEnter Admin Email: ");
    String email = sc.next();

    System.out.print("Enter Admin Password: ");
    String password = sc.next();

    if (admin.email.equals(email) && admin.password.equals(password)) {

        System.out.println("\nAdmin Login Successful!");


    while (true) {

        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. View Lost Items");
        System.out.println("2. View Found Items");
        System.out.println("3. View Claims");
        System.out.println("4. Approve/Reject Claim");
        System.out.println("5. Mark Item as Returned");
        System.out.println("6. Back");

        System.out.print("\nEnter your choice: ");
        int adminChoice = readInt(sc);

    if (adminChoice == 1) {

        System.out.println("\n===== LOST ITEMS =====");

        for (LostItem item : lostItems) {
            item.displayLostItem();
            System.out.println();
        }

    } else if (adminChoice == 2) {

        System.out.println("\n===== FOUND ITEMS =====");

        for (FoundItem item : foundItems) {
            item.displayFoundItem();
            System.out.println();
        }

    } else if (adminChoice == 3) {

        System.out.println("\n===== CLAIMS =====");

        for (Claim claim : claims) {
            claim.displayClaim();
            System.out.println();
        }

   } else if (adminChoice == 4) {

    System.out.print("\nEnter Claim ID: ");
    int claimId = readInt(sc);

    boolean found = false;

    for (Claim claim : claims) {

        if (claim.claimId == claimId) {

            found = true;

            System.out.println("\n===== CLAIM DETAILS =====");
            claim.displayClaim();

            if (!claim.status.equals("Pending")) {

                System.out.println("\nThis claim has already been processed.");
                continue;
            }

            System.out.println("\n1. Approve");
            System.out.println("2. Reject");

            System.out.print("Enter choice: ");
            int statusChoice = readInt(sc);

            if (statusChoice == 1) {

                boolean itemFound = false;

                // Check Lost Items
                for (LostItem item : lostItems) {

                    if (item.itemId == claim.itemId) {

                        itemFound = true;

                        if (item.status.equals("Available")) {

                            claim.status = "Approved";
                            item.status = "Claimed";

                            System.out.println("\nClaim approved!");
                            System.out.println("Item status updated to Claimed.");

                        } else {

                            System.out.println(
                                "\nThis item is already " + item.status + "."
                            );
                            System.out.println("Claim cannot be approved.");
                        }

                        break;
                    }
                }

                // Check Found Items
                if (!itemFound) {

                    for (FoundItem item : foundItems) {

                        if (item.itemId == claim.itemId) {

                            itemFound = true;

                            if (item.status.equals("Available")) {

                                claim.status = "Approved";
                                item.status = "Claimed";

                                System.out.println("\nClaim approved!");
                                System.out.println(
                                    "Item status updated to Claimed."
                                );

                            } else {

                                System.out.println(
                                    "\nThis item is already " + item.status + "."
                                );
                                System.out.println(
                                    "Claim cannot be approved."
                                );
                            }

                            break;
                        }
                    }
                }

            } else if (statusChoice == 2) {

                claim.status = "Rejected";

                System.out.println("\nClaim rejected!");

            } else {

                System.out.println("\nInvalid choice!");
            }

            System.out.println("\n===== UPDATED CLAIM =====");
            claim.displayClaim();

            break;
        }
    }

    if (!found) {

        System.out.println("\nClaim not found.");
    }

}

else if (adminChoice == 5) {

    System.out.print("\nEnter Item ID to mark as returned: ");
    int returnItemId = readInt(sc);

    boolean itemFound = false;

    // Check Lost Items
    for (LostItem item : lostItems) {

        if (item.itemId == returnItemId) {

            itemFound = true;

            if (item.status.equals("Claimed")) {

                item.status = "Returned";

                System.out.println("\nItem marked as returned successfully!");
                item.displayLostItem();

            } else {

                System.out.println(
                    "\nItem cannot be marked as returned."
                );
                System.out.println(
                    "Current status: " + item.status
                );
            }

            break;
        }
    }

    // Check Found Items
    if (!itemFound) {

        for (FoundItem item : foundItems) {

            if (item.itemId == returnItemId) {

                itemFound = true;

                if (item.status.equals("Claimed")) {

                    item.status = "Returned";

                    System.out.println(
                        "\nItem marked as returned successfully!"
                    );

                    item.displayFoundItem();

                } else {

                    System.out.println(
                        "\nItem cannot be marked as returned."
                    );

                    System.out.println(
                        "Current status: " + item.status
                    );
                }

                break;
            }
        }
    }

    if (!itemFound) {

        System.out.println("\nItem not found.");
    }
}
else if (adminChoice == 6) {

    System.out.println("\nReturning to main menu...");
    break;

} else {

        System.out.println("\nInvalid choice!");
    }
} 
        }
        else {
    System.out.println("\nInvalid Admin email or password!");
}

}  
                else if (choice == 3) { 
            System.out.println("\nThank you for using CampusConnect!"); 
            break; 
        } 
        else { 
            System.out.println("\nInvalid choice!"); 
        } 

} 

sc.close(); 

}  
 
}   