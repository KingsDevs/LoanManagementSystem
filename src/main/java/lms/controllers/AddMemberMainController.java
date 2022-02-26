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
import javafx.stage.Stage;
import javafx.stage.Window;
import lms.App;
import lms.models.CoopMember;

public class AddMemberMainController implements Initializable
{
    @FXML
    private Button addMemberBtn;

    @FXML
    private TableView<CoopMember> memberTableView;

    @FXML
    private TableColumn<CoopMember, Integer> ageCol;

    @FXML
    private TableColumn<CoopMember, String> addressCol;

    @FXML
    private TableColumn<CoopMember, String> firstNameCol;

    @FXML
    private TableColumn<CoopMember, String> lastNameCol;

    @FXML
    private TableColumn<CoopMember, String> middleNameCol;

    @FXML
    private TableColumn<CoopMember, String> positionCol;

    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<CoopMember, String>("firstname"));
        middleNameCol.setCellValueFactory(new PropertyValueFactory<CoopMember, String>("middlename"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<CoopMember, String>("lastname"));
        positionCol.setCellValueFactory(new PropertyValueFactory<CoopMember, String>("position"));
        addressCol.setCellValueFactory(new PropertyValueFactory<CoopMember, String>("address"));
        ageCol.setCellValueFactory(new PropertyValueFactory<CoopMember, Integer>("age"));

        // try {
        //     ObservableList<CoopMember> observableList = FXCollections.observableList(CoopMember.getMembers());
        //     memberTableView.setItems(observableList);
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        
        updateTable();
        
    }

    @FXML
    void addMember(ActionEvent event) throws IOException 
    {

        FXMLLoader loader = new FXMLLoader(App.loadFxml("addMembers"));
        Parent root = loader.load();

        Scene scene = addMemberBtn.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage)window;
        
        Scene addMemberScene = new Scene(root);
        stage.setScene(addMemberScene);
        stage.setMaximized(true);
        stage.show();

    }

    public void updateTable()
    {
        ObservableList<CoopMember> data = memberTableView.getItems();
        
        try (ResultSet resultSet = CoopMember.getMembers()) {

            while (resultSet.next()) 
            {
                CoopMember coopMember = new CoopMember(
                    resultSet.getString("firstname"), 
                    resultSet.getString("middlename"), 
                    resultSet.getString("lastname"), 
                    resultSet.getString("position"), 
                    resultSet.getString("address"),
                    resultSet.getInt("age"));
                coopMember.setId(resultSet.getInt("id"));
                data.add(coopMember);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        memberTableView.setItems(data);
    }

    // public void addMemberInData(CoopMember coopMember)
    // {
    //     data.add(coopMember);
    //     memberTableView.setItems(data);
    // }
}
