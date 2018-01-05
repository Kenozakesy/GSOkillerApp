package StartUp;

import Classes.GameManager.Game;
import Classes.GameManager.GameManager;
import Classes.LobbyManager.LobbyManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class GameServer {

    private static Registry registry = null;
    private static final String bindingName = "gameServer";
    private static final int port = 1098;
    private static GameManager gameManager = null;

    public static void main(String[] args) {
        // Create a newGame
        gameManager = GameManager.getInstance();
        System.out.println("Server: Game created");

        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (Exception e) {
            System.out.println("ERROR: Could not create the registry.");
            e.printStackTrace();
        }

        //Server serverObject = new Server();

        try {
            registry.rebind(bindingName, gameManager);
            System.out.println("Game server started");
        } catch (Exception e) {
            System.out.println("ERROR: Failed to register the server object.");
            e.printStackTrace();
        }
    }
}
