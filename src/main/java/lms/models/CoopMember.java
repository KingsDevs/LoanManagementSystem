package lms.models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lms.helpers.Connect;

public class CoopMember 
{
    private String firstname;    
    private String middlename;    
    private String lastname;    
    private String position;    
    private String address;
    private int age; 

    
    public CoopMember(String firstname, String middlename, String lastname, String position, String address, int age)
    {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.position = position;
        this.address = address;
        this.age = age;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getMiddlename()
    {
        return middlename;
    }

    public String getLastname()
    {
        return lastname;
    }

    public String getPosition()
    {
        return position;
    }

    public String getAddress()
    {
        return address;
    }

    public int getAge()
    {
        return age;
    }

    public static ResultSet getMembers() throws SQLException
    {
        ResultSet resultSet = Connect.getStatement().executeQuery("SELECT * FROM coop_members ORDER BY lastname");
        return resultSet;
    }

    public static boolean isExist(String firstname, String middlename, String lastname) throws SQLException
    {
        String sql = "SELECT firstname, middlename, lastname "
                   + "FROM coop_members "
                   + "WHERE firstname = ?"
                   + " AND middlename = ?"
                   + " AND lastname = ?";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        
        preparedStatement.setString(1, firstname);
        preparedStatement.setString(2, middlename);
        preparedStatement.setString(3, lastname);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) 
        {
            //System.out.println(resultSet.getString("firstname"));
            if(resultSet.getString("firstname") != null)
            {
                return true;
            }    
        }

        return false;
    }

    public void insertCoopMember()
    {
        String sql = "INSERT INTO coop_members(firstname, middlename, lastname, address, position, age) "
                    +"VALUES(?,?,?,?,?,?)";
        
        try (PreparedStatement preparedStatement = Connect.getPreparedStatement(sql)) 
        {
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, middlename);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, position);
            preparedStatement.setInt(6, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }
}
