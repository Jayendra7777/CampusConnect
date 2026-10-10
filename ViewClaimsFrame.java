import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class ViewClaimsFrame extends JFrame {

    private JTextArea resultArea;

    public ViewClaimsFrame() {

        setTitle("CampusConnect - Claims");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("CLAIMS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(285, 20, 150, 35);
        panel.add(titleLabel);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setLineWrap(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(40, 75, 560, 320);
        panel.add(scrollPane);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(190, 420, 110, 35);
        panel.add(refreshButton);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(320, 420, 110, 35);
        panel.add(closeButton);

        refreshButton.addActionListener(e -> loadClaims());

        closeButton.addActionListener(e -> dispose());

        add(panel);
        AppStyle.styleFrame(this);

        setVisible(true);

        // Load claims when window opens
        loadClaims();
    }

    private void loadClaims() {

        resultArea.setText("");

        String sql = "SELECT * FROM Claims";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                resultArea.append(
                        "========================================\n"
                );

                resultArea.append(
                        "Claim ID       : " +
                        rs.getInt("claim_id") + "\n"
                );

                resultArea.append(
                        "Item ID        : " +
                        rs.getInt("item_id") + "\n"
                );

                resultArea.append(
                        "Student Name   : " +
                        rs.getString("student_name") + "\n"
                );

                resultArea.append(
                        "Status         : " +
                        rs.getString("status") + "\n"
                );

                resultArea.append(
                        "========================================\n\n"
                );
            }

            if (!found) {
                resultArea.setText("No claims available.");
            }

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