package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import lms.App;
import lms.helpers.FormValidation;
import lms.models.Loan;

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

        loanTypeChoiceBox.getItems().addAll(Loan.LOAN_TYPES);

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

        loanAmountField.textProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) 
                {
                    loanAmountField.setText(oldValue);
                }
            }

        });
        
    }

    @FXML
    void addLoan(ActionEvent event) 
    {
        addLoanBtn.setDisable(true);

        String firstname = firstNameField.getText();
        String middlename = middleNameField.getText();
        String lastName = lastnameField.getText();
        String address = addressVield.getText();
        String position = positionField.getText();
        String sAge = ageField.getText();
        String sLoanAmount = loanAmountField.getText();
        String loanType = loanTypeChoiceBox.getValue();

        boolean isCleared = true;

        firstNameValidation.setText("");
        lastnameValidation.setText("");
        middleNameValidation.setText("");
        addressValidation.setText("");
        positionValidation.setText("");
        loanAmountValidation.setText("");
        loanTypeValidation.setText("");
        mainFormValidationLabel.setText("");

        if(firstname.isEmpty() || firstname.isBlank())
        {
            firstNameValidation.setText(FormValidation.emptyField("First Name"));
            isCleared = false;
        }

        if(middlename.isEmpty() || middlename.isBlank())
        {
            middleNameValidation.setText(FormValidation.emptyField("Middle Name"));
            isCleared = false;
        }

        if(lastName.isEmpty() || lastName.isBlank())
        {
            lastnameValidation.setText(FormValidation.emptyField("Last Name"));
            isCleared = false;
        }

        if(address.isEmpty() || address.isBlank())
        {
            addressValidation.setText(FormValidation.emptyField("Address"));
            isCleared = false;
        }

        if(position.isEmpty() || position.isBlank())
        {
            positionValidation.setText(FormValidation.emptyField("Position"));
            isCleared = false;
        }


        if(loanType == null)
        {
            loanTypeValidation.setText(FormValidation.emptyField("Loan Type"));
            isCleared = false;
        }

        if (sAge.isEmpty() || sAge.isBlank())
        {
            ageValidation.setText(FormValidation.emptyField("Age"));
            isCleared = false;
        }
        else
        {
            if(Integer.parseInt(sAge) < 18)
            {
                ageValidation.setText("You are too young!");
                isCleared = false;
            }
            
        }

        if(sLoanAmount.isEmpty() || sLoanAmount.isBlank())
        {
            loanAmountValidation.setText(FormValidation.emptyField("Loan Amount"));
            isCleared = false;
        }
    }

    @FXML
    void cancel(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(App.loadFxml("main"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
            
        mainController.setAddLoanMain();
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        
        Scene scene = addLoanBtn.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage)window;

        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);
        stage.show();
    }

    @FXML
    void handle(KeyEvent keyEvent) 
    {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) 
        {
           addLoan(new ActionEvent());
        }
    }

    

}
