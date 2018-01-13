package classes.gamemanager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Board implements Serializable {

    /**
     *  Fields
     */
    private ArrayList<Cell> cells;
    private int height = 8;
    private int width = 8;

    /**
     *  Properties
     */
    public List<Cell> getCells() {return cells;}

    /**
     *  Constructor
     */
    public Board() {
        cells = new ArrayList<>();
        generateCells();
    }


    /**
     *  Methods
     */
    public void setColors()
    {
        for (Cell c : cells) {
           c.setColor();
        }
    }

    public void generateCells() {
        boolean checkColor = true;
        for (int i = 0; i < height; i++) {
            if (checkColor) {
                checkColor = false;
            } else {
                checkColor = true;
            }
            for (int x = 0; x < width; x++) {

                //create new color
                Color color;
                if (checkColor) {
                    color = Color.BLACK;
                    checkColor = false;
                } else {
                    color = Color.WHITE;
                    checkColor = true;
                }

                Point coordinate = new Point();
                Point anchor = new Point();

                anchor.setLocation(x * 70, i * 70);
                coordinate.setLocation(x + 1, i + 1);

                Cell cell = new Cell(color, coordinate, anchor);
                cells.add(cell);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        for (Cell C : cells) {
            C.draw(gc);
        }
    }

}
