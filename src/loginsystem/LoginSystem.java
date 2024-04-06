package loginsystem;
                                
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 *
 * @author zeyad
 */
public class LoginSystem {
    
    private User[] userLists;
    
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
                    String dateBirth = parts[4];
                    
                    // Creating a new User object and adding it to the userList
                    User user = new User(username, password, email, name, dateBirth);
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
}


