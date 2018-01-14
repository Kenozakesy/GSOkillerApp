package database;

import classes.gamemanager.Game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class DataGameManager {

    private static Logger log = Logger.getLogger("warning");

    private DataGameManager()
    {

    }

    public static List<Game> getName() {
        DatabaseConnection connection = new DatabaseConnection();

        List<Game> games = new ArrayList<>();

        try {
            String sql = "Select * from Game";
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                Game game = new Game(name);
                games.add(game);
            }
            return games;

        } catch (SQLException ex) {
            log.warning(ex.toString());
        } finally {
            connection.closeAll();
        }
        return games;
    }
}
