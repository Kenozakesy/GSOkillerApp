package database;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class DatabaseConnection
{
    private java.sql.Connection connection;
    private Statement statement;
    private ResultSet result;

    private static Logger log = Logger.getLogger("warning");

    public DatabaseConnection(){
        connection = setConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    // Deze is static omdat we niet weten waarom het static moet zijn, maar er genoeg reden voor waren, alleen die hebben we niet meer onthouden.
    public static Connection setConnection(){
        try{

            return DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433;databaseName=GSOkillerApp", "", "");
        }
        catch(Exception exception){
            log.warning(exception.toString());
            return null;
        }
    }

    private void closeStatement(){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.warning(e.toString());
            }
        }
    }

    private void closeResultSet(){
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                log.warning(e.toString());
            }
        }
    }

    private void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.warning(e.toString());
            }
        }
    }

    public void closeAll(){
        closeResultSet();
        closeStatement();
        closeConnection();
    }


}
