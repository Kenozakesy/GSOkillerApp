package ClassesTest.ClientApplicationTest;

import classes.clientapplication.Player;
import classes.lobbymanager.LobbyPlayer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gebruiker on 10-1-2018.
 */
public class PlayerTest {

    @Test
    public void ConstructorTest_1() throws Exception
    {
        Player p = new Player();
        Player q;
        q = p;
        Assert.assertEquals(p, q);
    }

    @Test
    public void ConstructorTest_2() throws Exception
    {
        Player p = new Player(1, "test");
        Assert.assertEquals(p.getName(), "test");
        Assert.assertEquals(p.getUniqueId(), 1);
    }

    @Test
    public void getLobbyPlayer() throws Exception
    {
        int id = 1;
        String name = "test";

        Player p = new Player(id, name);
        LobbyPlayer LP = new LobbyPlayer(id, name);

        LobbyPlayer LP2 = p.getLobbyPlayer();

        Assert.assertTrue(LP.equals(LP2));
    }
}
