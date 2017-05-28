package mydropbox;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost/myDropbox?"
            + "user=root&password=root";

    public static void main( String[] args ) throws Exception
    {

        System.out.println( "Welcome to myDropbox Application");
        System.out.println( "======================================================");
        System.out.println( "Please input command (newuser username password password, login");
        System.out.println( "username password, put filename, get filename, view, or logout).");
        System.out.println( "If you want to quit the program just type quit.");
        System.out.println( "======================================================" );
        Scanner sin = new Scanner(System.in);
        String command = "";
        String username = "";
        Bucket bucket = new Bucket();
        MySQLJava sqlJava = new MySQLJava(MYSQL_DRIVER,MYSQL_URL);
        while(!command.equals("quit")){
            System.out.printf(">>");
            String commandIN = sin.nextLine();
            if(commandIN.contains("newuser")){
                String[] commands = commandIN.split(" ");
                if(commands[2].equals(commands[3])){
                    boolean re = sqlJava.addUser(commands[1],commands[2]);
                    if(re){
                        System.out.println("OK");
                    }
                    else {
                        System.out.println("cannot use this user :"+commands[1]);
                    }
                }
                else{
                    System.out.println("password mismatch");
                }

            }
            else if(commandIN.contains("login")){
                String[] commands = commandIN.split(" ");
                String authen = sqlJava.authen(commands[1],commands[2]);
                if(authen.equals("OK")){
                    username = commands[1];
                }
                System.out.println(authen);
            }
            else if(commandIN.contains("put")){
                String[] commands = commandIN.split(" ");
                if(!username.isEmpty()){
                    boolean re = bucket.addObjectToBucket(username,commands[1],commands[1]);
                    if(re){
                        System.out.println("OK");
                    }
                }
                else{
                    System.out.println("pls login");
                }

            }
            else if(commandIN.contains("view")){
                if(!username.isEmpty()){
                    bucket.viewObjectsInBucket(username);
                }
                else{
                    System.out.println("pls login");
                }
            }
            else if(commandIN.contains("get")){
                String[] commands = commandIN.split(" ");
                if(!username.isEmpty()){
                    if(bucket.downloadFile(username,commands[1])){
                        System.out.println("OK");
                    }
                }
                else{
                    System.out.println("pls login");
                }
            }
            else if(commandIN.contains("logout")){
                username = "";
                System.out.println("OK");
            }
            else if(commandIN.contains("quit")){
                sqlJava.close();
                System.out.println("======================================================");
                System.out.println("Thank you for using myDropbox.");
                System.out.println("See you again!");
                System.exit(1);
            }
            else{
                System.out.println("command is not valid");
            }
        }
    }
}
