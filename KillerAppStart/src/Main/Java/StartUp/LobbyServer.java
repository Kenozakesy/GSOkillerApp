package startup;

import classes.lobbymanager.LobbyManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class LobbyServer {

    private static final Logger log = Logger.getLogger("attention");
    private static Registry registry = null;
    private static final String BINDINGNAME = "lobbyServer";
    private static final int PORT = 1099;


    public static void main(String[] args) {

        LobbyManager lobbyManager;

        // Create a newGame
        lobbyManager = LobbyManager.getInstance();
        log.warning("Server: Lobby created");

        try {
            registry = LocateRegistry.createRegistry(PORT);
        } catch (Exception e) {
            log.warning("ERROR: Could not create the registry.");
            log.warning(e.toString());
        }

        try {
            registry.rebind(BINDINGNAME, lobbyManager);
            log.warning("lobby server started");
        } catch (Exception e) {
            log.warning("ERROR: Failed to register the server object.");
            log.warning(e.toString());
        }
    }


}
