import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        AppStyle.setup(); setTitle("CampusConnect | Admin Console"); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setSize(980,700); setMinimumSize(new Dimension(820,620)); setLocationRelativeTo(null);
        JPanel root=new JPanel(new BorderLayout()); root.setBackground(AppStyle.BG); root.setBorder(new EmptyBorder(24,30,24,30));
        JPanel top=new JPanel(new BorderLayout()); top.setOpaque(false);
        JPanel brand=new JPanel(); brand.setOpaque(false); brand.setLayout(new BoxLayout(brand,BoxLayout.Y_AXIS));
        JLabel logo=new JLabel("◆  CampusConnect"); logo.setFont(AppStyle.font(Font.BOLD, 24)); logo.setForeground(AppStyle.TEXT);
        JLabel sub=AppStyle.muted("ADMIN CONSOLE  /  CAMPUS OPERATIONS"); sub.setFont(AppStyle.font(Font.BOLD, 10)); brand.add(logo); brand.add(Box.createVerticalStrut(5)); brand.add(sub); top.add(brand,BorderLayout.WEST);
        JButton logout=AppStyle.secondaryButton("Log out"); logout.addActionListener(e->{dispose();new LoginFrame();}); top.add(logout,BorderLayout.EAST); root.add(top,BorderLayout.NORTH);
        JPanel content=new JPanel(new BorderLayout(0,22)); content.setOpaque(false); content.setBorder(new EmptyBorder(34,0,0,0));
        JPanel welcome=new AppStyle.GradientPanel(new Color(22,43,81),new Color(56,91,160)); welcome.setLayout(new BoxLayout(welcome,BoxLayout.Y_AXIS)); welcome.setBorder(new EmptyBorder(25,30,25,30));
        JLabel hello=new JLabel("Good to see you, Admin."); hello.setFont(AppStyle.font(Font.BOLD, 28)); hello.setForeground(Color.WHITE);
        JLabel desc=new JLabel("Review reports, manage claims, and help reunite people with their belongings."); desc.setFont(AppStyle.font(Font.PLAIN, 13)); desc.setForeground(new Color(220,231,251)); welcome.add(hello); welcome.add(Box.createVerticalStrut(8)); welcome.add(desc); content.add(welcome,BorderLayout.NORTH);
        JPanel grid=new JPanel(new GridLayout(2,3,14,14)); grid.setOpaque(false);
        grid.add(card("01","Lost items","Review reported lost items.",()->new ViewLostItemsFrame(),new Color(63,111,235)));
        grid.add(card("02","Found items","Browse found-item reports.",()->new ViewFoundItemsFrame(),new Color(17,158,144)));
        grid.add(card("03","Claims inbox","See claims waiting for review.",()->new ViewClaimsFrame(),new Color(132,88,224)));
        grid.add(card("04","Approve / reject","Process pending claims.",()->new ApproveRejectClaimFrame(),new Color(224,142,41)));
        grid.add(card("05","Returned items","Mark claimed items returned.",()->new MarkReturnedFrame(),new Color(36,151,96)));
        JPanel tip=new JPanel(); tip.setBackground(AppStyle.CARD); tip.setBorder(new CompoundBorder(new LineBorder(AppStyle.BORDER,1,true),new EmptyBorder(20,16,20,16))); tip.setLayout(new BoxLayout(tip,BoxLayout.Y_AXIS));
        JLabel check=new JLabel("✓  Keep status updated"); check.setFont(AppStyle.font(Font.BOLD, 15)); check.setForeground(AppStyle.TEXT); JLabel tipText=new JLabel("<html><div style='width:150px'>Approve claims only after checking item details.</div></html>"); tipText.setForeground(AppStyle.MUTED); tip.add(check); tip.add(Box.createVerticalStrut(10)); tip.add(tipText); grid.add(tip);
        content.add(grid,BorderLayout.CENTER); root.add(content,BorderLayout.CENTER); JLabel foot=AppStyle.muted("CampusConnect • Admin tools"); foot.setBorder(new EmptyBorder(18,0,0,0)); root.add(foot,BorderLayout.SOUTH); setContentPane(root); AppStyle.styleFrame(this); setVisible(true);
    }
    private JPanel card(String num,String title,String description,Runnable action,Color accent) {
        JPanel panel=new JPanel(); panel.setBackground(AppStyle.CARD); panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS)); panel.setBorder(new CompoundBorder(new LineBorder(AppStyle.BORDER,1,true),new EmptyBorder(17,18,16,18)));
        JLabel n=new JLabel(num); n.setFont(AppStyle.font(Font.BOLD, 11)); n.setForeground(accent); JLabel h=new JLabel(title); h.setFont(AppStyle.font(Font.BOLD, 16)); h.setForeground(AppStyle.TEXT); JLabel d=new JLabel("<html><div style='width:190px'>"+description+"</div></html>"); d.setFont(AppStyle.font(Font.PLAIN, 11)); d.setForeground(AppStyle.MUTED);
        JButton b=AppStyle.secondaryButton("Open  →"); b.setAlignmentX(Component.LEFT_ALIGNMENT); b.addActionListener(e->action.run()); panel.add(n); panel.add(Box.createVerticalStrut(10)); panel.add(h); panel.add(Box.createVerticalStrut(7)); panel.add(d); panel.add(Box.createVerticalGlue()); panel.add(Box.createVerticalStrut(10)); panel.add(b); return panel;
    }
}
