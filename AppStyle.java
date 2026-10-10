import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;

/** Shared Prime Video-inspired dark theme for CampusConnect. */
public final class AppStyle {
    public static final Color DEEP = new Color(15, 23, 30);       // streaming-app dark canvas
    public static final Color NAVY = new Color(241, 245, 249);   // high-contrast heading text
    public static final Color BLUE = new Color(0, 168, 225);     // Prime Video-like cyan blue
    public static final Color BLUE_LIGHT = new Color(43, 190, 245);
    public static final Color BG = new Color(15, 23, 30);
    public static final Color CARD = new Color(27, 39, 50);
    public static final Color TEXT = new Color(241, 245, 249);
    public static final Color MUTED = new Color(169, 185, 200);
    public static final Color BORDER = new Color(52, 70, 86);
    public static final Color TEAL = new Color(0, 188, 180);
    private static final String FONT_FAMILY = chooseFont();
    private AppStyle() {}

    private static String chooseFont() {
        String[] available = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        // Amazon Ember is the closest family to Prime Video's interface font when installed.
        if (Arrays.asList(available).contains("Amazon Ember")) return "Amazon Ember";
        if (Arrays.asList(available).contains("Segoe UI")) return "Segoe UI";
        return "Arial";
    }
    public static Font font(int style, int size) { return new Font(FONT_FAMILY, style, size); }

