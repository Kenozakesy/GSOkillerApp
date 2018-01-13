package classes.Singletons;

import classes.clientapplication.Player;
import Database.PlayerManager;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class PlayerSingleton {

    /**
     * Fields
     */
    //private static Player instance = null;
    private static Player instance = new Player();

    /**
     * Properties
     */
    public static Player getPlayer() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private PlayerSingleton() {

    }

    /**
     * Methods
     */
    public static boolean login(String username, String password) {
        Player player = PlayerManager.getPlayer(username, password);
        if (player != null) {
            instance = player;
            return true;
        } else {
            return false;
        }
    }


}
