package classes.gamemanager;

import classes.clientapplication.Player;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Game implements Serializable{

    /**
     * Fields
     */
    private int turn;
    private Board board;
    private List<PlayerInGame> players;
    private String name;

    private PlayerInGame currentPlayerTurn;
    private PlayerInGame winner;

    /**
     * Properties
     */
    public String getName() {
        return name;
    }

    public int getTurn() {
        return turn;
    }

    public Board getBoard() {
        return board;
    }


    public PlayerInGame getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }
    public List<PlayerInGame> getPlayers() {
        return players;
    }
    public PlayerInGame getWinner() {
        return winner;
    }

    /**
     * Constructor
     */
    public Game() {
        generateBoard();
        this.winner = null;
        this.turn = 0;
    }

    public Game(String name) {
        this.name = name;
    }


    /**
     * Methods
     */
    public void setColors()
    {
        board.setColors();
        for (PlayerInGame p : players) {
            p.setColors();
        }
    }

    public boolean checkGameWon() {
        PlayerInGame loser = null;
        for (PlayerInGame P : players) {
            if (P.getStones().isEmpty()) {
                loser = P;
            }
        }

        for (PlayerInGame P : players) {
            if (P != loser && loser != null) {
                this.winner = P;
                return true;
            }
        }
        return false;
    }

    //for now only one player and computer AI
    public void addPlayers(List<Player> playerlist) {
        //instanieert lijst
        players = new ArrayList<>();

        for (Player p : playerlist) {
            PlayerInGame newPlayer = new PlayerInGame(p.getUniqueId(), p.getName(), this);
            players.add(newPlayer);
        }

        switchTurns();
    }

    private void generateBoard() {
        this.board = new Board();
    }

    public void switchTurns() {
        this.turn++;
        for (PlayerInGame P : players) {
            if (P != currentPlayerTurn) {
                currentPlayerTurn = P;
                break;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        this.board.draw(gc);
        for (PlayerInGame P : this.players) {
            P.drawStones(gc);
        }
    }

}
