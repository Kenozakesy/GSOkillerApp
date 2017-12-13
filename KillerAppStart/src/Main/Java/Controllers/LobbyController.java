package Controllers;

import Classes.Singletons.PlayerSingleton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class LobbyController implements Initializable{

    /**
     *  GUI Objects
     */
    @FXML
    private Button btnLogOut;

    @FXML
    private Label lbLogInAs;

    @FXML
    private Button btnStartGame;

    /**
     *  Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbLogInAs.setText("Logged in as: " + PlayerSingleton.getPlayer().getName());

        //hier maak een connectie met de server (LobbyManager)
    }


    /**
     *  Actions
     */
    @FXML
    public void btnLogOut(Event e)
    {
        //Go to a new view
        Stage stage = (Stage) btnStartGame.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../loginView.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (root1 != null) {
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            stage2.show();
        }
    }

    @FXML
    public void btnStartGame(Event e)
    {
        //Go to a new view
        Stage stage = (Stage) btnStartGame.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../boardView.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (root1 != null) {
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            stage2.show();
        }
    }
}
