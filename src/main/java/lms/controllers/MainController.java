package lms.controllers;

import java.io.IOException;
import java.net.URL;
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

    // public MainController() throws IOException
    // {
    //     Pane setPane = getPane("home");
    //     mainPane.setCenter(setPane);
    // }
    
    public void setHome() throws IOException
    {
        homeAction(new ActionEvent());
    }

    @FXML
    void addLoanAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addLoanMain");
        mainPane.setCenter(setPane);
    }

    @FXML
    void addMemberAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addMembersMain");
        mainPane.setCenter(setPane);
        
    }

    @FXML
    void homeAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("home");
        mainPane.setCenter(setPane);
    }

    @FXML
    void settingsAction(ActionEvent event) throws IOException 
    {
        Pane setPane = getPane("addMembers");
        mainPane.setCenter(setPane);
    }

    public void setAddMemberMain() throws IOException
    {
        addMemberAction(new ActionEvent());
    }


    private Pane getPane(String paneName) throws IOException
    {
        Pane viewPane = null;

        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + paneName +".fxml"));
        viewPane = FXMLLoader.load(App.loadFxml(paneName));
        

        return viewPane;
    }
}
