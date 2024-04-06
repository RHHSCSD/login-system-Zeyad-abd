package loginsystem;


public class main {
    public static void main(String[] args) {
        // Create an instance of LoginSystem
        LoginSystem loginSystem = new LoginSystem();

        // Call encryptPassword method using the instance
        String hello = "1P#l";
        System.out.println(loginSystem.isStrongPassword(hello));
    }
}
