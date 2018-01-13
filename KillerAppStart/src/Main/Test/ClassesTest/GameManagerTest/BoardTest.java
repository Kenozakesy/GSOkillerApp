package ClassesTest.GameManagerTest;

import classes.gamemanager.Board;
import classes.gamemanager.Cell;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Gebruiker on 10-1-2018.
 */
public class BoardTest {

    @Test
    public void ConstructorTest() throws Exception
    {
        Board board = new Board();
        ArrayList<Cell> cells = board.getCells();
        Assert.assertEquals(cells, board.getCells());
    }

    @Test
    public void setColorsTest() throws Exception
    {
        Board board = new Board();
        ArrayList<Cell> cells = board.getCells();

        board.setColors();

        Assert.assertEquals(cells, board.getCells());
    }

}
