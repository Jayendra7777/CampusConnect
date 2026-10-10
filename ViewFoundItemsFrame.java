import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class ViewFoundItemsFrame extends JFrame {

    private JTextArea resultArea;

    public ViewFoundItemsFrame() {

        setTitle("CampusConnect - Found Items");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("FOUND ITEMS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(280, 20, 200, 35);
        panel.add(titleLabel);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setLineWrap(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(40, 75, 600, 370);
        panel.add(scrollPane);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(220, 465, 110, 35);
        panel.add(refreshButton);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(350, 465, 110, 35);
        panel.add(closeButton);

        refreshButton.addActionListener(e -> loadFoundItems());

        closeButton.addActionListener(e -> dispose());

        add(panel);
        AppStyle.styleFrame(this);

        setVisible(true);

        // Load data when window opens
        loadFoundItems();
    }

    private void loadFoundItems() {

        resultArea.setText("");

        String sql = "SELECT * FROM FoundItems";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                resultArea.append(
                        "============================================\n"
                );

                resultArea.append(
                        "Item ID       : " + rs.getInt("item_id") + "\n"
                );

                resultArea.append(
                        "Item Name     : " + rs.getString("item_name") + "\n"
                );

                resultArea.append(
                        "Description   : " + rs.getString("description") + "\n"
                );

                resultArea.append(
                        "Location      : " + rs.getString("location") + "\n"
                );

                resultArea.append(
                        "Date          : " + rs.getString("date") + "\n"
                );

                resultArea.append(
                        "Reported By   : " + rs.getString("reporter_name") + "\n"
                );

                resultArea.append(
                        "Contact       : " + rs.getString("contact_number") + "\n"
                );

                resultArea.append(
                        "Status        : " + rs.getString("status") + "\n"
                );

                resultArea.append(
                        "Photo         : " + safePhoto(rs) + "\n"
                );

                resultArea.append(
                        "============================================\n\n"
                );
            }

            if (!found) {
                resultArea.setText("No found items available.");
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
    private String safePhoto(ResultSet rs) {
        try { String p = rs.getString("photo_path"); return (p == null || p.trim().isEmpty()) ? "Not provided" : p; }
        catch (Exception ignored) { return "Not provided"; }
    }
}