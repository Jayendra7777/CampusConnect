import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class MarkReturnedFrame extends JFrame {

    private JTextField itemIdField;
    private JTextArea itemDetailsArea;

    public MarkReturnedFrame() {

        setTitle("CampusConnect - Mark Item as Returned");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("MARK ITEM AS RETURNED");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(145, 20, 320, 35);
        panel.add(titleLabel);

        // Item ID
        JLabel itemIdLabel = new JLabel("Item ID:");
        itemIdLabel.setBounds(60, 80, 120, 25);
        panel.add(itemIdLabel);

        itemIdField = new JTextField();
        itemIdField.setBounds(180, 80, 230, 25);
        panel.add(itemIdField);

        // View button
        JButton viewButton = new JButton("View Item");
        viewButton.setBounds(425, 77, 110, 32);
        panel.add(viewButton);

        // Details
        itemDetailsArea = new JTextArea();
        itemDetailsArea.setEditable(false);
        itemDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(itemDetailsArea);
        scrollPane.setBounds(60, 130, 475, 150);
        panel.add(scrollPane);

        // Mark Returned
        JButton returnedButton = new JButton("Mark as Returned");
        returnedButton.setBounds(180, 315, 180, 40);
        panel.add(returnedButton);

        // Close
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(240, 370, 100, 35);
        panel.add(closeButton);

        // Actions
        viewButton.addActionListener(e -> viewItem());

        returnedButton.addActionListener(e -> markAsReturned());

        closeButton.addActionListener(e -> dispose());

        add(panel);
        AppStyle.styleFrame(this);

        setVisible(true);
    }

    private void viewItem() {

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

            itemDetailsArea.setText("");

            boolean found = false;

            try (Connection con = DatabaseConnection.getConnection()) {

                String lostSql =
                        "SELECT * FROM LostItems WHERE item_id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(lostSql)) {

                    ps.setInt(1, itemId);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        found = true;

                        displayItemDetails(rs, "LOST ITEM");
                    }
                }

                if (!found) {

                    String foundSql =
                            "SELECT * FROM FoundItems WHERE item_id = ?";

                    try (PreparedStatement ps =
                                 con.prepareStatement(foundSql)) {

                        ps.setInt(1, itemId);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            found = true;

                            displayItemDetails(rs, "FOUND ITEM");
                        }
                    }
                }
            }

            if (!found) {

                itemDetailsArea.setText(
                        "Item not found."
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
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void displayItemDetails(
            ResultSet rs,
            String itemType) throws Exception {

        itemDetailsArea.setText(
                "===== " + itemType + " =====\n\n" +
                "Item ID     : " + rs.getInt("item_id") + "\n" +
                "Item Name   : " + rs.getString("item_name") + "\n" +
                "Description : " + rs.getString("description") + "\n" +
                "Location    : " + rs.getString("location") + "\n" +
                "Date        : " + rs.getString("date") + "\n" +
                "Reported By : " + rs.getString("reporter_name") + "\n" +
                "Status      : " + rs.getString("status")
        );
    }

    private void markAsReturned() {

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

            boolean updated = false;

            try (Connection con = DatabaseConnection.getConnection()) {

                // Try LostItems first
                String lostSql =
                        "UPDATE LostItems " +
                        "SET status = 'Returned' " +
                        "WHERE item_id = ? AND status = 'Claimed'";

                try (PreparedStatement ps =
                             con.prepareStatement(lostSql)) {

                    ps.setInt(1, itemId);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        updated = true;
                    }
                }

                // If not found in LostItems, try FoundItems
                if (!updated) {

                    String foundSql =
                            "UPDATE FoundItems " +
                            "SET status = 'Returned' " +
                            "WHERE item_id = ? AND status = 'Claimed'";

                    try (PreparedStatement ps =
                                 con.prepareStatement(foundSql)) {

                        ps.setInt(1, itemId);

                        int rows = ps.executeUpdate();

                        if (rows > 0) {
                            updated = true;
                        }
                    }
                }
            }

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Item marked as Returned successfully!"
                );

                viewItem();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Item was not found or is not currently Claimed.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
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
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}