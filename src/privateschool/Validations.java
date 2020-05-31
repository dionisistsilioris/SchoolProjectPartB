package privateschool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Validations {

    public static boolean validQuestion(String answer) {

        if (answer.equalsIgnoreCase("yes")) {
            return true;
        } else {
            System.out.println("addition completed.");
            return false;
        }
    }

    public static boolean validName(String name) {
        if (name.length() > 1 && name.matches("[a-zA-Z]*")) {
            return true;
        } else {
            System.out.println("wrong!please enter again your data.");
            return false;
        }
    }

    public static boolean validDate(String date) {

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            System.out.println("wrong date!please enter date.");
            return false;
        }
    }

    public static LocalDate strToLocalDate(String date) //format a String date to a Localdate format
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public static boolean validationPositive(double fee) {
        if (fee < 0) {
            System.out.println("wrong tuition fees!please enter again tuition fees.");
            return false;
        } else {
            return true;
        }
    }

    public static boolean validMark(double mark) {
        if (mark < 0) {
            System.out.println("wrong mark!please enter again mark.");
            return false;
        } else {
            return true;
        }
    }

    public static boolean validNumber(int number) {

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Please enter a positive number: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
                return false;
            }
            number = scanner.nextInt();

        } while (number < 0);
        System.out.printf("You have entered a positive number %d.\n", number);

        return true;
    }

public static int getValidCourseId(){
    int number=0;
    boolean idOK = false;
    do {
        number = getValidNumber();
        if(!Course.isValidCourseId(number)) {
            System.out.println("Not valid course ID");
        }
        else {
            idOK = true;
        }
    } while(!idOK);
    return number;
}
    
public static int getValidNumber(){
        Scanner scan=new Scanner (System.in);
        int number=0;
        do {
            System.out.print("Please enter a valid choice: ");
            while (!scan.hasNextInt()) {
                String input = scan.next();
                System.out.println(input + " is not a valid choice. You will have to retry.");
            }
            number = scan.nextInt();
        } while (number<1);
       
        return number;
    }

public static int getValidStudentId(){
    int number=0;
    boolean idOK = false;
    do {
        number = getValidNumber();
        if(!Student.isValidStudentId(number)) {
            System.out.println("Not valid student ID");
        }
        else {
            idOK = true;
        }
    } while(!idOK);
    return number;
}

public static int getValidAssignmentId(){
    int number=0;
    boolean idOK = false;
    do {
        number = getValidNumber();
        if(!Assignment.isValidAssignmentId(number)) {
            System.out.println("Not valid assignment ID");
        }
        else {
            idOK = true;
        }
    } while(!idOK);
    return number;
}
}


