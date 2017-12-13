package Classes.Singletons;

import Classes.ClientApplication.Player;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class PlayerSingleton {

    /**
     *  Fields
     */
    private static Player instance = new Player();

    /**
     *  Properties
     */
    public static Player getPlayer()
    {
        if(instance == null)
        {
            instance = new Player();
        }
        return instance;
    }

    /**
     *  Constructor
     */
    private PlayerSingleton() {

    }


}
