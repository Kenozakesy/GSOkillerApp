package DatabaseTest;

import Classes.ClientApplication.Player;
import Classes.GameManager.PlayerInGame;
import Database.PlayerManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class PlayerTest {

    @Test
    public void DatabaseConnectionTest() throws Exception
    {
        String username = "koen";
        String password = "koen1234";

        Player player = PlayerManager.getPlayer(username, password);
        Assert.assertTrue(player != null);

        System.out.println(player.getUniqueId());
    }
}
