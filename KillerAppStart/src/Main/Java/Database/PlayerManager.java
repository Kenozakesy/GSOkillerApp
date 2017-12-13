package Database;

import Classes.ClientApplication.Player;
import Classes.GameManager.PlayerInGame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class PlayerManager {

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

                Player player = new Player(id, username);
                return player;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connection.closeAll();
        }
        return null;
    }
}
