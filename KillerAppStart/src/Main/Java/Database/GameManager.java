package Database;

import Classes.GameManager.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class GameManager {

    public static List<Game> getName() {
        DatabaseConnection connection = new DatabaseConnection();

        List<Game> games = new ArrayList<>();

        try {
            String sql = "Select * from Game";
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);
            //preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                Game game = new Game(name);
                games.add(game);
            }
            return games;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connection.closeAll();
        }
        return null;
    }
}
