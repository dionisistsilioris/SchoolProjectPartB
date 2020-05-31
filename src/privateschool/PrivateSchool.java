package privateschool;

import java.util.Scanner;
import java.sql.*;
import java.util.InputMismatchException;
//----------------------------------------------------------------------------------------------------------------------------

public class PrivateSchool {

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    public static final String USERNAME = "dionisis";
    public static final String PASSWORD = "1987";

//----------------------------------------------------------------------------------------------------------------------------
    public void enterDataFromUser() {
        int option;
        do {
            System.out.println("");
            System.out.println("please choose the category that you want to insert your data: ");
            System.out.println("");
            System.out.println("1. if you want to insert student.");
            System.out.println("2. if you want to insert trainer.");
            System.out.println("3. if you want to insert Assignment.");
            System.out.println("4. if you want to insert Course.");
            System.out.println("5. if you want to insert Student per course.");
            System.out.println("6. if you want to insert trainer per course.");
            System.out.println("7. if you want to insert assignments per student per course.");
            System.out.println("8. return to the main menu.");
            System.out.println("0. EXIT.");

            Scanner scan = new Scanner(System.in);

            try {
                option = scan.nextInt();
            } catch (InputMismatchException ie) {
                scan.nextLine();
                option = 100;
            }

            switch (option) {
                case (1):
                    Student.addStudent();
                    break;
                case (2):
                    Trainer.addTrainer();
                    break;
                case (3):
                    Assignment.addAssignment();
                    break;
                case (4):
                    Course.addCourse();
                    break;
                case (5):
                    int student_id = StudentCourse.chooseStudent();
                    int courses_id = StudentCourse.chooseCourse();
                    StudentCourse.insertStudentToCourse(student_id, courses_id);
                    break;
                case (6):
                    int course_id = TrainerCourses.chooseCourse();
                    int trainer_id = TrainerCourses.chooseTrainer();
                    TrainerCourses.insertTrainerToCourses(course_id, trainer_id);
                    break;
                case (7):
                    AssignmentCourseStudent.addStudentAssignment();
                    break;
                case (8):
                    mainMenu();
                case (100):
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (option != 0);

    }
//----------------------------------------------------------------------------------------------------------------------------

    public void displayMenu() {
        System.out.println("");
        System.out.println("please choose the category that you want to see the information: ");
        System.out.println("");
        System.out.println("1. if you want to see all students.");
        System.out.println("2. if you want to see all courses.");
        System.out.println("3. if you wantto see all assignments.");
        System.out.println("4. if you wantto see all trainers.");
        System.out.println("5. if you want to see all the students per course.");
        System.out.println("6. if you want to see all the trainers per course.");
        System.out.println("7. if you want to see all the assignments per course.");
        System.out.println("8. if you want to see all the assignments per course per student.");
        System.out.println("9. if you want to see a list of students that belong to more than one courses.");
        System.out.println("10. return to the main menu.");
        System.out.println("0. EXIT.");
        System.out.print("-> ");

    }
//----------------------------------------------------------------------------------------------------------------------------

    public void menu() {

        int choice = 0;
        Scanner scan = new Scanner(System.in);

        do {
            displayMenu();
            try {
                choice = scan.nextInt();
            } catch (InputMismatchException ie) {
                scan.nextLine();
                choice = 100;
            }
            switch (choice) {
                case (1):
                    Student.selectAllStudents();
                    break;
                case (2):
                    Course.selectAllCourses();
                    break;
                case (3):
                    Assignment.selectAllAssignments();
                    break;
                case (4):
                    Trainer.selectAllTrainers();
                    break;
                case (5):
                    StudentCourse.getStudentsCourses();
                    break;
                case (6):
                    TrainerCourses.getTrainersCourses();
                    break;
                case (7):
                    AssignmentCourse.getAssignmentsCourses();
                    break;
                case (8):
                    AssignmentCourseStudent.getAssignmentCourseStudent();
                    break;
                case (9):
                    StudentCourse.getMoreThanOneStudentCourses();
                    break;
                case (100):
                    System.out.println("Invalid choice!");
                    break;
                case (10):
                    mainMenu();
            }
        } while (choice != 0);
    }

    public static void mainMenu() {
        PrivateSchool school = new PrivateSchool();
        Scanner scan = new Scanner(System.in);

        System.out.println("please select one of the below category:");
        System.out.println("1.sign up to our school.");
        System.out.println("2.information for our students.");
        int choice = scan.nextInt();
        if (choice == 1) {
            school.enterDataFromUser();
        } else {

            school.menu();
        }

    }

//-----------------------------------------------------------------------------------------------------------------------------        
    public static void main(String[] args) {
        System.out.println("Hello welcome to our school. \n");
        mainMenu();

    }

}
