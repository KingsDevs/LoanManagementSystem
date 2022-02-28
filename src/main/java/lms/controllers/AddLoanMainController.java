package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lms.App;
import lms.models.Loan;

public class AddLoanMainController implements Initializable
{

    @FXML
    private Button addLoanBtn;

    @FXML
    private TableView<Loan> loanListTableView;

    @FXML
    private TableColumn<Loan, String> dueDateCol;

    @FXML
    private TableColumn<Loan, String> lenderCol;

    @FXML
    private TableColumn<Loan, Double> loanAmountCol;

    @FXML
    private TableColumn<Loan, Double> loanBalanceCol;

    @FXML
    private TableColumn<Loan, Double> monthlyCol;

    @FXML
    private TableColumn<Loan, String> statusCol;

    @FXML
    private Button payLoanBtn;

    @FXML
    private Button viewLoanBtn;

    @FXML
    private TextField searchField;

    public Loan selectedLoan;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lenderCol.setCellValueFactory(new PropertyValueFactory<Loan, String>("lender"));
        loanAmountCol.setCellValueFactory(new PropertyValueFactory<Loan, Double>("loanAmount"));
        loanBalanceCol.setCellValueFactory(new PropertyValueFactory<Loan, Double>("loanBalance"));
        monthlyCol.setCellValueFactory(new PropertyValueFactory<Loan, Double>("monthly"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<Loan, String>("loanDueDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Loan, String>("loanStatus"));

        ObservableList<Loan> data = loanListTableView.getItems();
        try {
            updateTable(data);
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        payLoanBtn.setDisable(true);
        viewLoanBtn.setDisable(true);

        loanListTableView.getSelectionModel().selectedItemProperty().addListener(
            (obs , oldSelection, newSelection) ->  {
                if(newSelection != null)
                {
                    selectedLoan = newSelection;
                    payLoanBtn.setDisable(false);
                    viewLoanBtn.setDisable(false);
                }
                else
                {
                    // selectedLoan = null;
                    payLoanBtn.setDisable(true);
                    viewLoanBtn.setDisable(true);
                }
            }
        );


        searchField.textProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
            {
                if (newValue.isBlank() || newValue.isEmpty()) 
                {
                    try {
                        updateTable(data);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try {
                        updateTableBySearch(data);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
            }

        });
        
    }

    @FXML
    void addLoan(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(App.loadFxml("addLoan"));
        Parent root = loader.load();

        Scene scene = addLoanBtn.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage)window;
        
        Scene addLoanScene = new Scene(root);
        stage.setScene(addLoanScene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    void payLoan(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(App.loadFxml("loanPayment"));
        Parent root = loader.load();


        PaymentController paymentController = loader.getController();
        paymentController.setLoan(selectedLoan);

        Scene scene = addLoanBtn.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage)window;
        
        Scene loanPayment = new Scene(root);
        stage.setScene(loanPayment);
        stage.show();
    }

    @FXML
    void viewLoan(ActionEvent event) throws IOException 
    {
        
        FXMLLoader loader = new FXMLLoader(App.loadFxml("viewLoanDetails"));
        Parent root = loader.load();

        ViewLoanDetailsController viewLoanDetailsController = loader.getController();
        viewLoanDetailsController.setLoan(selectedLoan);

        Stage viewLoanDetailsStage = new Stage();
        viewLoanDetailsStage.setTitle("View Loan Details");

        Scene viewLoanDetails = new Scene(root);
        viewLoanDetailsStage.setScene(viewLoanDetails);

        viewLoanDetailsStage.initModality(Modality.WINDOW_MODAL);
        viewLoanDetailsStage.initOwner(addLoanBtn.getScene().getWindow());
        viewLoanDetailsStage.show();
    }

    @FXML
    void clickOutside(MouseEvent event) 
    {
        loanListTableView.getSelectionModel().clearSelection();
    }

    public void updateTable(ObservableList<Loan> data) throws SQLException, IOException
    {
        ResultSet resultSet = Loan.getLoans();
        insertDataInTable(data, resultSet);
    }

    private void insertDataInTable(ObservableList<Loan> data, ResultSet resultSet) throws SQLException
    {
        data.clear();
        while (resultSet.next()) 
            {
                Loan loan = new Loan(
                        resultSet.getInt("coop_member_id"),
                        resultSet.getString("loan_type"),
                        resultSet.getDouble("loan_amount"),
                        resultSet.getDouble("loan_balance"),
                        resultSet.getString("loan_status"),
                        resultSet.getString("loan_due_date")
                    );
                
                String firstname = resultSet.getString("firstname");
                char middleInitial = resultSet.getString("middlename").charAt(0);
                String lastname = resultSet.getString("lastname");

                loan.setLoanId(resultSet.getInt("loan_id"));
                loan.setLoanCreated(resultSet.getString("loan_created"));
                loan.setLender(lastname + ", " + firstname + ", " + middleInitial + ".");
                data.add(loan);

            }

        loanListTableView.setItems(data);
    }

    private void updateTableBySearch(ObservableList<Loan> data) throws SQLException, IOException
    {
        String searchedText = searchField.getText();
        
        if(!(searchedText.isEmpty() || searchedText.isBlank()))
        {
            ResultSet resultSet = Loan.searchLoans(searchedText);
            insertDataInTable(data, resultSet);
        }
    }

    @FXML
    void searchLoan(ActionEvent event) 
    {

    }

}

