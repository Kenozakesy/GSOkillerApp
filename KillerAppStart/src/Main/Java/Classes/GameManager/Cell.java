package classes.gamemanager;

import Enums.Side;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Cell implements Serializable {

    /**
     * Fields
     */
    private Stone stone;

    private int width;
    private int height;
    private Point coordinate;
    private Point anchor;

    private transient Color color;
    private Side colorStatic;

    /**
     * Properties
     */
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Point getCoordinate() {
        return coordinate;
    }
    public Point getAnchor() {
        return anchor;
    }
    public Color getColor() {
        return color;
    }
    public Stone getStone() {
        return stone;
    }
    public void setStone(Stone stone) {
        this.stone = stone;
    }

    /**
     * Constructor
     */
    public Cell(Color color, Point coordinate, Point anchor) {
        this.width = 70;
        this.height = 70;
        this.color = color;
        this.coordinate = coordinate;
        this.anchor = anchor;
        if (color == Color.BLACK) {
            colorStatic = Side.Black;
        } else {
            colorStatic = Side.White;
        }
    }

    /**
     * Methods
     */
    public void setColor() {
        if (colorStatic == Side.Black) {
            color = Color.BLACK;
        } else {
            color = Color.WHITE;
        }
    }

    //Methods
    public void removeStone() {
        this.stone = null;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(anchor.getX(), anchor.getY(), width, height);
    }

}
