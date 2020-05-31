package privateschool;

import java.sql.*;
import java.util.Scanner;
import static privateschool.Student.DB_URL;
import static privateschool.Student.MYSQL_JDBC_DRIVER;
import static privateschool.Student.PASSWORD;
import static privateschool.Student.USERNAME;
import static privateschool.TrainerCourses.DB_URL;
import static privateschool.TrainerCourses.MYSQL_JDBC_DRIVER;
import static privateschool.TrainerCourses.PASSWORD;
import static privateschool.TrainerCourses.USERNAME;

public class StudentCourse {

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";

    private Integer stco_id;
    private Integer st_id;
    private Integer co_id;

    public StudentCourse(Integer stco_id, Integer st_id, Integer co_id) {
        this.stco_id = stco_id;
        this.st_id = st_id;
        this.co_id = co_id;
    }

    public Integer getStco_id() {
        return stco_id;
    }

    public void setStco_id(Integer stco_id) {
        this.stco_id = stco_id;
    }

    public Integer getSt_id() {
        return st_id;
    }

    public void setSt_id(Integer st_id) {
        this.st_id = st_id;
    }

    public Integer getCo_id() {
        return co_id;
    }

    public void setCo_id(Integer co_id) {
        this.co_id = co_id;
    }

    public static void getStudentsCourses() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "select c.title, s.last_name,s.first_name\n"
                    + "from students_courses stco, students s, courses c\n"
                    + "where stco.students_st_id=s.st_id\n"
                    + "and stco.courses_co_id=c.co_id\n"
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

    public static void getMoreThanOneStudentCourses() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "select st_id, st.first_name, st.last_name, count(st_id)\n"
                    + "from students st,students_courses stco\n"
                    + "where stco.students_st_id=st.st_id\n"
                    + "group by st_id\n"
                    + "having count(st_id)>1;";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                int st_id = resultSet.getInt("st_id");
                String last_name = resultSet.getString("first_name");
                String first_name = resultSet.getString("last_name");

                System.out.println("st_id: " + st_id + " first_name: " + first_name + " last_name: " + last_name);
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

    public static int chooseStudent() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        int student_id = 0;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database.....");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("connection created successfully.");

            String query = "SELECT st_id,first_name,last_name FROM STUDENTS";
            preparedStatement = connection.prepareStatement(query);
            resultset = preparedStatement.executeQuery(query);

            while (resultset.next()) {
                int st_id = resultset.getInt("st_id");
                String first_name = resultset.getString("first_name");
                String last_name = resultset.getString("last_name");

                System.out.println("Student id: " + st_id + " Name: " + first_name + " Surname: " + last_name);
            }
            Scanner scan = new Scanner(System.in);
            System.out.println("choose student_id");
            student_id = scan.nextInt();

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
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
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
        return student_id;
    }

    public static int chooseCourse() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        int courses_id = 0;

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
            courses_id = scan.nextInt();

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
        return courses_id;
    }

    public static void insertStudentToCourse(int student_id, int courses_id) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "INSERT INTO STUDENTS_COURSES(STUDENTS_ST_ID ,COURSES_CO_ID) VALUES ('" + student_id + "', '" + courses_id + "')";
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
