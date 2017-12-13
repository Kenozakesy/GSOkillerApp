package Classes.GameManager;

import Enums.Side;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

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
    private ArrayList<PlayerInGame> players;
    private String name;

    private PlayerInGame currentPlayerTurn;
    private PlayerInGame winner;

    /**
     *  Properties
     */
    public String getName() {return name;}

    public int getTurn() {return turn;}
    public void setTurn(int turn) {this.turn = turn;}

    public Board getBoard() {return board;}
    public void setBoard(Board board) {this.board = board;}

    public PlayerInGame getCurrentPlayerTurn() {return currentPlayerTurn;}

    public ArrayList<PlayerInGame> getPlayers() {return players;}

    public PlayerInGame getWinner() {return winner;}

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
        PlayerInGame loser = null;
        for (PlayerInGame P: players)
        {
            if(P.getStones().size() == 0)
            {
                loser = P;
            }
        }

        for (PlayerInGame P: players) {
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

        PlayerInGame player = new PlayerInGame("player", Side.White, this);
        PlayerInGame computer = new PlayerInGame("com", Side.Black, this);

        players.add(player);
        players.add(computer);
    }

    private void generateBoard()
    {
        this.board = new Board();
    }

    public void switchTurns()
    {
        for (PlayerInGame P: players) {
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
        for (PlayerInGame P:this.players)
        {
            P.drawStones(gc);
        }
    }

}
