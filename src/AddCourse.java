import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCourse {
    private JPanel AddCoursePanel;
    private JTextField CourseName;
    private JTextField Batch;
    private JTextField NoOfSeats;
    private JTextField NoOfYear;
    private JTextField courseID;
    private JButton addCourseButton;

    public AddCourse() {
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeCourseData();
            }
        });
    }

    public void storeCourseData() {
        String courseIDText = courseID.getText();
        String courseNameText = CourseName.getText();
        String batchText = Batch.getText();
        int noOfSeatsValue = Integer.parseInt(NoOfSeats.getText());
        int noOfYearsValue = Integer.parseInt(NoOfYear.getText());

        // Insert data into the database
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "INSERT INTO course (courseID, courseName, noOfSeats, batch, noOfYears) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, courseIDText);
                preparedStatement.setString(2, courseNameText);
                preparedStatement.setInt(3, noOfSeatsValue);
                preparedStatement.setString(4, batchText);
                preparedStatement.setInt(5, noOfYearsValue);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Course added successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding course to the database.");
        }
    }

    public void showAddCourse() {
        JFrame addCourseFrame = new JFrame("Add Course");
        addCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addCourseFrame.setContentPane(this.AddCoursePanel);
        addCourseFrame.pack();
        addCourseFrame.setLocationRelativeTo(null);
        addCourseFrame.setVisible(true);
    }
}
