package lms.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lms.models.CoopMember;
import lms.models.Loan;

public class ViewLoanDetailsController 
{

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label loanAmountLabel;

    @FXML
    private TableColumn<?, ?> loanBalanceCol;

    @FXML
    private Label loanBalanceLabel;

    @FXML
    private Label loanCreatedLabel;

    @FXML
    private Label loanStatusLabel;

    @FXML
    private Label loanTypeLabel;

    @FXML
    private Label middlenameLabel;

    @FXML
    private TableColumn<?, ?> paymentAmountCol;

    @FXML
    private TableColumn<?, ?> paymentDateCol;

    @FXML
    private TableView<?> paymentsTable;

    @FXML
    private Button printBtn;

    @FXML
    private ListView<String> scheduleList;

    private Loan loan;

    private void updateCoopMemberDetails()
    {
        CoopMember coopMember;
        try 
        {
            coopMember = CoopMember.getMemberById(loan.getCoopMemberId());
            firstnameLabel.setText(firstnameLabel.getText() + coopMember.getFirstname());
            middlenameLabel.setText(middlenameLabel.getText() + coopMember.getMiddlename());
            lastnameLabel.setText(lastnameLabel.getText() + coopMember.getLastname());
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private void updateLoanDetails()
    {
        loanTypeLabel.setText(loanTypeLabel.getText() + loan.getLoanType());
        loanAmountLabel.setText(loanAmountLabel.getText() + loan.getLoanAmount());
        loanBalanceLabel.setText(loanBalanceLabel.getText() + loan.getLoanBalance());
        loanStatusLabel.setText(loanStatusLabel.getText() + loan.getLoanStatus());
        loanCreatedLabel.setText(loanCreatedLabel.getText() + loan.getLoanCreated());
        dueDateLabel.setText(dueDateLabel.getText() + loan.getLoanDueDate());
    
    }

    private void updateScheduleList()
    {
        LocalDate loanCreated = LocalDate.parse(loan.getLoanCreated());

        int limit;
        if(loan.getLoanType().equals(Loan.LOAN_TYPES[0]))
        {
            limit = Loan.SHORT_TERM_MONTHS_DUE;
        }
        else
        {
            limit = Loan.LONG_TERM_MONTHS_DUE;
        }

        for(int i = 1; i <= limit; i++)
        {
            loanCreated = loanCreated.plusMonths(1);
            scheduleList.getItems().add(i  + ". " + loanCreated.toString());
        }
    }

    public void setLoan(Loan loan)
    {
        this.loan = loan;
        updateCoopMemberDetails();
        updateLoanDetails();
        updateScheduleList();
    }

    @FXML
    void cancel(ActionEvent event) 
    {

    }

    @FXML
    void print(ActionEvent event) 
    {

    }

}
