package classes.gamemanager;

import enums.Side;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class PlayerInGame implements Serializable {

    /**
     * Fields
     */
    private static Side staticSide = Side.BLACK;
    private ArrayList<Stone> stones;
    private Game game;

    private int uniqueId;
    private String name;
    private Side side;

    /**
     * Properties
     */
    public int getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Stone> getStones() {
        return stones;
    }

    public Side getSide() {
        return side;
    }

    /**
     * Constructor
     */
    public PlayerInGame(int uniqueId, String name, Game game) {
        this.stones = new ArrayList<>();
        this.uniqueId = uniqueId;
        this.game = game;
        this.name = name;

        side = staticSide;
        staticUpdate();


        createStones();
    }

    private static void staticUpdate()
    {
        if(staticSide == Side.BLACK)
        {
            staticSide = Side.WHITE;
        }
        else
        {
            staticSide = Side.BLACK;
        }
    }

    public PlayerInGame(int uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    /**
     * Methods
     */

    public void setColors() {
        for (Stone s : stones) {
            s.setColor();
        }
    }

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

    public void createStones() {
        if (side == Side.BLACK) {

            int odd = 0;
            for (Cell C : game.getBoard().getCells()) {
                odd++;
                if (odd > 24) {
                    break;
                }
                else if (C.getColor() == Color.BLACK) {
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
                else if (C.getColor() == Color.BLACK && odd > 40) {
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

    private void removeStone(Stone stone) {
        stones.remove(stone);
    }

    //Hier komen alle algoritmes
    public boolean calculate(Cell destination) {
        //gets current selected stone
        Stone stone = getStoneSelected();
        if (stone == null) {
            return false;
        }
        //gets the cell the stone is at
        Cell sl = stone.getCell();

        if (destination.getStone() != null) {
            return false;
        }

        switch (this.side) {
            case WHITE:
                Color black = Color.BLACK;
                if ( //move
                        (sl.getCoordinate().x == destination.getCoordinate().x - 1 || sl.getCoordinate().x == destination.getCoordinate().x + 1) &&
                                sl.getCoordinate().y == destination.getCoordinate().y + 1
                        ) {

                    moveStone(stone, destination, sl);

                    return true;
                } else if (
                        ((sl.getCoordinate().x == destination.getCoordinate().x - 2 || sl.getCoordinate().x == destination.getCoordinate().x + 2) &&
                                (sl.getCoordinate().y == destination.getCoordinate().y - 2 || sl.getCoordinate().y == destination.getCoordinate().y + 2)
                        ) && hitStoneCheck(sl, destination, stone, black)) {
                    return true;
                }

                break;
            case BLACK:
                Color white = Color.WHITE;
                if ( //move
                        (sl.getCoordinate().x == destination.getCoordinate().x - 1 || sl.getCoordinate().x == destination.getCoordinate().x + 1) &&
                                sl.getCoordinate().y == destination.getCoordinate().y - 1
                        ) {

                    moveStone(stone, destination, sl);

                    return true;
                } else if ( //hit
                        ((sl.getCoordinate().x == destination.getCoordinate().x - 2 || sl.getCoordinate().x == destination.getCoordinate().x + 2) &&
                                (sl.getCoordinate().y == destination.getCoordinate().y - 2 || sl.getCoordinate().y == destination.getCoordinate().y + 2)
                        ) && hitStoneCheck(sl, destination, stone, white)) {

                    return true;
                }
                break;
        }

        return false;
    }

    private boolean hitStoneCheck(Cell sl, Cell destination, Stone stone, Color color) {
        //hit
        int midX = (sl.getCoordinate().x + destination.getCoordinate().x) / 2;
        int midY = (sl.getCoordinate().y + destination.getCoordinate().y) / 2;
        Point point = new Point(midX, midY);
        for (Cell C : game.getBoard().getCells()) {
            if (C.getCoordinate().equals(point) && C.getStone() != null && C.getStone().getColor() == color) {
                Stone removable = C.getStone();
                hitStone(stone, removable, destination, sl);
                return true;

            }
        }
        return false;

    }

    private void moveStone(Stone stone, Cell destination, Cell sl) {
        stone.setCell(destination);
        destination.setStone(stone);
        sl.removeStone();
    }

    private void hitStone(Stone stone, Stone removable, Cell destination, Cell sl) {
        stone.setCell(destination);
        destination.setStone(stone);
        sl.removeStone();

        removable.getCell().removeStone();

        for (PlayerInGame P : game.getPlayers()) {
            if (P != this) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PlayerInGame)) {
            return false;
        }

        PlayerInGame other = (PlayerInGame) obj;

        if (!other.name.equals(name) || other.uniqueId != uniqueId) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }
}
