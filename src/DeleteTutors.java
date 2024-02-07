import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTutors {
    private JPanel DeleteTutorPanel;
    private JTextField tutorIDTextField;
    private JButton DELETEButton;

    public DeleteTutors() {
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTutor();
            }
        });
    }

    public void removeTutor() {
        // Get tutor ID to delete
        String tutorIDToDelete = tutorIDTextField.getText();

        // Delete data from the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Use a prepared statement to avoid SQL injection
            String query = "DELETE FROM teacher WHERE TutorID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tutorIDToDelete);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Tutor deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Tutor not found or already deleted.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting tutor from the database.");
        }
    }

    public void showDeleteTutor() {
        JFrame deleteTutorFrame = new JFrame("Delete Tutor");
        deleteTutorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteTutorFrame.setContentPane(this.DeleteTutorPanel);  // Assuming this is the main container for your dashboard UI
        deleteTutorFrame.setSize(1000, 600);
        deleteTutorFrame.pack();
        deleteTutorFrame.setLocationRelativeTo(null); // Center the frame
        deleteTutorFrame.setVisible(true);
    }
}
