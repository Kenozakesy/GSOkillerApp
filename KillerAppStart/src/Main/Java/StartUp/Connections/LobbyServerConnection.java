package startup.connections;

import classes.lobbymanager.Lobby;
import classes.lobbymanager.LobbyPlayer;
import FontysPublisher.IRemotePropertyListener;
import interfaces.ILobbyManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 29-12-2017.
 */
public class LobbyServerConnection implements ILobbyManager {

    //singleton value
    private static Logger log = Logger.getLogger("Warning!");
    private static LobbyServerConnection data;

    private String ipAddress = "localhost";
    private String bindingName = "lobbyServer";
    private int portNumber = 1099;


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
                log.warning(e.toString());
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
            Registry registry;
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
            lobbyManager = (ILobbyManager) registry.lookup(bindingName);

            log.warning("Connected to LobbyServer");
        } catch (Exception e) {
            log.warning(e.toString());
        }
    }
    public void disconnect() {
        try {
            lobbyManager = null;
        }
        catch (Exception e)
        {
            log.warning(e.toString());
        }
    }


    @Override
    public boolean createLobby(LobbyPlayer lobbyPlayer) throws RemoteException {

        boolean check = false;
        if(lobbyManager.createLobby(lobbyPlayer))
        {
            check = true;
        }
        return check;
    }

    @Override
    public boolean joinLobby(LobbyPlayer lobbyPlayer, int lobbyID) throws RemoteException {
        boolean check = false;
        if(lobbyManager.joinLobby(lobbyPlayer, lobbyID))
        {
            check = true;
        }
        return check;
    }

    @Override
    public List<Lobby> getAllLobbys() throws RemoteException {
        return lobbyManager.getAllLobbys();
    }

    @Override
    public void removePlayerExistence(LobbyPlayer player) throws RemoteException {
        lobbyManager.removePlayerExistence(player);
    }

    @Override
    public void startGame(LobbyPlayer player) throws RemoteException {
        lobbyManager.startGame(player);
    }

    @Override
    public List<LobbyPlayer> getPlayerList(LobbyPlayer player) throws RemoteException {
        return lobbyManager.getPlayerList(player);
    }

    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException();
    }
}
