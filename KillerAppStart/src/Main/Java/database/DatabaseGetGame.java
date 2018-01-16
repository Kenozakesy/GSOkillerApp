package database;

import classes.clientapplication.Player;
import classes.gamemanager.Game;
import classes.gamemanager.PlayerInGame;
import classes.gamemanager.Stone;
import enums.Side;
import javafx.scene.paint.Color;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
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
        throw  new NotImplementedException();
    }
}
