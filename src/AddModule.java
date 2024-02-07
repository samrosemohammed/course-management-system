import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddModule {
    private JPanel AddModulePanel;
    private JTextField module1;
    private JTextField module2;
    private JTextField module3;
    private JComboBox level;
    private JTextField course;
    private JButton uploadButton;
    private JTextField module4;

    public AddModule() {
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveModuleData();
            }
        });
    }

    public void showAddModule() {
        JFrame addModuleFrame = new JFrame("Add Module Frame");
        addModuleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addModuleFrame.setContentPane(this.AddModulePanel);  // Assuming this is the main container for your dashboard UI
        addModuleFrame.setSize(1000, 600);
        addModuleFrame.pack();
        addModuleFrame.setLocationRelativeTo(null); // Center the frame
        addModuleFrame.setVisible(true);
    }

    private void saveModuleData() {
        String module1Value = module1.getText();
        String module2Value = module2.getText();
        String module3Value = module3.getText();
        String module4Value = module4.getText();
        String courseValue = course.getText();
        String levelValue = (String) level.getSelectedItem();

        // Validate input fields
        if (module1Value.isEmpty() || module2Value.isEmpty() || module3Value.isEmpty() || module4Value.isEmpty() || courseValue.isEmpty() || levelValue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return; // Stop processing if validation fails
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            String query = "INSERT INTO course_modules (level, module_1, module_2, module_3, module_4, course) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, levelValue);
                preparedStatement.setString(2, module1Value);
                preparedStatement.setString(3, module2Value);
                preparedStatement.setString(4, module3Value);
                preparedStatement.setString(5, module4Value);
                preparedStatement.setString(6, courseValue);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Module data saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save module data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving module data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
