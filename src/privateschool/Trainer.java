
package privateschool;
import java.sql.*;
import java.util.Scanner;

public class Trainer {
    
    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";
    
    private int id;
    private String firstName;
    private String lastName;
    private String subject;

    public Trainer(int id, String firstName, String lastName, String subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
     

      public static void selectAllTrainers() {
      
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement=connection.createStatement();
            String query="SELECT TR_ID,FIRST_NAME,LAST_NAME,SUBJECT FROM TRAINERS";
            resultSet=statement.executeQuery(query);
            
            while(resultSet.next()){
            int tr_id=resultSet.getInt("tr_id");
            String first_name=resultSet.getString("first_name");
            String last_name=resultSet.getString("last_name");
            String subject=resultSet.getString("subject");
                System.out.println("trainer id: "+tr_id+" name: "+first_name+" surname: "+last_name+" subject: "+subject);
            }
            
        }catch(ClassNotFoundException | SQLException e){
                e.printStackTrace();
                System.out.println("Something went wrong!");
                }
          finally {
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
      
      public static void insertTrainers(String firstName, String lastName, String subject) {
        
        Statement statement = null;
        Connection connection = null;
        
     
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement = connection.createStatement();
            String query = "INSERT INTO TRAINERS(FIRST_NAME, LAST_NAME, SUBJECT) VALUES ('" + firstName + "', '" + lastName + "', '" + subject + "')";
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

          public static void addTrainer() {
        Scanner scan = new Scanner(System.in);
        String firstName;
        String lastName;
        String subject;
        String answer;

        do {
            do {
                System.out.println("please enter the first name of trainer.");
                firstName = scan.nextLine();
            } while (!Validations.validName(firstName));
            do {
                System.out.println("please enter the last name of trainer.");
                lastName = scan.nextLine();
            } while (!Validations.validName(lastName));
            do {
                System.out.println("please enter the subject.");
                subject = scan.nextLine();
            } while (!Validations.validName(subject));


    Trainer.insertTrainers( firstName, lastName, subject);

            System.out.println("do you want to continue yes or no");
            answer = scan.nextLine();
        } while (!Validations.validQuestion(answer));
    }


    @Override
    public String toString() {
        return "Trainer{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", subject=" + subject + '}';
    }

      
}
