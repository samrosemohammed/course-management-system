import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStudent {
    private JPanel DeleteStudentPanel;
    private JButton DELETEButton;
    private JTextField deleteStudentID;

    public DeleteStudent() {
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });
    }

    public void removeStudent() {
        // Get student ID to be deleted
        String studentIDToDelete = deleteStudentID.getText();

        // Delete student from the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Use a prepared statement to avoid SQL injection
            String query = "DELETE FROM student_login WHERE SID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, studentIDToDelete);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Student deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found or deletion failed.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting student from the database.");
        }
    }


    public void showDeleteStudent() {
        JFrame deleteStudentFrame = new JFrame("Delete Student");
        deleteStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteStudentFrame.setContentPane(this.DeleteStudentPanel);  // Assuming this is the main container for your dashboard UI
        deleteStudentFrame.setSize(1000, 600);
        deleteStudentFrame.pack();
        deleteStudentFrame.setLocationRelativeTo(null); // Center the frame
        deleteStudentFrame.setVisible(true);
    }
}
