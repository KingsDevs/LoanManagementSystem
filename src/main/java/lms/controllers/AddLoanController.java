package lms.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AddLoanController implements Initializable
{

    @FXML
    private Button addLoanBtn;

    @FXML
    private Label addressValidation;

    @FXML
    private TextField addressVield;

    @FXML
    private TextField ageField;

    @FXML
    private Label ageValidation;

    @FXML
    private TextField firstNameField;

    @FXML
    private Label firstNameValidation;

    @FXML
    private TextField lastnameField;

    @FXML
    private Label lastnameValidation;

    @FXML
    private TextField loanAmountField;

    @FXML
    private Label loanAmountValidation;

    @FXML
    private ChoiceBox<String> loanTypeChoiceBox;

    @FXML
    private Label loanTypeValidation;

    @FXML
    private Label mainFormValidationLabel;

    @FXML
    private TextField middleNameField;

    @FXML
    private Label middleNameValidation;

    @FXML
    private TextField positionField;

    @FXML
    private Label positionValidation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        ageField.textProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
                if(!newValue.matches("\\d*"))
                {
                    ageField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });
        
    }

    @FXML
    void addLoan(ActionEvent event) 
    {

    }

    @FXML
    void cancel(ActionEvent event) 
    {

    }

    @FXML
    void handle(KeyEvent event) 
    {

    }

    

}
