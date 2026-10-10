import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Selects and stores item photos inside the project uploads folder. */
public final class ItemPhotoSupport {
    private ItemPhotoSupport() {}
    public static File chooseImage(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a photo of the item");
        chooser.setFileFilter(new FileNameExtensionFilter("Image files (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"));
        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) return null;
        File f = chooser.getSelectedFile();
        String n = f.getName().toLowerCase();
        if (!(n.endsWith(".jpg") || n.endsWith(".jpeg") || n.endsWith(".png") || n.endsWith(".gif"))) {
            JOptionPane.showMessageDialog(parent, "Please choose a JPG, PNG, or GIF image.", "Unsupported image", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return f;
    }
    public static String copyIntoUploads(File source, String kind, int itemId) throws IOException {
        if (source == null) return null;
        Path dir = Paths.get("uploads"); Files.createDirectories(dir);
        String ext = source.getName().substring(source.getName().lastIndexOf('.')).toLowerCase();
        Path target = dir.resolve(kind.toLowerCase() + "_" + itemId + "_" + System.currentTimeMillis() + ext);
        Files.copy(source.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString().replace('\\', '/');
    }
    public static ImageIcon thumbnail(String path, int width, int height) {
        if (path == null || path.trim().isEmpty()) return null;
        File f = new File(path);
        if (!f.isFile()) return null;
        ImageIcon original = new ImageIcon(path);
        Image image = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
