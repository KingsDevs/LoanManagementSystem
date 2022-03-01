package lms.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
            Loan.updateLoanStatus(Loan.LOAN_STATUSES[2]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        homeAction(new ActionEvent());  
    }

    @FXML
    void addLoanAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addLoanMain");
        mainPane.setCenter(setPane);
        
        addLoanBtn.pseudoClassStateChanged(activeTab, true);
        if(oldActive != null && oldActive != addLoanBtn)
        {
            oldActive.pseudoClassStateChanged(activeTab, false);
        }
        oldActive = addLoanBtn;
    }

    @FXML
    void addMemberAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addMembersMain");
        mainPane.setCenter(setPane);
    
        addMemberBtn.pseudoClassStateChanged(activeTab, true);
        if(oldActive != null && oldActive != addMemberBtn)
        {
            oldActive.pseudoClassStateChanged(activeTab, false);
        }
        oldActive = addMemberBtn;
        
    }

    @FXML
    void homeAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("home");
        mainPane.setCenter(setPane);

        homeBtn.pseudoClassStateChanged(activeTab, true);
        if(oldActive != null && oldActive != homeBtn)
        {
            oldActive.pseudoClassStateChanged(activeTab, false);
        }
        oldActive = homeBtn;
    }

    @FXML
    void settingsAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addMembers");
        mainPane.setCenter(setPane);

        settingsBtn.pseudoClassStateChanged(activeTab, true);
        if(oldActive != null && oldActive != settingsBtn)
        {
            oldActive.pseudoClassStateChanged(activeTab, false);
        }
        oldActive = settingsBtn;
    }

    public void setAddMemberMain() throws IOException
    {
        addMemberAction(new ActionEvent());
    }

    public void setAddLoanMain() throws IOException
    {
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
        if(oldActive != null && oldActive != settingsBtn)
        {
            oldActive.pseudoClassStateChanged(activeTab, false);
        }
        oldActive = activeTabBtn;
    }
}
