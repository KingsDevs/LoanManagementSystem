package lms.models;

public class LoanPayment 
{
    private int paymentId;
    private int loanId;
    private String paymentDate;
    private double loanBalance;

    public LoanPayment(int loanId, double loanBalance)
    {
        this.loanId = loanId;
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

    public double getLoanBalance()
    {
        return loanBalance;
    }

    public void setPaymentId(int paymentId)
    {
        this.paymentId = paymentId;
    }
}
