package DatabaseTest;

import Database.DatabaseConnection;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gebruiker on 13-12-2017.
 */
public class ConnectionTest {

    @Test
    public void DatabaseConnectionTest() throws Exception
    {
        DatabaseConnection db = new DatabaseConnection();
        Assert.assertFalse(db.getConnection().equals(null));
    }
}
