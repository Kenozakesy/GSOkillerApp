package database;

import classes.clientapplication.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class PlayerManager {

    private static Logger log = Logger.getLogger("warning");

    private PlayerManager()
    {

    }


    public static Player getPlayer(String username, String password) {
        DatabaseConnection connection = new DatabaseConnection();

        try {
            String sql = "Select * from Player WHERE Name = ? AND PASSWORD = ?";
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");

                return new Player(id, username);
            }

        } catch (SQLException ex) {
            log.warning(ex.toString());
        } finally {
            connection.closeAll();
        }
        return null;
    }
}
