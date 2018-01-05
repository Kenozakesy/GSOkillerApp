package StartUp.Connections;

import Classes.ClientApplication.Player;
import FontysPublisher.IRemotePropertyListener;
import Interfaces.IGameManager;
import Interfaces.ILobbyManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
    public void startGame(Player player) throws RemoteException {
        gameManager.startGame(player);
    }



    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {

    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {

    }


}