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
public class CellTest {

    @Test
    public void ConstructorTest() throws Exception
    {
        Point coordinate = new Point(1,2);
        Point anchor = new Point(3,4);
        Color color = Color.BLACK;

        Cell cell = new Cell(color, coordinate, anchor);

        Assert.assertEquals(cell.getAnchor(), anchor);
        Assert.assertEquals(cell.getCoordinate(), coordinate);
        Assert.assertEquals(cell.getColor(), color);
        Assert.assertEquals(cell.getHeight(), 70);
        Assert.assertEquals(cell.getWidth(), 70);
    }

    @Test
    public void setColorTest() throws Exception
    {
        Point coordinate = new Point(1,2);
        Point anchor = new Point(3,4);
        Color black = Color.BLACK;
        Color white = Color.WHITE;

        Cell cell = new Cell(black, coordinate, anchor);
        cell.removeStone();

        cell.setColor();

        Cell cell2 = new Cell(white, coordinate, anchor);
        cell2.setColor();

        Assert.assertEquals(cell2.getAnchor(), anchor);
        Assert.assertEquals(cell2.getCoordinate(), coordinate);
        Assert.assertEquals(cell2.getColor(), white);

        Assert.assertEquals(cell.getAnchor(), anchor);
        Assert.assertEquals(cell.getCoordinate(), coordinate);
        Assert.assertEquals(cell.getColor(), black);

    }

    @Test
    public void setgetStoneTest() throws Exception
    {
        Point coordinate = new Point(1,2);
        Point anchor = new Point(3,4);
        Color black = Color.BLACK;
        Color white = Color.WHITE;

        Stone stone = new Stone(black, anchor, coordinate);
        Cell cell = new Cell(black, coordinate, anchor);

        cell.setStone(stone);

        Assert.assertEquals(stone, cell.getStone());

    }
}
