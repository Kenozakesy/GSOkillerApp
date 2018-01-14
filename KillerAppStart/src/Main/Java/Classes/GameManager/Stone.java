package classes.gamemanager;

import enums.ColorStatic;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Stone implements Serializable {

    /**
     * Fields
     */
    private Cell cell;

    private int width;
    private int height;
    private transient Color color;
    private transient Color selectedColor;
    private transient Color originalColor;
    private Point coordinate;
    private Point anchor;

    private ColorStatic colorStatic;
    private boolean selected;

    /**
     * Properties
     */
    public Cell getCell() {
        return cell;
    }
    public void setCell(Cell cell) {
        this.cell = cell;
        this.coordinate = cell.getCoordinate();
        this.anchor.x = cell.getAnchor().x + 10;
        this.anchor.y = cell.getAnchor().y + 10;
    }
    public Color getColor() {
        return color;
    }

    public Point getCoordinate() {
        return coordinate;
    }
    public Point getAnchor() {
        return anchor;
    }

    /**
     * Constructor
     */
    public Stone(Color color, Point anchor, Point coordinate) {
        this.selected = false;
        this.height = 50;
        this.width = 50;
        this.color = color;
        this.selectedColor = Color.RED;
        this.originalColor = color;
        this.coordinate = coordinate;
        this.anchor = anchor;

        if (color == Color.BLACK) {
            colorStatic = ColorStatic.BLACK;
        } else {
            colorStatic = ColorStatic.WHITE;
        }
    }

    /**
     * Methods
     */
    public void setColor() {
        this.selectedColor = Color.RED;

        if (colorStatic == ColorStatic.BLACK) {
            color = Color.BLACK;
            this.originalColor = Color.BLACK;
        } else if (colorStatic == ColorStatic.WHITE) {
            color = Color.WHITE;
            this.originalColor = Color.WHITE;
        }

        if (selected) {
            this.color = this.selectedColor;
        } else {
            this.color = this.originalColor;
        }
    }

    public Stone checkSelected() {
        if (color == selectedColor) {
            return this;
        }
        return null;
    }

    public void setDeselected() {
        color = originalColor;
        selected = false;
    }

    public void setSelected() {
        if (color == originalColor) {
            color = selectedColor;
            selected = true;
        } else {
            color = originalColor;
            selected = false;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(3);
        gc.fillOval(anchor.getX(), anchor.getY(), width, height);
        gc.strokeOval(anchor.getX(), anchor.getY(), width, height);
    }
}
