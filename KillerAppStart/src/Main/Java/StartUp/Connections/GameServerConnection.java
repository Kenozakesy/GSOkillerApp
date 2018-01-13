package StartUp.Connections;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import FontysPublisher.IRemotePropertyListener;
import Interfaces.IGameManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Created by Gebruiker on 3-1-2018.
 */
public class GameServerConnection implements IGameManager{

    //singleton value
    private static GameServerConnection data;

    private String ipAddress = "127.0.0.1";
    private String bindingName = "gameServer";
    private int portNumber = 1098;

    private Registry registry = null;
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
                e.printStackTrace();
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
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
            gameManager = (IGameManager) registry.lookup(bindingName);

            System.out.println("Connected to gameServer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            gameManager = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
        Game game = gameManager.getGame(player);
        return game;
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
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {

    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {

    }


}
