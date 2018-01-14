package classes.clientapplication;

import classes.lobbymanager.LobbyPlayer;
import java.io.Serializable;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class Player implements Serializable{

    /**
     *  Fields
     */
    private int uniqueId;
    private String name;

    /**
     *  Properties
     */
    public int getUniqueId() {return uniqueId;}

    public String getName() {return name;}

    /**
     *  Constructor
     */
    public Player()
    {

    }

    public Player(int uniqueId, String name)
    {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    /**
     *  Methods
     */
    public LobbyPlayer getLobbyPlayer()
    {
        return new LobbyPlayer(uniqueId, name);
    }


}
