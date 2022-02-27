package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lms.App;
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

    private Loan loan;

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

    public void setLoan(Loan loan)
    {
        this.loan = loan;
        updateFields();
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
    void submit(ActionEvent event) 
    {

    }

    

}
