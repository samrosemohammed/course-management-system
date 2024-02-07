import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProgressReport {
    private JPanel ProgressReportPanel;
    private JTextField resultSearchID;
    private JTable showResult;
    private JLabel dynamicName;
    private JLabel dynamicCourse;
    private JLabel resultLabel;
    private JButton showResultButton;

    public ProgressReport() {
        showResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearchResult();
            }
        });
    }

    public void showProgressReport() {
        JFrame progressReportFrame = new JFrame("Progress Report");
        progressReportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        progressReportFrame.setContentPane(this.ProgressReportPanel);  // Assuming this is the main container for your dashboard UI
        progressReportFrame.setSize(1000, 600);
        progressReportFrame.pack();
        progressReportFrame.setLocationRelativeTo(null); // Center the frame
        progressReportFrame.setVisible(true);
    }

    private void handleSearchResult() {
        String searchID = resultSearchID.getText();

        // Validate the search ID
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Retrieve the result based on the search ID
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            String query = "SELECT * FROM student_report WHERE student_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, searchID);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Check if the result set is not empty
                if (resultSet.next()) {
                    // Set the text for dynamicName and dynamicCourse labels
                    dynamicName.setText(resultSet.getString("student_name"));
                    dynamicCourse.setText(resultSet.getString("course"));

                    // Calculate pass or fail based on mark
                    int mark = resultSet.getInt("total_percentage");
                    String resultLabelText = mark >= 40 ? "Pass" : "Fail";
                    resultLabel.setText(resultLabelText);

                    // Creating a DefaultTableModel to store the data for the JTable
                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID");
                    model.addColumn("Name");
                    model.addColumn("Course");
                    model.addColumn("Module");
                    model.addColumn("Mark");
                    model.addColumn("GPA");

                    // Populating the model with data from the ResultSet
                    do {
                        model.addRow(new Object[]{
                                resultSet.getString("student_ID"),
                                resultSet.getString("student_name"),
                                resultSet.getString("course"),
                                resultSet.getString("module"),
                                resultSet.getString("total_percentage"),
                                resultSet.getString("gpa")
                        });
                    } while (resultSet.next());

                    // Setting the model for the JTable
                    showResult.setModel(model);
                } else {
                    // If the result set is empty, display a message
                    JOptionPane.showMessageDialog(null, "No results found for the given ID.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                    // Clear the labels and table
                    dynamicName.setText("");
                    dynamicCourse.setText("");
                    resultLabel.setText(""); // Clear the result label
                    showResult.setModel(new DefaultTableModel());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching for result: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
