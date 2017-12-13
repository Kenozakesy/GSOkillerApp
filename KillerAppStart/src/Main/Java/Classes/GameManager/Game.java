package Classes.GameManager;

import Enums.Side;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

import static Enums.Side.White;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Game {

    /**
     *  Fields
     */
    private static Random random = new Random();

    private int turn;
    private Board board;
    private ArrayList<Player> players;
    private String name;

    private Player currentPlayerTurn;
    private Player winner;

    /**
     *  Properties
     */
    public String getName() {return name;}

    public int getTurn() {return turn;}
    public void setTurn(int turn) {this.turn = turn;}

    public Board getBoard() {return board;}
    public void setBoard(Board board) {this.board = board;}

    public Player getCurrentPlayerTurn() {return currentPlayerTurn;}

    public ArrayList<Player> getPlayers() {return players;}

    public Player getWinner() {return winner;}

    /**
     *  Constructor
     */
    public Game()
    {
        generateBoard();
        addPlayers();
        switchTurns();
        this.winner = null;
    }

    public Game(String name)
    {
        this.name = name;
    }


    /**
     *  Methods
     */
    public boolean checkGameWon()
    {
        Player loser = null;
        for (Player P: players)
        {
            if(P.getStones().size() == 0)
            {
                loser = P;
            }
        }

        for (Player P: players) {
            if(P != loser && loser != null)
            {
                this.winner = P;
                return true;
            }
        }

        this.winner = null;
        return false;
    }

    //for now only one player and computer AI
    private void addPlayers()
    {
        players = new ArrayList<>();

        Player player = new Player("player", Side.White, this);
        Player computer = new Player("com", Side.Black, this);

        players.add(player);
        players.add(computer);
    }

    private void generateBoard()
    {
        this.board = new Board();
    }

    public void switchTurns()
    {
        for (Player P: players) {
            if(P != currentPlayerTurn)
            {
                currentPlayerTurn = P;
                break;
            }
        }
    }

    public void draw(GraphicsContext gc)
    {
        this.board.Draw(gc);
        for (Player P:this.players)
        {
            P.drawStones(gc);
        }
    }

}
