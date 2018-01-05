package StartUp;

import Classes.LobbyManager.LobbyManager;
import Interfaces.ILobbyManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class LobbyServer {

    private static Registry registry = null;
    private static final String bindingName = "lobbyServer";
    private static final int port = 1099;
    private static LobbyManager lobbyManager = null;

    public static void main(String[] args) {

        // Create a newGame
        lobbyManager = LobbyManager.getInstance();
        System.out.println("Server: Lobby created");

        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (Exception e) {
            System.out.println("ERROR: Could not create the registry.");
            e.printStackTrace();
        }

        //Server serverObject = new Server();

        try {
            registry.rebind(bindingName, lobbyManager);
            System.out.println("lobby server started");
        } catch (Exception e) {
            System.out.println("ERROR: Failed to register the server object.");
            e.printStackTrace();
        }
    }


}
