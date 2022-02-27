package lms.models;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lms.helpers.Connect;

public class CoopMember 
{

    public int id;
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

    public int getId()
    {
        return id;
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

    public void setId(int id)
    {
        this.id = id;
    }

    public static ResultSet getMembers() throws SQLException, IOException
    {
        ResultSet resultSet = Connect.getStatement().executeQuery("SELECT * FROM coop_members ORDER BY lastname");
        return resultSet;
    }

    public static boolean isExist(String firstname, String middlename, String lastname) throws SQLException, IOException
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

    public void insertCoopMember() throws IOException
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

    public static ResultSet searchMemberByFirstname(String firstname) throws SQLException, IOException
    {
        String sql ="SELECT * "
                    + "FROM coop_members "
                    + "WHERE firstname LIKE ?"
                    + " LIMIT 5";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setString(1, "%"+ firstname + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
        
    }

    public static CoopMember getMemberByFirstname(String firstname) throws SQLException, IOException
    {
        String sql ="SELECT * "
                    + "FROM coop_members "
                    + "WHERE firstname = ?"
                    + " LIMIT 1";
                    
        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setString(1, firstname);

        ResultSet resultSet = preparedStatement.executeQuery();

        CoopMember coopMember = new CoopMember(resultSet.getString("firstname"), 
                                               resultSet.getString("middlename"), 
                                               resultSet.getString("lastname"), 
                                               resultSet.getString("position"), 
                                               resultSet.getString("address"), 
                                               resultSet.getInt("age")
                                              );

        
    

        return coopMember;
    }

    public static int getMemberIdFromDb(String firstname, String middlename, String lastname) throws SQLException, IOException
    {
        String sql ="SELECT id "
                    + "FROM coop_members "
                    + "WHERE firstname = ?"
                    + " AND middlename = ?"
                    + " AND lastname = ?"
                    + " LIMIT 1";
        
        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setString(1, firstname);
        preparedStatement.setString(2, middlename);
        preparedStatement.setString(3, lastname);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.getInt("id");
        
    }

    public static CoopMember getMemberById(int id) throws SQLException, IOException
    {
        String sql ="SELECT * "
                    + "FROM coop_members "
                    + "WHERE id = ?"
                    + " LIMIT 1";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        CoopMember coopMember = null;

        while (resultSet.next()) 
        {
            coopMember = new CoopMember(
                resultSet.getString("firstname"),
                resultSet.getString("middlename"),
                resultSet.getString("lastname"),
                resultSet.getString("position"),
                resultSet.getString("address"),
                resultSet.getInt("age")
            );
            coopMember.setId(
                resultSet.getInt("id")
            );
        }

        return coopMember;
    
    }
}
