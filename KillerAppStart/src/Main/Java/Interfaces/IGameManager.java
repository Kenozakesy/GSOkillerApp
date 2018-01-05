package Interfaces;

import Classes.ClientApplication.Player;
import FontysPublisher.IRemotePublisherForListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public interface IGameManager extends Remote, IRemotePublisherForListener{

        void startGame(Player player) throws RemoteException;
}
