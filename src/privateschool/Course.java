package privateschool;

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Course {

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";

    private String title;
    private String stream;
    private String type;
    private String startDate;
    private String endDate;

    public Course(String title, String stream, String type, String startDate, String endDate) {
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static void selectAllCourses() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database.....");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("connection created successfully.");

            statement = connection.createStatement();

            String query = "SELECT co_id,title,stream,type,start_date,end_date FROM COURSES";

            resultset = statement.executeQuery(query);

            while (resultset.next()) {
                int co_id = resultset.getInt("co_id");
                String title = resultset.getString("title");
                String stream = resultset.getString("stream");
                String type = resultset.getString("type");
                String startDate = resultset.getString("start_date");
                String endDate = resultset.getString("end_date");

                System.out.println("Co_id: " + co_id + " title: " + title + " stream: " + stream + " type: " + type + " start_date: " + startDate + " end_date: " + endDate);
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

    public static void insertCourses(String title, String stream, String type, LocalDate startDate, LocalDate endDate) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "INSERT INTO COURSES(TITLE, STREAM,TYPE,START_DATE,END_DATE) VALUES ('" + title + "', '" + stream + "', '" + type + "', '" + startDate + "', '" + endDate + "')";
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

    public static void addCourse() {
        Scanner scan = new Scanner(System.in);
        String title;
        String stream;
        String type;
        String startDate;
        String endDate;
        String answer;

        do {

            System.out.println("please enter the course title.");
            title = scan.nextLine();

            System.out.println("please enter the course stream.");
            stream = scan.nextLine();

            System.out.println("please enter the course type.");
            System.out.println("please choose between part time or full time.");
            type = scan.nextLine();

            do {
                System.out.println("please enter start date.");
                System.out.println("the date must be in this format day-Month-year.");
                startDate = scan.nextLine();
            } while (!Validations.validDate(startDate));
            LocalDate stda = Validations.strToLocalDate(startDate);
            do {
                System.out.println("please enter end date.");
                System.out.println("the date must be in this format day-Month-year.");
                endDate = scan.nextLine();
            } while (!Validations.validDate(endDate));
            LocalDate enda = Validations.strToLocalDate(endDate);

            Course.insertCourses(title, stream, type, stda, enda);

            System.out.println("do you want to continue yes or no;");
            answer = scan.nextLine();
        } while (!Validations.validQuestion(answer));
    }

    public static boolean isValidCourseId(int number) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();

            String query = "SELECT * FROM COURSES"
                    + "  WHERE CO_ID=" + number;

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

    @Override
    public String toString() {
        return "Course{" + "title=" + title + ", stream=" + stream + ", type=" + type + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }

}
