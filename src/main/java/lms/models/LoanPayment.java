package lms.models;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import lms.helpers.Connect;

public class LoanPayment 
{
    private int paymentId;
    private int loanId;
    private String paymentDate;
    private double paymentAmount;
    private double loanBalance;

    public LoanPayment(int loanId, double paymentAmount, double loanBalance)
    {
        this.loanId = loanId;
        this.paymentAmount = paymentAmount;
        this.loanBalance = loanBalance;
    }

    public int getPaymentId()
    {
        return paymentId;
    }

    public int getLoanId()
    {
        return loanId;
    }

    public String getPaymentDate()
    {
        return paymentDate;
    }

    public double getPaymentAmount()
    {
        return paymentAmount;
    }

    public double getLoanBalance()
    {
        return loanBalance;
    }

    public void setPaymentId(int paymentId)
    {
        this.paymentId = paymentId;
    }

    public void setPaymentDate(String paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public static ResultSet getPayments(int loanId) throws SQLException, IOException
    {
        String sql = "SELECT * FROM loan_payment WHERE loan_id = ?";
        
        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        preparedStatement.setInt(1, loanId);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
        
    }

    public void insertPayment() throws SQLException, IOException
    {
        String sql = "INSERT INTO loan_payment(loan_id, payment_date, payment_amount, loan_balance) "
                    +"VALUES(?,?,?,?)";
    
        LocalDate todayDate = LocalDate.now();

        PreparedStatement preparedStatement = Connect.getPreparedStatement(sql);
        
        preparedStatement.setInt(1, loanId);
        preparedStatement.setString(2, todayDate.toString());
        preparedStatement.setDouble(3, paymentAmount);
        preparedStatement.setDouble(4, loanBalance);

        preparedStatement.executeUpdate();
    }
}
