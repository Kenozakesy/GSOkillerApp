package ClassesTest.SingletonTest;

import classes.Singletons.PlayerSingleton;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gebruiker on 12-1-2018.
 */
public class PlayerSingletonTest {

    @Test
    public void ConstructorTest()
    {
        String name = "koen";
        String password = "koen123";

        PlayerSingleton.login(name, password);

        Assert.assertEquals(name, PlayerSingleton.getPlayer().getName());

        boolean check = PlayerSingleton.login(name, "");
        Assert.assertFalse(check);
    }
}
