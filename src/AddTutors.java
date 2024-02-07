import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTutors {
    private JPanel AddTutorPanel;
    private JTextField tutorID;
    private JTextField tutorName;
    private JTextField tutorEmail;
    private JTextField dateAddTutor;
    private JButton addTutorButton;

    public AddTutors() {
        addTutorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeTutorData();
            }
        });
    }

    public void storeTutorData() {
        // Get data from text fields
        String tutorIDText = tutorID.getText();
        String tutorNameText = tutorName.getText();
        String tutorEmailText = tutorEmail.getText();
        String dateAddTutorText = dateAddTutor.getText();

        // Insert data into the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Use a prepared statement to avoid SQL injection
            String query = "INSERT INTO teacher (TutorID, TutorName, Email, Date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tutorIDText);
                preparedStatement.setString(2, tutorNameText);
                preparedStatement.setString(3, tutorEmailText);
                preparedStatement.setString(4, dateAddTutorText);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Tutor added successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding tutor to the database.");
        }
    }
    public void showAddTutor() {
        JFrame addTutorsFrame = new JFrame("Add Tutors");
        addTutorsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addTutorsFrame.setContentPane(this.AddTutorPanel);  // Assuming this is the main container for your dashboard UI
        addTutorsFrame.setSize(1000, 600);
        addTutorsFrame.pack();
        addTutorsFrame.setLocationRelativeTo(null); // Center the frame
        addTutorsFrame.setVisible(true);
    }
}
