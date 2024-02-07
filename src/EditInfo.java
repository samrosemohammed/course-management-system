import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditInfo {
    private JPanel EditInfoPanel;
    private JTextField editName;
    private JTextField editEmail;
    private JButton updateButton;

    private String userType;
    private String loggedInUserEmail;

    public EditInfo(String userType, String loggedInUserEmail) {
        this.userType = userType;
        this.loggedInUserEmail = loggedInUserEmail;

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = editName.getText();
                String newEmail = editEmail.getText();

                if (updateUserInfo(newName, newEmail)) {
                    JOptionPane.showMessageDialog(null, "User information updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update user information", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void showEditInfo() {
        retrieveAndDisplayUserInfo();

        JFrame editInfoFrame = new JFrame("Edit Info");
        editInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editInfoFrame.setContentPane(this.EditInfoPanel);
        editInfoFrame.setSize(500, 300);
        editInfoFrame.setLocationRelativeTo(null);
        editInfoFrame.setVisible(true);
    }

    private void retrieveAndDisplayUserInfo() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            String query = "";
            switch (userType) {
                case "Student":
                    query = "SELECT * FROM student_login WHERE email = ?";
                    break;
                case "Teacher":
                    query = "SELECT * FROM teacher_login WHERE email = ?";
                    break;
                case "Admin":
                    query = "SELECT * FROM admin_login WHERE email = ?";
                    break;
                default:
                    System.out.println("Unsupported user type");
                    return;
            }

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, loggedInUserEmail);
                var resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    editName.setText(resultSet.getString("username"));
                    editEmail.setText(resultSet.getString("email"));
                } else {
                    System.out.println("User not found");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving user information: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean updateUserInfo(String newName, String newEmail) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            String updateQuery = "";
            switch (userType) {
                case "Student":
                    updateQuery = "UPDATE student_login SET username = ?, email = ? WHERE email = ?";
                    break;
                case "Teacher":
                    updateQuery = "UPDATE teacher_login SET username = ?, email = ? WHERE email = ?";
                    break;
                case "Admin":
                    updateQuery = "UPDATE admin_login SET username = ?, email = ? WHERE email = ?";
                    break;
                default:
                    System.out.println("Unsupported user type");
                    return false;
            }

            try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newEmail);
                preparedStatement.setString(3, loggedInUserEmail);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating user information: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}