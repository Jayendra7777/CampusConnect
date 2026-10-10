import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class ApproveRejectClaimFrame extends JFrame {

    private JTextField claimIdField;
    private JTextArea claimDetailsArea;

    public ApproveRejectClaimFrame() {

        setTitle("CampusConnect - Approve / Reject Claim");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("PROCESS CLAIM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(220, 20, 220, 35);
        panel.add(titleLabel);

        // Claim ID
        JLabel claimIdLabel = new JLabel("Claim ID:");
        claimIdLabel.setBounds(60, 80, 120, 25);
        panel.add(claimIdLabel);

        claimIdField = new JTextField();
        claimIdField.setBounds(180, 80, 250, 25);
        panel.add(claimIdField);

        // Check button
        JButton checkButton = new JButton("View Claim");
        checkButton.setBounds(440, 77, 120, 32);
        panel.add(checkButton);

        // Details
        claimDetailsArea = new JTextArea();
        claimDetailsArea.setEditable(false);
        claimDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(claimDetailsArea);
        scrollPane.setBounds(60, 130, 500, 180);
        panel.add(scrollPane);

        // Approve button
        JButton approveButton = new JButton("Approve");
        approveButton.setBounds(150, 350, 130, 40);
        panel.add(approveButton);

        // Reject button
        JButton rejectButton = new JButton("Reject");
        rejectButton.setBounds(350, 350, 130, 40);
        panel.add(rejectButton);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(260, 415, 120, 35);
        panel.add(closeButton);

        // Actions
        checkButton.addActionListener(e -> viewClaim());

        approveButton.addActionListener(e -> updateClaim("Approved"));

        rejectButton.addActionListener(e -> updateClaim("Rejected"));

        closeButton.addActionListener(e -> dispose());

        add(panel);
        AppStyle.styleFrame(this);

        setVisible(true);
    }

    private void viewClaim() {

        String claimIdText = claimIdField.getText().trim();

        if (claimIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Claim ID.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            int claimId = Integer.parseInt(claimIdText);

            String sql =
                    "SELECT * FROM Claims WHERE claim_id = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, claimId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    claimDetailsArea.setText(
                            "===== CLAIM DETAILS =====\n\n" +
                            "Claim ID     : " +
                            rs.getInt("claim_id") + "\n" +
                            "Item ID      : " +
                            rs.getInt("item_id") + "\n" +
                            "Student Name : " +
                            rs.getString("student_name") + "\n" +
                            "Status       : " +
                            rs.getString("status")
                    );

                } else {

                    claimDetailsArea.setText(
                            "Claim not found."
                    );
                }

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Claim ID must be a number.",
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

    private void updateClaim(String newStatus) {

        String claimIdText = claimIdField.getText().trim();

        if (claimIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Claim ID.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            int claimId = Integer.parseInt(claimIdText);

            int itemId = -1;
            String currentStatus = "";

            // First get claim details
            String selectSql =
                    "SELECT item_id, status FROM Claims WHERE claim_id = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(selectSql)) {

                ps.setInt(1, claimId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    itemId = rs.getInt("item_id");
                    currentStatus = rs.getString("status");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Claim not found.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }
            }

            // Prevent processing an already processed claim
            if (!currentStatus.equalsIgnoreCase("Pending")) {

                JOptionPane.showMessageDialog(
                        this,
                        "This claim has already been processed.\n" +
                        "Current status: " + currentStatus,
                        "Claim Already Processed",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Update claim status using ClaimDAO
            boolean success =
                    ClaimDAO.updateClaimStatus(
                            claimId,
                            newStatus
                    );

            if (!success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update claim.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // If approved, mark the corresponding item as Claimed
            if (newStatus.equals("Approved")) {

                updateItemStatus(itemId, "Claimed");
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Claim " + newStatus + " successfully!"
            );

            viewClaim();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Claim ID must be a number.",
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

    private void updateItemStatus(int itemId, String status) {

        String lostSql =
                "UPDATE LostItems SET status = ? WHERE item_id = ?";

        String foundSql =
                "UPDATE FoundItems SET status = ? WHERE item_id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {

            try (PreparedStatement ps =
                         con.prepareStatement(lostSql)) {

                ps.setString(1, status);
                ps.setInt(2, itemId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    return;
                }
            }

            try (PreparedStatement ps =
                         con.prepareStatement(foundSql)) {

                ps.setString(1, status);
                ps.setInt(2, itemId);

                ps.executeUpdate();
            }

        } catch (Exception e) {

            System.out.println(
                    "Error updating item status: " +
                    e.getMessage()
            );
        }
    }
}