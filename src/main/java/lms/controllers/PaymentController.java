package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lms.App;
import lms.helpers.FormValidation;
import lms.models.CoopMember;
import lms.models.Loan;
import lms.models.LoanPayment;

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

    @FXML
    private Label paymentAmountValidation;

    @FXML
    private Label warningText;

    private Loan loan;
    private double minimumPayment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        // firstNameField.focusedProperty().addListener((o, oldValue, newValue) -> {
        //     if(newValue)
        //     {
        //         Platform.runLater(() -> {
        //             int caretPosition = firstNameField.getCaretPosition();
        //             if(firstNameField.getAnchor() != caretPosition)
        //             {
        //                 firstNameField.selectRange(caretPosition, caretPosition);
        //             }
        //         });
        //     }
        // });

        Platform.runLater(() -> {
            paymentAmountField.requestFocus();
        });

        paymentAmountField.textProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) 
                {
                    paymentAmountField.setText(oldValue);
                }
            }

        });
        
    }

    private void updateFields()
    {
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

    private void setMinimumPayment()
    {
        double totalInterest = loan.getMonthly();

        if(loan.getLoanType().equals(Loan.LOAN_TYPES[0]))
        {
            totalInterest *= Loan.LONG_TERM_MONTHS_DUE;
        }
        else
        {
            totalInterest *= Loan.SHORT_TERM_MONTHS_DUE;
        }

        double totalBill = loan.getLoanAmount() + totalInterest;

        if(loan.getLoanType().equals(Loan.LOAN_TYPES[0]))
        {
            minimumPayment = totalBill / Loan.SHORT_TERM_MONTHS_DUE;
        }
        else
        {
            minimumPayment = totalBill / Loan.SHORT_TERM_MONTHS_DUE;
        }

        if(minimumPayment >=  loan.getLoanBalance())
        {
            minimumPayment = loan.getLoanBalance();
        }

        
    }

    public void setLoan(Loan loan)
    {
        this.loan = loan;
        updateFields();
        setMinimumPayment();
        warningText.setText("Your Minimum Payment: " + String.format("%.2f", minimumPayment));
        
    }

    @FXML
    void cancel(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(App.loadFxml("main"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
            
        mainController.setAddLoanMain();
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        
        Scene scene = submitBtn.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage)window;

        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);
        stage.show();
    }

    @FXML
    void submit(ActionEvent event) throws SQLException, IOException 
    {
        paymentAmountValidation.setText("");
        submitBtn.setDisable(true);

        String sPaymentAmount = paymentAmountField.getText();

        if(sPaymentAmount.isEmpty() || sPaymentAmount.isBlank())
        {
            FormValidation.emptyField("Payment Amount");
        }
        else
        {
            double paymentAmount = Double.parseDouble(sPaymentAmount);
            
            if(paymentAmount <  minimumPayment)
            {
                paymentAmountValidation.setText("The minimum payment is " + String.format("%.2f", minimumPayment));
            }
            else if(paymentAmount > loan.getLoanBalance())
            {
                paymentAmountValidation.setText("Your payment exceed to your current balance");
            }
            else
            {
                double currentBalance = loan.getLoanBalance() - paymentAmount;
                LoanPayment loanPayment = new LoanPayment(loan.getLoanId(), paymentAmount, currentBalance);
                loanPayment.insertPayment();
                loan.updateLoanBalance(currentBalance);

                cancel(new ActionEvent());
            }
        }
        submitBtn.setDisable(false);
    }

    

}
