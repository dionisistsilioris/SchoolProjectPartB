package privateschool;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Student {

    public static final String MYSQL_JDBC_DRIVER ="com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private double tuitionFees;

    public Student(String firstName, String lastName, String dateOfBirth, double tuitionFees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public static void selectAllStudents() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

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
            if (preparedStatement!= null) {
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
    }

    public static void insertStudents(String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "INSERT INTO STUDENTS(FIRST_NAME, LAST_NAME,DATE_OF_BIRTH,TUITION_FEES) VALUES ('" + firstName + "', '" + lastName + "', '" + dateOfBirth + "', '" + tuitionFees + "')";
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

    public static void addStudent() {
        Scanner scan = new Scanner(System.in);
        String firstName;
        String lastName;
        String dateOfBirth;
        double tuitionFees;
        String answer;

        do {
            do {
                System.out.print("please enter your first name.");
                firstName = scan.nextLine();
            } while (!Validations.validName(firstName));

            do {
                System.out.println("please enter your last name.");
                lastName = scan.nextLine();
            } while (!Validations.validName(lastName));
            do {
                System.out.println("please enter your birth date.");
                System.out.println("the date must be in this format day-Month-year.");
                dateOfBirth = scan.next();
            } while (!Validations.validDate(dateOfBirth));
                   LocalDate dob=Validations.strToLocalDate(dateOfBirth);  
            do {
                System.out.println("please enter the tuition fees.");
                tuitionFees = scan.nextDouble();
                scan.nextLine();
            } while (!Validations.validationPositive(tuitionFees));

            Student.insertStudents(firstName, lastName, dob, tuitionFees);

            System.out.println("do you want to continue yes or no;");
            answer = scan.nextLine();
        } while (!Validations.validQuestion(answer));
    }
    
    
    
public static boolean isValidStudentId(int number){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();

            String query = "SELECT * FROM STUDENTS"
                    + "  WHERE ST_ID=" + number;

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
        return "Student{" + "firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", tuitionFees=" + tuitionFees + '}';
    }


   }