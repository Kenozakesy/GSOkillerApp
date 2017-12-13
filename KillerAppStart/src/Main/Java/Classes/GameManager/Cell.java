package Classes.GameManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Cell {

    /**
     *  Fields
     */
    private Stone stone;

    private int width;
    private int height;
    private Point coordinate;
    private Point anchor;
    private javafx.scene.paint.Color color;

    /**
     *  Properties
     */
    public int getWidth() {return width;}
    public void setWidth(int width) {this.width = width;}

    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}

    public Point getCoordinate() {return coordinate;}
    public void setCoordinate(Point coordinate) {this.coordinate = coordinate;}

    public Point getAnchor() { return anchor;}
    public void setAnchor(Point anchor) {this.anchor = anchor;}

    public javafx.scene.paint.Color getColor() {return color;}
    public void setColor(javafx.scene.paint.Color color) {this.color = color;}

    public Stone getStone() {return stone;}
    public void setStone(Stone stone) {this.stone = stone;}

    /**
     *  Constructor
     */
    public Cell(javafx.scene.paint.Color color, Point coordinate, Point anchor)
    {
        this.width = 70;
        this.height = 70;
        this.color = color;
        this.coordinate = coordinate;
        this.anchor = anchor;
    }

    /**
     *  Methods
     */
    //Methods
    public void removeStone()
    {
        this.stone = null;
    }

    public void draw(GraphicsContext gc)
    {
        gc.setFill(color);
        gc.fillRect(anchor.getX(), anchor.getY(), width, height);
    }

}
