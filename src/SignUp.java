import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUp extends JFrame {
    private JTextField signUserName;
    private JPasswordField signUserPassword;
    private JComboBox<String> signUserType;
    private JButton signInButton;
    private JPanel signUpPanel;
    private JTextField signUserEmail;
    private JButton loginInButton;
    private JComboBox<String> courseBox;
    private JLabel course;

    public SignUp() {
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!connectionAndInsertData()) {
                    // Validation failed, do not proceed to open the Dashboard
                    return;
                }

                // Open the Dashboard class
                DashBoard dashboard = new DashBoard(signUserName.getText(), signUserEmail.getText(), (String) signUserType.getSelectedItem());
                dashboard.showDashboard();

                // Close the login page (optional)
                JFrame signUpFrame = (JFrame) SwingUtilities.getWindowAncestor(signUpPanel);
                signUpFrame.dispose();
            }
        });
        loginInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginPage();

                // Close the sign-up page (optional)
                JFrame signUpFrame = (JFrame) SwingUtilities.getWindowAncestor(signUpPanel);
                signUpFrame.dispose();
            }
        });
        signUserType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUserType = (String) signUserType.getSelectedItem();

                // Show/hide the course label and JComboBox based on the user type
                if ("Student".equals(selectedUserType)) {
                    course.setVisible(true);
                    courseBox.setVisible(true);
                } else {
                    course.setVisible(false);
                    courseBox.setVisible(false);
                }
            }
        });
    }

    private void openLoginPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.showLoginPage();
    }

    public void showSignUp() {
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpFrame.setContentPane(signUpPanel);
        signUpFrame.pack();
        signUpFrame.setSize(500, 500);
        signUpFrame.setLocationRelativeTo(null); // center the frame
        signUpFrame.setVisible(true);
    }

    private boolean connectionAndInsertData() {
        String userName = signUserName.getText();
        String userPassword = String.valueOf(signUserPassword.getPassword());
        String userEmail = signUserEmail.getText();
        String userType = (String) signUserType.getSelectedItem();
        String userCourse = (String) courseBox.getSelectedItem();

        // Validate input fields
        if (userName.isEmpty() || userPassword.isEmpty() || userEmail.isEmpty() || userType.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false; // Stop processing if validation fails
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            System.out.println("Database connection");
            System.out.println("User Type Selected is = " + userType);
            String insertSql = "";
            switch (userType) {
                case "Student":
                    insertSql = "INSERT INTO student_login (username, email, password, course) VALUES ('" + userName + "', '" + userEmail + "', '" + userPassword + "', '" + userCourse + "')";
                    break;
                case "Teacher":
                    insertSql = "INSERT INTO teacher_login (username, email, password) VALUES ('" + userName + "', '" + userEmail + "', '" + userPassword + "')";
                    break;
                case "Admin":
                    insertSql = "INSERT INTO admin_login (username, email, password) VALUES ('" + userName + "', '" + userEmail + "', '" + userPassword + "')";
                    break;
                default:
                    System.out.println("Unsupported user type");
                    return false;
            }

            Statement stmt = con.createStatement();
            stmt.executeUpdate(insertSql);
            System.out.println("Insert Successfully");
            return true; // Successful insertion
        } catch (SQLException e) {
            // Log the exception details for debugging
            e.printStackTrace();

            // Display a more informative error message to the user
            JOptionPane.showMessageDialog(null, "Error inserting data into the database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}