import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCourse {
    private JTextField deleteCourseID;
    private JButton DELETEButton;
    private JPanel DeleteCoursePanel;


    public DeleteCourse() {
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCourse();
            }
        });
    }

    public void removeCourse() {
        // Get course ID to delete
        String courseIDToDelete = deleteCourseID.getText();

        // Delete data from the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "DELETE FROM course WHERE courseID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, courseIDToDelete);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Course deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Course not found or already deleted.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting course from the database.");
        }
    }

    public void showDeleteCourse() {
        JFrame deleteCourseFrame = new JFrame("Delete Course");
        deleteCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteCourseFrame.setContentPane(this.DeleteCoursePanel);  // Assuming this is the main container for your dashboard UI
        deleteCourseFrame.setSize(1000, 600);
        deleteCourseFrame.pack();
        deleteCourseFrame.setLocationRelativeTo(null); // Center the frame
        deleteCourseFrame.setVisible(true);
    }
}
