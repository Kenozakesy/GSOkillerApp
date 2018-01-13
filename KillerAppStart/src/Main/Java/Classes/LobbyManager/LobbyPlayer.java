package classes.LobbyManager;

import Enums.Side;

import java.io.Serializable;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class LobbyPlayer implements Serializable{

    /**
     *  Fields
     */
    private int uniqueId;
    private String name;
    private String password;
    private Side side;

    private boolean host;

    /**
     *  Properties
     */
    public boolean isHost() {return host;}
    public void setHost(boolean host) {this.host = host;}

    public int getUniqueId() {return uniqueId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    /**
     *  Constructor
     */
    public LobbyPlayer()
    {

    }

    public LobbyPlayer(int uniqueId, String name)
    {
        this.name = name;
        this.uniqueId = uniqueId;
        this.host = false;
    }

    /**
     *  Methods
     */



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LobbyPlayer)) {
            return false;
        }

        LobbyPlayer other = (LobbyPlayer) obj;

        if (!other.name.equals(name)) {
            return false;
        }
        if (other.uniqueId != uniqueId) {
            return false;
        }

        return true;
    }
}