    public static void setup() {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch (Exception ignored) { }
        UIManager.put("control", BG);
        UIManager.put("Panel.background", BG);
        UIManager.put("nimbusBase", BLUE);
        UIManager.put("nimbusBlueGrey", CARD);
        UIManager.put("nimbusFocus", BLUE_LIGHT);
        UIManager.put("text", TEXT);
        UIManager.put("TextField.background", new Color(20, 31, 41));
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("PasswordField.background", new Color(20, 31, 41));
        UIManager.put("PasswordField.foreground", TEXT);
        UIManager.put("OptionPane.background", CARD);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Button.font", font(Font.BOLD, 13));
        UIManager.put("TextField.font", font(Font.PLAIN, 14));
        UIManager.put("PasswordField.font", font(Font.PLAIN, 14));
        UIManager.put("TextArea.font", font(Font.PLAIN, 13));
        UIManager.put("ComboBox.font", font(Font.PLAIN, 14));
        UIManager.put("Table.font", font(Font.PLAIN, 13));
        UIManager.put("TableHeader.font", font(Font.BOLD, 13));
        UIManager.put("Table.background", CARD);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.selectionBackground", new Color(28, 90, 120));
        UIManager.put("Table.selectionForeground", Color.WHITE);
    }

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text); b.setFont(font(Font.BOLD, 14));
        b.setForeground(Color.WHITE); b.setBackground(BLUE); b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new CompoundBorder(new LineBorder(BLUE, 1, true), new EmptyBorder(11, 18, 11, 18)));
        b.setOpaque(true); b.setContentAreaFilled(true); return b;
    }
    public static JButton secondaryButton(String text) {
        JButton b = new JButton(text); b.setFont(font(Font.BOLD, 13));
        b.setForeground(TEXT); b.setBackground(CARD); b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(9, 14, 9, 14)));
        b.setOpaque(true); b.setContentAreaFilled(true); return b;
    }
    public static JTextField textField() {
        JTextField f = new JTextField(); f.setFont(font(Font.PLAIN, 14));
        f.setForeground(TEXT); f.setCaretColor(BLUE_LIGHT); f.setBackground(new Color(20, 31, 41)); f.setHorizontalAlignment(JTextField.LEFT);
        f.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(9, 12, 9, 12)));
        return f;
    }
    public static JLabel heading(String text) {
        JLabel l = new JLabel(text); l.setFont(font(Font.BOLD, 25)); l.setForeground(TEXT); return l;
    }
    public static JLabel muted(String text) {
        JLabel l = new JLabel(text); l.setFont(font(Font.PLAIN, 13)); l.setForeground(MUTED); return l;
    }
    public static JPanel page(String title, String subtitle) {
        JPanel root = new JPanel(new BorderLayout(0, 18)); root.setBackground(BG); root.setBorder(new EmptyBorder(24, 28, 24, 28));
        JPanel head = new JPanel(); head.setLayout(new BoxLayout(head, BoxLayout.Y_AXIS)); head.setOpaque(false);
        JLabel h = heading(title); h.setAlignmentX(Component.LEFT_ALIGNMENT); head.add(h);
        JLabel sub = muted(subtitle); sub.setAlignmentX(Component.LEFT_ALIGNMENT); head.add(Box.createVerticalStrut(5)); head.add(sub);
        root.add(head, BorderLayout.NORTH); return root;
    }
    public static JPanel card(LayoutManager layout) {
        JPanel p = new JPanel(layout); p.setBackground(CARD);
        p.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(20, 22, 20, 22))); return p;
    }
    public static void styleFrame(JFrame frame) {
        setup(); frame.getContentPane().setBackground(BG); styleTree(frame.getContentPane(), true);
    }
    private static void styleTree(Component c, boolean root) {
        if (c instanceof JTable) {
            JTable t=(JTable)c; t.setRowHeight(34); t.setGridColor(BORDER); t.setFillsViewportHeight(true);
            t.setBackground(CARD); t.setForeground(TEXT); t.setSelectionBackground(new Color(28, 90, 120)); t.setSelectionForeground(Color.WHITE);
            t.setShowGrid(false); t.setIntercellSpacing(new Dimension(0, 1));
            t.getTableHeader().setBackground(new Color(22, 39, 52)); t.getTableHeader().setForeground(Color.WHITE);
            t.getTableHeader().setFont(font(Font.BOLD, 13));
        } else if (c instanceof JTextField) {
            JTextField f=(JTextField)c; f.setFont(font(Font.PLAIN,14)); f.setForeground(TEXT); f.setBackground(new Color(20,31,41)); f.setCaretColor(BLUE_LIGHT);
            f.setHorizontalAlignment(JTextField.LEFT); f.setBorder(new CompoundBorder(new LineBorder(BORDER,1,true),new EmptyBorder(8,10,8,10)));
        } else if (c instanceof JPasswordField) {
            JPasswordField f=(JPasswordField)c; f.setFont(font(Font.PLAIN,14)); f.setForeground(TEXT); f.setBackground(new Color(20,31,41)); f.setCaretColor(BLUE_LIGHT); f.setHorizontalAlignment(JTextField.LEFT);
            f.setBorder(new CompoundBorder(new LineBorder(BORDER,1,true),new EmptyBorder(8,10,8,10)));
        } else if (c instanceof JTextArea) {
            JTextArea a=(JTextArea)c; a.setFont(font(Font.PLAIN,14)); a.setBackground(new Color(20,31,41)); a.setForeground(TEXT); a.setCaretColor(BLUE_LIGHT); a.setMargin(new Insets(10,10,10,10));
        } else if (c instanceof JButton) {
            JButton b=(JButton)c; b.setFont(font(Font.BOLD,13)); b.setFocusPainted(false); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            String s=b.getText()==null?"":b.getText().toLowerCase();
            if (s.contains("submit") || s.contains("search") || s.contains("approve") || s.contains("sign in") || s.contains("login") || s.contains("refresh") || s.contains("save") || s.contains("choose")) {
                b.setBackground(BLUE); b.setForeground(Color.WHITE); b.setOpaque(true); b.setContentAreaFilled(true); b.setBorder(new CompoundBorder(new LineBorder(BLUE,1,true),new EmptyBorder(9,14,9,14)));
            } else { b.setBackground(CARD); b.setForeground(TEXT); b.setOpaque(true); b.setContentAreaFilled(true); b.setBorder(new CompoundBorder(new LineBorder(BORDER,1,true),new EmptyBorder(9,14,9,14))); }
        } else if (c instanceof JLabel) {
            JLabel l=(JLabel)c;
            if (l.getFont().getSize() < 18) l.setFont(font(Font.PLAIN,13));
            if (l.getForeground().equals(Color.BLACK) || l.getForeground().equals(new Color(22,38,63))) l.setForeground(TEXT);
        } else if (c instanceof JComboBox) {
            c.setFont(font(Font.PLAIN,14)); c.setForeground(TEXT); c.setBackground(CARD);
        } else if (c instanceof JScrollPane) {
            c.setBackground(BG); ((JScrollPane)c).getViewport().setBackground(CARD); ((JScrollPane)c).setBorder(new LineBorder(BORDER,1,true));
        } else if (c instanceof JPanel) {
            JPanel p=(JPanel)c;
            if (p.isOpaque()) {
                if (root) p.setBackground(BG);
                else if (p.getBackground().equals(Color.WHITE) || p.getBackground().equals(new Color(244,248,255)) || p.getBackground().equals(new Color(235,241,252))) p.setBackground(CARD);
            }
        }
        if (c instanceof Container) for (Component child:((Container)c).getComponents()) styleTree(child, false);
    }
    public static class GradientPanel extends JPanel {
        private final Color start,end;
        public GradientPanel(Color start,Color end){this.start=start;this.end=end;setOpaque(false);}
        @Override protected void paintComponent(Graphics g){super.paintComponent(g);Graphics2D g2=(Graphics2D)g.create();g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g2.setPaint(new GradientPaint(0,0,start,getWidth(),getHeight(),end));g2.fillRect(0,0,getWidth(),getHeight());g2.dispose();}
    }
}
