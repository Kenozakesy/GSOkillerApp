package ClassesTest.LobbyManagerTest;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.lobbymanager.Lobby;
import classes.lobbymanager.LobbyPlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gebruiker on 10-1-2018.
 */
public class LobbyTest {

    @Before
    public void Test()
    {

    }

    @Test
    public void ConstructorTest() throws Exception
    {
        Lobby lobby = new Lobby();
        Assert.assertEquals(lobby.getUniqueId(), 3);

        LobbyPlayer LP = new LobbyPlayer(1, "henk");
        lobby.addPlayer(LP);

        Assert.assertEquals(lobby.getLobbyPlayers().size(), 1);

        lobby.removePlayer(LP);

        Assert.assertEquals(lobby.getLobbyPlayers().size(), 0);

        LobbyPlayer LP2 = lobby.checkPlayerInLobby(LP);
        Assert.assertEquals(null, LP2);


        lobby.addPlayer(LP);
        LobbyPlayer LP3 = lobby.checkPlayerInLobby(LP);
        Assert.assertEquals(LP, LP3);

    }

    @Test
    public void toStringTest() throws Exception
    {
        String string = "ID 2, Lobby owner: , Players: 0/2";

        Lobby lobby = new Lobby();
        Assert.assertEquals(lobby.toString(), string);


    }

    @Test
    public void equalsMethode() throws Exception
    {
        int id = 0;
        String name = "test";
        Game game = new Game();
        Player player = null;

        Lobby pig = new Lobby();
        Lobby pig2 = new Lobby();

        Assert.assertTrue(pig.equals(pig));
        Assert.assertFalse(pig.equals(pig2));
        //Assert.assertFalse(pig.equals(pigfalse2));


    }


}
