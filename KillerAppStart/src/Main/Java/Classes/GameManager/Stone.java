package Classes.GameManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Stone {

    /**
     *  Fields
     */
    private Cell cell;

    private boolean dam;
    private int width;
    private int height;
    private Color color;
    private final Color selectedColor;
    private final Color originalColor;
    private Point coordinate;
    private Point coordinateNext;
    private Point anchor;

    /**
     *  Properties
     */
    public Cell getCell() {return cell;}
    public void setCell(Cell cell) {
        this.cell = cell;
        this.coordinate = cell.getCoordinate();
        this.anchor.x = cell.getAnchor().x + 10;
        this.anchor.y = cell.getAnchor().y + 10;
    }

    public boolean isDam() {return dam;}
    public void setDam(boolean dam) {this.dam = dam;}

    public int getWidth() {return width;}
    public void setWidth(int width) {this.width = width;}

    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}

    public Color getColor() {return color;}
    public void setColor(Color color) {this.color = color;}

    public Point getCoordinate() {return coordinate;}
    public void setCoordinate(Point coordinate) {this.coordinate = coordinate;}

    public Point getCoordinateNext() {return coordinateNext;}
    public void setCoordinateNext(Point coordinateNext) {this.coordinateNext = coordinateNext;}

    public Point getAnchor() {return anchor;}
    public void setAnchor(Point anchor) {this.anchor = anchor;}

    /**
     *  Constructor
     */
    public Stone(Color color, Point anchor, Point coordinate) {
        this.dam = false;
        this.height = 50;
        this.width = 50;
        this.color = color;
        this.selectedColor = Color.RED;
        this.originalColor = color;
        this.coordinate = coordinate;
        this.coordinateNext = null;
        this.anchor = anchor;
    }


    /**
     *  Methods
     */
    public void move(Cell nextLocation){

    }

    public Stone checkSelected() {
        if(color == selectedColor)
        {
            return this;
        }
        return null;
    }

    public void setDeselected()
    {
        color = originalColor;
    }

    public void setSelected(){
        if(color == originalColor)
        {
            color = selectedColor;
        }
        else
        {
            color = originalColor;
        }
    }

    public void upgradeDam()
    {
        this.dam = true;
        // plus a visual adjustment
    }

    public void move()
    {

    }

    public void draw(GraphicsContext gc)
    {
        gc.setFill(color);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(3);
        gc.fillOval(anchor.getX(), anchor.getY(), width, height);
        gc.strokeOval(anchor.getX(), anchor.getY(), width, height);
    }
}
