package lms.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        paymentAmountField.selectHome();
        
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void submit(ActionEvent event) {

    }

    

}
