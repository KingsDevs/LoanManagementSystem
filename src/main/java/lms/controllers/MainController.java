package lms.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lms.App;
import lms.models.Loan;

public class MainController 
{
    @FXML
    private Button addLoanBtn;

    @FXML
    private Button addMemberBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button reportsBtn;

    
    @FXML
    private Button logoutBtn;

    // private final int HOME_REF = 1;
    // private final int ADD_MEMBER_REF = 2;
    // private final int ADD_LOAN_REF = 3;
    // private final int SETTINGS_REF = 4;

    private Button oldActive = null;

    //private final int [] BUTTON_REFS = {HOME_REF, ADD_MEMBER_REF, ADD_LOAN_REF, SETTINGS_REF};

    private final PseudoClass activeTab = PseudoClass.getPseudoClass("activeTab");
    
    public void setHome() throws IOException
    {
        try {
            Loan.updateLoanStatusToDue();
            Loan.updateLoanBalance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        oldActive = null;
        homeAction(new ActionEvent());  
    }

    @FXML
    void addLoanAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addLoanMain");
        mainPane.setCenter(setPane);
        
        setTabActive(addLoanBtn);
    }

    @FXML
    void addMemberAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addMembersMain");
        mainPane.setCenter(setPane);
    
        setTabActive(addMemberBtn);
        
    }

    @FXML
    void homeAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("home");
        mainPane.setCenter(setPane);

        setTabActive(homeBtn);
    }

    @FXML
    void settingsAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("settings");
        mainPane.setCenter(setPane);

        setTabActive(settingsBtn);
    }

    @FXML
    void report(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("reports");
        mainPane.setCenter(setPane);

        setTabActive(reportsBtn);
    }

    @FXML
    void logout(ActionEvent event) 
    {
        Alert logoutConfirmation = new Alert(AlertType.CONFIRMATION);
        logoutConfirmation.setContentText("Do you really want to logout?");
        
        logoutConfirmation.showAndWait().ifPresent( response -> {
            if(response == ButtonType.OK)
            {
                FXMLLoader loader = new FXMLLoader(App.loadFxml("login"));

                Scene scene = logoutBtn.getScene();
                Window window = scene.getWindow();
                Stage stage = (Stage)window;

                Scene loginScene;
                try {
                    loginScene = new Scene(loader.load());
                    stage.setScene(loginScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // mainScene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
            }
        });
    }


    public void setAddMemberMain() throws IOException
    {
        oldActive = null;
        addMemberAction(new ActionEvent());
    }

    public void setAddLoanMain() throws IOException
    {
        oldActive = null;
        addLoanAction(new ActionEvent());
    }


    private Pane getPane(String paneName) throws IOException
    {
        Pane viewPane = null;

        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + paneName +".fxml"));
        viewPane = FXMLLoader.load(App.loadFxml(paneName));
        

        return viewPane;
    }

    private void setTabActive(Button activeTabBtn)
    {
        
        activeTabBtn.pseudoClassStateChanged(activeTab, true);
        if(oldActive != null && oldActive != activeTabBtn)
        {
            oldActive.pseudoClassStateChanged(activeTab, false);
        }

        if(activeTabBtn != oldActive)
            oldActive = activeTabBtn;
    }
}
