# CampusConnect

## Campus Lost and Found Management System

CampusConnect is a Java-based console application that demonstrates a campus lost-and-found workflow. Students can report lost or found items, search by item name, and submit claims. An administrator can review claims and update item status.

> **Repository status:** The source currently published on this branch is the console-based version. The newer Swing GUI + MySQL/JDBC project from the latest ZIP has not yet been uploaded to this GitHub repository.

## Features

### Student Portal
- Report lost items and found items
- Search using a partial item name
- Submit claims for available items
- Enter reporter details and contact number
- Validate item IDs and claim IDs
- Prevent duplicate active claims for the same item

### Admin Portal
- Admin login
- View lost items, found items, and submitted claims
- Approve or reject claims
- Mark items as returned

### Java Concepts Demonstrated
- Classes and objects
- Encapsulation and constructors
- Inheritance and polymorphism
- Interfaces
- `ArrayList` collections and enhanced `for` loops
- Conditional statements and loops
- Exception handling and input validation

## Project Structure

```text
CampusConnect/
├── Main.java
├── User.java
├── Student.java
├── Admin.java
├── LostItem.java
├── FoundItem.java
├── Claim.java
├── Searchable.java
├── Claimable.java
└── README.md
```

## Requirements

- Java JDK installed
- VS Code, IntelliJ IDEA, Eclipse, or another Java-compatible IDE

## How to Run

Open a terminal in the directory containing the Java files.

Compile:

```bash
javac *.java
```

Run:

```bash
java Main
```

## Suggested Demo Flow

1. Open the Student Portal.
2. Report a lost or found item.
3. Search for an item using part of its name.
4. Submit a claim.
5. Sign in to the Admin Portal.
6. View reported items and claims.
7. Approve or reject a claim.
8. Mark an item as returned.

## Limitations

- This published console version stores items and claims in in-memory collections; data is not persisted after the program exits.
- The console version is separate from the newer Swing GUI + MySQL/JDBC version.
- Demo credentials in source code are for educational use only and should not be reused for a real deployment.

## Contributors

- **Jayendra Chand** — Project development
- **Rudra Pratap** — PowerPoint presentation (PPT)
- **Karan Pareek** — Project assistance and support

## Future Improvements

- Publish the current Swing GUI and MySQL/JDBC implementation to this repository
- Add persistent storage and robust account authentication
- Add automated tests for reporting, searching, claims, and admin workflows
- Improve error handling and project setup documentation
