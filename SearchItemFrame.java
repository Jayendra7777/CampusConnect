import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class SearchItemFrame extends JFrame {

    private JTextField searchField;
    private JTextArea resultArea;

    public SearchItemFrame() {

        setTitle("CampusConnect - Search Item");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("SEARCH ITEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(250, 20, 200, 35);
        panel.add(titleLabel);

        // Search label
        JLabel searchLabel = new JLabel("Enter Item Name:");
        searchLabel.setBounds(50, 75, 130, 25);
        panel.add(searchLabel);

        // Search field
        searchField = new JTextField();
        searchField.setBounds(180, 75, 280, 25);
        panel.add(searchField);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(475, 72, 100, 32);
        panel.add(searchButton);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(50, 125, 525, 270);
        panel.add(scrollPane);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(260, 415, 120, 35);
        panel.add(closeButton);

        // Actions
        searchButton.addActionListener(e -> searchItems());

        closeButton.addActionListener(e -> dispose());

        add(panel);
        AppStyle.styleFrame(this);

        setVisible(true);
    }

    private void searchItems() {

        String searchName = searchField.getText().trim();

        if (searchName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an item name.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        resultArea.setText("");

        boolean found = false;

        String lostSql =
                "SELECT * FROM LostItems " +
                "WHERE item_name LIKE ?";

        String foundSql =
                "SELECT * FROM FoundItems " +
                "WHERE item_name LIKE ?";

        try (Connection con = DatabaseConnection.getConnection()) {

            // Search Lost Items
            try (PreparedStatement ps = con.prepareStatement(lostSql)) {

                ps.setString(1, "%" + searchName + "%");

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    found = true;

                    resultArea.append(
                            "===== LOST ITEM =====\n" +
                            "Item ID: " + rs.getInt("item_id") + "\n" +
                            "Item Name: " + rs.getString("item_name") + "\n" +
                            "Description: " + rs.getString("description") + "\n" +
                            "Location: " + rs.getString("location") + "\n" +
                            "Date: " + rs.getString("date") + "\n" +
                            "Reported By: " + rs.getString("reporter_name") + "\n" +
                            "Contact: " + rs.getString("contact_number") + "\n" +
                            "Status: " + rs.getString("status") + "\n" +
                            "Photo: " + safePhoto(rs) + "\n" +
                            "----------------------------\n"
                    );
                }
            }

            // Search Found Items
            try (PreparedStatement ps = con.prepareStatement(foundSql)) {

                ps.setString(1, "%" + searchName + "%");

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    found = true;

                    resultArea.append(
                            "===== FOUND ITEM =====\n" +
                            "Item ID: " + rs.getInt("item_id") + "\n" +
                            "Item Name: " + rs.getString("item_name") + "\n" +
                            "Description: " + rs.getString("description") + "\n" +
                            "Location: " + rs.getString("location") + "\n" +
                            "Date: " + rs.getString("date") + "\n" +
                            "Reported By: " + rs.getString("reporter_name") + "\n" +
                            "Contact: " + rs.getString("contact_number") + "\n" +
                            "Status: " + rs.getString("status") + "\n" +
                            "Photo: " + safePhoto(rs) + "\n" +
                            "----------------------------\n"
                    );
                }
            }

            if (!found) {
                resultArea.setText("No matching items found.");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage(),
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private String safePhoto(ResultSet rs) {
        try { String p = rs.getString("photo_path"); return (p == null || p.trim().isEmpty()) ? "Not provided" : p; }
        catch (Exception ignored) { return "Not provided"; }
    }
}