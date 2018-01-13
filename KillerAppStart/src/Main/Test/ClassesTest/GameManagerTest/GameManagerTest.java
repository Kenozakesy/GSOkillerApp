package ClassesTest.GameManagerTest;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.gamemanager.GameManager;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 10-1-2018.
 */
public class GameManagerTest {

    @Test
    public void ConstructorTest() throws Exception
    {
        Assert.assertNotNull(GameManager.getInstance());

        List<Player> players = new ArrayList<>();
        Player player = new Player(1, "name");
        Player player2 = new Player(2, "name");
        players.add(player);
        players.add(player2);

        GameManager.getInstance().startGame(players);

        Game game = GameManager.getInstance().getGame(player);
        Assert.assertNotNull(game);
    }

}
