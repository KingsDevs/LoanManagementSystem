package lms.models;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lms.helpers.Connect;

public class LoanPaymentSched 
{
    private int loanPaymentSchedId;
    private String loanPaymentSchedule;
    private String loanPaymentStatus;

    public LoanPaymentSched(int loanPaymentSchedId, String loanPaymentSchedule, String loanPaymentStatus)
    {
        this.loanPaymentSchedId = loanPaymentSchedId;
        this.loanPaymentSchedule = loanPaymentSchedule;
        this.loanPaymentStatus = loanPaymentStatus;
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

    public static LoanPaymentSched getLoanPaymentSched(int loanPaymentSchedId) throws SQLException, IOException
    {
        String sql = "SELECT * FROM loan_payment_sched WHERE sched_id = ? LIMIT 1";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setInt(1, loanPaymentSchedId);

        ResultSet resultSet = preparedStatement.executeQuery();

        LoanPaymentSched loanPaymentSched = new LoanPaymentSched(
            resultSet.getInt("sched_id"),
            resultSet.getString("schedule"),
            resultSet.getString("status")
        );

        return loanPaymentSched;
    }

    public void insertLoanPaymentSchedule() throws SQLException, IOException
    {
        String sql = "INSERT INTO loan_payment_sched(sched_id, schedule, status) "
                   + "VALUES(?,?,?)";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        
        preparedStatement.setInt(1, loanPaymentSchedId);
        preparedStatement.setString(2, loanPaymentSchedule);
        preparedStatement.setString(3, loanPaymentStatus);

        preparedStatement.executeUpdate();
    }

    public static void updateLoanPayment(int loanPaymentSchedId, String loanPaymentSched, String loanPaymentStatus) throws SQLException, IOException
    {
        String sql = "UPDATE loans SET(schedule = ?, status = ?) WHERE sched_id = ?";

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);

        preparedStatement.setString(1, loanPaymentSched);
        preparedStatement.setString(2, loanPaymentStatus);
        preparedStatement.setInt(3, loanPaymentSchedId);

        preparedStatement.executeUpdate();
    }

}
