package lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lms.models.Loan;

public class ReportsController implements Initializable
{

    @FXML
    private Label dayCountLabel;

    @FXML
    private Label monthCountLabel;

    @FXML
    private Label yearCountLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
       Platform.runLater(() -> {
            
            try {
                setLoanDayCount();
                setLoanMonthCount();
                setLoanYearCount();
            } catch (SQLException | IOException e) {
                
                e.printStackTrace();
            }
            
       });
        
    }

    private void setLoanDayCount() throws SQLException, IOException
    {
        dayCountLabel.setText("" + Loan.getLoanCountThisDay());
    }

    private void setLoanMonthCount() throws SQLException, IOException
    {
        monthCountLabel.setText("" + Loan.getLoanCountThisMonth());
    }

    private void setLoanYearCount() throws SQLException, IOException
    {
        yearCountLabel.setText("" + Loan.getLoanCountThisYear());
    }


}
