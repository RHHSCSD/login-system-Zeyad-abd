package loginsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterGUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField usernameFieldLogin;
    private JPasswordField passwordFieldLogin;
    private JTextField usernameFieldRegister;
    private JTextField emailFieldRegister;
    private JTextField nameFieldRegister;
    private JPasswordField passwordFieldRegister;
    private LoginSystem loginSystem;

    public LoginRegisterGUI() {
        setTitle("Login/Register System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginSystem = new LoginSystem(); // Create an instance of LoginSystem

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel registerPanel = createRegisterPanel();

        mainPanel.add(loginPanel, "login");
        mainPanel.add(registerPanel, "register");

        add(mainPanel);

        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1));

        usernameFieldLogin = new JTextField();
        passwordFieldLogin = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call loginUser method from LoginSystem
                String username = usernameFieldLogin.getText();
                String password = new String(passwordFieldLogin.getPassword());
                String result = loginSystem.loginUser(username, password);
                JOptionPane.showMessageDialog(null, result);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "register");
            }
        });

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameFieldLogin);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordFieldLogin);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        return loginPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(6, 1));

        usernameFieldRegister = new JTextField();
        emailFieldRegister = new JTextField();
        nameFieldRegister = new JTextField();
        passwordFieldRegister = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton loginRedirectButton = new JButton("Login");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call registerUser method from LoginSystem
                String username = usernameFieldRegister.getText();
                String password = new String(passwordFieldRegister.getPassword());
                String email = emailFieldRegister.getText();
                String name = nameFieldRegister.getText();
                String result = loginSystem.registerUser(email, password, username, name);
                JOptionPane.showMessageDialog(null, result);
            }
        });

        loginRedirectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "login");
            }
        });

        registerPanel.add(new JLabel("Username:"));
        registerPanel.add(usernameFieldRegister);
        registerPanel.add(new JLabel("Email:"));
        registerPanel.add(emailFieldRegister);
        registerPanel.add(new JLabel("Name:"));
        registerPanel.add(nameFieldRegister);
        registerPanel.add(new JLabel("Password:"));
        registerPanel.add(passwordFieldRegister);
        registerPanel.add(registerButton);
        registerPanel.add(loginRedirectButton);

        return registerPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegisterGUI();
            }
        });
    }
}
