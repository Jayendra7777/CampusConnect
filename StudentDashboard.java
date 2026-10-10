import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class StudentDashboard extends JFrame {
    private final String studentEmail;
    public StudentDashboard(String studentEmail) {
        AppStyle.setup(); this.studentEmail=studentEmail;
        setTitle("CampusConnect | Student Hub"); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setSize(980,700); setMinimumSize(new Dimension(820,620)); setLocationRelativeTo(null);
        JPanel root=new JPanel(new BorderLayout()); root.setBackground(AppStyle.BG); root.setBorder(new EmptyBorder(24,30,24,30));
        JPanel top=new JPanel(new BorderLayout()); top.setOpaque(false);
        JPanel brand=new JPanel(); brand.setOpaque(false); brand.setLayout(new BoxLayout(brand,BoxLayout.Y_AXIS));
        JLabel logo=new JLabel("◆  CampusConnect"); logo.setFont(AppStyle.font(Font.BOLD, 24)); logo.setForeground(AppStyle.TEXT);
        JLabel sub=AppStyle.muted("STUDENT HUB  /  LOST & FOUND"); sub.setFont(AppStyle.font(Font.BOLD, 10));
        brand.add(logo); brand.add(Box.createVerticalStrut(5)); brand.add(sub); top.add(brand,BorderLayout.WEST);
        JButton logout=AppStyle.secondaryButton("Log out"); logout.addActionListener(e->{dispose();new LoginFrame();}); top.add(logout,BorderLayout.EAST); root.add(top,BorderLayout.NORTH);
        JPanel content=new JPanel(new BorderLayout(0,22)); content.setOpaque(false); content.setBorder(new EmptyBorder(34,0,0,0));
        JPanel welcome=new AppStyle.GradientPanel(new Color(34,77,157),new Color(67,125,242)); welcome.setLayout(new BoxLayout(welcome,BoxLayout.Y_AXIS)); welcome.setBorder(new EmptyBorder(26,30,26,30));
        JLabel hello=new JLabel("Your campus, connected."); hello.setFont(AppStyle.font(Font.BOLD, 29)); hello.setForeground(Color.WHITE);
        JLabel who=new JLabel("Welcome back, "+studentEmail); who.setFont(AppStyle.font(Font.PLAIN, 14)); who.setForeground(new Color(230,238,255));
        welcome.add(hello); welcome.add(Box.createVerticalStrut(8)); welcome.add(who); content.add(welcome,BorderLayout.NORTH);
        JPanel grid=new JPanel(new GridLayout(2,2,16,16)); grid.setOpaque(false);
        grid.add(actionCard("01","Report a lost item","Tell the campus community what you've lost.","Open form  →",()->new ReportLostItemFrame(),new Color(64,111,235)));
        grid.add(actionCard("02","Report a found item","Help an item find its owner.","Open form  →",()->new ReportFoundItemFrame(),new Color(16,164,151)));
        grid.add(actionCard("03","Search items","Look through lost and found reports.","Start searching  →",()->new SearchItemFrame(),new Color(132,88,224)));
        grid.add(actionCard("04","Submit a claim","Claim an item that belongs to you.","Submit claim  →",()->new ClaimFrame(studentEmail),new Color(231,145,43)));
        content.add(grid,BorderLayout.CENTER); root.add(content,BorderLayout.CENTER);
        JLabel foot=AppStyle.muted("Small actions make a more helpful campus."); foot.setBorder(new EmptyBorder(18,0,0,0)); root.add(foot,BorderLayout.SOUTH);
        setContentPane(root); AppStyle.styleFrame(this); setVisible(true);
    }
    private JPanel actionCard(String num,String title,String desc,String action,Runnable click,Color accent) {
        JPanel card=new JPanel(); card.setBackground(AppStyle.CARD); card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(new LineBorder(AppStyle.BORDER,1,true),new EmptyBorder(20,22,18,22)));
        JLabel number=new JLabel(num); number.setFont(AppStyle.font(Font.BOLD, 12)); number.setForeground(accent);
        JLabel h=new JLabel(title); h.setFont(AppStyle.font(Font.BOLD, 19)); h.setForeground(AppStyle.TEXT);
        JLabel d=new JLabel("<html><div style='width:260px'>"+desc+"</div></html>"); d.setFont(AppStyle.font(Font.PLAIN, 12)); d.setForeground(AppStyle.MUTED);
        JButton b=AppStyle.secondaryButton(action); b.setAlignmentX(Component.LEFT_ALIGNMENT); b.addActionListener(e->click.run());
        card.add(number); card.add(Box.createVerticalStrut(13)); card.add(h); card.add(Box.createVerticalStrut(8)); card.add(d); card.add(Box.createVerticalGlue()); card.add(Box.createVerticalStrut(14)); card.add(b); return card;
    }
}
