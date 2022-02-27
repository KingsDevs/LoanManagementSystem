package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lms.models.CoopMember;
import lms.models.Loan;

public class PaymentController implements Initializable
{

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField loanTypeField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField paymentAmountField;

    @FXML
    private Button submitBtn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        paymentAmountField.requestFocus();
        paymentAmountField.selectAll();

        
        
    }

    public void setLoan(Loan loan)
    {
        System.out.println("dsd");
        CoopMember coopMember;
        try 
        {
            coopMember = CoopMember.getMemberById(loan.getCoopMemberId());
            firstNameField.setText(coopMember.getFirstname());
            middleNameField.setText(coopMember.getMiddlename());
            lastnameField.setText(coopMember.getLastname());

            loanTypeField.setText(loan.getLoanType());
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

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void submit(ActionEvent event) 
    {

    }

    

}
