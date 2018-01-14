package ClassesTest.LobbyManagerTest;

import classes.lobbymanager.Lobby;
import classes.lobbymanager.LobbyManager;
import classes.lobbymanager.LobbyPlayer;
import classes.singletons.PlayerSingleton;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 10-1-2018.
 */
public class LobbyManagerTest {


    @Test
    public void ConstructorTest() throws Exception
    {
        Assert.assertNotNull(LobbyManager.getInstance());
        Lobby.setID();
    }

    @Test
    public void CreateLobbyTest() throws Exception
    {
        int id = 0;
        int id2 = 1;
        String name = "name";

        LobbyPlayer LP = new LobbyPlayer(id, name);
        LobbyPlayer LP2 = new LobbyPlayer(id2, name);
        LobbyPlayer LP3 = new LobbyPlayer(2, name);

        LobbyManager.getInstance().createLobby(LP);
        int number = LobbyManager.getInstance().getAllLobbys().size();
        Assert.assertEquals(1,number);

        boolean check = LobbyManager.getInstance().createLobby(LP);
        Assert.assertFalse(check);
        boolean check2 = LobbyManager.getInstance().createLobby(LP2);
        Assert.assertTrue(check2);

        //join lobby
        boolean check3 = LobbyManager.getInstance().joinLobby(LP2, 1);
        Assert.assertFalse(check3);
        boolean check4 = LobbyManager.getInstance().joinLobby(LP3, 2);
        Assert.assertTrue(check4);
        boolean check5 = LobbyManager.getInstance().joinLobby(LP3, 2);
        Assert.assertTrue(check5);
        boolean check6 = LobbyManager.getInstance().joinLobby(LP, 1);
        Assert.assertTrue(check6);

        //checkstarttable
        PlayerSingleton.login("koen", "koen123");
        LobbyManager.getInstance().joinLobby(PlayerSingleton.getPlayer().getLobbyPlayer(), 2);
        boolean check7 = LobbyManager.getInstance().checkStartAble();
        Assert.assertFalse(check7);

        //getPlayerList

        int size2 = LobbyManager.getInstance().getPlayerList(LP).size();
        Assert.assertEquals(0, size2);
        int size3 = LobbyManager.getInstance().getPlayerList(LP2).size();
        Assert.assertEquals(2, size3);

    }

    @Test
    public void addLobbiesTest() throws Exception
    {
        Assert.assertNotNull(LobbyManager.getInstance());

        List<Lobby> lobbyList = new ArrayList<>();
        lobbyList.add(new Lobby());
        LobbyManager.getInstance().addLobbys(lobbyList);

        int size = LobbyManager.getInstance().getAllLobbys().size();
        Assert.assertEquals(size, 1);

        lobbyList.clear();
        LobbyManager.getInstance().addLobbys(lobbyList);

    }





}
