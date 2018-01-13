package ClassesTest.GameManagerTest;

import classes.clientapplication.Player;
import org.junit.Assert;
import org.junit.Test;
import classes.gamemanager.*;

import java.util.ArrayList;


/**
 * Created by Gebruiker on 10-1-2018.
 */
public class PlayerInGameTest {

    @Test
    public void constructorTest() throws Exception
    {
        int id = 0;
        String name = "test";
        Game game = new Game();

        PlayerInGame pig = new PlayerInGame(id, name);

        Assert.assertEquals(pig.getName(), name);
        Assert.assertEquals(pig.getUniqueId(), id);

        PlayerInGame pig2 = new PlayerInGame(id, name, game);
        PlayerInGame pig3 = new PlayerInGame(id, name, game);

        Assert.assertEquals(pig2.getName(), name);
        Assert.assertEquals(pig3.getUniqueId(), id);

        pig.setName("akira");
        Assert.assertEquals(pig.getName(), "akira");

    }

    @Test
    public void stonetest() throws Exception
    {
        int id = 0;
        String name = "test";
        Game game = new Game();

        PlayerInGame pig = new PlayerInGame(id, name, game);
        pig.setColors();

        ArrayList<Stone> stones = pig.getStones();
        Stone stone = stones.get(1);

        pig.setSelectedStone(stone);
        Stone stoneSel =  pig.getStoneSelected();
        Assert.assertEquals(stoneSel, stone);

        pig.deSelectAllStones();
        Assert.assertNull(pig.getStoneSelected());

    }

    @Test
    public void equalsMethode() throws Exception
    {
        int id = 0;
        String name = "test";
        Game game = new Game();
        Player player = null;

        PlayerInGame pig = new PlayerInGame(id, name);
        PlayerInGame pig2 = new PlayerInGame(id, name);
        PlayerInGame pigfalse1 = new PlayerInGame(id, "");
        PlayerInGame pigfalse2 = new PlayerInGame(1, name);

        Assert.assertTrue(pig.equals(pig2));

        Assert.assertTrue(pig.equals(pig));
        Assert.assertFalse(pig.equals(player));
        Assert.assertFalse(pig.equals(game));
        Assert.assertFalse(pig.equals(pigfalse1));
        Assert.assertFalse(pig.equals(pigfalse2));


    }

    @Test
    public void calculateTest() throws Exception
    {
        int id = 0;
        String name = "test";
        Game game = new Game();
        Player player = null;

        PlayerInGame pig = new PlayerInGame(id, name, game);


        ArrayList<Stone> stones = pig.getStones();
        Stone stone = stones.get(1);

        Assert.assertFalse(pig.calculate(game.getBoard().getCells().get(1)));




    }
}
