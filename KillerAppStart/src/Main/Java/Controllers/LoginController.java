package Controllers;

import Classes.LobbyManager.LobbyPlayer;
import Classes.Singletons.PlayerSingleton;
import StartUp.Connections.LobbyServerConnection;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    /**
     *  GUI objects
     */
    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lbSignal;

    /**
     *  Initializet
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     *  Actions
     */
    @FXML
    public void btnLogin(Event e)
    {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if(PlayerSingleton.login(username, password)) {

            //Go to a new view
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../lobbyView.fxml"));
            Parent root1 = null;
            try {
                root1 = fxmlLoader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (root1 != null) {
                Stage stage2 = new Stage();
                stage2.setScene(new Scene(root1));

                //does something when it closes
//                stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                    public void handle(WindowEvent we) {
//                        try {
//                            LobbyPlayer lobbyPlayer = PlayerSingleton.getPlayer().getLobbyPlayer();
//                            LobbyServerConnection.getInstance().RemovePlayerExistence(lobbyPlayer);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

                stage2.show();
            }
        }
        else
        {
            lbSignal.setText("Login failed");
        }
    }

}
