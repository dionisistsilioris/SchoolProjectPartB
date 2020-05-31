package privateschool;

import java.sql.*;
import java.util.Scanner;
import static privateschool.Course.DB_URL;
import static privateschool.Course.MYSQL_JDBC_DRIVER;
import static privateschool.Course.PASSWORD;
import static privateschool.Course.USERNAME;
import static privateschool.Trainer.DB_URL;
import static privateschool.Trainer.MYSQL_JDBC_DRIVER;
import static privateschool.Trainer.PASSWORD;
import static privateschool.Trainer.USERNAME;

public class TrainerCourses {

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";

    public static void getTrainersCourses() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "select c.title, t.last_name, t.first_name\n"
                    + "from trainers_courses trco, trainers t, courses c\n"
                    + "where  trco.co_id=c.co_id\n"
                    + "and trco.tr_id=t.tr_id\n"
                    + "order by c.co_id;";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                String title = resultSet.getString("title");
                String last_name = resultSet.getString("last_name");
                String first_name = resultSet.getString("first_name");

                System.out.println("title: " + title + " last_name: " + last_name + " first_name: " + first_name);
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

    public static int chooseCourse() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        int course_id = 0;

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
                Scanner scan = new Scanner(System.in);
                System.out.println("choose course_id");
                course_id = scan.nextInt();

            

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
        return course_id;
    }

    public static int chooseTrainer() {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int trainer_id=0;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "SELECT TR_ID,FIRST_NAME,LAST_NAME,SUBJECT FROM TRAINERS";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int tr_id = resultSet.getInt("tr_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String subject = resultSet.getString("subject");
                System.out.println("trainer id: " + tr_id + " name: " + first_name + " surname: " + last_name + " subject: " + subject);
                    
                
            }
            
                Scanner scan = new Scanner(System.in);
                System.out.println("choose trainer_id");
                    trainer_id = scan.nextInt();
                    
           
            

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
        return trainer_id;
    }

    public static void insertTrainerToCourses(int course_id, int trainer_id) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "INSERT INTO TRAINERS_COURSES(CO_ID , TR_ID) VALUES ('" + course_id + "', '" + trainer_id + "')";
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

}
