import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditStudent {
    private JPanel EditStudentPanel;
    private JTextField studentID;
    private JTextField editStudentName;
    private JTextField editStudentEmail;
    private JTextField editCourseName;
    private JButton updateStudentButton;

    public EditStudent() {
        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });
    }

    public void updateStudent() {
        // Get data from text fields
        String studentIDText = studentID.getText();
        String studentNameText = editStudentName.getText();
        String studentEmailText = editStudentEmail.getText();
        String courseNameText = editCourseName.getText();

        // Update data in the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Use a prepared statement to avoid SQL injection
            String query = "UPDATE student_login SET username=?, email=?, course=? WHERE SID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, studentNameText);
                preparedStatement.setString(2, studentEmailText);
                preparedStatement.setString(3, courseNameText);
                preparedStatement.setString(4, studentIDText);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Student information updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found or no changes made.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating student information in the database.");
        }
    }

    public void showEditStudent() {
        JFrame editStudentFrame = new JFrame("Edit Student");
        editStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editStudentFrame.setContentPane(this.EditStudentPanel);  // Assuming this is the main container for your dashboard UI
        editStudentFrame.setSize(1000, 600);
        editStudentFrame.pack();
        editStudentFrame.setLocationRelativeTo(null); // Center the frame
        editStudentFrame.setVisible(true);
    }
}
