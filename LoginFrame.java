import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton studentTab, adminTab;
    private String selectedRole = "Student";

    public LoginFrame() {
        AppStyle.setup();
        setTitle("CampusConnect | Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 680));
        setSize(1180, 760);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 0, 0));

        JPanel hero = new AppStyle.GradientPanel(new Color(12, 29, 61), new Color(42, 91, 177));
        hero.setLayout(new BorderLayout());
        hero.setBorder(new EmptyBorder(44, 42, 30, 36));
        JPanel heroContent = new JPanel(); heroContent.setOpaque(false); heroContent.setLayout(new BoxLayout(heroContent, BoxLayout.Y_AXIS));
        JLabel brand = new JLabel("◆  CampusConnect"); brand.setFont(AppStyle.font(Font.BOLD, 29)); brand.setForeground(Color.WHITE); brand.setAlignmentX(Component.LEFT_ALIGNMENT); heroContent.add(brand);
        heroContent.add(Box.createVerticalStrut(18));
        JLabel eyebrow = new JLabel("C A M P U S   L O S T  &  F O U N D"); eyebrow.setFont(AppStyle.font(Font.BOLD, 11)); eyebrow.setForeground(new Color(194, 215, 255)); heroContent.add(eyebrow);
        heroContent.add(Box.createVerticalStrut(30));
        JLabel headline = new JLabel("Lost something?"); headline.setFont(AppStyle.font(Font.BOLD, 37)); headline.setForeground(Color.WHITE); heroContent.add(headline);
        JLabel headline2 = new JLabel("Let's reconnect."); headline2.setFont(AppStyle.font(Font.BOLD, 37)); headline2.setForeground(new Color(127, 190, 255)); heroContent.add(headline2);
        heroContent.add(Box.createVerticalStrut(14));
        JLabel description = new JLabel("<html>Report, find and reunite lost items across campus<br>with a simpler, friendlier experience.</html>"); description.setFont(AppStyle.font(Font.PLAIN, 14)); description.setForeground(new Color(226, 236, 255)); heroContent.add(description);
        heroContent.add(Box.createVerticalStrut(28));
        heroContent.add(featureCard("↗", "Report items", "Post lost or found items in seconds.", new Color(68, 119, 242)));
        heroContent.add(Box.createVerticalStrut(11));
        heroContent.add(featureCard("⌕", "Search & discover", "Find matches using item names.", new Color(20, 174, 151)));
        heroContent.add(Box.createVerticalStrut(11));
        heroContent.add(featureCard("✓", "Verified claims", "Admins review claims and item status.", new Color(144, 98, 241)));
        hero.add(heroContent, BorderLayout.CENTER);
        JLabel footer = new JLabel("SAFER CAMPUS  ·  STRONGER COMMUNITY"); footer.setFont(AppStyle.font(Font.BOLD, 10)); footer.setForeground(new Color(205, 220, 250)); hero.add(footer, BorderLayout.SOUTH);

        JPanel right = new JPanel(new GridBagLayout()); right.setBackground(AppStyle.BG); right.setBorder(new EmptyBorder(22, 24, 22, 24));
        JPanel card = new JPanel(); card.setBackground(AppStyle.CARD); card.setBorder(new CompoundBorder(new LineBorder(AppStyle.BORDER, 1, true), new EmptyBorder(27, 30, 25, 30)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); card.setPreferredSize(new Dimension(450, 590));
        JLabel logo = new JLabel("◆"); logo.setFont(AppStyle.font(Font.BOLD, 32)); logo.setForeground(AppStyle.BLUE); logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel("CampusConnect"); title.setFont(AppStyle.font(Font.BOLD, 26)); title.setForeground(AppStyle.TEXT); title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitle = AppStyle.muted("Sign in to continue"); subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(logo); card.add(Box.createVerticalStrut(1)); card.add(title); card.add(Box.createVerticalStrut(6)); card.add(subtitle); card.add(Box.createVerticalStrut(22));

        JPanel tabs = new JPanel(new GridLayout(1, 2, 9, 0)); tabs.setOpaque(false); tabs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
        studentTab = new JButton("Student"); adminTab = new JButton("Admin"); styleTab(studentTab, true); styleTab(adminTab, false); tabs.add(studentTab); tabs.add(adminTab); card.add(tabs); card.add(Box.createVerticalStrut(20));
        JLabel emailLabel = new JLabel("Email or contact number"); styleLabel(emailLabel); card.add(emailLabel); card.add(Box.createVerticalStrut(6));
        emailField = AppStyle.textField(); emailField.setHorizontalAlignment(SwingConstants.LEFT); emailField.setAlignmentX(Component.LEFT_ALIGNMENT); emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43)); emailField.setPreferredSize(new Dimension(350, 43)); emailField.setToolTipText("Use the same email or contact number every time"); card.add(emailField); card.add(Box.createVerticalStrut(13));
        JLabel passLabel = new JLabel("Password"); styleLabel(passLabel); card.add(passLabel); card.add(Box.createVerticalStrut(6));
        passwordField = new JPasswordField(); passwordField.setHorizontalAlignment(SwingConstants.LEFT); passwordField.setAlignmentX(Component.LEFT_ALIGNMENT); passwordField.setFont(AppStyle.font(Font.PLAIN, 14)); passwordField.setForeground(AppStyle.TEXT); passwordField.setBackground(new Color(20, 31, 41)); passwordField.setBorder(new CompoundBorder(new LineBorder(AppStyle.BORDER, 1, true), new EmptyBorder(9, 12, 9, 12))); passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43)); passwordField.setPreferredSize(new Dimension(350, 43)); card.add(passwordField); card.add(Box.createVerticalStrut(18));
        JButton loginButton = AppStyle.primaryButton("Sign in  →"); loginButton.setAlignmentX(Component.CENTER_ALIGNMENT); loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 47)); loginButton.setPreferredSize(new Dimension(350, 47)); card.add(loginButton); card.add(Box.createVerticalStrut(11));
        JLabel or = AppStyle.muted("—  OR  —"); or.setAlignmentX(Component.CENTER_ALIGNMENT); card.add(or); card.add(Box.createVerticalStrut(10));
        JButton registerButton = AppStyle.secondaryButton("Create a student account  +"); registerButton.setAlignmentX(Component.CENTER_ALIGNMENT); registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43)); registerButton.setPreferredSize(new Dimension(350, 43)); card.add(registerButton); card.add(Box.createVerticalStrut(13));
        JLabel note = AppStyle.muted("Admin access is restricted."); note.setAlignmentX(Component.CENTER_ALIGNMENT); card.add(note); card.add(Box.createVerticalStrut(4));
        JLabel security = AppStyle.muted("Your campus community, connected."); security.setAlignmentX(Component.CENTER_ALIGNMENT); card.add(Box.createVerticalGlue()); card.add(security);

        studentTab.addActionListener(e -> chooseRole("Student")); adminTab.addActionListener(e -> chooseRole("Admin"));
        loginButton.addActionListener(e -> login()); passwordField.addActionListener(e -> login());
        registerButton.addActionListener(e -> { if (selectedRole.equals("Admin")) { JOptionPane.showMessageDialog(this, "Student registration is only available in Student mode.", "Admin access", JOptionPane.INFORMATION_MESSAGE); return; } new RegisterDialog(this); });
        right.add(card); add(hero); add(right); setVisible(true);
    }

    private JPanel featureCard(String icon, String title, String text, Color accent) {
        JPanel panel = new JPanel(new BorderLayout(13, 0)); panel.setOpaque(true); panel.setBackground(new Color(255,255,255,24));
        panel.setBorder(new CompoundBorder(new LineBorder(new Color(170,198,244,90),1,true),new EmptyBorder(12,14,12,14)));
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER); iconLabel.setFont(AppStyle.font(Font.BOLD, 22)); iconLabel.setForeground(Color.WHITE); iconLabel.setOpaque(true); iconLabel.setBackground(accent); iconLabel.setPreferredSize(new Dimension(42,42));
        JPanel words = new JPanel(); words.setOpaque(false); words.setLayout(new BoxLayout(words,BoxLayout.Y_AXIS));
        JLabel h = new JLabel(title); h.setFont(AppStyle.font(Font.BOLD, 14)); h.setForeground(Color.WHITE);
        JLabel d = new JLabel(text); d.setFont(AppStyle.font(Font.PLAIN, 11)); d.setForeground(new Color(225,235,252));
        words.add(h); words.add(Box.createVerticalStrut(4)); words.add(d); panel.add(iconLabel,BorderLayout.WEST); panel.add(words,BorderLayout.CENTER); panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,66)); return panel;
    }
    private void styleLabel(JLabel l) {
        l.setFont(AppStyle.font(Font.BOLD, 12));
        l.setForeground(AppStyle.TEXT);
        l.setHorizontalAlignment(SwingConstants.LEFT);
        l.setAlignmentX(0.0f);
        // Give labels the full form width so their text stays left-aligned,
        // instead of appearing centered above the input fields.
        l.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        l.setPreferredSize(new Dimension(350, 20));
        l.setMinimumSize(new Dimension(350, 20));
    }
    private void styleTab(JButton b, boolean active) { b.setFont(AppStyle.font(Font.BOLD, 13)); b.setFocusPainted(false); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); b.setOpaque(true); b.setBackground(active?AppStyle.BLUE:AppStyle.CARD); b.setForeground(active?Color.WHITE:AppStyle.TEXT); b.setBorder(new CompoundBorder(new LineBorder(active?AppStyle.BLUE:AppStyle.BORDER,1,true),new EmptyBorder(9,12,9,12))); }
    private void chooseRole(String role) { selectedRole=role; styleTab(studentTab,role.equals("Student")); styleTab(adminTab,role.equals("Admin")); }

    private void login() {
        String loginId = emailField.getText().trim(); String password = new String(passwordField.getPassword());
        if (loginId.isEmpty() || password.isEmpty()) { JOptionPane.showMessageDialog(this,"Please enter your email/contact and password.","Missing details",JOptionPane.WARNING_MESSAGE); return; }
        if (selectedRole.equals("Admin")) {
            // Demo admin account. Do not publish these credentials in a public repository.
            final String adminUser = "admin123@gmail.com";
            final String adminPassword = "admin123";
            if (loginId.equalsIgnoreCase(adminUser) && password.equals(adminPassword)) {
                dispose();
                new AdminDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin email or password.", "Sign-in failed", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (UserDAO.authenticateStudent(loginId, password)) { dispose(); new StudentDashboard(loginId); }
            else JOptionPane.showMessageDialog(this,"Account not found or password is incorrect. Create an account first if you are new.","Sign-in failed",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(LoginFrame::new); }
}
