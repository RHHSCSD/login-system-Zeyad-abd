package loginsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zeyad
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    private String dateBirth;

    public User(String username, String password, String email, String name, String dateBirth){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.dateBirth = dateBirth;

    }
    
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public String getdateBirth(){
        return dateBirth;
    }
    public String toString(String delim){
        String output = username+delim+password+delim+email+delim+name+delim+dateBirth;
        return output;
    }

} 