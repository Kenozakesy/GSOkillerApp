package classes.lobbymanager;

import classes.clientapplication.Player;
import classes.singletons.PlayerSingleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class Lobby implements Serializable {

    /**
     *  Fields
     */
    private static int uniqueIdGenerator = 0;
    private int uniqueId;
    private List<LobbyPlayer> lobbyPlayers;

    /**
     *  Properties
     */
    public int getUniqueId() {return uniqueId;}
    public List<LobbyPlayer> getLobbyPlayers() {return lobbyPlayers;}

    /**
     *  Constructor
     */
    public Lobby()
    {
        uniqueId = uniqueIdGenerator;
        uniqueIdGenerator++;
        lobbyPlayers = new ArrayList<>(2);
    }

    /**
     *  Methods
     */

    public void addPlayer(LobbyPlayer lobbyPlayer)
    {
        lobbyPlayers.add(lobbyPlayer);
    }

    public void removePlayer(LobbyPlayer lobbyPlayer)
    {
        LobbyPlayer playerData = null;
        for (LobbyPlayer player : lobbyPlayers) {
            if(player.equals(lobbyPlayer))
            {
                playerData = player;
                break;
            }
        }
        lobbyPlayers.remove(playerData);
    }

    public LobbyPlayer checkPlayerInLobby(LobbyPlayer p)
    {
        for (LobbyPlayer lobbyPlayer : lobbyPlayers) {
            if(lobbyPlayer.equals(p))
            {
                return lobbyPlayer;
            }
        }
        return null;
    }

    private boolean checkInLobby()
    {
        Player player = PlayerSingleton.getPlayer();
        LobbyPlayer lobbyPlayer = new LobbyPlayer(player.getUniqueId(), player.getName());

        for (LobbyPlayer p : lobbyPlayers) {
            if(p.equals(lobbyPlayer))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {

        String check = "";

        if(checkInLobby())
        {
            check = ", !";
        }

        String name = "";
        for (LobbyPlayer p : lobbyPlayers) {
            if(p.isHost())
            {
                name = p.getName();
            }
        }

        return "ID " + uniqueId + ", Lobby owner: " + name + ", Players: "+ lobbyPlayers.size() + "/2" + check;
    }

    public static void setID()
    {
        uniqueIdGenerator = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Lobby)) {
            return false;
        }

        Lobby other = (Lobby) obj;

        if (other.uniqueId != uniqueId) {
            return false;
        }

        return true;
    }
}
