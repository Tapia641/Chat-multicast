package Cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class Cliente extends Application implements Runnable{

    public static final String IP = "230.0.0.0";
    public static final Integer PORT = 4321;

    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("ChatCliente.fxml"));
        primaryStage.setTitle("Cliente");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread hilo = new Thread(this);
        hilo.start();

    }

    public static void main(String[] args) {
        launch(args);
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
            System.out.println(msg);
            //AreaTexto.appendText("\n" + msg);
        }
    }

    public void run() {
        try {
            receiveUDPMessage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
