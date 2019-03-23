package Servidor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorServidor implements Initializable {

    public static final String IP = "230.0.0.0";
    public static final Integer PORT = 4321;

    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            receiveUDPMessage(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
