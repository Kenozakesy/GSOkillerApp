package database;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.gamemanager.PlayerInGame;
import classes.gamemanager.Stone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 14-1-2018.
 */
public class DatabaseSaveGame {

    private static Logger log = Logger.getLogger("warning");

    private DatabaseSaveGame() {
    }

    public static void saveGame(Game game) {
        DatabaseConnection connection = new DatabaseConnection();

        int gameid = getHighestID("Game") + 1;
        game.setUniqueid(gameid);

        try {
            String sql = "INSERT INTO Game (ID, Name) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, gameid);
            preparedStatement.setString(2, "Game " + String.valueOf(gameid));

            preparedStatement.execute();

            for (PlayerInGame pl : game.getPlayers()) {
                String sql2 = "INSERT INTO Player_Game (Game_ID, Player_ID, COLOR) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement2 = connection.getConnection().prepareStatement(sql2);

                preparedStatement2.setInt(1, gameid);
                preparedStatement2.setInt(2, pl.getUniqueId());
                preparedStatement2.setString(3, pl.getSide().toString());

                preparedStatement2.execute();
            }

        } catch (SQLException ex) {
            log.warning(ex.toString());
        } finally {
            connection.closeAll();
        }
    }

    public static int getHighestID(String table) {
        DatabaseConnection connection = new DatabaseConnection();

        int id = 0;

        try {
            String sql = "select MAX(ID) as ID FROM " + table;
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("ID");
                break;
            }

        } catch (SQLException ex) {
            log.warning(ex.toString());
        } finally {
            connection.closeAll();
        }
        return id;
    }

    public static Player setWinner(Game game) {
        DatabaseConnection connection = new DatabaseConnection();

        try {
            String sql = "UPDATE Player_Game SET Winner = ? WHERE GAME_ID = ? AND Player_ID = ?";
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            String winner = "";
            for (PlayerInGame pig : game.getPlayers()) {
                if (game.getWinner().equals(pig)) {
                    winner = "Winner";
                } else {
                    winner = "Loser";
                }

                preparedStatement.setString(1, winner);
                preparedStatement.setInt(2, game.getUniqueid());
                preparedStatement.setInt(3, pig.getUniqueId());

                preparedStatement.execute();
            }

        } catch (SQLException ex) {
            log.warning(ex.toString());
            log.warning("something went wrong");
        } finally {
            connection.closeAll();
        }
        return null;
    }

    public static Player saveTurn(Game game) {
        DatabaseConnection connection = new DatabaseConnection();

        int turnid = getHighestID("Turn") + 1;

        try {
            String sql = "INSERT INTO Turn (ID, NUMBER, PLAYER_ID, GAME_ID) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, turnid);
            preparedStatement.setInt(2, game.getTurn());
            preparedStatement.setInt(3, game.getCurrentPlayerTurn().getUniqueId());
            preparedStatement.setInt(4, game.getUniqueid());

            preparedStatement.execute();

            List<Stone> stones = new ArrayList<>();
            for (PlayerInGame pl : game.getPlayers()) {
                stones.addAll(pl.getStones());
            }

            for (Stone s : stones) {
                String sql2 = "INSERT INTO Stone (NewCoordinateX, NewCoordinateY, color, TURN_ID) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement2 = connection.getConnection().prepareStatement(sql2);

                preparedStatement2.setDouble(1, s.getCoordinate().getX());
                preparedStatement2.setDouble(2, s.getCoordinate().getY());
                preparedStatement2.setString(3, s.getSideColor().toString());
                preparedStatement2.setInt(4, turnid);

                preparedStatement2.execute();
            }

        } catch (SQLException ex) {
            log.warning(ex.toString());
            log.warning("something went wrong");
        } finally {
            connection.closeAll();
        }
        return null;
    }
}
