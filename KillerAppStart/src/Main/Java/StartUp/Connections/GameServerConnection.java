package startup.connections;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import FontysPublisher.IRemotePropertyListener;
import interfaces.IGameManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 3-1-2018.
 */
public class GameServerConnection implements IGameManager{

    //singleton value
    private static Logger log = Logger.getLogger("Warning!");
    private static GameServerConnection data;

    private String ipAddress = "localhost";
    private String bindingName = "gameServer";
    private int portNumber = 1098;


    private IGameManager gameManager;

    /**
     *  Properties
     */
    public IGameManager getLobbyManager() {return gameManager;}
    public static GameServerConnection getInstance() {
        if(data == null)
        {
            try {
                data = new GameServerConnection();
            } catch (RemoteException e) {
                log.warning(e.toString());
            }
        }
        return data;
    }

    /**
     *  Constructor
     */
    private GameServerConnection() throws RemoteException {
        super();

    }

    /**
     *  Methods
     */
    public void connect() {
        try {
            Registry registry;
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
            gameManager = (IGameManager) registry.lookup(bindingName);

            log.warning("Connected to gameServer");
        } catch (Exception e) {
            log.warning(e.toString());
        }
    }

    public void disconnect() {
        try {
            gameManager = null;
        }
        catch (Exception e)
        {
            log.warning(e.toString());
        }
    }


    /**
     *  Override methods
     */
    @Override
    public void startGame(List<Player> players) throws RemoteException {
        gameManager.startGame(players);
    }

    @Override
    public Game getGame(Player player) throws RemoteException {
        return gameManager.getGame(player);
    }

    @Override
    public void sendGame(Game game) throws RemoteException {
        gameManager.sendGame(game);
    }

    @Override
    public void sendGameDatabase(Game game) throws RemoteException {
        gameManager.sendGameDatabase(game);
    }

    @Override
    public List<Game> getAllGamesFromPlayer(Player player) throws RemoteException {
        return gameManager.getAllGamesFromPlayer(player);
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
