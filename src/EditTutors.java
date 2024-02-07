import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditTutors {
    private JPanel EditTutorPanel;
    private JTextField editTutorID;
    private JTextField editTutorName;
    private JTextField editEmail;
    private JButton updateButton;
    private JTextField editTutorDate;

    public EditTutors() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTutor();
            }
        });
    }

    public void updateTutor() {
        // Get data from text fields
        String tutorIDText = editTutorID.getText();
        String tutorNameText = editTutorName.getText();
        String emailText = editEmail.getText();
        String dateText = editTutorDate.getText();

        // Update data in the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Use a prepared statement to avoid SQL injection
            String query = "UPDATE teacher SET TutorName=?, Email=?, Date=? WHERE TutorID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tutorNameText);
                preparedStatement.setString(2, emailText);
                preparedStatement.setString(3, dateText);
                preparedStatement.setString(4, tutorIDText);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Tutor updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Tutor not found or no changes made.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating tutor in the database.");
        }
    }

    public void showEditTutor() {
        JFrame editTutorFrame = new JFrame("Edit Tutor");
        editTutorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editTutorFrame.setContentPane(this.EditTutorPanel);  // Assuming this is the main container for your dashboard UI
        editTutorFrame.setSize(1000, 600);
        editTutorFrame.pack();
        editTutorFrame.setLocationRelativeTo(null); // Center the frame
        editTutorFrame.setVisible(true);
    }
}
