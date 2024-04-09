package loginsystem;

/**
 * Represents a user in the login system.
 * Each user has a username, password, email, name, and temporary code.
 * This class provides methods to access and manipulate user information.
 * 
 * @author zeyad
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    private String tempCode;

    /**
     * Constructs a new User object with the specified username, password, email, name, and temporary code.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param name the name of the user
     * @param tempCode the temporary code of the user
     */
    public User(String username, String password, String email, String name, String tempCode){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.tempCode = tempCode;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the new password to set
     */
    public void setPassword(String password){
       this.password = password;
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername(){
        return username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user
     */
    public String getPassword(){
        return password;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email of the user
     */
    public String getEmail(){
        return email;
    }

    /**
     * Gets the name of the user.
     * 
     * @return the name of the user
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the temporary code of the user.
     * 
     * @return the temporary code of the user
     */
    public String getTempCode(){
        return tempCode;
    }

    /**
     * Returns a string representation of the User object using the specified delimiter.
     * The string consists of the username, password, email, name, and temporary code separated by the delimiter.
     * 
     * @param delim the delimiter to use for separating the fields
     * @return a string representation of the User object
     */
    public String toString(String delim){
        String output = username + delim + password + delim + email + delim + name + delim + tempCode;
        return output;
    }
}
