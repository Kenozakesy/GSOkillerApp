package Controllers;

import classes.clientapplication.Player;
import classes.gamemanager.Cell;
import classes.gamemanager.Game;
import classes.gamemanager.PlayerInGame;
import classes.Singletons.PlayerSingleton;
import FontysPublisher.IRemotePropertyListener;
import Interfaces.IGameManager;
import StartUp.Connections.GameServerConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class BoardController extends UnicastRemoteObject implements Initializable, IRemotePropertyListener {

    /**
     * GUI Objects
     */
    @FXML
    private Canvas cvBoard;

    @FXML
    private Label lbName;

    @FXML
    private Label lbOpponentName;

    @FXML
    private Label lbTurn;

    @FXML
    private Label lblTurnNumber;

    @FXML
    private Label lbNotify;

    @FXML
    private Label lbWinner;

    /**
     * Fields
     */
    Timer timer;
    TimerTask task;
    private IGameManager gamemanager;
    private Game game;

    public BoardController() throws RemoteException {}

    /**
     * Initialize
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //ook nog kijken naar de push techniek en dit ng testen
        //get game from server
        Player player = PlayerSingleton.getPlayer();
        try {
            gamemanager = GameServerConnection.getInstance().getLobbyManager();
            subscribe();

            game = GameServerConnection.getInstance().getGame(player);
            game.setColors();
            update();

        } catch (RemoteException e) {

            e.printStackTrace();
        }
    }

    /**
     * Actions
     */
    @FXML
    public void btnReset() {

    }

    //code has to be restructured
    @FXML
    public void canvasClick(MouseEvent mouse) {

        boolean check = true;
        Rectangle mouseLocation = new Rectangle((int) mouse.getX(), (int) mouse.getY(), 1, 1);

        Player player = PlayerSingleton.getPlayer();
        PlayerInGame pig = new PlayerInGame(player.getUniqueId(), player.getName());

        //kijkt of speler wel spel mag veranderen
        if (check && pig.equals(game.getCurrentPlayerTurn())) {

            //loops through every cell generated
            for (Cell C : game.getBoard().getCells()) {

                Rectangle cellHitbox = new Rectangle(C.getAnchor().x, C.getAnchor().y, C.getWidth(), C.getHeight());
                //check if mouse is in correct box
                if (mouseLocation.intersects(cellHitbox)) {

                    //van kleur veranderen
                    if (C.getStone() != null && game.getCurrentPlayerTurn().getStones().contains(C.getStone())) {
                        game.getCurrentPlayerTurn().setSelectedStone(C.getStone());
                        try {
                            GameServerConnection.getInstance().sendGame(game);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        //update();
                        break;
                    } else {
                        //hier bereken algoritme
                        PlayerInGame currentPlayer = game.getCurrentPlayerTurn();
                        if (currentPlayer.calculate(C)) {
                            currentPlayer.deSelectAllStones(); //lokaal only

                            try {
                                GameServerConnection.getInstance().sendGameDatabase(game);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            lbNotify.setText("succes");
                            break;
                        } else {
                            lbNotify.setText("failure");
                            break;
                        }

                    }
                }
            }
        }
        //At the bottom it stays

        //update();
    }

    public void drawCanvas() {
        //refreshed de picture box
        GraphicsContext gc = cvBoard.getGraphicsContext2D();
        draw(gc);
    }

    private void draw(GraphicsContext gc) {

        gc.clearRect(0, 0, cvBoard.getWidth(), cvBoard.getHeight());
        game.draw(gc);
    }

    private void update() {

        Player player = PlayerSingleton.getPlayer();
        PlayerInGame pig = new PlayerInGame(player.getUniqueId(), player.getName());

        String opponent = "";
        for (PlayerInGame P : game.getPlayers()) {
            if (!P.equals(pig)) {
                opponent = P.getName();
                break;
            }
        }

        String finalOpponent = opponent;
        Platform.runLater(() -> {
            drawCanvas();

            lblTurnNumber.setText("TurnNumber: " + this.game.getTurn());
            lbOpponentName.setText("Opponent :" + finalOpponent);
            lbName.setText("Name: " + PlayerSingleton.getPlayer().getName());

            lbTurn.setText("Current turn: " + game.getCurrentPlayerTurn().getName());
            if (game.checkGameWon()) {
                lbTurn.setText("");
                lbWinner.setText("Winner is: " + game.getWinner().getName());
                cvBoard.setDisable(true);

                task = new TimerTask() {
                    @Override
                    public void run() {
                        goBackToLobbyView();
                    }
                };
                timer = new Timer();
                timer.schedule(task, 7000);
            }
        });
    }

    public void goBackToLobbyView()
    {
        Platform.runLater(() -> {
            Stage stage = (Stage) lblTurnNumber.getScene().getWindow();
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
                stage2.show();
            }
        });
    }

    /**
     * subscribe
     */
    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) throws RemoteException {

        Game game = (Game) evt.getNewValue();

        Player player = PlayerSingleton.getPlayer();
        PlayerInGame pig = new PlayerInGame(player.getUniqueId(), player.getName());

        for (PlayerInGame p : game.getPlayers()) {
            if(p.equals(pig))
            {
                this.game = game;
                game.setColors();
                update();
                break;
            }
        }
    }

    public void subscribe() throws RemoteException{
        gamemanager.subscribeRemoteListener(this,"game");
    }

    public void unsubscribe() throws RemoteException{
        gamemanager.unsubscribeRemoteListener(this,"game");
    }
}
