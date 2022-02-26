package lms.helpers;

import java.io.IOException;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lms.App;
import lms.models.User;

public class Connect 
{

    // private static Path dbFilePath = Paths.get("/db/dblms.db");

    // private static String dbUrl = App.loadDb().toString();
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException, IOException
    {
        preparedStatement = getConnection().prepareStatement(sql);
        return preparedStatement;
    }

    public static Statement getStatement() throws SQLException, IOException
    {
        if(statement == null)
        {
            statement = getConnection().createStatement();
        }

        return statement;
    }

    public static Connection getConnection() throws IOException
    {
        if (connection == null) 
        {
            connection = connect();    
        }

        return connection;
    }

    private static Connection connect() throws IOException {
        // SQLite connection string
        
        Connection conn = null;
        String dbUrl = App.getApplicationPath() + "/db/dblms.db";
        try {
            // Properties config = new Properties();
            // config.setProperty("open_mode", "0"); 

            conn = DriverManager.getConnection("jdbc:sqlite:/" + dbUrl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public ResultSet select(String tableName, String columns) throws IOException
    {

        String sql = "SELECT " + columns + " FROM " + tableName;
        
        try (Connection conn = getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))
        {
            
            return rs;
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }


        return null;
    }


    public User getUserData(Statement stmt) throws SQLException
    {
        String username;
        String password;

        // Connection conn = connect();
        // Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("SELECT * FROM user LIMIT 1");

        username = rs.getString("username");  
        password = rs.getString("password");  

        User user = new User(username, password);

        return user;
    }
}
