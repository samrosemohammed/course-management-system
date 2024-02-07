import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DashBoard {
    private JButton dashboardButton;
    private JButton coursesButton;
    private JButton tutorsButton;
    private JButton studentsButton;
    private JButton settingsButton;
    private JButton moduleButton;
    private JButton logoutButton;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JTextField courseSearch;
    private JButton deleteCourseButton;
    private JButton editCourseButton;
    private JButton addCourseButton;
    private JTable CourseTable;
    private JTextField tutorSearch;
    private JButton deleteTutorsButton;
    private JButton editTutorButton;
    private JButton addTutorsButton;
    private JTable TutorsTable;
    private JTextField studentSearch;
    private JButton veiwProjectButton;
    private JButton deleteStudentButton;
    private JButton editStudentButton;
    private JTable StudentTable;
    private JTextField settingUserName;
    private JTextField settingUserEmail;
    private JPasswordField oldPassword;
    private JPasswordField newPassword;
    private JPanel DashboardPane;
    private JButton editInfoButton;
    private JButton savePasswordButton;
    private JLabel totalCourse;
    private JLabel totalTeacher;
    private JLabel totalStudents;
    private JButton createStudentReportButton;
    private JTextField moduleSearch;
    private JButton addModuleButton;
    private JTable ModuleTable;
    private JButton enrollClicked;
    private JTable table2;
    private JButton enrollButton;
    private String loggedInUserName;
    private String loggedInUserEmail;
    private String userType;


    public DashBoard(String loggedInUserName, String loggedInUserEmail, String userType) {
        this.loggedInUserEmail = loggedInUserEmail;
        this.loggedInUserName = loggedInUserName;
        this.userType = userType;

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCourse addCourse = new AddCourse();
                addCourse.showAddCourse();
            }
        });
        editCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditCourse editCourse = new EditCourse();
                editCourse.showEditCourse();
            }
        });
        deleteCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteCourse deleteCourse = new DeleteCourse();
                deleteCourse.showDeleteCourse();
            }
        });
        addTutorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddTutors addTutors = new AddTutors();
                addTutors.showAddTutor();
            }
        });
        editTutorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditTutors editTutors = new EditTutors();
                editTutors.showEditTutor();
            }
        });
        deleteTutorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteTutors deleteTutors = new DeleteTutors();
                deleteTutors.showDeleteTutor();
            }
        });
        editStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditStudent editStudent = new EditStudent();
                editStudent.showEditStudent();
            }
        });
        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteStudent deleteStudent = new DeleteStudent();
                deleteStudent.showDeleteStudent();
            }
        });
        veiwProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProgressReport progressReport = new ProgressReport();
                progressReport.showProgressReport();
            }
        });
        createStudentReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateStudentReport createStudentReport = new CreateStudentReport();
                createStudentReport.showStudentReport();
            }
        });
        savePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        addModuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddModule addModule = new AddModule();
                addModule.showAddModule();
            }
        });
        editInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditInfo editInfo = new EditInfo(userType, loggedInUserEmail);
                editInfo.showEditInfo();
            }
        });
        enrollClicked.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentEnrollment enrollStudent = new StudentEnrollment();
                enrollStudent.showStudentEnrollment();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    // Close the dashboard panel
                    closeDashboard();
                }
            }
        });

        courseSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterCourses();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterCourses();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterCourses();
            }
        });
        tutorSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTutors();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTutors();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTutors();
            }
        });
        studentSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterStudents();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterStudents();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterStudents();
            }
        });
        moduleSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterModules();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterModules();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterModules();
            }
        });
    }

    private void filterCourses() {
        try {
            // Get the text entered in the courseSearch field
            String searchText = courseSearch.getText().trim().toLowerCase();

            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL query to retrieve courses based on the entered alphabet
            String query = "SELECT * FROM course WHERE LOWER(courseName) LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, searchText + "%"); // Search for courses starting with the entered alphabet
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the filtered data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("CourseID");
                model.addColumn("CourseName");
                model.addColumn("NoOfSeats");
                model.addColumn("Batch");
                model.addColumn("NoOfYears");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("courseID"),
                            resultSet.getString("courseName"),
                            resultSet.getInt("noOfSeats"),
                            resultSet.getString("batch"),
                            resultSet.getInt("noOfYears")
                    });
                }

                // Setting the model for the CourseTable JTable
                CourseTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error filtering courses: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTutors() {
        try {
            // Get the text entered in the tutorSearch field
            String searchText = tutorSearch.getText().trim().toLowerCase();

            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL query to retrieve tutors based on the entered alphabet
            String query = "SELECT * FROM teacher WHERE LOWER(TutorName) LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, searchText + "%"); // Search for tutors starting with the entered alphabet
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the filtered data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("TutorID");
                model.addColumn("TutorName");
                model.addColumn("Email");
                model.addColumn("Date");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("TutorID"),
                            resultSet.getString("TutorName"),
                            resultSet.getString("Email"),
                            resultSet.getString("Date")
                    });
                }

                // Setting the model for the TutorsTable JTable
                TutorsTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error filtering tutors: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterStudents() {
        try {
            // Get the text entered in the studentSearch field
            String searchText = studentSearch.getText().trim().toLowerCase();

            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL query to retrieve students based on the entered alphabet
            String query = "SELECT SID, username, email, last_login_date FROM student_login WHERE LOWER(username) LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, searchText + "%"); // Search for students whose names start with the entered alphabet
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the filtered data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("SID");
                model.addColumn("Username");
                model.addColumn("Email");
                model.addColumn("Last Login Date");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("SID"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("last_login_date")
                    });
                }

                // Setting the model for the StudentTable JTable
                StudentTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error filtering students: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterModules() {
        try {
            // Get the text entered in the moduleSearch field
            String searchText = moduleSearch.getText().trim().toLowerCase();

            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL query to retrieve modules based on the entered alphabet
            String query = "SELECT level, module_1, module_2, module_3, module_4, course FROM course_modules WHERE LOWER(module_1) LIKE ? " +
                    "OR LOWER(module_2) LIKE ? OR LOWER(module_3) LIKE ? OR LOWER(module_4) LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set parameters for the query to search for modules whose names contain the entered alphabet
                for (int i = 1; i <= 4; i++) {
                    preparedStatement.setString(i, "%" + searchText + "%");
                }
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the filtered data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Level");
                model.addColumn("Module 1");
                model.addColumn("Module 2");
                model.addColumn("Module 3");
                model.addColumn("Module 4");
                model.addColumn("Course");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("level"),
                            resultSet.getString("module_1"),
                            resultSet.getString("module_2"),
                            resultSet.getString("module_3"),
                            resultSet.getString("module_4"),
                            resultSet.getString("course"),
                    });
                }

                // Setting the model for the ModuleTable JTable
                ModuleTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error filtering modules: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void closeDashboard() {
        JFrame dashboardFrame = (JFrame) SwingUtilities.getWindowAncestor(DashboardPane);
        dashboardFrame.dispose();
    }

    public void showDashboard() {
        JFrame dashboardFrame = new JFrame("Dashboard");
        dashboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dashboardFrame.setContentPane(this.DashboardPane);  // Assuming this is the main container for your dashboard UI
        dashboardFrame.setSize(1000, 600);
        dashboardFrame.pack();
        dashboardFrame.setLocationRelativeTo(null); // Center the frame
        dashboardFrame.setVisible(true);

        // Adding action listeners to the buttons
        dashboardButton.addActionListener(new TabButtonClickListener(0));
        coursesButton.addActionListener(new TabButtonClickListener(1));
        tutorsButton.addActionListener(new TabButtonClickListener(2));
        studentsButton.addActionListener(new TabButtonClickListener(3));
        moduleButton.addActionListener(new TabButtonClickListener(4));
        enrollButton.addActionListener(new TabButtonClickListener(5));
        settingsButton.addActionListener(new TabButtonClickListener(6));

        // for count
        updateCounts();

        // display module table
        updateModuleTable();

        // display student table
        updateStudentTable();

        // display tutors table
        updateTutorsTable();

        // display course table
        updateCourseTable();

        // display enroll table
        updateEnrollTable();

        // for setting detail
        profileDetails();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    // ActionListener to switch tabs based on the button clicked
    private class TabButtonClickListener implements ActionListener {
        private int tabIndex;

        public TabButtonClickListener(int tabIndex) {
            this.tabIndex = tabIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tabbedPane1.setSelectedIndex(tabIndex);
        }
    }

    private void updateCounts() {
        try {
            int teacherCount = getTeacherCount();
            int studentCount = getStudentCount();
            int courseCount = getCourseCount();
            // int enrollCount = getEnrolledStudentCount();

            totalTeacher.setText(String.valueOf(teacherCount));
            totalStudents.setText(String.valueOf(studentCount));
            totalCourse.setText(String.valueOf(courseCount));
            // totalEnroll.setText(String.valueOf(enrollCount)); // Assuming you have a JLabel for enrolled students count
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating counts: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getTeacherCount() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM teacher")) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }
    private int getCourseCount() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM course")) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }

    private int getStudentCount() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM student_login")) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int studentCount = resultSet.getInt("count");
                    studentCount += getEnrolledStudentCount(); // Add the count of enrolled students
                    return studentCount;
                }
            }
        }
        return 0;
    }

    private int getEnrolledStudentCount() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "")) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM student_enroll")) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }

    public void profileDetails() {
        settingUserEmail.setText(loggedInUserEmail);
        settingUserName.setText(loggedInUserName);
    }

    public void changePassword() {
        // Get the entered passwords from the password fields
        char[] oldPasswordChars = oldPassword.getPassword();
        char[] newPasswordChars = newPassword.getPassword();

        // Validate input fields
        if (oldPasswordChars.length == 0 || newPasswordChars.length == 0) {
            JOptionPane.showMessageDialog(null, "Please fill in both old and new password fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return; // Stop processing if validation fails
        }

        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String dbPassword = "";

            // Establish a connection to the database
            try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
                // Determine the user type to select the appropriate table
                String userTypeTable = "";
                switch (userType) {
                    case "Student":
                        userTypeTable = "student_login";
                        break;
                    case "Teacher":
                        userTypeTable = "teacher_login";
                        break;
                    case "Admin":
                        userTypeTable = "admin_login";
                        break;
                    default:
                        System.out.println("Unsupported user type");
                        return;
                }

                // Prepare and execute an SQL statement to check the old password
                String checkPasswordQuery = "SELECT * FROM " + userTypeTable + " WHERE email = ? AND password = ?";
                try (PreparedStatement checkPasswordStatement = connection.prepareStatement(checkPasswordQuery)) {
                    checkPasswordStatement.setString(1, loggedInUserEmail);
                    checkPasswordStatement.setString(2, new String(oldPasswordChars));

                    ResultSet resultSet = checkPasswordStatement.executeQuery();

                    if (resultSet.next()) {
                        // Old password matches, proceed to update the password
                        // Prepare and execute an SQL statement to update the password
                        String updatePasswordQuery = "UPDATE " + userTypeTable + " SET password = ? WHERE email = ?";
                        try (PreparedStatement updatePasswordStatement = connection.prepareStatement(updatePasswordQuery)) {
                            // Set parameters for the prepared statement
                            updatePasswordStatement.setString(1, new String(newPasswordChars));
                            updatePasswordStatement.setString(2, loggedInUserEmail);

                            // Execute the update
                            int affectedRows = updatePasswordStatement.executeUpdate();

                            if (affectedRows > 0) {
                                // Password update successful
                                System.out.println("Password update successful");
                                JOptionPane.showMessageDialog(null, "Password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                // Password update failed
                                System.out.println("Password update failed");
                                JOptionPane.showMessageDialog(null, "Password update failed", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        // Old password does not match
                        System.out.println("Old password does not match");
                        JOptionPane.showMessageDialog(null, "Old password does not match", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating password: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateModuleTable() {
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT level, module_1, module_2, module_3, module_4, course FROM course_modules";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Level");
                model.addColumn("Module 1");
                model.addColumn("Module 2");
                model.addColumn("Module 3");
                model.addColumn("Module 4");
                model.addColumn("Course");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("level"),
                            resultSet.getString("module_1"),
                            resultSet.getString("module_2"),
                            resultSet.getString("module_3"),
                            resultSet.getString("module_4"),
                            resultSet.getString("course"),
                    });
                }

                // Setting the model for the JTable
                ModuleTable.setModel(model); // Assuming the JTable in ModulePanel is named 'table1'
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating module table: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudentTable() {
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT SID, username, email, last_login_date FROM student_login";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("SID");
                model.addColumn("Username");
                model.addColumn("Email");
                model.addColumn("Last Login Date");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("SID"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("last_login_date")
                    });
                }

                // Setting the model for the JTable
                StudentTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating student table: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTutorsTable() {
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM teacher";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("TutorID");
                model.addColumn("TutorName");
                model.addColumn("Email");
                model.addColumn("Date");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("TutorID"),
                            resultSet.getString("TutorName"),
                            resultSet.getString("Email"),
                            resultSet.getString("Date")
                    });
                }

                // Setting the model for the JTable
                TutorsTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating tutors table: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateCourseTable() {
        try {
            // Replace the URL, username, and password with your actual database information
            String url = "jdbc:mysql://localhost:3306/login";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM course";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("CourseID");
                model.addColumn("CourseName");
                model.addColumn("NoOfSeats");
                model.addColumn("Batch");
                model.addColumn("NoOfYears");

                // Populating the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("courseID"),
                            resultSet.getString("courseName"),
                            resultSet.getInt("noOfSeats"),
                            resultSet.getString("batch"),
                            resultSet.getInt("noOfYears")
                    });
                }

                // Setting the model for the JTable
                CourseTable.setModel(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating course table: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateEnrollTable() {
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");

            // Define SQL query to select data from student_enroll table
            String query = "SELECT * FROM student_enroll";

            // Prepare statement and execute query
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creating a DefaultTableModel to store the data for the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Student Name");
                model.addColumn("Student Email");
                model.addColumn("Course");
                model.addColumn("Level");
                model.addColumn("Module 1");
                model.addColumn("Module 2");

                // Populate the model with data from the ResultSet
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getString("studentName"),
                            resultSet.getString("studentEmail"),
                            resultSet.getString("course"),
                            resultSet.getString("level"),
                            resultSet.getString("module_1"),
                            resultSet.getString("module_2")
                    });
                }

                // Setting the model for the JTable
                table2.setModel(model); // Assuming the JTable in EnrollmentPanel is named 'table1'
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating enrollment table: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
