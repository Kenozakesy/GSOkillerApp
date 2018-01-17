package ClassesTest.GameManagerTest;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.gamemanager.PlayerInGame;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gebruiker on 10-1-2018.
 */
public class GameTest {

    @Test
    public void ConstructorTest() throws Exception
    {
        String name = "name";
        Game game = new Game(name);
        Game game2 = new Game(1 ,name, 1);
        Game game3 = new Game(1 ,name);

        List playlist = new ArrayList();
        playlist.add(new PlayerInGame(1, "NAME"));
        game3.addPlayersDatabase(playlist);

        Assert.assertEquals(game.getName(), name);

        game2.increaseTurn();
        Assert.assertEquals(game2.getTurn(), 2);
        game2.decreaseTurn();
        Assert.assertEquals(game2.getTurn(), 1);

    }

    @Test
    public void addPlayersTest() throws Exception
    {
        String name = "name";
        int id = 1;
        int id2 = 2;

        Game game = new Game();


        List<Player> players = new ArrayList<>();
        players.add(new Player(id, name));
        players.add(new Player(id2, name));

        game.addPlayers(players);
        game.setColors();
        Assert.assertEquals(game.getTurn(), 1);
    }

    @Test
    public void getPlayersTest() throws Exception
    {
        String name = "name";
        int id = 1;
        int id2 = 2;

        Game game = new Game();

        List<Player> players = new ArrayList<>();
        players.add(new Player(id, name));
        players.add(new Player(id2, name));

        game.addPlayers(players);
        Assert.assertEquals(game.getTurn(), 1);

        PlayerInGame pig = new PlayerInGame(id, name);

        Assert.assertEquals(game.getPlayers().size(), players.size());
        Assert.assertTrue(pig.equals(game.getCurrentPlayerTurn()));
    }

    @Test
    public void checkGameWonTest() throws Exception
    {
        String name = "name";
        int id = 1;
        int id2 = 2;

        Game game = new Game();

        List<Player> players = new ArrayList<>();
        players.add(new Player(id, name));
        players.add(new Player(id2, name));

        game.addPlayers(players);
        Assert.assertEquals(game.getTurn(), 1);

        Assert.assertFalse(game.checkGameWon());
        Assert.assertNull(game.getWinner());

        String gameid = "ID: 0, name: null";
        Assert.assertEquals(gameid, game.toString());
    }
}
