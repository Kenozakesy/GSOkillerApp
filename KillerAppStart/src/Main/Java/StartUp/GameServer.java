package startup;

import classes.gamemanager.GameManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class GameServer {

    private static final Logger log = Logger.getLogger("warning");
    private static Registry registry = null;
    private static final String BINDINGNAME = "gameServer";
    private static final int PORT = 1098;


    public static void main(String[] args) {

        GameManager gameManager;

        // Create a newGame
        gameManager = GameManager.getInstance();

        log.warning("Server: Game created");
        log.warning("Server: Game created");

        try {
            registry = LocateRegistry.createRegistry(PORT);
        } catch (Exception e) {
            log.warning("ERROR: Could not create the registry.");
            log.warning(e.toString());
        }

        try {
            registry.rebind(BINDINGNAME, gameManager);
            log.warning("Game server started");

        } catch (Exception e) {
            log.warning("ERROR: Failed to register the server object.");
            log.warning(e.toString());

        }
    }
}
