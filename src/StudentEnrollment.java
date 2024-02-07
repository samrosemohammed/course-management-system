import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentEnrollment {
    private JPanel StudentEnrollPanel;
    private JTextField enrollName;
    private JTextField enrollEmail;
    private JComboBox<String> enrollLevel;
    private JComboBox<String> enrollCourse;
    private JCheckBox check1;
    private JCheckBox check2;
    private JCheckBox check3;
    private JCheckBox check4;
    private JButton enrollNowButton;
    private JPanel checkBoxPanel;

    public StudentEnrollment() {
        // Set initial visibility of checkBoxPanel based on default selection of enrollLevel
        checkBoxPanel.setVisible(enrollLevel.getSelectedItem().equals("Level 6"));

        // Add action listener to enrollLevel combobox
        enrollLevel.addActionListener(e -> {
            // Check the selected level
            String selectedLevel = (String) enrollLevel.getSelectedItem();

            // Update visibility of checkBoxPanel based on the selected level
            checkBoxPanel.setVisible(selectedLevel.equals("Level 6"));
        });

        // Add action listener to enrollCourse combobox
        enrollCourse.addActionListener(e -> {
            // Check the selected course
            String selectedCourse = (String) enrollCourse.getSelectedItem();

            // Populate the check boxes based on the selected course
            switch (selectedCourse) {
                case "BIT":
                    populateCheckBoxes("DMS", "AI/ML", "Data Analysts", "Networking");
                    break;
                case "BIBM":
                    populateCheckBoxes("Marketing", "Account", "Digit Marketing", "Business Law");
                    break;
                case "IBA":
                    populateCheckBoxes("Principles of Management", "Finance", "Business Ethics", "Strategic Management");
                    break;
            }
        });

        enrollNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollNowButtonClicked();
            }
        });
    }

    // Method to populate the check boxes with module names
    private void populateCheckBoxes(String... modules) {
        if (modules.length >= 4) {
            check1.setText(modules[0]);
            check2.setText(modules[1]);
            check3.setText(modules[2]);
            check4.setText(modules[3]);
        }
    }

    // Method to handle enrollment when "Enroll Now" button is clicked
    private void enrollNowButtonClicked() {
        String studentName = enrollName.getText();
        String studentEmail = enrollEmail.getText();
        String course = (String) enrollCourse.getSelectedItem();
        String level = (String) enrollLevel.getSelectedItem();

        String module1 = "";
        String module2 = "";

        // Check which checkboxes are selected and assign corresponding module names
        if (check1.isSelected()) {
            module1 = check1.getText();
            if (check2.isSelected()) {
                module2 = check2.getText();
            }
        } else if (check2.isSelected()) {
            module1 = check2.getText();
        }

        // Insert enrollment data into the database
        insertEnrollmentData(studentName, studentEmail, course, level, module1, module2);
    }

    // Method to insert enrollment data into the database
    private void insertEnrollmentData(String studentName, String studentEmail, String course, String level, String module1, String module2) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");

            String query = "INSERT INTO student_enroll (studentName, studentEmail, course, level, module_1, module_2) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, studentEmail);
                preparedStatement.setString(3, course);
                preparedStatement.setString(4, level);
                preparedStatement.setString(5, module1);
                preparedStatement.setString(6, module2);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Enrollment successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to enroll student", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error enrolling student: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showStudentEnrollment() {
        JFrame studentEnrollFrame = new JFrame("Enrollment");
        studentEnrollFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentEnrollFrame.setContentPane(this.StudentEnrollPanel);  // Assuming this is the main container for your dashboard UI
        studentEnrollFrame.setSize(1000, 600);
        studentEnrollFrame.pack();
        studentEnrollFrame.setLocationRelativeTo(null); // Center the frame
        studentEnrollFrame.setVisible(true);
    }
}
