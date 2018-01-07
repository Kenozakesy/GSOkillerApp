package Controllers;

import Classes.ClientApplication.Player;
import Classes.GameManager.Cell;
import Classes.GameManager.Game;
import Classes.GameManager.PlayerInGame;
import Classes.Singletons.PlayerSingleton;
import Enums.Side;
import StartUp.Connections.GameServerConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class BoardController implements Initializable {

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
    private Label lbNotify;

    @FXML
    private Label lbWinner;

    /**
     * Fields
     */
    private Game game;

    /**
     * Initialize
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //ook nog kijken naar de push techniek en dit ng testen
        //get game from server
        Player player = PlayerSingleton.getPlayer();
        try {
            game = GameServerConnection.getInstance().getGame(player);
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

        if (check) {
            //loops through every cell generated
            for (Cell C : game.getBoard().getCells()) {
                Rectangle cellHitbox = new Rectangle(C.getAnchor().x, C.getAnchor().y, C.getWidth(), C.getHeight());
                //check if mouse is in correct box
                if (mouseLocation.intersects(cellHitbox)) {

                    //van kleur veranderen
                    if (C.getStone() != null && game.getCurrentPlayerTurn().getStones().contains(C.getStone())) {
                        game.getCurrentPlayerTurn().setSelectedStone(C.getStone());
                        break;
                    } else {
                        //hier bereken algoritme
                        PlayerInGame player = game.getCurrentPlayerTurn();
                        if (player.calculate(C)) {
                            player.deSelectAllStones();

                            game.switchTurns();
                            lbNotify.setText("succes");

                        } else {
                            lbNotify.setText("failure");
                        }
                    }
                }
            }
        }
        //At the bottom it stays
        update();
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

        drawCanvas();

        String opponent = "";
        for (PlayerInGame P : game.getPlayers()) {
            if (P.getSide() == Side.Black) {
                opponent = P.getName();
                break;
            }
        }

        lbOpponentName.setText("Opponent :" + opponent);
        lbName.setText("Name: " + PlayerSingleton.getPlayer().getName());

        lbTurn.setText("Current turn: " + game.getCurrentPlayerTurn().getName());

        if (game.checkGameWon()) {
            lbTurn.setText("");
            lbWinner.setText("Winner is: " + game.getWinner().getName());
            cvBoard.setDisable(true);
        }
    }

}
