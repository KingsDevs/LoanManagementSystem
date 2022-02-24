package lms.models;

import java.time.LocalDate;

public class Loan 
{
    private int loanId;
    private int coopMemberId;
    private String loanType;
    private double loanAmount;
    private double loanPaidAmount;
    private double serviceFee;
    private String loanStatus;
    private String loanCreated;
    private String loanDueDate;

    public final static String[] LOAN_STATUSES = {"ACTIVE", "PAID", "DUE"};
    public final static String[] LOAN_TYPES = {"SHORT TERM", "LONG TERM"};

    public final static double INTEREST = 2.5 / 100;
    public final static double SERVICE_FEE_RATE = 2 / 100;

    public final static double MINIMUM = 10000;
    public final static double MAXIMUM = 100000;

    public Loan(int coopMemberId, String loanType, double loanAmount, String loanStatus, String loanDueDate)
    {
        this.coopMemberId = coopMemberId;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
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

    public double getLoanPaidAmount()
    {
        return loanPaidAmount;
    }

    public double getServiceFee()
    {
        return serviceFee;
    }

    public String getLoanStatus()
    {
        return loanStatus;
    }

    public String getLoanDueDate()
    {
        return loanDueDate;
    }

}
