package ClassesTest.DatabaseTest;

import ClassesTest.SingletonTest.PlayerSingletonTest;
import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.singletons.PlayerSingleton;
import database.DatabaseGetGame;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Gebruiker on 17-1-2018.
 */
public class DatabaseGetGametest {

    @Test
    public void methodtest()
    {
        PlayerSingleton.login("koen", "koen123");
        Player player = PlayerSingleton.getPlayer();

        List<Game> games = DatabaseGetGame.getGames(player);
        Assert.assertTrue(games.size() >= 0);

        Game game = DatabaseGetGame.getGameStateTurn(1, 0);
        Assert.assertNotNull(game);

    }
}
