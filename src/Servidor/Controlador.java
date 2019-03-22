package Servidor;

import javafx.fxml.Initializable;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IniciarServidor();
    }

    public static void IniciarServidor() {

        /* CREAMOS EL GRUPO */
        InetAddress grupo = null;

        try {

            /* SOCKET MULTICAST CON PUERTO 9999 POR EL QUE ESCUCHA */
            MulticastSocket servidorSocket = new MulticastSocket(9976);
            servidorSocket.setReuseAddress(true);
            servidorSocket.setTimeToLive(1);

            /* UNIENDOSE AL GRUPO */
            String mensaje = "Soy el servidor";
            byte[] b = mensaje.getBytes();
            grupo = InetAddress.getByName("228.1.1.1");
            servidorSocket.joinGroup(grupo);

            while (true) {

                /* ----------------COMENZAMOS A ENVIAR MENSAJE---------------- */

                /* SERVIDOR CON UN PUERTO DIFERENTE */
                //DatagramPacket paqueteMensaje = new DatagramPacket(b, b.length, grupo, 9999);

                /* MANDANDO SALUDO */
                //servidorSocket.send(paqueteMensaje);
                //System.out.println("Mensaje: " + mensaje + ", con TTL: " + servidorSocket.getTimeToLive());

                /* MOSTRAMOS LOS MENSAJES QUE HAN PASADO POR EL SERVIDOR */
                /* OBTENEMOS EL MENSAJE DEL Cliente */
                DatagramPacket paqueteServidor = new DatagramPacket(new byte[100], 100);
                servidorSocket.receive(paqueteServidor);

                /* LO TRANSFORMAMOS A UNA CADENA */
                 mensaje = new String(paqueteServidor.getData());
                System.out.println(paqueteServidor.getAddress() + " Dice: ");
                System.out.println("Datagrama recibido: " + mensaje);

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
