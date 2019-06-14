package Cliente;

import static Cliente.ClienteGUI.IP;
import static Cliente.ClienteGUI.PORT;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class Chat extends Thread{
    public static final String IP = "230.0.0.0";
    public static final Integer PORT = 4321;
    public static ClienteGUI Interfaz;

    public Chat(ClienteGUI Nuevo) throws IOException, ClassNotFoundException {
        Interfaz = Nuevo;
    }
    
    public void MandaMensaje(String[] message) throws IOException, ClassNotFoundException {
        //Guardamos el texto total
        String aux = "";
        for (String i : message) {
            aux += i;
        }
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(IP);
        System.out.println("Mensaje que se envia: " + aux);
        byte[] msg = aux.getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length, group, PORT);
        socket.send(packet);
        socket.close();
    }

    public void RecibeMensaje() throws IOException, BadLocationException, ClassNotFoundException {
        byte[] buffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(PORT);
        InetAddress group = InetAddress.getByName(IP);
        socket.joinGroup(group);

        while (true) {
            System.out.println("Esperando mensajes...");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println("Mensaje recibido: " + msg);
            Interfaz.AgregarTexto(msg);
        }
    }

    @Override
    public void run() {
               byte[] buffer = new byte[1024];
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(PORT);
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
        InetAddress group = null;
        try {
            group = InetAddress.getByName(IP);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.joinGroup(group);
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            System.out.println("Esperando mensajes...");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                   try {
                       socket.receive(packet);
                   } catch (IOException ex) {
                       Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                   }
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println("Mensaje recibido: " + msg);
                   try {
                       Interfaz.AgregarTexto(msg);
                   } catch (MalformedURLException ex) {
                       Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                   }
        }
    }
}
