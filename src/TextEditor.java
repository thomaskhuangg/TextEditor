import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class TextEditor extends JFrame
        implements ActionListener {

    private JPanel panel, toolPanel;
    private JButton save, open, help;
    private JTextArea textArea;
    private JScrollPane jp;
    private JFileChooser fc;
    private String textName;


    public TextEditor() {
        super("Text Editor");

        fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
        fc.setDialogTitle("File Manager");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        toolPanel = new JPanel(new GridLayout(1, 0, 5, 5));
        save = new JButton("Save");
        save.addActionListener(this);
        open = new JButton("Open");
        open.addActionListener(this);
        help = new JButton("Help");
        help.addActionListener(this);
        toolPanel.add(save);
        toolPanel.add(open);
        toolPanel.add(help);


        panel = new JPanel(new BorderLayout());
        textArea = new JTextArea("");
        jp = new JScrollPane(textArea);
        panel.add(jp);

        add(toolPanel, BorderLayout.PAGE_START);
        add(panel);
    }

    public void loadFile(String textName) {
        try {
            File f = new File(textName);
            Scanner fs = new Scanner(f);
         String temp = "";
            while (fs.hasNextLine()) {
                String content = fs.next();
                temp += content;
            }
            textArea.setText(temp);
            System.out.print(temp);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "File" + textName + "not found.",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            dispose();

            if (fc.showSaveDialog(TextEditor.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fc.getSelectedFile();
                    PrintWriter pw = new PrintWriter(file);
                    String filePath = file.getAbsolutePath();

                    textArea.write(pw);
                    pw.flush();
                    pw.close();

                    String[] arguments = new String[]{"123"};
                    TextEditor.main(arguments);

                    Desktop desktop = Desktop.getDesktop();
                    File directory = new File(filePath);

                    try {
                        desktop.open(directory);
                    } catch (IllegalArgumentException iae) {
                        JOptionPane.showMessageDialog(null,
                                "File not found.",
                                "Error",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource() == open) {

            dispose();

            if (fc.showSaveDialog(TextEditor.this) == JFileChooser.APPROVE_OPTION) {
                String[] arguments = new String[]{"123"};
                TextEditor.main(arguments);
                loadFile(fc.getSelectedFile().getAbsolutePath());
                repaint();

            }
        }
    }


    public static void main(String[] args) {

        String windows = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(windows);
        } catch (Exception e) {
            System.out.println(windows + " PLAF not installed");
        }

        TextEditor window = new TextEditor();
        window.setBounds(500, 200, 600, 400);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setVisible(true);

    }
}
