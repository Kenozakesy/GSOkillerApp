package classes.lobbymanager;

import classes.singletons.PlayerSingleton;
import enums.MessageType;
import FontysPublisher.IRemotePropertyListener;
import FontysPublisher.RemotePublisher;
import interfaces.ILobbyManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class LobbyManager extends UnicastRemoteObject implements ILobbyManager {

    /**
     * Fields
     */

    private static Logger log = Logger.getLogger("warning");

    private static LobbyManager instance;
    private List<Lobby> lobbys;
    private RemotePublisher publisher;
    private static final String LOBBY = "lobby";

    /**
     * Properties
     */
    public static LobbyManager getInstance() {
        if (instance == null) {
            try {
                instance = new LobbyManager();
            } catch (RemoteException e) {
                log.warning(e.toString());
            }
        }
        return instance;
    }

    public List<Lobby> getLobbys() {
        return lobbys;
    }

    /**
     * Constructor
     */
    private LobbyManager() throws RemoteException {
        super();
        lobbys = new ArrayList<>();

        String[] properties = new String[2];
        properties[0] = LOBBY;
        properties[1] = "test";
        try {
            publisher = new RemotePublisher(properties);
        } catch (RemoteException e) {
            log.warning(e.toString());
        }
    }

    /**
     * Override Methods
     */
    @Override
    public synchronized boolean createLobby(LobbyPlayer lobbyPlayer) throws RemoteException {

        for (Lobby L : lobbys) {
            for (LobbyPlayer P : L.getLobbyPlayers()) {
                if (P.equals(lobbyPlayer)) {
                    return false;
                }
            }
        }

        Lobby newLobby = new Lobby();
        lobbyPlayer.setHost(true);
        newLobby.addPlayer(lobbyPlayer);
        lobbys.add(newLobby);

        publisher.inform(LOBBY, null, lobbys);
        return true;
    }

    @Override
    public synchronized boolean joinLobby(LobbyPlayer lobbyPlayer, int lobbyID) throws RemoteException {

        Lobby lobbyData = null;
        for (Lobby l : lobbys) {
            if (l.getUniqueId() == lobbyID) {
                lobbyData = l;
                break;
            }
        }
        if(lobbyData == null)
        {
            return false;
        }

        Lobby lobbyIn = null;


        //als je dezelfde lobby aanklikt waar je inzit
        LobbyPlayer player = lobbyData.checkPlayerInLobby(lobbyPlayer);
        if (player != null) {
            if (player.isHost()) {
                lobbys.remove(lobbyData);
            } else {
                lobbyData.removePlayer(lobbyPlayer);
            }
        } else //als je niet in dezelfde lobby zit
        {
            for (Lobby l : lobbys) {
                for (LobbyPlayer p : l.getLobbyPlayers()) {
                    if (p.equals(lobbyPlayer) && p.isHost()) {
                        return false;
                    } else if (p.equals(lobbyPlayer)) {
                        lobbyIn = l;
                        break;
                    }
                }
            }

            if (lobbyData.getLobbyPlayers().size() < 2) {
                if (lobbyIn != null) {
                    lobbyIn.removePlayer(lobbyPlayer);
                }
                lobbyData.addPlayer(lobbyPlayer);
            } else {
                return false;
            }
        }

        publisher.inform(LOBBY, null, lobbys);
        return true;
    }

    @Override
    public synchronized List<Lobby> getAllLobbys() throws RemoteException {
        return lobbys;
    }

    @Override
    public synchronized void removePlayerExistence(LobbyPlayer player) throws RemoteException {

        LobbyPlayer playerData = null;
        Lobby lobbyData = null;
        for (Lobby l : lobbys) {
            for (LobbyPlayer p : l.getLobbyPlayers()) {
                if (p.equals(player)) {
                    playerData = p;
                    lobbyData = l;
                    break;
                }
            }
        }
        if(playerData == null)
        {
            return;
        }
        if (playerData.isHost()) {
            lobbys.remove(lobbyData);
        } else {
            lobbyData.getLobbyPlayers().remove(playerData);
        }
        publisher.inform(LOBBY, null, lobbys);
    }

    @Override
    public synchronized void startGame(LobbyPlayer player) throws RemoteException {

        List<LobbyPlayer> lobbyplayers = new ArrayList<>();
        //lijst verkrijgen
        for (Lobby l : lobbys) {
            for (LobbyPlayer p : l.getLobbyPlayers()) {
                if(p.equals(player))
                {
                    lobbyplayers.addAll(l.getLobbyPlayers());
                    break;
                }
            }
        }

        MessageType type = MessageType.STARTGAME;
        //doorsturen
        publisher.inform(LOBBY, type, lobbyplayers);
    }

    @Override
    public List<LobbyPlayer> getPlayerList(LobbyPlayer player) throws RemoteException {

        List<LobbyPlayer> lobbyplayers = new ArrayList<>();

        //lijst verkrijgen
        for (Lobby l : lobbys) {
            for (LobbyPlayer p : l.getLobbyPlayers()) {
                if(p.equals(player))
                {
                    lobbyplayers.addAll(l.getLobbyPlayers());
                    break;
                }
            }
        }

        return  lobbyplayers;
    }

    //for push methods
    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
        publisher.subscribeRemoteListener(listener, property);
    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException {
        publisher.unsubscribeRemoteListener(listener, property);
    }

    /**
     * Methods
     */
    public void addLobbys(List<Lobby> lobbyList) {
        lobbys.clear();
        lobbys.addAll(lobbyList);
    }


    public boolean checkStartAble()
    {
        LobbyPlayer player = PlayerSingleton.getPlayer().getLobbyPlayer();
        for (Lobby l : lobbys) {

            LobbyPlayer lobbyPlayer = l.checkPlayerInLobby(player);
            if(l.getLobbyPlayers().contains(lobbyPlayer) && l.getLobbyPlayers().size() >= 2 && lobbyPlayer.isHost())
            {
                return true;
            }
        }
        return false;
    }


}
