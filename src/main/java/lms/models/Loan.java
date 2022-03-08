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

    public static Boolean isCoopMemberAlreadyLoaned(int coopMemberId) throws SQLException, IOException
    {
        String sql = "SELECT * FROM loans WHERE coop_member_id = ? LIMIT 1";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setInt(1, coopMemberId);

        ResultSet resultSet = preparedStatement.executeQuery();
        

        try 
        {
            int loanId = resultSet.getInt("loan_id");
        } catch (SQLException e) 
        {
            if (e.getMessage().equals("ResultSet closed")) 
            {
                return false;    
            }
           
        }
        
        

        return true;
    }

    public static int getLoanIdFromDb(int coopMemberId) throws SQLException, IOException
    {
        String sql = "SELECT loan_id FROM loans WHERE coop_member_id = ? LIMIT 1";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setInt(1, coopMemberId);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.getInt("loan_id");
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

    public static void updateLoanBalance() throws SQLException, IOException
    {
        String sql = "SELECT loan_id, loan_status, loan_type, loan_balance, loan_amount FROM loans";

        ResultSet resultSet = Connect.getStatement().executeQuery(sql);
        LocalDate todayDate = LocalDate.now();

        ArrayList<Integer> loanIds = new ArrayList<Integer>();
        ArrayList<Double> loanBalances = new ArrayList<Double>();

       
        
        while(resultSet.next())
        {
            int loanId = resultSet.getInt("loan_id");
            LoanPaymentSched loanPaymentSched = LoanPaymentSched.getLoanPaymentSched(loanId);

            String loanStatus = resultSet.getString("loan_status");

            if(!loanStatus.equals(LOAN_STATUSES[1]))
            {
                String[] schedules = loanPaymentSched.getLoanPaymentSchedule().split(",");
                String statuses = loanPaymentSched.getLoanPaymentStatus();
                String[] arrStatues = statuses.split(",");

                int schedLength = schedules.length;

                for(int i = schedLength-1; i >= 0; i--)
                {
                    LocalDate paymentSched = LocalDate.parse(schedules[i]);

                    if(!arrStatues[i].equals(LOAN_STATUSES[2]) && (todayDate.isAfter(paymentSched) || todayDate.equals(paymentSched)))
                    {
                        double loanBalance = resultSet.getDouble("loan_balance");
                        loanBalance += resultSet.getDouble("loan_amount") * INTEREST;

                        loanBalances.add(loanBalance);
                        loanIds.add(loanId);

                        String changedStatus = LoanPaymentSched.changeStatusToDue(statuses);
                        LoanPaymentSched.updateLoanPayment(loanId, changedStatus);

                        break;
                        
                    }
                }

            }
        }
        
        if(loanIds.size() > 0)
        {
            String when = "";
            String in = "";
            for(int i = 1; i <= loanIds.size(); i++)
            {
                when += "WHEN loan_id = ? THEN ? ";
                
                in += "?";
                if(i < loanIds.size())
                {
                    in += ",";
                }
            }

            // when += "END";

            sql = "UPDATE loans SET loan_balance=(CASE " + when + "END) " + "WHERE loan_id IN(" + in + ")";
            System.out.println(sql);
            int last = 0;

            PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
            for(int i = 1; i <= loanIds.size(); i++)
            {
                preparedStatement.setInt(i, loanIds.get(i-1));
                preparedStatement.setDouble(i + 1, loanBalances.get(i-1));

                last = 2 * i + 1;
            }
            
            int index = 0;
            for(int i = last; i < loanIds.size() + last; i++)
            {
                preparedStatement.setInt(last, loanIds.get(index));
                index ++;
            }

            preparedStatement.executeUpdate();
            
        }

    }

    public static void updateLoanStatusToDue() throws SQLException, IOException
    {
        String sql = "SELECT loan_id, loan_status, loan_due_date FROM loans";

        ResultSet resultSet = Connect.getStatement().executeQuery(sql);
        ArrayList<Integer> loadIds = new ArrayList<Integer>();
        LocalDate todayDate = LocalDate.now();

        while(resultSet.next())
        {
            LocalDate loanDueDate = LocalDate.parse(resultSet.getString("loan_due_date"));
           // String loanDueDate = resultSet.getString("loan_due_date");
            String status = resultSet.getString("loan_status");

            if (loanDueDate.compareTo(todayDate) <= 0 && status.equals(LOAN_STATUSES[0])) 
            {
                System.out.println(resultSet.getInt("loan_id"));
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
        preparedStatement.setString(1, LOAN_STATUSES[2]);

        for(int i = 1; i <= loadIds.size(); i++)
        {
            preparedStatement.setInt(i+1, loadIds.get(i-1));
        }

        preparedStatement.executeUpdate();

    }

    private static ResultSet getLoanColLoanCreated() throws SQLException, IOException
    {
        String sql = "SELECT loan_created FROM loans";
        ResultSet resultSet = Connect.getStatement().executeQuery(sql);

        return resultSet;
    }

    public static int getLoanCountThisDay() throws SQLException, IOException
    {
        int count = 0;

        ResultSet resultSet = getLoanColLoanCreated();
        
        String today = LocalDate.now().toString();

        while (resultSet.next()) 
        {
            String loanCreated = resultSet.getString("loan_created");

            if(loanCreated.equals(today))
            {
                count++;
            }
        }

        return count;
    }

    public static int getLoanCountThisYear() throws SQLException, IOException
    {
        int count = 0;

        ResultSet resultSet = getLoanColLoanCreated();
        
        String[] today = LocalDate.now().toString().split("-");

        while (resultSet.next()) 
        {
            String[] loanCreated = resultSet.getString("loan_created").split("-");
            

            if(loanCreated[2].equals(today[2]))
            {
                count++;
            }
        }

        return count;
    }

    public static int getLoanCountThisMonth() throws SQLException, IOException
    {
        int count = 0;

        ResultSet resultSet = getLoanColLoanCreated();
        
        String[] today = LocalDate.now().toString().split("-");

        while (resultSet.next()) 
        {
            String[] loanCreated = resultSet.getString("loan_created").split("-");
            

            if(loanCreated[1].equals(today[1]) && loanCreated[2].equals(today[2]))
            {
                count++;
            }
        }

        return count;
    }



}
