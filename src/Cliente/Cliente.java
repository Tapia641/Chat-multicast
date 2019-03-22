package Cliente;

import Clases.Informacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Cliente {

    public static void main(String[] args) {

        MarcoCliente mimarco = new MarcoCliente();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}


class MarcoCliente extends JFrame {

    public MarcoCliente() {

        setBounds(600, 300, 280, 350);

        LaminaMarcoCliente milamina = new LaminaMarcoCliente();

        add(milamina);

        setVisible(true);
    }

}

class LaminaMarcoCliente extends JPanel implements Runnable{

    public LaminaMarcoCliente() {

        nick = new JTextField(5);

        add(nick);

        ip = new JTextField(8);

        add(ip);

        JLabel texto = new JLabel("CHAT CLIENTE");

        add(texto);

        campochat = new JTextArea(12, 20);

        add(campochat);

        mensaje = new JTextField(20);

        add(mensaje);

        miboton = new JButton("Enviar");

        EnviaTexto mievento = new EnviaTexto();
        miboton.addActionListener(mievento);

        add(miboton);

        //Poner la instancia de la clase que se quiere ejecutar
        Thread mihilo = new Thread(this);
        mihilo.start();

    }

    @Override
    public void run() {
        try {
            //Poner como puerto global
            ServerSocket servidorCliente= new ServerSocket(1234);

            Socket cliente;
            Informacion InfoRecibida;

            while (true){
                cliente = servidorCliente.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());

               InfoRecibida = (Informacion) flujoEntrada.readObject();

               campochat.append("\n" + InfoRecibida.getNombre() + " : " + InfoRecibida.getMensaje());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class EnviaTexto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            campochat.append("\n Yo:" + mensaje);

            try {
                //Creamos el socket
                Socket misocket = new Socket("127.0.0.1", 1234);

                Informacion Info = new Informacion();

                Info.setIP(ip.getText());
                Info.setNombre(nick.getText());
                Info.setMensaje(mensaje.getText());

                //Flujo de salida
                ObjectOutputStream paquete = new ObjectOutputStream(misocket.getOutputStream());


                paquete.writeObject(Info);
                misocket.close();

                //Limpiamos el campo
                mensaje.setText("");

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private JTextField mensaje, nick, ip;
    private JTextArea campochat;
    private JButton miboton;

}