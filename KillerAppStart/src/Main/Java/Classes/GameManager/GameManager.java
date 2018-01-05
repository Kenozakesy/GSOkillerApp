package Classes.GameManager;

import Classes.ClientApplication.Player;
import Classes.LobbyManager.Lobby;
import Classes.LobbyManager.LobbyManager;
import Classes.LobbyManager.LobbyPlayer;
import Classes.Singletons.PlayerSingleton;
import FontysPublisher.IRemotePropertyListener;
import FontysPublisher.RemotePublisher;
import Interfaces.IGameManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 2-1-2018.
 */
public class GameManager extends UnicastRemoteObject implements IGameManager {

    /**
     * Fields
     */
    private static GameManager instance;
    private List<Game> games;

    public RemotePublisher publisher;

    /**
     * Properties
     */
    public static GameManager getInstance() {
        if (instance == null) {
            try {
                instance = new GameManager();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public List<Game> getGames() {
        return games;
    }

    /**
     * Constructor
     */
    private GameManager() throws RemoteException {
        super();
        games = new ArrayList<>();

        String[] properties = new String[2];
        properties[0] = "game";
        properties[1] = "test";
        try {
            publisher = new RemotePublisher(properties);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Override Methods
     */


    @Override
    public void startGame(Player player) throws RemoteException {

    }


    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {

    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {

    }

    /**
     * Methods
     */

}
