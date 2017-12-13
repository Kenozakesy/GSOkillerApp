package DatabaseTest;

import Classes.GameManager.Game;
import Database.DatabaseConnection;
import Database.GameManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class GameTest {

    @Test
    public void DatabaseConnectionTest() throws Exception
    {
        List<Game> games = GameManager.getName();
        Assert.assertTrue(games.size() >= 2);

        for (Game G: games) {
            System.out.println(G.getName());
        }
    }
}