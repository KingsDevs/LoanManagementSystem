package lms.controllers;

import java.io.IOException;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lms.App;

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

    private final int HOME_REF = 1;
    private final int ADD_MEMBER_REF = 2;
    private final int ADD_LOAN_REF = 3;
    private final int SETTINGS_REF = 4;

    private final int [] BUTTON_REFS = {HOME_REF, ADD_MEMBER_REF, ADD_LOAN_REF, SETTINGS_REF};

    private final PseudoClass activeTab = PseudoClass.getPseudoClass("activeTab");
    
    public void setHome() throws IOException
    {
        homeAction(new ActionEvent());
    }

    @FXML
    void addLoanAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addLoanMain");
        mainPane.setCenter(setPane);
        setTabActive(ADD_LOAN_REF);
    }

    @FXML
    void addMemberAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addMembersMain");
        mainPane.setCenter(setPane);
        setTabActive(ADD_MEMBER_REF);
        
    }

    @FXML
    void homeAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("home");
        mainPane.setCenter(setPane);
        setTabActive(HOME_REF);
    }

    @FXML
    void settingsAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addMembers");
        mainPane.setCenter(setPane);
        setTabActive(SETTINGS_REF);
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

    private void setTabActive(int num)
    {
        if(num == HOME_REF)
        {
            homeBtn.pseudoClassStateChanged(activeTab, true);
        }
        else
        {
            homeBtn.pseudoClassStateChanged(activeTab, false);
        }

        if(num == ADD_MEMBER_REF)
        {
            addMemberBtn.pseudoClassStateChanged(activeTab, true);
        }
        else
        {
            addMemberBtn.pseudoClassStateChanged(activeTab, false);
        }

        if(num == ADD_LOAN_REF)
        {
            addLoanBtn.pseudoClassStateChanged(activeTab, true);
        }
        else
        {
            addLoanBtn.pseudoClassStateChanged(activeTab, false);
        }

        if(num == SETTINGS_REF)
        {
            settingsBtn.pseudoClassStateChanged(activeTab, true);
        }
        else
        {
            settingsBtn.pseudoClassStateChanged(activeTab, false);
        }
    }
}
