package StartUp;

import Classes.ClientApplication.Player;
import Classes.LobbyManager.LobbyPlayer;
import Classes.Singletons.PlayerSingleton;
import StartUp.Connections.LobbyServerConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class ClientServer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../loginView.fxml"));
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //does something when program closes
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("application close");
//                try {
//                    LobbyPlayer lobbyPlayer = PlayerSingleton.getPlayer().getLobbyPlayer();
//                    LobbyServerConnection.getInstance().RemovePlayerExistence(lobbyPlayer);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
            }
        }));
        launch(args);
    }
}
