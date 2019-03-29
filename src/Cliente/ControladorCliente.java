package Cliente;

import Clases.Emoji;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorCliente extends JEditorPane implements Initializable {

    static ImageIcon SMILE_IMG = new ImageIcon(new File("src/Imagenes/slightly-smiling-face_1f642.png").getAbsolutePath());


    /* DECLARAMOS UNA CLASE CON UN HILO A LA ESCUCHA PARA RECIBIR LOS MENSAJES */
    public static class ServidorLocal extends ControladorCliente implements Runnable {

        public ServidorLocal() {
            Thread hilo = new Thread(this);
            hilo.start();
        }

        public void run() {
            try {
                receiveUDPMessage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        public void receiveUDPMessage() throws IOException {
            byte[] buffer = new byte[1024];
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(IP);
            socket.joinGroup(group);
            while (true) {
                System.out.println("Esperando mensajes...");
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                System.out.println(msg.toCharArray());
                //AreaTexto.appendText("\n" + msg);
            }
        }

    }

    public static final String IP = "230.0.0.0";
    public static final Integer PORT = 4321;
    @FXML
    JFXTextField EntradaMensaje;
    @FXML
    JFXButton BotonEnviar;
    @FXML
    JFXTextArea AreaTexto;
    @FXML
    Label NombrePanel;


    public ControladorCliente() {
        super();
    }

    public String getNombrePanel() {
        return NombrePanel.getText();
    }

    public void setNombrePanel(String Nombre) {
        NombrePanel.setText(Nombre);
    }

    public void addTextChat(String text) {
        //AreaTexto.appendText("\n" + text);

    }

    @FXML
    public void ClickEnviar(javafx.scene.input.MouseEvent event) throws IOException {
        String TextoCompleto = NombrePanel.getText() + ": " + EntradaMensaje.getText();
        MandaMensaje(TextoCompleto, IP, PORT);
        AreaTexto.appendText("\n" + TextoCompleto);
        EntradaMensaje.setText("");
    }


    public void MandaMensaje(String message, String ipAddress, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(ipAddress);

        byte[] msg = message.getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
        socket.send(packet);
        socket.close();
    }

    public String PedirNombre() {
        TextInputDialog nombre = new TextInputDialog("Nombre");
        nombre.setTitle("Datos");
        nombre.setHeaderText("Ingresa tu nombre de usuario");
        nombre.setContentText("Nombre:");
        Optional<String> result = nombre.showAndWait();

        if (result.get().isEmpty()) {
            return PedirNombre();
        } else {
            try {
                return result.get();
            } catch (Exception e) {
                return PedirNombre();
            }
        }

    }

    public void initialize(URL location, ResourceBundle resources) {
        //ESTABLECEMOS EL NOMBRE DEL USUARIO
        Emoji C = new Emoji();
        NombrePanel.setText(PedirNombre());

        //INICIAMOS CON EL HILO DEL SERVIDOR
        ServidorLocal server = new ServidorLocal();
    }

}
