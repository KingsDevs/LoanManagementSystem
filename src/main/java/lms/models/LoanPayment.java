package lms.models;

import java.io.IOException;
import java.sql.PreparedStatement;
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
