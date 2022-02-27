package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lms.models.CoopMember;
import lms.models.Loan;
import lms.models.LoanPayment;

public class ViewLoanDetailsController implements Initializable
{

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label loanAmountLabel;

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
    private TableColumn<LoanPayment, Integer> idCol;

    @FXML
    private TableColumn<LoanPayment, Double> paymentAmountCol;

    @FXML
    private TableColumn<LoanPayment, String> paymentDateCol;

    @FXML
    private TableColumn<LoanPayment, Double> loanBalanceCol;

    @FXML
    private TableView<LoanPayment> paymentsTable;

    @FXML
    private Button printBtn;

    @FXML
    private ListView<String> scheduleList;

    private Loan loan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        idCol.setCellValueFactory(new PropertyValueFactory<LoanPayment, Integer>("paymentId"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<LoanPayment, String>("paymentDate"));
        paymentAmountCol.setCellValueFactory(new PropertyValueFactory<LoanPayment, Double>("paymentAmount"));
        loanBalanceCol.setCellValueFactory(new PropertyValueFactory<LoanPayment, Double>("loanBalance"));
       
        
    }

    private void updatePaymentTable() throws SQLException, IOException
    {
        ObservableList<LoanPayment> data = paymentsTable.getItems();
        ResultSet resultSet = LoanPayment.getPayments(loan.getLoanId());

        while(resultSet.next())
        {
            LoanPayment loanPayment = new LoanPayment(
                loan.getLoanId(), 
                resultSet.getDouble("payment_amount"), 
                resultSet.getDouble("loan_balance")
                );
            
            loanPayment.setPaymentId(resultSet.getInt("payment_id"));
            loanPayment.setPaymentDate(resultSet.getString("payment_date"));

            data.add(loanPayment);
        }
        
    }

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

        try {
            updatePaymentTable();
        } catch (SQLException e) {
            
            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
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
