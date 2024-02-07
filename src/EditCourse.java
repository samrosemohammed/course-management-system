import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditCourse {
    private JTextField editCourseName;
    private JTextField editSeatsNo;
    private JTextField editBatch;
    private JButton updateCourseButton;
    private JTextField editCourseID;
    private JPanel EditCoursePanel;

    public EditCourse() {
        updateCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCourseData();
            }
        });
    }

    public void updateCourseData() {
        String courseIDText = editCourseID.getText();
        String courseNameText = editCourseName.getText();
        String batchText = editBatch.getText();
        int noOfSeatsValue = Integer.parseInt(editSeatsNo.getText());

        // Update data in the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "UPDATE course SET courseName=?, batch=?, noOfSeats=? WHERE courseID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, courseNameText);
                preparedStatement.setString(2, batchText);
                preparedStatement.setInt(3, noOfSeatsValue);
                preparedStatement.setString(4, courseIDText);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Course updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Course not found or no changes made.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating course in the database.");
        }
    }

    public void showEditCourse() {
        JFrame editCourseFrame = new JFrame("Edit Course");
        editCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editCourseFrame.setContentPane(this.EditCoursePanel);  // Assuming this is the main container for your dashboard UI
        editCourseFrame.setSize(1000, 600);
        editCourseFrame.pack();
        editCourseFrame.setLocationRelativeTo(null); // Center the frame
        editCourseFrame.setVisible(true);
    }
}
