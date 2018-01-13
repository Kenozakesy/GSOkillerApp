package ClassesTest.GameManagerTest;


import classes.gamemanager.Cell;
import classes.gamemanager.Stone;

import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;


/**
 * Created by Gebruiker on 10-1-2018.
 */
public class StoneTest {

    @Test
    public void constructorTest() throws Exception
    {
        Point coordinate = new Point(1,2);
        Point anchor = new Point(3,4);
        Color black = Color.BLACK;
        Color white = Color.WHITE;

        Stone stone = new Stone(white, anchor,coordinate);
        Stone stone2 = new Stone(black, anchor,coordinate);

        stone.setColor();
        stone2.setColor();

        Assert.assertEquals(stone.getColor(), white);
        Assert.assertEquals(stone.getCoordinate(), coordinate);
        Assert.assertEquals(stone.getAnchor(), anchor);

        Assert.assertEquals(stone2.getColor(), black);
        Assert.assertEquals(stone2.getCoordinate(), coordinate);
        Assert.assertEquals(stone2.getAnchor(), anchor);

    }

    @Test
    public void selectedTest() throws Exception
    {
        Point coordinate = new Point(1,2);
        Point anchor = new Point(3,4);
        Color black = Color.BLACK;
        Color white = Color.WHITE;
        Color red = Color.RED;

        Stone stone = new Stone(white, anchor,coordinate);

        stone.setSelected();
        Assert.assertEquals(stone.getColor(), red);
        stone.setColor();
        stone.setColor();

        Stone t = stone.checkSelected();
        Assert.assertEquals(stone, t);

        stone.setSelected();
        Assert.assertEquals(stone.getColor(), white);

        Stone t2 = stone.checkSelected();
        Assert.assertNull(t2);

        stone.setSelected();
        Assert.assertEquals(stone.getColor(), red);

        stone.setDeselected();
        Assert.assertEquals(stone.getColor(), white);

    }

    @Test
    public void setgetCellTest() throws Exception
    {
        Point coordinate = new Point(1,2);
        Point anchor = new Point(3,4);
        Color white = Color.WHITE;

        Stone stone = new Stone(white, anchor,coordinate);
        Cell cell = new Cell(white, coordinate, anchor);

        stone.setCell(cell);
        Assert.assertEquals(cell, stone.getCell());

    }
}
