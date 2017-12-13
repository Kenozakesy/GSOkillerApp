package Classes.GameManager;

import Enums.Side;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class PlayerInGame {

    /**
     *  Fields
     */
    private ArrayList<Stone> stones;
    private Game game;

    private int uniqueId;
    private String name;
    private Side side;



    /**
     *  Properties
     */
    public int getUniqueId() {return uniqueId;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Side getSide() {
        return side;
    }
    public void setSide(Side side) {
        this.side = side;
    }

    public ArrayList<Stone> getStones() {
        return stones;
    }

    /**
     *  Constructor
     */
    public PlayerInGame(String name, Side side, Game game) {
        this.stones = new ArrayList<>();
        this.game = game;

        this.name = name;
        this.side = side;

        createStones();
    }

    /**
     *  Methods
     */
    public void setSelectedStone(Stone stone) {
        for (Stone S : stones) {
            if (S != stone) {
                S.setDeselected();
            }
        }
        stone.setSelected();
    }

    public void deSelectAllStones() {
        for (Stone S : stones) {
            S.setDeselected();
        }
    }

    public Stone getStoneSelected() {
        for (Stone S : stones) {
            Stone stone = S.checkSelected();
            if (stone != null) {
                return stone;
            }
        }
        return null;
    }

    private void createStones() {
        if (side == Side.Black) {

            int odd = 0;
            for (Cell C : game.getBoard().getCells()) {
                odd++;
                if (odd > 24) {
                    break;
                }
                if (C.getColor() == Color.BLACK) {
                    Point anchor = new Point(C.getAnchor().x + 10, C.getAnchor().y + 10);
                    Point coordinate = new Point(C.getCoordinate().x, C.getCoordinate().y);
                    Stone stone = new Stone(Color.BLACK, anchor, coordinate);

                    stone.setCell(C);
                    C.setStone(stone);

                    stones.add(stone);
                }
            }
        } else {
            int odd = 0;
            for (Cell C : game.getBoard().getCells()) {
                odd++;
                if (odd > 64) {
                    break;
                }
                if (C.getColor() == Color.BLACK && odd > 40) {
                    Point anchor = new Point(C.getAnchor().x + 10, C.getAnchor().y + 10);
                    Point coordinate = new Point(C.getCoordinate().x, C.getCoordinate().y);
                    Stone stone = new Stone(Color.WHITE, anchor, coordinate);

                    stone.setCell(C);
                    C.setStone(stone);

                    stones.add(stone);
                }
            }
        }
    }

    private void removeStone(Stone stone)
    {
        stones.remove(stone);
    }

    //Hier komen alle algoritmes
    public boolean calculate(Cell destination) {
        //gets current selected stone
        Stone stone = getStoneSelected();
        if(stone == null)
        {
            return false;
        }
        //gets the cell the stone is at
        Cell SL = stone.getCell();

        if (destination.getStone() != null) {
            return false;
        }

        switch (this.side) {
            case White:
                Color black = Color.BLACK;
                if ( //move
                        (SL.getCoordinate().x == destination.getCoordinate().x - 1 || SL.getCoordinate().x == destination.getCoordinate().x + 1) &&
                                SL.getCoordinate().y == destination.getCoordinate().y + 1
                        ) {
                    //move
                    System.out.println("moved");

                    moveStone(stone, destination, SL);

                    return true;
                } else if (
                        (SL.getCoordinate().x == destination.getCoordinate().x - 2 || SL.getCoordinate().x == destination.getCoordinate().x + 2) &&
                                (SL.getCoordinate().y == destination.getCoordinate().y - 2 || SL.getCoordinate().y == destination.getCoordinate().y + 2)
                        )
                {
                    if(hitStoneCheck(SL, destination, stone, black))
                    {
                        return true;
                    }
                }

                break;
            case Black:
                Color white = Color.WHITE;
                if ( //move
                        (SL.getCoordinate().x == destination.getCoordinate().x - 1 || SL.getCoordinate().x == destination.getCoordinate().x + 1) &&
                                SL.getCoordinate().y == destination.getCoordinate().y - 1
                        ) {
                    //move
                    System.out.println("moved");

                    moveStone(stone, destination, SL);

                    return true;
                }
                else if ( //hit
                        (SL.getCoordinate().x == destination.getCoordinate().x - 2 || SL.getCoordinate().x == destination.getCoordinate().x + 2) &&
                                (SL.getCoordinate().y == destination.getCoordinate().y - 2 || SL.getCoordinate().y == destination.getCoordinate().y + 2)
                        ) {
                    if(hitStoneCheck(SL, destination, stone, white))
                    {
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    private boolean hitStoneCheck(Cell SL, Cell destination, Stone stone,  Color color)
    {
        //hit
        int midX = (SL.getCoordinate().x + destination.getCoordinate().x) / 2;
        int midY = (SL.getCoordinate().y + destination.getCoordinate().y) / 2;
        Point point = new Point(midX, midY);
        for (Cell C : game.getBoard().getCells()) {
            if (C.getCoordinate().equals(point) && C.getStone() != null) {
                if (C.getStone().getColor() == color) {
                    Stone removable = C.getStone();
                    hitStone(stone, removable, destination,SL);
                    return  true;
                }
            }
        }
        return false;

    }

    private void moveStone(Stone stone, Cell destination, Cell SL)
    {
        stone.setCell(destination);
        destination.setStone(stone);
        SL.removeStone();
    }

    private void hitStone(Stone stone,Stone removable, Cell destination, Cell SL)
    {
        stone.setCell(destination);
        destination.setStone(stone);
        SL.removeStone();

        removable.getCell().removeStone();

        for (PlayerInGame P: game.getPlayers())
        {
            if(P != this)
            {
                P.removeStone(removable);
                break;
            }
        }
    }

    public void drawStones(GraphicsContext gc) {
        for (Stone S : stones) {
            S.draw(gc);
        }
    }
}
