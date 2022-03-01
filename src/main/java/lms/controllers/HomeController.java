package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import lms.models.Loan;

public class HomeController implements Initializable
{

    @FXML
    private ListView<String> activeLoanList;

    @FXML
    private ListView<String> dueLoansList;

    @FXML
    private ListView<String> paidLoansList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        Platform.runLater(() ->{
            try {
                loadActiveLoans();
                loadPaidLoans();
                loadDueLoans();
            } catch (SQLException e) {
                
                e.printStackTrace();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
        });
        
    }

    private void loadActiveLoans() throws SQLException, IOException
    {
        ResultSet resultSet = Loan.getLoanByStatus(Loan.LOAN_STATUSES[0]);

        while (resultSet.next()) 
        {
            String firstname = resultSet.getString("firstname");
            char middleInitial = resultSet.getString("middlename").charAt(0);
            String lastname = resultSet.getString("lastname");
            String loanType = resultSet.getString("loan_type");
            double loanBalance = resultSet.getDouble("loan_balance");

            activeLoanList.getItems().add(loanType + " : " + lastname + ", " + firstname + ", " + middleInitial + ". | Bal: " + loanBalance);
        }
    }

    private void loadPaidLoans() throws SQLException, IOException
    {
        ResultSet resultSet = Loan.getLoanByStatus(Loan.LOAN_STATUSES[1]);

        while (resultSet.next()) 
        {
            String firstname = resultSet.getString("firstname");
            char middleInitial = resultSet.getString("middlename").charAt(0);
            String lastname = resultSet.getString("lastname");
            String loanType = resultSet.getString("loan_type");
            paidLoansList.getItems().add(loanType + " : " + lastname + ", " + firstname + ", " + middleInitial);
        }
    }

    private void loadDueLoans() throws SQLException, IOException
    {
        ResultSet resultSet = Loan.getLoanByStatus(Loan.LOAN_STATUSES[2]);

        while (resultSet.next()) 
        {
            String firstname = resultSet.getString("firstname");
            char middleInitial = resultSet.getString("middlename").charAt(0);
            String lastname = resultSet.getString("lastname");
            String loanType = resultSet.getString("loan_type");
            double loanBalance = resultSet.getDouble("loan_balance");

            dueLoansList.getItems().add(loanType + " : " + lastname + ", " + firstname + ", " + middleInitial + ". | Bal: " + loanBalance);
        }
    }

    

}
