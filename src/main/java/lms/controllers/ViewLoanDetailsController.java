package lms.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewLoanDetailsController 
{

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label loanAmountLabel;

    @FXML
    private TableColumn<?, ?> loanBalanceCol;

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
    private TableColumn<?, ?> paymentAmountCol;

    @FXML
    private TableColumn<?, ?> paymentDateCol;

    @FXML
    private TableView<?> paymentsTable;

    @FXML
    private Button printBtn;

    @FXML
    private ListView<?> scheduleList;

    @FXML
    void cancel(ActionEvent event) 
    {

    }

    @FXML
    void print(ActionEvent event) 
    {

    }

}
