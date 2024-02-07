import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CreateStudentReport {
    private JPanel CreateStudentReportPanel;
    private JTextField studentID;
    private JTextField studentName;
    private JComboBox studentCourse;
    private JTextField percentage;
    private JTextField gpa;
    private JButton updateResultButton;
    private JTextField moduleName;

    public CreateStudentReport() {
        updateResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateResult();
            }
        });
    }

    private void handleUpdateResult() {
        // Get the entered data from the form
        String enteredStudentID = studentID.getText();
        String enteredStudentName = studentName.getText();
        String enteredStudentCourse = (String) studentCourse.getSelectedItem();
        String enteredModuleName = moduleName.getText();
        String enteredPercentage = percentage.getText();
        String enteredGPA = gpa.getText();

        // Check if studentID exists in the student_login table
        if (!doesStudentExist(enteredStudentID)) {
            JOptionPane.showMessageDialog(null, "Student with ID " + enteredStudentID + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate input fields
        if (enteredStudentID.isEmpty() || enteredStudentName.isEmpty() || enteredStudentCourse.isEmpty()
                || enteredModuleName.isEmpty() || enteredPercentage.isEmpty() || enteredGPA.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return; // Stop processing if validation fails
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            // Insert data into student_report table
            String insertQuery = "INSERT INTO student_report (student_ID, student_name, course, module, total_percentage, gpa) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = con.prepareStatement(insertQuery)) {
                insertStatement.setString(1, enteredStudentID);
                insertStatement.setString(2, enteredStudentName);
                insertStatement.setString(3, enteredStudentCourse);
                insertStatement.setString(4, enteredModuleName);
                insertStatement.setString(5, enteredPercentage);
                insertStatement.setString(6, enteredGPA);

                int rowsAffected = insertStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Report successfully inserted
                    JOptionPane.showMessageDialog(null, "Report updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update report", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean doesStudentExist(String studentID) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            String query = "SELECT SID FROM student_login WHERE SID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, studentID);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next(); // If there is at least one result, the student exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of any error
        }
    }
    public void showStudentReport() {
        JFrame studentReport = new JFrame("Student Report");
        studentReport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentReport.setContentPane(this.CreateStudentReportPanel);  // Assuming this is the main container for your dashboard UI
        studentReport.setSize(1000, 600);
        studentReport.pack();
        studentReport.setLocationRelativeTo(null); // Center the frame
        studentReport.setVisible(true);
    }
}
