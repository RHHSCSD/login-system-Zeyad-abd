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

/**
 *
 * @author zeyad
 */
public class LoginSystem {
    
    private User[] userLists;
    private Set<String> badPasswords;
       
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
}


