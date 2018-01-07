package Classes.GameManager;

import Classes.ClientApplication.Player;
import Database.PlayerManager;
import Enums.Side;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Game {

    /**
     * Fields
     */
    private static Random random = new Random();

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

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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
    }

    public Game(String name) {
        this.name = name;
    }


    /**
     * Methods
     */
    public boolean checkGameWon() {
        PlayerInGame loser = null;
        for (PlayerInGame P : players) {
            if (P.getStones().size() == 0) {
                loser = P;
            }
        }

        for (PlayerInGame P : players) {
            if (P != loser && loser != null) {
                this.winner = P;
                return true;
            }
        }

        this.winner = null;
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
        for (PlayerInGame P : players) {
            if (P != currentPlayerTurn) {
                currentPlayerTurn = P;
                break;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        this.board.Draw(gc);
        for (PlayerInGame P : this.players) {
            P.drawStones(gc);
        }
    }

}
