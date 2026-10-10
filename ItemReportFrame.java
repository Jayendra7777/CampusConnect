import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/** Shared modern form used by both Report Lost and Report Found screens. */
public class ItemReportFrame extends JFrame {
    private final boolean lost;
    private JTextField idField, nameField, locationField, dateField, reporterField, contactField;
    private JTextArea descriptionArea;
    private JLabel photoPreview, photoName;
    private File selectedPhoto;

    public ItemReportFrame(boolean lost) {
        this.lost = lost;
        String action = lost ? "Lost" : "Found";
        setTitle("CampusConnect • Report " + action + " Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 760); setMinimumSize(new Dimension(760, 700)); setLocationRelativeTo(null);

        JPanel root = AppStyle.page("Report " + action + " Item", "Add the details below so your campus community can help reunite this item.");
        JPanel card = AppStyle.card(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(7, 8, 7, 8); c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
        idField = AppStyle.textField(); nameField = AppStyle.textField(); locationField = AppStyle.textField(); dateField = AppStyle.textField(); reporterField = AppStyle.textField(); contactField = AppStyle.textField();
        descriptionArea = new JTextArea(3, 24); descriptionArea.setFont(AppStyle.font(Font.PLAIN, 14)); descriptionArea.setLineWrap(true); descriptionArea.setWrapStyleWord(true); descriptionArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        int row=0;
        addRow(card,c,row++,"Item ID",idField);
        addRow(card,c,row++,"Item name",nameField);
        addRow(card,c,row++,"Description",new JScrollPane(descriptionArea));
        addRow(card,c,row++,"Location found/lost",locationField);
        addRow(card,c,row++,"Date (e.g. 10-Oct-2026)",dateField);
        addRow(card,c,row++,"Reporter name",reporterField);
        addRow(card,c,row++,"Contact number",contactField);

        JPanel photoPanel = new JPanel(new BorderLayout(12, 0)); photoPanel.setOpaque(false);
        photoPreview = new JLabel("No photo selected", SwingConstants.CENTER); photoPreview.setPreferredSize(new Dimension(150, 105));
        photoPreview.setForeground(AppStyle.MUTED); photoPreview.setBackground(new Color(244,248,255)); photoPreview.setOpaque(true);
        photoPreview.setBorder(BorderFactory.createLineBorder(AppStyle.BORDER));
        JPanel photoActions = new JPanel(); photoActions.setOpaque(false); photoActions.setLayout(new BoxLayout(photoActions, BoxLayout.Y_AXIS));
        JButton choose = AppStyle.secondaryButton("＋ Choose item photo"); choose.setAlignmentX(Component.LEFT_ALIGNMENT);
        photoName = AppStyle.muted("JPG or PNG • optional"); photoName.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton clear = AppStyle.secondaryButton("Remove photo"); clear.setAlignmentX(Component.LEFT_ALIGNMENT);
        photoActions.add(choose); photoActions.add(Box.createVerticalStrut(8)); photoActions.add(photoName); photoActions.add(Box.createVerticalStrut(8)); photoActions.add(clear);
        photoPanel.add(photoPreview, BorderLayout.WEST); photoPanel.add(photoActions, BorderLayout.CENTER);
        addRow(card,c,row++,"Item photo",photoPanel);
        c.gridx=0; c.gridy=row; c.gridwidth=2; c.weightx=1; c.insets=new Insets(18,8,4,8);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0)); actions.setOpaque(false);
        JButton cancel=AppStyle.secondaryButton("Cancel"); JButton submit=AppStyle.primaryButton("Submit " + action + " Item  →");
        actions.add(cancel); actions.add(submit); card.add(actions,c);
        choose.addActionListener(e -> pickPhoto()); clear.addActionListener(e -> {selectedPhoto=null; photoPreview.setIcon(null); photoPreview.setText("No photo selected"); photoName.setText("JPG or PNG • optional");});
        cancel.addActionListener(e -> dispose()); submit.addActionListener(e -> submit());
        root.add(card,BorderLayout.CENTER); add(new JScrollPane(root)); AppStyle.styleFrame(this); setVisible(true);
    }
    private void addRow(JPanel panel, GridBagConstraints c, int row, String label, Component field) {
        c.gridy=row; c.gridx=0; c.gridwidth=1; c.weightx=0; c.fill=GridBagConstraints.HORIZONTAL;
        JLabel l=new JLabel(label); l.setFont(AppStyle.font(Font.BOLD, 13)); l.setForeground(AppStyle.TEXT); panel.add(l,c);
        c.gridx=1; c.weightx=1; c.fill=GridBagConstraints.HORIZONTAL; panel.add(field,c);
    }
    private void pickPhoto() {
        File f=ItemPhotoSupport.chooseImage(this); if(f==null)return;
        selectedPhoto=f; photoName.setText(f.getName()); photoPreview.setText("");
        ImageIcon icon=ItemPhotoSupport.thumbnail(f.getAbsolutePath(),145,100); photoPreview.setIcon(icon);
    }
    private void submit() {
        try {
            String idText=idField.getText().trim();
            if(idText.isEmpty()) throw new NumberFormatException();
            int id=Integer.parseInt(idText);
            String name=nameField.getText().trim(), desc=descriptionArea.getText().trim(), loc=locationField.getText().trim(), date=dateField.getText().trim(), reporter=reporterField.getText().trim(), contact=contactField.getText().trim();
            if(name.isEmpty()||desc.isEmpty()||loc.isEmpty()||date.isEmpty()||reporter.isEmpty()||contact.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Please complete all required fields.","Missing details",JOptionPane.WARNING_MESSAGE); return;
            }
            String photoPath=ItemPhotoSupport.copyIntoUploads(selectedPhoto,lost?"lost":"found",id);
            boolean ok=lost ? LostItemDAO.addLostItem(id,name,desc,loc,date,reporter,contact,"Available",photoPath)
                            : FoundItemDAO.addFoundItem(id,name,desc,loc,date,reporter,contact,"Available",photoPath);
            if(ok){JOptionPane.showMessageDialog(this, actionName()+" item saved successfully"+(photoPath==null?".":" with photo."),"CampusConnect",JOptionPane.INFORMATION_MESSAGE);dispose();}
            else JOptionPane.showMessageDialog(this,"Could not save item. Check the VS Code terminal for the database error. If this ID already exists, choose a new ID.","Database error",JOptionPane.ERROR_MESSAGE);
        } catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Item ID must be a valid number.","Invalid Item ID",JOptionPane.WARNING_MESSAGE);}
          catch(IOException ex){JOptionPane.showMessageDialog(this,"Could not copy photo: "+ex.getMessage(),"Photo error",JOptionPane.ERROR_MESSAGE);}
    }
    private String actionName(){return lost?"Lost":"Found";}
}
