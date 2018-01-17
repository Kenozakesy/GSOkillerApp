package database;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.gamemanager.PlayerInGame;
import classes.gamemanager.Stone;
import enums.ColorStatic;
import enums.Side;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 15-1-2018.
 */
public class DatabaseGetGame {

    private static Logger log = Logger.getLogger("warning");

    private DatabaseGetGame() {
    }

    public static List<Game> getGames(Player player) {
        DatabaseConnection connection = new DatabaseConnection();

        List<Game> gameList = new ArrayList<>();

        try {
            String sql = "Select * FROM Game G JOIN Player_Game PG ON PG.GAME_ID = G.ID  WHERE PG.Player_ID = ?" ;
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, player.getUniqueId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");

                Game game = new Game(id, name);
                gameList.add(game);
            }

        } catch (SQLException ex) {
            log.warning(ex.toString());
        } finally {
            connection.closeAll();
        }

        return gameList;
    }

    public static Game getGameStateTurn(int gameid, int turnnumber) {

        DatabaseConnection connection = new DatabaseConnection();

        Game gamebegin = null;

        try {
            //eerst ophalen game
            String sql = "SELECT P.name as Pname,P.ID AS pid ,G.name as Gname, PG.COLOR as COLOR \n" +
                    "FROM game G \n" +
                    "JOIN Player_Game PG ON PG.GAME_ID = G.ID \n" +
                    "JOIN PLAYER P ON PG.Player_ID = P.ID\n" +
                    "WHERE G.ID = ?" ;

            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, gameid);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<PlayerInGame> playerlist = new ArrayList<>();

            while (resultSet.next()) { //hier game aanmaken
                if(gamebegin == null) {
                    String name = resultSet.getString("Gname");
                    gamebegin = new Game(gameid, name, turnnumber);
                }

                String color = resultSet.getString("COLOR");
                Side side = null;
                if(color.equals("BLACK"))
                {
                    side = Side.BLACK;
                } else {
                    side = Side.WHITE;
                }

                int playerid = resultSet.getInt("pid");
                String playername = resultSet.getString("Pname");
                PlayerInGame pig = new PlayerInGame(playerid, playername, side);
                playerlist.add(pig);
            }
            if(gamebegin == null)
            {
                return null;
            }
            gamebegin.addPlayersDatabase(playerlist);

            //nu nog alle stonen ophalen-------------------------------------------
            //eerst ophalen game
            String sql2 = "SELECT NewCoordinateX AS X, NewCoordinateY AS Y, S.color AS color\n" +
                    "FROM game G \n" +
                    "Inner JOIN Turn T ON G.ID = T.GAME_ID\n" +
                    "JOIN Stone S ON S.TURN_ID = T.ID\n" +
                    "WHERE G.ID = ?\n" +
                    "AND t.NUMBER = ?" ;

            PreparedStatement preparedStatement2 = connection.getConnection().prepareStatement(sql2);
            preparedStatement2.setInt(1, gameid);
            preparedStatement2.setInt(2, turnnumber);

            ResultSet resultSet2 = preparedStatement2.executeQuery();

            List<Stone> stonelist = new ArrayList<>();

            while (resultSet2.next()) { //hier game aanmaken
                int X = resultSet2.getInt("X");
                int Y = resultSet2.getInt("Y");
                String color = resultSet2.getString("COLOR");
                ColorStatic side;
                if(color.equals("BLACK"))
                {
                    side = ColorStatic.BLACK;
                } else {
                    side = ColorStatic.WHITE;
                }
                Stone stone = new Stone(X, Y, side);
                stonelist.add(stone);
            }

            for (PlayerInGame p : gamebegin.getPlayers()) {
               p.setStonesList(stonelist);
            }

            return gamebegin;
        } catch (SQLException ex) {
            log.warning(ex.toString());
        } finally {
            connection.closeAll();
        }

        return gamebegin;

    }
}
