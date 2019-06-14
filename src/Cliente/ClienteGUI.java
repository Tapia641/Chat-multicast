package Cliente;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ClienteGUI extends javax.swing.JFrame {

    public static final String IP = "230.0.0.0";
    public static final Integer PORT = 4321;
    public String ListaUsuarios = "@";
    public String TextoChat = "";
    public DefaultListModel modelo;
    public Chat MiChat;

    //Lista de emojis
    public static ImageIcon SONRISA = new ImageIcon(new File("src/Imagenes/slightly-smiling-face_1f642.png").getAbsolutePath());
    public static String nombre;

    /**
     * Creates new form ClienteGUI
     */
    public ClienteGUI() throws IOException, ClassNotFoundException {
        nombre = JOptionPane.showInputDialog("Ingresa tu nombre de usuario");

        this.setTitle(nombre);
        initComponents();
        modelo = new DefaultListModel();
        MiChat = new Chat(this);
        //ListaUsuarios += nombre + "&";
        String[] cadena = {"<b> " + nombre, " </b> se ha conectado.<br>"};
        MiChat.MandaMensaje(cadena);
        modelo.addElement(nombre);
        MiChat.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotonEnviar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        AreaChat = new javax.swing.JEditorPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        EntradaTexto = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        AreaLista = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BotonEnviar.setText("Enviar");
        BotonEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotonEnviarMouseClicked(evt);
            }
        });

        AreaChat.setContentType("text/html"); // NOI18N
        AreaChat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane2.setViewportView(AreaChat);

        jScrollPane1.setViewportView(EntradaTexto);

        AreaLista.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AreaLista.setToolTipText("");
        jScrollPane3.setViewportView(AreaLista);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonEnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonEnviarMouseClicked

        String[] texto = {"<b> " + nombre + " :</b> " + EntradaTexto.getText() + " <br> "};
        try {
            MiChat.MandaMensaje(texto);
            EntradaTexto.setText("");
        } catch (IOException ex) {
            Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_BotonEnviarMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        String[] texto = {"El usuario <b>" + nombre + "</b> ha salido. <br>"};
        try {
            MiChat.MandaMensaje(texto);
        } catch (IOException ex) {
            Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClienteGUI CL = null;
                try {
                    CL = new ClienteGUI();
                } catch (IOException ex) {
                    Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                CL.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane AreaChat;
    private javax.swing.JList<String> AreaLista;
    private javax.swing.JButton BotonEnviar;
    private javax.swing.JTextPane EntradaTexto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    void AgregarTexto(String msg) throws MalformedURLException {

        msg = msg.replaceAll("SONRISA", "<img width=\"30\" height=\"30\" SRC=\"" + ClienteGUI.class.getResource("SONRISA.png") + "\"></img>");
        msg = msg.replaceAll("ENOJADO", "<img width=\"30\" height=\"30\" SRC=\"" + ClienteGUI.class.getResource("ENOJADO.png") + "\"></img>");
        msg = msg.replaceAll("RISAS", "<img width=\"30\" height=\"30\" SRC=\"" + ClienteGUI.class.getResource("RISAS.png") + "\"></img>");
        msg = msg.replaceAll("LLORANDO", "<img width=\"30\" height=\"30\" SRC=\"" + ClienteGUI.class.getResource("LLORANDO.png") + "\"></img>");
        msg = msg.replaceAll("BESO", "<img width=\"30\" height=\"30\" SRC=\"" + ClienteGUI.class.getResource("BESO.png") + "\"></img>");
        msg = msg.replaceAll("ENAMORADO", "<img width=\"30\" height=\"30\" SRC=\"" + ClienteGUI.class.getResource("ENAMORADO.png") + "\"></img>");

        /*
        String aux = msg;
        String[] Usuarios = aux.split(":");
        String[] aux2 = Usuarios[0].split(" ");
        aux = aux.replaceAll(Usuarios[0], aux2[0]);
        if (!modelo.contains(aux)) {
            modelo.addElement(aux);
        }*/
        java.util.Date fecha = new Date();
        msg = msg + "<small>" + fecha + "</small><br>";

        AreaLista.setModel(modelo);

        if (msg.contains("PRIVADO")) {
            if (msg.contains(nombre)) {
                TextoChat += msg.replaceAll("PRIVADO " + nombre, "<b>[PRIVADO]</b>:");

            }
        } else {
            TextoChat += msg;

        }

        AreaChat.setText(TextoChat);
    }
}
