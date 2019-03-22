package Servidor;

import Clases.Informacion;

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {

        MarcoServidor mimarco = new MarcoServidor();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class MarcoServidor extends JFrame implements Runnable {

    public MarcoServidor() {

        setBounds(1200, 300, 280, 350);

        JPanel milamina = new JPanel();

        milamina.setLayout(new BorderLayout());

        areatexto = new JTextArea();

        milamina.add(areatexto, BorderLayout.CENTER);

        add(milamina);

        setVisible(true);

        Thread mihilo = new Thread(this);
        mihilo.start();

    }

    private JTextArea areatexto;

    @Override
    public void run() {
        System.out.println("Servidor iniciado...");
        String nombre, ip, mensaje;
        Informacion InfoRecibida = new Informacion();


        try {
            ServerSocket servidor = new ServerSocket(1234);

            while (true) {

                Socket misocket = servidor.accept();

                ObjectInputStream paquete_entrada = new ObjectInputStream(misocket.getInputStream());

                InfoRecibida = (Informacion) paquete_entrada.readObject();

                nombre = InfoRecibida.getNombre();
                ip = InfoRecibida.getIP();
                mensaje = InfoRecibida.getMensaje();

                areatexto.append("\n" + nombre + ": " + mensaje + " para" + ip);

                //Reenviamos la informacion
                Socket enviarDestino = new Socket(ip,1234);
                ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviarDestino.getOutputStream());
                paqueteReenvio.writeObject(paquete_entrada);
                enviarDestino.close();

                paqueteReenvio.close();
                misocket.close();

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
