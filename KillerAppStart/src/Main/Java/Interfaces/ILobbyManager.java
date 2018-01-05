package Interfaces;

import Classes.ClientApplication.Player;
import Classes.LobbyManager.Lobby;
import Classes.LobbyManager.LobbyPlayer;
import FontysPublisher.IRemotePublisherForListener;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public interface ILobbyManager extends Remote, IRemotePublisherForListener{

    boolean createLobby(LobbyPlayer lobbyPlayer) throws RemoteException;
    boolean joinLobby(LobbyPlayer lobbyPlayer, int LobbyID) throws RemoteException;
    List<Lobby> getAllLobbys() throws  RemoteException;
    void RemovePlayerExistence(LobbyPlayer player) throws  RemoteException;
    void startGame(LobbyPlayer player) throws  RemoteException;
}
