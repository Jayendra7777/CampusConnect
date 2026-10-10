import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class ClaimFrame extends JFrame {

    private JTextField claimIdField;
    private JTextField itemIdField;
    private JTextField studentNameField;
    private JTextArea itemDetailsArea;

    public ClaimFrame(String studentEmail) {

        setTitle("CampusConnect - Submit Claim");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("SUBMIT ITEM CLAIM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(185, 20, 250, 35);
        panel.add(titleLabel);

        // Claim ID
        JLabel claimIdLabel = new JLabel("Claim ID:");
        claimIdLabel.setBounds(50, 75, 120, 25);
        panel.add(claimIdLabel);

        claimIdField = new JTextField();
        claimIdField.setBounds(180, 75, 300, 25);
        panel.add(claimIdField);

        // Item ID
        JLabel itemIdLabel = new JLabel("Item ID:");
        itemIdLabel.setBounds(50, 115, 120, 25);
        panel.add(itemIdLabel);

        itemIdField = new JTextField();
        itemIdField.setBounds(180, 115, 300, 25);
        panel.add(itemIdField);

        // Student Name
        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameLabel.setBounds(50, 155, 120, 25);
        panel.add(studentNameLabel);

        studentNameField = new JTextField();
        studentNameField.setBounds(180, 155, 300, 25);
        studentNameField.setText(studentEmail);
        panel.add(studentNameField);

        // Check Item button
        JButton checkButton = new JButton("Check Item");
        checkButton.setBounds(200, 195, 150, 35);
        panel.add(checkButton);

        // Item details
        itemDetailsArea = new JTextArea();
        itemDetailsArea.setEditable(false);
        itemDetailsArea.setLineWrap(true);
        itemDetailsArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(itemDetailsArea);
        scrollPane.setBounds(50, 245, 480, 100);
        panel.add(scrollPane);

        // Submit claim button
        JButton submitButton = new JButton("Submit Claim");
        submitButton.setBounds(180, 365, 150, 40);
        panel.add(submitButton);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(350, 365, 100, 40);
        panel.add(closeButton);

        // Actions
        checkButton.addActionListener(e -> checkItem());

        submitButton.addActionListener(e -> submitClaim());

        closeButton.addActionListener(e -> dispose());

        add(panel);
        AppStyle.styleFrame(this);

        setVisible(true);
    }

    private void checkItem() {

        String itemIdText = itemIdField.getText().trim();

        if (itemIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an Item ID.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            int itemId = Integer.parseInt(itemIdText);

            boolean found = false;

            itemDetailsArea.setText("");

            try (Connection con = DatabaseConnection.getConnection()) {

                String lostSql =
                        "SELECT * FROM LostItems " +
                        "WHERE item_id = ? AND status = 'Available'";

                try (PreparedStatement ps = con.prepareStatement(lostSql)) {

                    ps.setInt(1, itemId);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        found = true;

                        itemDetailsArea.append(
                                "===== LOST ITEM =====\n" +
                                "Item ID: " + rs.getInt("item_id") + "\n" +
                                "Item Name: " + rs.getString("item_name") + "\n" +
                                "Description: " + rs.getString("description") + "\n" +
                                "Location: " + rs.getString("location") + "\n" +
                                "Status: " + rs.getString("status")
                        );
                    }
                }

                if (!found) {

                    String foundSql =
                            "SELECT * FROM FoundItems " +
                            "WHERE item_id = ? AND status = 'Available'";

                    try (PreparedStatement ps =
                                 con.prepareStatement(foundSql)) {

                        ps.setInt(1, itemId);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            found = true;

                            itemDetailsArea.append(
                                    "===== FOUND ITEM =====\n" +
                                    "Item ID: " + rs.getInt("item_id") + "\n" +
                                    "Item Name: " + rs.getString("item_name") + "\n" +
                                    "Description: " + rs.getString("description") + "\n" +
                                    "Location: " + rs.getString("location") + "\n" +
                                    "Status: " + rs.getString("status")
                            );
                        }
                    }
                }
            }

            if (!found) {

                itemDetailsArea.setText(
                        "No available item found with this ID."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Item ID must be a number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void submitClaim() {

        try {

            int claimId = Integer.parseInt(
                    claimIdField.getText().trim()
            );

            int itemId = Integer.parseInt(
                    itemIdField.getText().trim()
            );

            String studentName =
                    studentNameField.getText().trim();

            if (studentName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter student name.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Check whether the item exists and is available
            boolean itemAvailable = false;

            try (Connection con = DatabaseConnection.getConnection()) {

                String lostSql =
                        "SELECT item_id FROM LostItems " +
                        "WHERE item_id = ? AND status = 'Available'";

                try (PreparedStatement ps =
                             con.prepareStatement(lostSql)) {

                    ps.setInt(1, itemId);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        itemAvailable = true;
                    }
                }

                if (!itemAvailable) {

                    String foundSql =
                            "SELECT item_id FROM FoundItems " +
                            "WHERE item_id = ? AND status = 'Available'";

                    try (PreparedStatement ps =
                                 con.prepareStatement(foundSql)) {

                        ps.setInt(1, itemId);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            itemAvailable = true;
                        }
                    }
                }
            }

            if (!itemAvailable) {

                JOptionPane.showMessageDialog(
                        this,
                        "Item is not available for claiming.",
                        "Claim Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            boolean success = ClaimDAO.addClaim(
                    claimId,
                    itemId,
                    studentName,
                    "Pending"
            );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Claim submitted successfully!\nStatus: Pending"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to submit claim.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Claim ID and Item ID must be numbers.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}