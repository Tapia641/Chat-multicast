package Cliente;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.net.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorCliente implements Initializable {

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
    }

    public String getNombrePanel() {
        return NombrePanel.getText();
    }

    public void setNombrePanel(String Nombre){
        NombrePanel.setText(Nombre);
    }

    public void addTextChat(String text){
        AreaTexto.setText(text);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NombrePanel.setText(PedirNombre());
    }
}
