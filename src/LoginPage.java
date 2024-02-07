import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {
    private JPanel loginPanel;
    private JTextField userEmail;
    private JPasswordField userPassword;
    private JComboBox<String> userType;
    private JButton loginInButton;
    private JButton signInButton;
    private JPanel loginPanelInner;

    public LoginPage() {
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignUpPage();

                // Close the login page after opening the sign-up page
                JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(loginPanel);
                loginFrame.dispose();
            }
        });
        loginInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });
    }

    public void showLoginPage() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setContentPane(loginPanel);
        loginFrame.setSize(1000, 600);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    public void openSignUpPage() {
        SignUp signUp = new SignUp();
        signUp.showSignUp();
    }

    private void loginUser() {
        String enteredEmail = userEmail.getText();
        char[] enteredPass = userPassword.getPassword();
        String enteredUserType = (String) userType.getSelectedItem();

        // Validate input fields
        if (enteredEmail.isEmpty() || enteredPass.length == 0 || enteredUserType.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return; // Stop processing if validation fails
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            String query = "";
            switch (enteredUserType) {
                case "Student":
                    query = "SELECT * FROM student_login WHERE email = ? AND password = ?";
                    break;
                case "Teacher":
                    query = "SELECT * FROM teacher_login WHERE email = ? AND password = ?";
                    break;
                case "Admin":
                    query = "SELECT * FROM admin_login WHERE email = ? AND password = ?";
                    break;
                default:
                    System.out.println("Unsupported user type");
                    return;
            }

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, enteredEmail);
                preparedStatement.setString(2, new String(enteredPass));

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Login successful
                    System.out.println("Login successful");

                    // Open the Dashboard class
                    DashBoard dashboard = new DashBoard(resultSet.getString("username"), resultSet.getString("email"), enteredUserType);
                    dashboard.showDashboard();

                    // Close the login page (optional)
                    JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(loginPanel);
                    loginFrame.dispose();
                } else {
                    // Login failed
                    System.out.println("Login failed. Invalid credentials.");

                    JOptionPane.showMessageDialog(null, "Invalid email or password or type", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}