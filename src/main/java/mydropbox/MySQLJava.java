package mydropbox;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by May on 5/21/2017 AD.
 */
public class MySQLJava {
    enum TestTableColumns {
        id, TEXT;
    }

    private final String jdbcDriverStr;
    private final String jdbcURL;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private HashMap<String,String> allUsers;

    public MySQLJava(String jdbcDriverStr, String jdbcURL) throws Exception{
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;
        readData();
    }

    public void readData() throws Exception {
        try {
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from myDropbox.users;");
            allUsers=getResultSet(resultSet);
        }
        catch (Exception e){
            System.out.println("mySQL error");
        }
    }

    private HashMap<String,String> getResultSet(ResultSet resultSet) throws Exception {
        HashMap<String,String> temp = new HashMap<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(TestTableColumns.id.toString());
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
//            System.out.println("id: " + id);
//            System.out.println("username: " + username);
//            System.out.println("password: " +password);
            temp.put(username,password);
        }
        return temp;
    }
    public String authen(String username,String password){
        if(hasUser(username)){
            if(allUsers.get(username).equals(password)){
                return "OK";
            }else{
                return "wrong password";
            }
        }
        else {
            return "this username is not in the system";
        }
    }
    public boolean addUser(String username,String password){
        if(!hasUser(username)){
            if(insertDB(username,password)){
                allUsers.put(username,password);
                return true;
            }
        }
        return false;
    }
    private boolean insertDB(String username,String password){
        try {
            // the mysql insert statement
            PreparedStatement preparedStmt;
//            if(opt.equals("add")){
                String query = " insert into users (username, password)"
                        + " values (?, ?)";
                // create the mysql insert preparedstatement
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString (1, username);
                preparedStmt.setString (2, password);
//            }
//            else {
//                String query = " DELETE FROM users WHERE username= (?)";
//                // create the mysql insert preparedstatement
//                preparedStmt = connection.prepareStatement(query);
//                preparedStmt.setString (1, username);
//
//            }
            // execute the preparedstatement
            preparedStmt.execute();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean hasUser(String username){
        return allUsers.containsKey(username);
    }
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
        }
    }
}
