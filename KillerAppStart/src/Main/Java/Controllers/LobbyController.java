package controllers;

import classes.clientapplication.Player;
import classes.lobbymanager.Lobby;
import classes.lobbymanager.LobbyManager;
import classes.lobbymanager.LobbyPlayer;
import classes.singletons.PlayerSingleton;
import enums.MessageType;
import FontysPublisher.IRemotePropertyListener;
import interfaces.ILobbyManager;
import startup.connections.GameServerConnection;
import startup.connections.LobbyServerConnection;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class LobbyController extends UnicastRemoteObject implements Initializable, IRemotePropertyListener{

    /**
     *  GUI Objects
     */
    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnCheckHistory;

    @FXML
    private Button btnCreateLobby;

    @FXML
    private Label lbLogInAs;

    @FXML
    private Button btnStartGame;

    @FXML
    private ListView lvLobbys;

    /**
     *  Fields
     */
    private transient ILobbyManager lobbymanager;
    private transient Logger log = Logger.getLogger("warning");

    /**
     *  Constructor
     */
    public LobbyController() throws RemoteException {
        //only used for testing
    }

    /**
     *  Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnStartGame.setDisable(true);
        lbLogInAs.setText("Logged in as: " + PlayerSingleton.getPlayer().getName());

        connectLobbyServer();
        connectGameServer();
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
            log.warning(e1.toString());
        }
        if (root1 != null) {
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            stage2.show();
            try {
                Player player = PlayerSingleton.getPlayer();
                LobbyPlayer lp = player.getLobbyPlayer();
                LobbyServerConnection.getInstance().removePlayerExistence(lp);
                unsubscribe();
            } catch (RemoteException e1) {
                log.warning(e1.toString());
            }
            disconnectLobbyServer();
        }
    }

    @FXML
    public void btnStartGame(Event e)
    {
        //hier moet aan gewerkt worden

        LobbyPlayer lobbyPlayer = PlayerSingleton.getPlayer().getLobbyPlayer();
        try {
            List<LobbyPlayer> lobbyPlayerList = LobbyServerConnection.getInstance().getPlayerList(lobbyPlayer);

            List<Player> pl = new ArrayList<>();

            for (LobbyPlayer lp :lobbyPlayerList) {
                Player p = new Player(lp.getUniqueId(), lp.getName());
                pl.add(p);
            }

            //game gets starter on server
            GameServerConnection.getInstance().startGame(pl);
            sleep();
            //tartGamevanuit lobby
            LobbyServerConnection.getInstance().startGame(lobbyPlayer);

        } catch (RemoteException e1) {
            log.warning(e1.toString());
            log.warning("couldn't create a game");
        }
    }

    private void sleep()
    {
        try {
            Thread.sleep(100);
        } catch (Exception e1) {
            log.warning(e1.toString());
        }
    }

    @FXML
    public void btnCreateLobby(Event e)
    {
        //lobby gets created on the server
        Player player = PlayerSingleton.getPlayer();
        LobbyPlayer lobbyPlayer = new LobbyPlayer(player.getUniqueId(), player.getName());
        try {
            if(LobbyServerConnection.getInstance().createLobby(lobbyPlayer))
            {
                //lobby created / do nothing
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");

                // Header Text: null
                alert.setHeaderText(null);
                alert.setContentText("you are already in a lobby!");

                alert.showAndWait();
            }
        } catch (RemoteException e1) {
            log.warning(e1.toString());
        }
    }

    @FXML
    public void lvLobbyTap(MouseEvent click)
    {
        if (click.getClickCount() == 2 && lvLobbys.getSelectionModel().getSelectedIndex() != -1) {
            Lobby lobby = (Lobby) lvLobbys.getSelectionModel().getSelectedItem();

            Player player = PlayerSingleton.getPlayer();
            LobbyPlayer lp = new LobbyPlayer(player.getUniqueId(), player.getName());
            try {
                if(!LobbyServerConnection.getInstance().joinLobby(lp, lobby.getUniqueId()))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notification");

                    // Header Text: null
                    alert.setHeaderText("Not possible to join");
                    alert.setContentText("you are already in a lobby. \nOr the lobby you tried to join is full.");

                    alert.showAndWait();
                }
            } catch (RemoteException e) {
                log.warning(e.toString());
            }
        }
    }

    @FXML
    public void btnHistory()
    {
        //still in development
    }

    /**
     *  Override methods
     */
    private void disconnectLobbyServer(){
        LobbyServerConnection.getInstance().disconnect();
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) throws RemoteException {

        if(evt.getOldValue() == MessageType.STARTGAME){
            List<LobbyPlayer> lobbyplayers = (List<LobbyPlayer>) evt.getNewValue();
            if(lobbyplayers.contains(PlayerSingleton.getPlayer().getLobbyPlayer()))
            {
                //start new window here
                //Go to a new view
                Platform.runLater(() -> {
                    Stage stage = (Stage) btnStartGame.getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../boardView.fxml"));
                    Parent root1 = null;
                    try {
                        root1 = fxmlLoader.load();
                    } catch (IOException e1) {
                        log.warning(e1.toString());
                    }
                    if (root1 != null) {
                       gotoboard(root1);
                    }
                });
            }
        }
        else {
            List<Lobby> lobbys = (ArrayList<Lobby>) evt.getNewValue();
            LobbyManager.getInstance().addLobbys(lobbys);
            update();
        }
    }

    private void gotoboard(Parent root1)
    {
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        stage2.show();
        try {
            unsubscribe();
        } catch (RemoteException e1) {
            log.warning(e1.toString());
        }
    }

    public void subscribe() throws RemoteException{
        lobbymanager.subscribeRemoteListener(this,"lobby");
    }

    public void unsubscribe() throws RemoteException{
        lobbymanager.unsubscribeRemoteListener(this,"lobby");
    }

    /**
     *  normal methods
     */
    public void update() {
        Platform.runLater(() -> {
            lvLobbys.getItems().clear();
            for (Lobby l : LobbyManager.getInstance().getLobbys()) {
                lvLobbys.getItems().add(l);
            }
            checkGameStartable();
        });



    }

    public void checkGameStartable()
    {
        if(LobbyManager.getInstance().checkStartAble())
        {
            btnStartGame.setDisable(false);
        }
        else
        {
            btnStartGame.setDisable(true);
        }
    }

    public void connectLobbyServer()
    {
        //hier maak een connectie met de server (LobbyServer)
        try {
            LobbyServerConnection.getInstance().connect();

            lobbymanager = LobbyServerConnection.getInstance().getLobbyManager();

            //verkrijgt alle lobbys
            List<Lobby> lobbyList = LobbyServerConnection.getInstance().getAllLobbys();
            LobbyManager.getInstance().addLobbys(lobbyList);
            update();
            subscribeTry();
        }
        catch (Exception e)
        {

            btnCreateLobby.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lobby server");

            // Header Text: null
            alert.setHeaderText("connection error");
            alert.setContentText("Couldn't connect to the lobbyserver. \nGames cannot be played right now but you can still check your history");

            alert.showAndWait();
        }
    }

    public void connectGameServer()
    {
        //hier maak een connectie met de server (gameServer)
        try {
            GameServerConnection.getInstance().connect();
        }
        catch (Exception e)
        {
            btnCheckHistory.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game server");

            // Header Text: null
            alert.setHeaderText("connection error");
            alert.setContentText("Couldn't connect to the game server. \nLobbys can be created but you cannot play or retrieve games.");

            alert.showAndWait();
        }
    }

    private void subscribeTry()
    {
        try {
            subscribe();
        } catch (RemoteException e) {
            log.warning(e.toString());
        }
    }
}
