package StartUp.Connections;

import Classes.LobbyManager.Lobby;
import Classes.LobbyManager.LobbyPlayer;
import FontysPublisher.IRemotePropertyListener;
import Interfaces.ILobbyManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Created by Gebruiker on 29-12-2017.
 */
public class LobbyServerConnection implements ILobbyManager {

    //singleton value
    private static LobbyServerConnection data;

    private String ipAddress = "127.0.0.1";
    private String bindingName = "lobbyServer";
    private int portNumber = 1099;

    private Registry registry = null;
    private ILobbyManager lobbyManager;


    /**
     *  Properties
     */
    public ILobbyManager getLobbyManager() {return lobbyManager;}
    public static LobbyServerConnection getInstance() {
        if(data == null)
        {
            try {
                data = new LobbyServerConnection();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     *  Constructor
     */
    private LobbyServerConnection() throws RemoteException {
        super();

    }


    /**
     *  Methods
     */
    public void connect() {
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
            lobbyManager = (ILobbyManager) registry.lookup(bindingName);

            System.out.println("Connected to LobbyServer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            lobbyManager = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }





    @Override
    public boolean createLobby(LobbyPlayer lobbyPlayer) throws RemoteException {

        if(lobbyManager.createLobby(lobbyPlayer))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean joinLobby(LobbyPlayer lobbyPlayer, int lobbyID) throws RemoteException {
        if(lobbyManager.joinLobby(lobbyPlayer, lobbyID))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public List<Lobby> getAllLobbys() throws RemoteException {
        List<Lobby> lobbyList = lobbyManager.getAllLobbys();
        return lobbyList;
    }

    @Override
    public void RemovePlayerExistence(LobbyPlayer player) throws RemoteException {
        lobbyManager.RemovePlayerExistence(player);
    }

    @Override
    public void startGame(LobbyPlayer player) throws RemoteException {
        lobbyManager.startGame(player);
    }

    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
    }
}
