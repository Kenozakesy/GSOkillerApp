package controllers;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.gamemanager.PlayerInGame;
import classes.singletons.PlayerSingleton;
import enums.Side;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import startup.connections.GameServerConnection;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 15-1-2018.
 */
public class GetGameController implements Initializable{

    /**
     * GUI Objects
     */
    @FXML
    private Canvas cvBoard;

    @FXML
    private Label lblTurnNumber;

    @FXML
    private ListView lvGames;



    /**
     * Fields
     */
    private transient Logger log = Logger.getLogger("warning");
    private Game game;
    private List<Game> gameList = new ArrayList<>();

    /**
     * Initialize
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //ook nog kijken naar de push techniek en dit ng testen
        //get game from server

        Player player = PlayerSingleton.getPlayer();
        try {
            gameList = GameServerConnection.getInstance().getAllGamesFromPlayer(player);

            update();

        } catch (RemoteException e) {
            log.warning(e.toString());
        }
    }

    /**
     * Actions
     */
    @FXML
    public void btnLoad(Event e)
    {
        if(lvGames.getSelectionModel().getSelectedIndex() != -1)
        {
            Game selgame = (Game) lvGames.getSelectionModel().getSelectedItem();
            Game testgame = null;
            try {
                testgame = GameServerConnection.getInstance().getGameStateTurn(selgame.getUniqueid(), 0);
                this.game = testgame;
                this.game.setColors();

                Side color = null;
                Player player = PlayerSingleton.getPlayer();
                PlayerInGame pig = new PlayerInGame(player.getUniqueId(), player.getName());

                for (PlayerInGame P : game.getPlayers()) {
                    if (P.equals(pig)) {
                        color = P.getSide();
                        break;
                    }
                }

                lblTurnNumber.setText("You are: " + color.toString());
                drawCanvas();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }


    }

    @FXML
    public void btnNext(Event e)
    {
        int turn = this.game.getTurn() + 1;

        Game testgame = null;
        try {
            testgame = GameServerConnection.getInstance().getGameStateTurn(this.game.getUniqueid(), turn);
            this.game = testgame;
            this.game.setColors();
            drawCanvas();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void btnPrevious(Event e)
    {
        int turn = this.game.getTurn() - 1;

        Game testgame = null;
        try {
            testgame = GameServerConnection.getInstance().getGameStateTurn(this.game.getUniqueid(), turn);
            this.game = testgame;
            this.game.setColors();
            drawCanvas();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }

    }


    public void drawCanvas() {
        //refreshed de picture box
        GraphicsContext gc = cvBoard.getGraphicsContext2D();
        draw(gc);
    }

    private void draw(GraphicsContext gc) {

        gc.clearRect(0, 0, cvBoard.getWidth(), cvBoard.getHeight());
        this.game.draw(gc);
    }

    private void update() {

        lvGames.getItems().clear();
        for (Game g: gameList)
        {
            lvGames.getItems().add(g);
        }

    }

    @FXML
    public void btngoBackToLobbyView()
    {
        Platform.runLater(() -> {
            Stage stage = (Stage) lblTurnNumber.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../lobbyView.fxml"));
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
            }
        });
    }

}
