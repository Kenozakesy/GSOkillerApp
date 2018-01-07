package Interfaces;

import Classes.ClientApplication.Player;
import Classes.GameManager.Game;
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
}
