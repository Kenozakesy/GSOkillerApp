package Classes.ClientApplication;

import Enums.Side;

/**
 * Created by Gebruiker on 12-12-2017.
 */
public class Player {

    /**
     *  Fields
     */
    private int UniqueCode;
    private String name;
    private String password;
    private Side side;

    /**
     *  Properties
     */
    public int getUniqueCode() {return UniqueCode;}
    public void setUniqueCode(int uniqueCode) {UniqueCode = uniqueCode;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public Side getSide() {return side;}
    public void setSide(Side side) {this.side = side;}

    /**
     *  Constructor
     */
    public Player()
    {

    }

    /**
     *  Methods
     */


}
