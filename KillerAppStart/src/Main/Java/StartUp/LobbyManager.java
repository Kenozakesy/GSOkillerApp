package StartUp;

import Interfaces.ILobbyManager;
import com.sun.corba.se.spi.activation.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class LobbyManager implements ILobbyManager {

    private static Registry reg = null;
    private static final String bindingName = "server";
    private static final int port = 1099;

    public static void main(String[] args) {

        try {
            reg = LocateRegistry.createRegistry(port);
        } catch (Exception e) {
            System.out.println("ERROR: Could not create the registry.");
            e.printStackTrace();
        }

        LobbyManager serverObject = new LobbyManager(); //moet ff getest worden
        try {
            reg.rebind(bindingName, (ILobbyManager) UnicastRemoteObject.exportObject(serverObject, port));
            System.out.println("server started");
        } catch (Exception e) {
            System.out.println("ERROR: Failed to register the server object.");
            e.printStackTrace();
        }
    }

    @Override
    public void test() throws RemoteException {
        
    }
}
