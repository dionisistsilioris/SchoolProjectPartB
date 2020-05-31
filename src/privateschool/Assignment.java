package privateschool;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Assignment {

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";

    private String title;
    private String description;
    private String subDateTime;
    private double oralMark;
    private double totalMark;

    public Assignment(String title, String description, String subDateTime, double oralMark, double totalMark) {
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubDateTime() {
        return subDateTime;
    }

    public void setSubDateTime(String subDateTime) {
        this.subDateTime = subDateTime;
    }

    public double getOralMark() {
        return oralMark;
    }

    public void setOralMark(double oralMark) {
        this.oralMark = oralMark;
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark) {
        this.totalMark = totalMark;
    }

    public static void selectAllAssignments() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database.....");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("connection created successfully.");

            statement = connection.createStatement();

            String query = "SELECT ass_id,title,description,sub_date_time,oral_mark,total_mark FROM ASSIGNMENTS";

            resultset = statement.executeQuery(query);

            while (resultset.next()) {
                int ass_id = resultset.getInt("ass_id");
                String title = resultset.getString("title");
                String description = resultset.getString("description");
                String subDateTime = resultset.getString("sub_date_time");
                Double oralMark = resultset.getDouble("oral_mark");
                Double totalMark = resultset.getDouble("total_mark");

                System.out.println("ass_id: " + ass_id + "  title: " + title + "  description: " + description + " sub_date_time: " + subDateTime + " oral_mark: " + oralMark + " total_mark: " + totalMark);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
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

    public static void insertAssignments(int courseId, String title, String description, LocalDate subDateTime, Double oralMark, Double totalMark) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "INSERT INTO ASSIGNMENTS(CO_ID ,TITLE, DESCRIPTION, SUB_DATE_TIME, ORAL_MARK, TOTAL_MARK) VALUES ('" + courseId + "','" + title + "', '" + description + "', '" + subDateTime + "', '" + oralMark + "', '" + totalMark + "')";
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

    public static void addAssignment() {
        Scanner scan = new Scanner(System.in);

        String title;
        String description;
        String subDateTime;
        double oralMark;
        double totalMark;
        int courseId;
        String answer;

        do {
           
            System.out.println("please select the course id(co_id)\n you want to insert the assignment.");
            Course.selectAllCourses();
            System.out.println("enter here the co_id->");
            courseId = Validations.getValidCourseId();

           
            System.out.println("please enter the title of assignment.");
            title = scan.nextLine();
            scan.nextLine();

            System.out.println("please enter the description of assignment.");
            description = scan.nextLine();

            do {
                System.out.println("please enter the sub date time.");
                System.out.println("the date must be in this format day-Month-year.");
                subDateTime = scan.nextLine();
            } while (!Validations.validDate(subDateTime));
            LocalDate deadLine = Validations.strToLocalDate(subDateTime);
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

            Assignment.insertAssignments(courseId, title, description, deadLine, oralMark, totalMark);

            System.out.println("do you want to continue yes or no;");
            answer = scan.nextLine();
        } while (!Validations.validQuestion(answer));
    }

    public static boolean isValidAssignmentId(int number) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();

            String query = "SELECT * FROM ASSIGNMENTS"
                    + "  WHERE ASS_ID=" + number;

            resultset = statement.executeQuery(query);

            if (resultset.next()) {
                return true;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
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
        return false;

    }

    public static int chooseAssignments(int stid) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
   
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
     

            statement = connection.createStatement();

            String query = "SELECT s.st_id as sid, s.first_name, s.last_name, courses.title as course,\n"
                    + "a.title as assignments, a.ass_id\n"
                    + "FROM students s\n"
                    + "JOIN students_courses ON students_courses.students_st_id = s.st_id\n"
                    + "JOIN courses ON courses.co_id = students_courses.courses_co_id\n"
                    + "JOIN assignments a ON a.co_id = courses.co_id\n"
                    + "WHERE s.st_id=" + stid +" AND NOT EXISTS (SELECT 1 FROM students_assignments stass WHERE stass.students_st_id = s.st_id AND stass.assignments_ass_id = a.ass_id)\n"
                    + "ORDER BY sid;";

            resultset = statement.executeQuery(query);
            int counter = 0;
            while (resultset.next()) {
                counter++;
                System.out.println("student id: "+resultset.getInt("sid")+" ass_id: " + resultset.getInt("ass_id") +" first_name: "+resultset.getString("first_name")+" last_name: "+resultset.getString("last_name") + "course title: "+ resultset.getString("course")+" assignment: "+ resultset.getString("assignments"));
            }
            if(counter == 0) {
                System.out.println("No assignments for this student");
            }
            return counter;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
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
        return 0;
    }

    @Override
    public String toString() {
        return "Assignment{" + "title=" + title + ", description=" + description + ", subDateTime=" + subDateTime + ", oralMark=" + oralMark + ", totalMark=" + totalMark + '}';
    }

}
