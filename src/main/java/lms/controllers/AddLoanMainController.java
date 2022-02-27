package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
                    payLoanBtn.setDisable(false);
                    viewLoanBtn.setDisable(false);
                }
                else
                {
                    payLoanBtn.setDisable(true);
                    viewLoanBtn.setDisable(true);
                }
            }
        );
        
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
    void payLoan(ActionEvent event) 
    {
        System.out.println("pay loan");
    }

    @FXML
    void viewLoan(ActionEvent event) 
    {
        System.out.println("view loan");
    }

    @FXML
    void clickOutside(MouseEvent event) 
    {
        loanListTableView.getSelectionModel().clearSelection();
    }

    public void updateTable(ObservableList<Loan> data) throws SQLException, IOException
    {
        ResultSet resultSet = Loan.getLoans();

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
                loan.setLender(lastname + ", " + firstname + ", " + middleInitial + ".");
                data.add(loan);

            }

        loanListTableView.setItems(data);
    }

}

