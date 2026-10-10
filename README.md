# CampusConnect

**Campus Lost and Found Management System**

CampusConnect is a Java Swing desktop application designed to help a campus community report, discover, and recover lost belongings. It uses MySQL for persistent storage and JDBC for database operations, with separate workflows for students and administrators.

## Features

### Student portal
- Create a student account and sign in using an email address or contact number.
- Store student accounts in the MySQL `Users` table.
- Store student passwords as salted PBKDF2 hashes.
- Report lost items with a name, description, location, date, reporter name, and contact number.
- Report found items with the same details.
- Optionally attach an item photo (JPG, JPEG, PNG, or GIF).
- Search item names using partial, case-insensitive matches.
- Submit claims for items.

### Admin portal
- View reported lost items, found items, and submitted claims.
- Approve or reject pending claims.
- Mark items as returned.

### Java and database concepts demonstrated
- Object-oriented programming with classes, objects, inheritance, and method overriding.
- Interfaces for searchable and claimable behavior.
- Exception handling and input/database error handling.
- Collections and generic collection types such as `ArrayList<T>`.
- A synchronized claim-processing example demonstrating threads and synchronization.
- JDBC database connectivity and DAO classes using prepared statements.
- Java Swing forms, dashboards, tables, and a shared visual theme.

## Technology stack

- **Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **Database access:** JDBC / MySQL Connector/J
- **Password hashing:** PBKDF2 with HMAC-SHA256

## Project structure

```text
CampusConnect/
├── LoginFrame.java
├── RegisterDialog.java
├── StudentDashboard.java
├── AdminDashboard.java
├── ReportLostItemFrame.java
├── ReportFoundItemFrame.java
├── SearchItemFrame.java
├── ClaimFrame.java
├── ViewLostItemsFrame.java
├── ViewFoundItemsFrame.java
├── ViewClaimsFrame.java
├── ApproveRejectClaimFrame.java
├── MarkReturnedFrame.java
├── User.java / Student.java / Admin.java
├── LostItem.java / FoundItem.java / Claim.java
├── Searchable.java / Claimable.java
├── DatabaseConnection.java
├── UserDAO.java / LostItemDAO.java / FoundItemDAO.java / ClaimDAO.java
├── ClaimService.java
├── AppStyle.java
├── database/
│   └── campusconnect.sql
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── uploads/                 # Created when item photos are uploaded
├── ADMIN_SETUP.txt
└── README.md
```

## Requirements

- JDK 11 or later (the project has been configured/tested with a newer JDK as well).
- MySQL Server.
- MySQL Connector/J, included in `lib/`.

## Database setup

1. Start your local MySQL Server.
2. Open `database/campusconnect.sql` in MySQL Workbench and execute it. The script creates `CampusConnectDB` and the required tables if they do not already exist.
3. Configure your own MySQL account credentials in Windows PowerShell. Replace the example password with your own; do not commit credentials to source control.

   ```powershell
   setx CAMPUSCONNECT_DB_USER "root"
   setx CAMPUSCONNECT_DB_PASSWORD "YOUR_MYSQL_PASSWORD"
   ```

4. Close and reopen VS Code so it receives the new environment variables.
5. The default JDBC URL is `jdbc:mysql://localhost:3307/CampusConnectDB`. If your MySQL Server uses a different port, update the URL in `DatabaseConnection.java`.
6. Keep the MySQL Server running while using CampusConnect.

The SQL script uses `CREATE TABLE IF NOT EXISTS`; it does not delete existing records. The item DAO also supports adding the `photo_path` column to existing item tables when needed.

## Compile and run

Open a terminal in the `CampusConnect` project directory.

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force build
javac -encoding UTF-8 -cp "lib/mysql-connector-j-26.7.0.jar" -d build *.java
java -cp "build;lib/mysql-connector-j-26.7.0.jar" LoginFrame
```

### macOS / Linux

Use `:` instead of `;` in the Java classpath:

```bash
mkdir -p build
javac -encoding UTF-8 -cp "lib/mysql-connector-j-26.7.0.jar" -d build *.java
java -cp "build:lib/mysql-connector-j-26.7.0.jar" LoginFrame
```

## Demo administrator login

Select **Admin** on the login screen and use the classroom-demo credentials below:

- **Email:** `admin123@gmail.com`
- **Password:** `admin123`

These demo credentials are currently hardcoded in `LoginFrame.java` and are visible to anyone who can read the source. Change them before publishing the repository publicly or using the application beyond a classroom demonstration. Student registration does not create administrator accounts.

## Item photos

The Report Lost Item and Report Found Item forms accept optional JPG, JPEG, PNG, or GIF images. Selected images are copied into the project's `uploads/` folder, and their relative paths are stored in the relevant item table's `photo_path` column. Keep the `uploads/` folder with the project when moving it to another computer if you want to retain uploaded images.

## Limitations

- This is a classroom project/prototype, not a production-ready campus service.
- Administrator credentials are hardcoded for demonstration purposes.
- Database availability is required for registration, login, and database-backed item/claim operations.
- The synchronized `ClaimService` demonstrates thread synchronization; it should not be interpreted as a complete production-grade concurrency or duplicate-claim prevention system.
- Uploaded image files are stored locally in the project directory rather than in cloud storage.

## Contributors

- **Rudra Pratap** — PowerPoint presentation (PPT).
- **Karan Pareek** — Project assistance and support.

## Author

**Jayendra Chand**

## License

No license has been specified for this project. Contact the project author before reusing or redistributing it.
