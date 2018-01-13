package ClassesTest.LobbyManagerTest;

import classes.LobbyManager.LobbyPlayer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gebruiker on 10-1-2018.
 */
public class LobbyPlayerTest {

    @Test
    public void ConstructorTest() throws Exception
    {
        LobbyPlayer LP = new LobbyPlayer();
        LobbyPlayer LP2 = new LobbyPlayer();

        Assert.assertTrue(LP.equals(LP));
        Assert.assertFalse(LP.equals(""));
    }

    @Test
    public void ConstructorTest_2() throws Exception
    {
        String name = "";
        int id = 9;

        LobbyPlayer LP = new LobbyPlayer(id, name);
        LobbyPlayer LP2 = new LobbyPlayer(id, "name");
        LobbyPlayer LP3 = new LobbyPlayer(0, name);
        LobbyPlayer LP4 = new LobbyPlayer(id, name);
        LobbyPlayer LP5 = null;

        Assert.assertFalse(LP.equals(LP5));
        Assert.assertTrue(LP.equals(LP));
        Assert.assertFalse(LP.equals(LP2));
        Assert.assertFalse(LP.equals(LP3));
        Assert.assertTrue(LP.equals(LP4));
        Assert.assertFalse(LP.equals(""));

        Assert.assertEquals(LP.getUniqueId(), id);
        Assert.assertEquals(LP.getName(), name);
        LP.setName("");
        Assert.assertEquals(LP.getName(), "");

        LP.setHost(true);
        Assert.assertEquals(LP.isHost(), true);
    }
}
