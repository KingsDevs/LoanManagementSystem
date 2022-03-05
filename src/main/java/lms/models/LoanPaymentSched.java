package lms.models;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import lms.helpers.Connect;

public class LoanPaymentSched 
{
    private int loanPaymentSchedId;
    private String loanPaymentSchedule;
    private String loanPaymentStatus;

    public LoanPaymentSched(String loanPaymentSchedule, String loanPaymentStatus)
    {
        this.loanPaymentSchedule = loanPaymentSchedule;
        this.loanPaymentStatus = loanPaymentStatus;
    }

    public void setLoanPaymentSchedId(int loanPaymentSchedId)
    {
        this.loanPaymentSchedId =loanPaymentSchedId;
    }

    public int getLoanPaymentId()
    {
        return loanPaymentSchedId;
    }

    public String getLoanPaymentSchedule()
    {
        return loanPaymentSchedule;
    }

    public String getLoanPaymentStatus()
    {
        return loanPaymentStatus;
    }

    public static LoanPaymentSched getLoanPaymentSched(int loanId) throws SQLException, IOException
    {
        String sql = "SELECT * FROM loan_payment_sched WHERE loan_id = ? LIMIT 1";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setInt(1, loanId);

        ResultSet resultSet = preparedStatement.executeQuery();

        LoanPaymentSched loanPaymentSched = new LoanPaymentSched(
            resultSet.getString("schedule"),
            resultSet.getString("status")
        );

        loanPaymentSched.setLoanPaymentSchedId(resultSet.getInt("sched_id"));

        return loanPaymentSched;
    }

    public void insertLoanPaymentSchedule(int loanId) throws SQLException, IOException
    {
        String sql = "INSERT INTO loan_payment_sched(sched_id, loan_id, schedule, status) "
                   + "VALUES(?,?,?,?)";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        
        preparedStatement.setInt(1, loanPaymentSchedId);
        preparedStatement.setInt(2, loanId);
        preparedStatement.setString(3, loanPaymentSchedule);
        preparedStatement.setString(4, loanPaymentStatus);

        preparedStatement.executeUpdate();
    }

    public static void updateLoanPayment(int loanId, String loanPaymentStatus) throws SQLException, IOException
    {
        String sql = "UPDATE loan_payment_sched SET status = ? WHERE loan_id = ?";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);

        
        preparedStatement.setString(1, loanPaymentStatus);
        preparedStatement.setInt(2, loanId);

        preparedStatement.executeUpdate();
    }

    public static String generateScheds(String loanType)
    {
        int limit = 0;

        if(loanType.equals(Loan.LOAN_TYPES[0]))
        {
            limit = Loan.SHORT_TERM_MONTHS_DUE;
        }
        else
        {
            limit = Loan.LONG_TERM_MONTHS_DUE;
        }

        String generatedSched = "";
        LocalDate today = LocalDate.now();

        for(int i = 1; i <= limit; i++)
        {
            today = today.plusMonths(1);
            generatedSched += today.toString();
            if(i < limit)
            {
                generatedSched += ",";
            }
        }

        return generatedSched;
    }

    public static String generateSchedStatus(String statuses)
    {
        String[] arrStatuses = statuses.split(",");

        String generateSchedStatus = "";

        int size = arrStatuses.length;

        for(int i = 1; i <= size; i++)
        {
            generateSchedStatus += Loan.LOAN_STATUSES[0];

            if(i < size)
            {
                generateSchedStatus += ",";
            }
        }

        return generateSchedStatus;
    }

}
