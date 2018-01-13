package classes.gamemanager;

import classes.clientapplication.Player;
import FontysPublisher.IRemotePropertyListener;
import FontysPublisher.RemotePublisher;
import Interfaces.IGameManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 2-1-2018.
 */
public class GameManager extends UnicastRemoteObject implements IGameManager {

    /**
     * Fields
     */
    private static GameManager instance;
    private List<Game> games;

    private RemotePublisher publisher;

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
    public synchronized void startGame(List<Player> players) throws RemoteException {
        Game game = new Game();
        game.addPlayers(players);
        games.add(game);

        //Hier voor het eerst game opslaan in database

    }

    @Override
    public synchronized Game getGame(Player player) throws RemoteException {

        PlayerInGame pig = new PlayerInGame(player.getUniqueId(), player.getName());
        for (Game game : games) {
            for (PlayerInGame PG: game.getPlayers()) {
                if(pig.equals(PG))
                {
                    return game;
                }
            }
        }

        return null;
    }

    @Override
    public synchronized void sendGame(Game game) throws RemoteException {

        //hiet moet eigenlijk een aanpassing doorgestuurd worden
        publisher.inform("game", null, game);
    }

    @Override
    public void sendGameDatabase(Game game) throws RemoteException {

        //hier sla je dingen in de database op




        //hier nieuw turn aanmaken
        game.switchTurns();
        publisher.inform("game", null, game);

        if(game.checkGameWon())
        {
            games.remove(game);
        }
    }

    @Override
    public synchronized void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
        publisher.subscribeRemoteListener(listener, property);
    }

    @Override
    public synchronized void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
        publisher.unsubscribeRemoteListener(listener, property);
    }

    /**
     * Methods
     */

}
