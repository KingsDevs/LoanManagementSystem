package lms.models;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import lms.helpers.Connect;

public class Loan 
{
    private int loanId;
    private int coopMemberId;
    private String loanType;
    private double loanAmount;
    private double loanBalance;
    private double serviceFee;
    private String loanStatus;
    private String loanCreated;
    private String loanDueDate;

    private String lender;


    public final static String[] LOAN_STATUSES = {"ACTIVE", "PAID", "DUE"};
    public final static String[] LOAN_TYPES = {"SHORT TERM", "LONG TERM"};

    public final static double INTEREST = 2.5 / 100;
    public final static double SERVICE_FEE_RATE = 2.0 / 100;

    public final static double MINIMUM = 10000;
    public final static double MAXIMUM = 100000;

    public final static int SHORT_TERM_MONTHS_DUE = 6;
    public final static int LONG_TERM_MONTHS_DUE = 12;

    public Loan(int coopMemberId, String loanType, double loanAmount, double loanBalance, String loanStatus, String loanDueDate)
    {
        this.coopMemberId = coopMemberId;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.loanBalance = loanBalance;
        this.loanStatus = loanStatus;
        this.loanDueDate = loanDueDate;

        // LocalDate todayDate = LocalDate.now();
        // loanCreated = todayDate.toString();
        // loanDueDate = todayDate.plusMonths(2).toString();

    }

    public void setLoanId(int loanId)
    {
        this.loanId = loanId;
    }

    public void setLender(String lender)
    {
        this.lender = lender;
    }

    public void setLoanCreated(String loanCreated)
    {
        this.loanCreated = loanCreated;
    }

    public String getLoanCreated()
    {
        return loanCreated;
    }

    public int getLoanId()
    {
        return loanId;
    }

    public int getCoopMemberId()
    {
        return coopMemberId;
    }

    public String getLoanType()
    {
        return loanType;
    }

    public double getLoanAmount()
    {
        return loanAmount;
    }

    public double getServiceFee()
    {
        return serviceFee;
    }

    public String getLoanStatus()
    {
        return loanStatus;
    }

    public String getLender()
    {
        return lender;
    }

    public String getLoanDueDate()
    {
        return loanDueDate;
    }

    public double getLoanBalance()
    {
        return loanBalance;
    }

    public double getMonthly()
    {
        return loanAmount * INTEREST;
    }

    public static ResultSet getLoans() throws SQLException, IOException
    {
        String sql = "SELECT * FROM loans JOIN coop_members ON coop_member_id = coop_members.id";
        ResultSet resultSet = Connect.getStatement().executeQuery(sql);

        return resultSet;
        
    }

    public static void insertLoans(int coopMemberId, String loanType, double loanAmount) throws IOException
    {
        String sql = "INSERT INTO loans(coop_member_id, loan_type, loan_amount, loan_balance, service_fee, loan_status, loan_created, loan_due_date) "
                    +"VALUES(?,?,?,?,?,?,?,?)";

        LocalDate todayDate = LocalDate.now();
        try (PreparedStatement preparedStatement = Connect.getPreparedStatement(sql)) 
        {
            preparedStatement.setInt(1, coopMemberId);
            preparedStatement.setString(2, loanType);
            preparedStatement.setDouble(3, loanAmount);
            preparedStatement.setDouble(4, loanAmount);
            preparedStatement.setDouble(5, loanAmount * SERVICE_FEE_RATE);
           // System.out.println(loanAmount + " * " + SERVICE_FEE_RATE + " = " + loanAmount * SERVICE_FEE_RATE);
            preparedStatement.setString(6, LOAN_STATUSES[0]);
            preparedStatement.setString(7, todayDate.toString());

            if(loanType.equals(LOAN_TYPES[0]))
            {
                preparedStatement.setString(8, todayDate.plusMonths(SHORT_TERM_MONTHS_DUE).toString());
            }
            else
            {
                preparedStatement.setString(8, todayDate.plusMonths(LONG_TERM_MONTHS_DUE).toString());
            }


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    public void updateLoanBalance(double currentBalance) throws SQLException, IOException
    {
        String sql = "UPDATE loans SET loan_balance = ?, loan_status = ? "
                   + "WHERE loan_id = ?";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        
        if(currentBalance <= 0)
        {
            loanStatus = LOAN_STATUSES[1];
        }

        preparedStatement.setDouble(1, currentBalance);
        preparedStatement.setString(2, loanStatus);
        preparedStatement.setInt(3, loanId);

        preparedStatement.executeUpdate();
    }

    public static ResultSet searchLoans(String searchText) throws SQLException, IOException
    {
        String sql = "SELECT * FROM loans JOIN coop_members ON coop_member_id = coop_members.id "
                    +"WHERE coop_members.firstname LIKE ? "
                    +"OR coop_members.middlename LIKE ? "
                    +"OR coop_members.lastname LIKE ? "
                    +"OR loans.loan_type LIKE ? "
                    +"OR loans.loan_status LIKE ?";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);

        preparedStatement.setString(1,"%" + searchText + "%");
        preparedStatement.setString(2,"%" + searchText + "%");
        preparedStatement.setString(3,"%" + searchText + "%");
        preparedStatement.setString(4,"%" + searchText + "%");
        preparedStatement.setString(5,"%" + searchText + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    public static ResultSet getLoanByStatus(String loanStatus) throws SQLException, IOException
    {
        String sql = "SELECT *" 
                    +"FROM loans JOIN coop_members ON loans.coop_member_id = coop_members.id "
                    +"WHERE loans.loan_status = ?";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setString(1, loanStatus);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    public static void updateLoanStatus(String loanStatus) throws SQLException, IOException
    {
        String sql = "SELECT loan_id, loan_due_date FROM loans";

        ResultSet resultSet = Connect.getStatement().executeQuery(sql);
        ArrayList<Integer> loadIds = new ArrayList<Integer>();
        String todayDate = LocalDate.now().toString();

        while(resultSet.next())
        {
            String loanDueDate = resultSet.getString("loan_due_date");
            if (loanDueDate.equals(todayDate)) 
            {
                loadIds.add(resultSet.getInt("loan_id"));    
            }
        }

        sql = "UPDATE loans SET loan_status = ? WHERE loan_id IN (";
        String inId = "";

        for(int i = 1; i <= loadIds.size(); i++)
        {
            inId += "?";
            if(i < loadIds.size())
            {
                inId += ",";
            }
        }

        inId += ")";
        sql += inId;

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setString(1, loanStatus);

        for(int i = 1; i <= loadIds.size(); i++)
        {
            preparedStatement.setInt(i+1, loadIds.get(i-1));
        }

        preparedStatement.executeUpdate();

    }

}
