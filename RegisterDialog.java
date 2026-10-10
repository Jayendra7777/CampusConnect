import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class RegisterDialog extends JDialog {
    private final JTextField nameField = AppStyle.textField();
    private final JTextField loginField = AppStyle.textField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField confirmField = new JPasswordField();

    public RegisterDialog(JFrame owner) {
        super(owner, "Create student account", true);
        AppStyle.setup();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel root = new JPanel(); root.setBackground(AppStyle.BG); root.setBorder(new EmptyBorder(24, 28, 24, 28)); root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Create your account"); title.setFont(AppStyle.font(Font.BOLD, 23)); title.setForeground(AppStyle.TEXT); title.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel subtitle = AppStyle.muted("Use the same email/contact and password each time."); subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(title); root.add(Box.createVerticalStrut(5)); root.add(subtitle); root.add(Box.createVerticalStrut(20));
        addField(root, "Full name", nameField); addField(root, "Email or contact number", loginField);
        stylePassword(passwordField); addField(root, "Create password (minimum 6 characters)", passwordField);
        stylePassword(confirmField); addField(root, "Confirm password", confirmField);
        JButton create = AppStyle.primaryButton("Create account"); create.setAlignmentX(Component.LEFT_ALIGNMENT); create.addActionListener(e -> createAccount());
        JButton cancel = AppStyle.secondaryButton("Cancel"); cancel.addActionListener(e -> dispose());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0)); actions.setOpaque(false); actions.add(create); actions.add(cancel); actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(Box.createVerticalStrut(12)); root.add(actions); setContentPane(root); pack(); setSize(470, 490); setResizable(false); setLocationRelativeTo(owner); setVisible(true);
    }

    private void addField(JPanel root, String label, JComponent field) {
        JLabel l = new JLabel(label); l.setFont(AppStyle.font(Font.BOLD, 12)); l.setForeground(AppStyle.TEXT); l.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); field.setPreferredSize(new Dimension(390, 40));
        root.add(l); root.add(Box.createVerticalStrut(5)); root.add(field); root.add(Box.createVerticalStrut(12));
    }
    private void stylePassword(JPasswordField field) { field.setFont(AppStyle.font(Font.PLAIN, 14)); field.setForeground(AppStyle.TEXT); field.setBackground(new Color(20,31,41)); field.setCaretColor(AppStyle.BLUE_LIGHT); field.setBorder(new CompoundBorder(new LineBorder(AppStyle.BORDER,1,true), new EmptyBorder(8,10,8,10))); }
    private void createAccount() {
        String name = nameField.getText().trim(); String loginId = loginField.getText().trim();
        String password = new String(passwordField.getPassword()); String confirm = new String(confirmField.getPassword());
        if (name.isEmpty() || loginId.isEmpty() || password.isEmpty() || confirm.isEmpty()) { JOptionPane.showMessageDialog(this, "Please fill in every field.", "Missing details", JOptionPane.WARNING_MESSAGE); return; }
        if (password.length() < 6) { JOptionPane.showMessageDialog(this, "Use a password with at least 6 characters.", "Password too short", JOptionPane.WARNING_MESSAGE); return; }
        if (!password.equals(confirm)) { JOptionPane.showMessageDialog(this, "Passwords do not match.", "Check password", JOptionPane.WARNING_MESSAGE); return; }
        if (!loginId.contains("@") && !loginId.matches("[+0-9() -]{7,20}")) { JOptionPane.showMessageDialog(this, "Enter a valid email address or contact number.", "Invalid login", JOptionPane.WARNING_MESSAGE); return; }
        boolean created = UserDAO.registerStudent(name, loginId, password);
        if (created) { JOptionPane.showMessageDialog(this, "Account created! You can now sign in using the same login and password.", "Welcome to CampusConnect", JOptionPane.INFORMATION_MESSAGE); dispose(); }
        else JOptionPane.showMessageDialog(this, "An account with that email/contact may already exist, or the database is unavailable.", "Could not create account", JOptionPane.ERROR_MESSAGE);
    }
}
