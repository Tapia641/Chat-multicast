package Clases;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.io.File;

public class Emoji extends JEditorPane {

    static ImageIcon SMILE_IMG = new ImageIcon(new File("src/Imagenes/slightly-smiling-face_1f642.png").getAbsolutePath());
    static ImageIcon ENOJADO = new ImageIcon(new File("src/Imagenes/angry-face_1f620.png").getAbsolutePath());


    public static void main(String[] args) {
        JFrame frame = new JFrame("Autoreplace :) with Smiles images example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Emoji app = new Emoji();
        app.setEditorKit(new StyledEditorKit());
        app.initListener();
        JScrollPane scroll = new JScrollPane(app);
        frame.getContentPane().add(scroll);

        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Emoji() {
        super();
    }

    private void initListener() {

        getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent event) {

                final DocumentEvent e = event;

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        if (e.getDocument() instanceof StyledDocument) {
                            try {
                                StyledDocument doc = (StyledDocument) e.getDocument();
                                int start = Utilities.getRowStart(Emoji.this, Math.max(0, e.getOffset() - 1));
                                int end = Utilities.getWordStart(Emoji.this, e.getOffset() + e.getLength());
                                String text = doc.getText(start, end - start);

                                int i = text.indexOf(":)");
                                while (i >= 0) {
                                    final SimpleAttributeSet attrs = new SimpleAttributeSet(
                                            doc.getCharacterElement(start + i).getAttributes());
                                    if (StyleConstants.getIcon(attrs) == null) {
                                        StyleConstants.setIcon(attrs, SMILE_IMG);
                                        doc.remove(start + i, 2);
                                        doc.insertString(start + i, ":)", attrs);
                                    }
                                    i = text.indexOf(":)", i + 2);
                                }
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }

            public void removeUpdate(DocumentEvent e) {
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    static ImageIcon createImage() {
        ImageIcon res = new ImageIcon(new File("src/Imagenes/worried-face_1f61f.png").getAbsolutePath());
        return res;
    }
}
