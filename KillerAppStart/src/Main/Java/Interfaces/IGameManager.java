package interfaces;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import FontysPublisher.IRemotePublisherForListener;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public interface IGameManager extends Remote, IRemotePublisherForListener{

        void startGame(List<Player> players) throws RemoteException;
        Game getGame(Player player) throws RemoteException;

        void sendGame(Game game) throws RemoteException;
        void sendGameDatabase(Game game) throws RemoteException;
}
