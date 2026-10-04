# CampusConnect

## Campus Lost and Found Management System

CampusConnect is a Java-based console application designed to
demonstrate a simple campus lost and found management workflow.

The system allows students to report lost or found items, search for
items, submit claims, and provide reporter contact information. An
administrator can review claims, approve or reject them, and update an
item's status when it is returned.

## Features

### Student Portal

-   Report a lost item
-   Report a found item
-   Search items by partial item name
-   Submit a claim for an available item
-   Store reporter name and contact number
-   Prevent duplicate item IDs
-   Prevent duplicate active claims for the same item

### Admin Portal

-   Admin login
-   View lost items
-   View found items
-   View submitted claims
-   Approve or reject claims
-   Mark claimed items as returned

### Validation and Exception Handling

-   Handles invalid numeric input using `try-catch`
-   Prevents duplicate Item IDs
-   Prevents duplicate Claim IDs
-   Prevents claims for unavailable items
-   Prevents multiple active claims for the same item
-   Displays a clear message when no claims are available

## Project Structure

``` text
CampusConnect/
│
├── Main.java
├── User.java
├── Student.java
├── Admin.java
├── LostItem.java
├── FoundItem.java
├── Claim.java
└── README.md
```

## Java Concepts Used

-   Object-Oriented Programming
-   Classes and Objects
-   Inheritance
-   Constructors
-   ArrayList
-   Loops and Conditional Statements
-   String handling
-   Exception Handling using `try-catch`
-   Basic input validation

## Item Status Flow

``` text
Available
    ↓
Claim Submitted
    ↓
Pending
    ↓
Admin Approval
    ↓
Claimed
    ↓
Returned
```

A claim can also be rejected by the administrator.

## How to Run

### Requirements

-   Java JDK installed
-   VS Code, IntelliJ IDEA, Eclipse, or any Java-compatible IDE

### Compile

Open a terminal in the project folder and run:

``` bash
javac *.java
```

### Run

``` bash
java Main
```

## Demo Flow

A typical demonstration can follow this sequence:

1.  Open the Student Portal.
2.  Enter the student's name.
3.  Report a lost or found item.
4.  Enter reporter name and contact number.
5.  Search for an item using part of its name.
6.  Submit a claim.
7.  Return to the main menu.
8.  Login as Admin.
9.  View the reported items and claims.
10. Approve or reject a claim.
11. Mark an approved/claimed item as returned.

## Project Scope

CampusConnect is developed as a demonstration/prototype model. It uses
in-memory `ArrayList` collections rather than a database or permanent
account storage. The focus is on demonstrating the core Java programming
and OOP concepts required for a campus lost and found workflow.

## Author

CampusConnect Java Project
