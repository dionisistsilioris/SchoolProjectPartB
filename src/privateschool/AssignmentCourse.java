
package privateschool;
import java.sql.*;
import java.util.Scanner;


public class AssignmentCourse {
      public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";
    
     public static void getAssignmentsCourses() {
      
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connection created successfully.");
            statement=connection.createStatement();
            String query="select a.title, co.title cTitle\n" +
                         "from assignments a,courses co\n" +
                         "where a.co_id=co.co_id;";
            resultSet=statement.executeQuery(query);
            
            while(resultSet.next()){
           
            String title=resultSet.getString("title");
            String cTitle=resultSet.getString("cTitle");
            
           
                System.out.println("assignment title : " +title+ "course title: "+cTitle);
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
 

}
