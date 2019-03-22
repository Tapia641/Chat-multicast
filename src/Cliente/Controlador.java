package Cliente;

import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ResourceBundle;

public class Controlador implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IniciarCliente();
    }

    public static void IniciarCliente() {

        /* CREAMOS EL GRUPO */
        InetAddress grupo = null;

        try {

            /* SOCKET MULTICAST CON PUERTO 9999 */
            MulticastSocket clienteSocket = new MulticastSocket(9999);
            System.out.println("Cliente escuchando puerto " + clienteSocket.getPort());
            clienteSocket.setReuseAddress(true);

            try {

                /* DIRECCION DEL GRUPO */
                grupo = InetAddress.getByName("228.1.1.1");

            } catch (UnknownHostException e) {
                System.out.println("Direccion erronea ");
            }

            /* GRUPO POR EL CUAL SE RECIBE MULTIPLES TRAMAS */
            clienteSocket.joinGroup(grupo);
            System.out.println("Unido al grupo :)");

            while (true) {

                String msg = "";
                System.out.println("Escribe un mensaje:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                msg = br.readLine();
                DatagramPacket data = new DatagramPacket(msg.getBytes(), 0, msg.length(), grupo, 8885);
                clienteSocket.send(data);

                /* ----------------COMENZAMOS A RECIBIR EL MENSAJE------------------ */
                /* OBTENEMOS EL MENSAJE DEL SERVIDOR */
                DatagramPacket paqueteServidor = new DatagramPacket(new byte[100], 100);
                clienteSocket.receive(paqueteServidor);

                /* LO TRANSFORMAMOS A UNA CADENA */
                String mensaje = new String(paqueteServidor.getData());
                System.out.println(paqueteServidor.getAddress() + " Dice: ");
                System.out.println("Datagrama recibido: " + mensaje);

                /* DATOS DEL PAQUETE RECIBIDO */
                //System.out.println(
                //      "Servidor descubierto " + paqueteServidor.getAddress() + ":" + paqueteServidor.getPort());



                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /* ----------------COMENZAMOS A ENVIAR UNA RESPUESTA---------------- */
                /* SERVIDOR CON UN PUERTO DIFERENTE */

                //String respuesta = "Hola chicos, soy el cliente 1";
                //byte[] b = respuesta.getBytes();
                //DatagramPacket paqueteRespuesta = new DatagramPacket(b, b.length, grupo, 9999);

                /* MANDANDO SALUDO */
                //clienteSocket.send(paqueteRespuesta);
                //System.out.println("Mensaje enviado: " + respuesta + ", con TTL: " + clienteSocket.getTimeToLive());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
