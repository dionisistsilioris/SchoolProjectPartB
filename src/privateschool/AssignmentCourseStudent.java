package privateschool;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import static privateschool.Student.DB_URL;
import static privateschool.Student.MYSQL_JDBC_DRIVER;
import static privateschool.Student.PASSWORD;
import static privateschool.Student.USERNAME;

public class AssignmentCourseStudent {

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";

    public static void getAssignmentCourseStudent() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "    SELECT s.st_id as sid, s.first_name, s.last_name, courses.title as course,\n"
                    + "assignments.title as assignment, students_assignments.oral_mark, students_assignments.total_mark\n"
                    + "FROM students s\n"
                    + "JOIN students_courses ON students_courses.students_st_id = s.st_id\n"
                    + "JOIN courses ON courses.co_id = students_courses.courses_co_id\n"
                    + "JOIN assignments ON assignments.co_id = courses.co_id\n"
                    + "JOIN students_assignments ON students_assignments.assignments_ass_id = assignments.ass_id \n"
                    + "and students_assignments.students_st_id=s.st_id\n"
                    + "UNION ALL\n"
                    + "SELECT s.st_id as sid, s.first_name, s.last_name, courses.title as course,\n"
                    + "a.title as assignments, 'N/A', 'N/A'\n"
                    + "FROM students s\n"
                    + "JOIN students_courses ON students_courses.students_st_id = s.st_id\n"
                    + "JOIN courses ON courses.co_id = students_courses.courses_co_id\n"
                    + "JOIN assignments a ON a.co_id = courses.co_id\n"
                    + "WHERE NOT EXISTS (SELECT 1 FROM students_assignments stass WHERE stass.students_st_id = s.st_id AND stass.assignments_ass_id = a.ass_id)\n"
                    + "ORDER BY sid;";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {


                System.out.println("course title: " + resultSet.getString(4) + " last_name: " + resultSet.getString(3)+ " first_name: "
                        + resultSet.getString(2)+ " assignment title: " + resultSet.getString(5) + " oral_mark: " + resultSet.getObject(6) + " total_mark: " + resultSet.getObject(7));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
       public static void insertStudentsAssignments(int studentId, int assignmentId, Double oralMark, Double totalMark ) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "INSERT INTO students_assignments(students_st_id,assignments_ass_id,oral_mark,total_mark) VALUES ('" + studentId + "', '" +assignmentId + "', '" + oralMark + "', '" + totalMark + "')";
            int result = statement.executeUpdate(query);
            System.out.println(result + " row(s) affected.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
  public static void addStudentAssignment() {
        Scanner scan = new Scanner(System.in);

       
        double oralMark;
        double totalMark;
        int studentId;
        int assignmentId;
        String answer;

        do {
             
                System.out.println("please select the student id(st_id)\n you want to insert.");
                Student.selectAllStudents();
                System.out.println("enter here the st_id->");
                studentId=Validations.getValidStudentId();
                
                System.out.println("please select the assignment id(ass_id)\n you want to insert.");
                int n = Assignment.chooseAssignments(studentId);
                if(n == 0) {
                    return;
                }
                System.out.println("enter here the ass_id->");
                assignmentId=Validations.getValidAssignmentId();
              
      
            do {
                System.out.println("please enter the oral mark.");
                oralMark = scan.nextDouble();
                scan.nextLine();
            } while (!Validations.validMark(oralMark));
            do {
                System.out.println("please enter the total mark.");
                totalMark = scan.nextDouble();
                scan.nextLine();
            } while (!Validations.validMark(totalMark));
            
           AssignmentCourseStudent.insertStudentsAssignments(studentId , assignmentId, oralMark, totalMark);
         

            System.out.println("do you want to continue yes or no;");
            answer = scan.nextLine();
        } while (!Validations.validQuestion(answer));
      }
    
}
