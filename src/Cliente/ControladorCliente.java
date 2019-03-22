package Cliente;

import Servidor.MulticastServidor;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorCliente implements Runnable  {

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


    @FXML
    public void ClickEnviar(javafx.scene.input.MouseEvent event) throws IOException {
        MandaMensaje(EntradaMensaje.getText(), IP, PORT);
        AreaTexto.appendText("\n" + "Yo: " + EntradaMensaje.getText());
        EntradaMensaje.setText("");
    }

    public void receiveUDPMessage(String ip, int port) throws
            IOException {
        byte[] buffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(PORT);
        InetAddress group = InetAddress.getByName(IP);
        socket.joinGroup(group);
        while (true) {
            System.out.println("Esperando mensajes...");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(msg);
            socket.send(packet);
        }
    }
    public static void MandaMensaje(String message, String ipAddress, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(ipAddress);

        /**/
        byte[] msg = message.getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length,
                group, port);
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

    @Override
    public void run() {
        try {
            receiveUDPMessage(IP, PORT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
