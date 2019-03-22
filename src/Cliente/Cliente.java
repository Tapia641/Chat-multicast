package Cliente;

import Servidor.MulticastServidor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Cliente extends Application {

    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("ChatCliente.fxml"));
        primaryStage.setTitle("Cliente");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


        Thread t = new Thread(new ControladorCliente());
        t.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
