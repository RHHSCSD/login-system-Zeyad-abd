package loginsystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

/**
 * This class implements a basic login system.
 * It allows users to register, login, and performs password strength checks.
 */
public class LoginSystem {
    
    private User[] userLists;
    private Set<String> badPasswords;
    
    /**
     * Saves user information to a file.
     * @param user The user to be saved.
     */
    private void saveUser(User user){
        try {
            // Specify the directory path where you want to create the file
            String directoryPath = "src/loginsystem/";
            
            // Create the directory if it doesn't exist
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create the file within the specified directory
            FileWriter fileWriter = new FileWriter(directoryPath + "users.txt", true); // true for append mode
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Writing user data to the file
            printWriter.println(user.toString(";")); // Using the toString method with a delimiter
            printWriter.close(); // Closing the writer
        } catch (IOException e) {
            // Handle file IO exception
            e.printStackTrace();
        }
    }
    
    /**
     * Loads user information from a file.
     */
    private void loadUsers() {
        List<User> userList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/loginsystem/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Splitting the line based on the delimiter and creating User object
                String[] parts = line.split(";");
                if (parts.length == 5) { // Assuming there are 5 parts in each line
                    String username = parts[0];
                    String password = parts[1];
                    String email = parts[2];
                    String name = parts[3];
                    String tempCode = parts[4];
                    
                    // Creating a new User object and adding it to the userList
                    User user = new User(username, password, email, name, tempCode);
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the userList to an array
        userLists = userList.toArray(new User[0]);
    }
    
    /**
     * Checks if the given user is unique based on username and email.
     * @param user The user to be checked.
     * @return True if the user is unique, false otherwise.
     */
    private boolean isUniqueUser(User user) {
        // Load users if userLists is not initialized
        if (userLists == null) {
            loadUsers();
        }
        
        // Check if username or email already exists in userLists
        for (User existingUser : userLists) {
            if (existingUser.getUsername().equals(user.getUsername()) || 
                existingUser.getEmail().equals(user.getEmail())) {
                return false; // Username or email already exists
            }
        }
        
        return true; // Username and email are unique
    }
    
    /**
     * Encrypts the given password using MD5 hashing algorithm.
     * @param password The password to be encrypted.
     * @return The encrypted password.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available.
     */
    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        // java helper class to perform encryption
        MessageDigest md = MessageDigest.getInstance("MD5");
        // give the helper function the password
        md.update(password.getBytes());
        // perform the encryption
        byte byteData[] = md.digest();
        // To express the byte data as a hexadecimal number (the normal way)
        StringBuilder encryptedPassword = new StringBuilder();
        for (byte aByteData : byteData) {
            encryptedPassword.append(Integer.toHexString((aByteData & 0xFF) | 0x100), 1, 3);
        }
        return encryptedPassword.toString();
    }
    
    /**
     * Loads a set of bad passwords from a file.
     */
    private void loadBadPasswords() {
        badPasswords = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/loginsystem/badpasswords.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                badPasswords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Checks if the given password is strong.
     * @param password The password to be checked.
     * @return True if the password is strong, false otherwise.
     */
    private boolean isStrongPassword(String password) {
        loadBadPasswords();
        // Check length
        if (password.length() < 5) {
            return false;
        }
        
        // Check uppercase, lowercase, digit, and special character
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                // Assuming special characters are anything not alphanumeric
                hasSpecialChar = true;
            }
        }
        
        // Check if any required character type is missing
        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
            return false;
        }
        
        // Check if password is not in the list of bad passwords
        if (badPasswords.contains(password)) {
            return false;
        }
        
        // Password meets all criteria
        return true;
    }
    
    /**
     * Generates a temporary code.
     * @return The generated temporary code.
     */
    private String generateTempCode() {
        // Define characters to use in the temp code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder tempCode = new StringBuilder();
        
        // Create a Random object
        Random random = new Random();
        
        // Generate 4 random characters
        for (int i = 0; i < 4; i++) {
            // Generate a random index within the range of the characters string
            int randomIndex = random.nextInt(characters.length());
            
            // Append the character at the random index to the temp code
            tempCode.append(characters.charAt(randomIndex));
        }
        
        return tempCode.toString();
    }
    
    /**
     * Registers a new user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param username The username of the user.
     * @param name The name of the user.
     * @return A string indicating the registration status.
     */
    public String registerUser(String email, String password, String username, String name) {


        String tempcode = generateTempCode();
        // Create a new User object
        User user = new User(username, password, email, name, tempcode);

        // Check if the username is unique
        if (!isUniqueUser(user)) {
            return "Username or email already exists. Please choose a different one.";
        }

        // Check if the password is strong
        if (!isStrongPassword(password)) {
            return "Password is not strong enough. Please choose a stronger one.";
        }

        try {
            // Encrypt the password
            password = password+tempcode;
            String encryptedPassword = encryptPassword(password);

            // Update the User object with the encrypted password
            user.setPassword(encryptedPassword);

            // Save the User object to the file
            saveUser(user);

            return "User registered successfully.";
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error occurred while encrypting the password.");
            e.printStackTrace();
        }
        return "Unknown error occurred during registration.";
    }
    
    /**
     * Logs in a user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A string indicating the login status.
     */
    public String loginUser(String username, String password) {
        // Load users if userLists is not initialized
        loadUsers();

        User userToLogin = null;
        // Finding the user with the given username
        for (User existingUser : userLists) {
            if (existingUser.getUsername().equals(username)) {
                userToLogin = existingUser;
                break;
            }
        }

        // If user with given username is not found
        if (userToLogin == null) {
            return "User not found. Please check your username.";
        }

        try {
            // Get the temp code for the user
            String tempCode = userToLogin.getTempCode();

            // Append the temp code to the entered password
            password = password + tempCode;

            // Encrypt the entered password
            String encryptedPassword = encryptPassword(password);

            // Compare the encrypted password with the stored password
            if (userToLogin.getPassword().equals(encryptedPassword)) {
                return "Login successful.";
            } else {
                System.out.println("Incorrect password.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error occurred while encrypting the password.");
            e.printStackTrace();
        }
        return "Unknown error occurred during registration.";
    }
}
