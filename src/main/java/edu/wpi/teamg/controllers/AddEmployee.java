package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.AccountDAO;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.ORMClasses.Employee;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.SearchableComboBox;

public class AddEmployee {
  @FXML MFXButton empSubmit;
  @FXML MFXButton empClear;

  @FXML MFXTextField FirstName;
  @FXML MFXTextField lastName;
  @FXML MFXTextField emailName;
  @FXML MFXTextField userName;
  @FXML MFXTextField Password;

  @FXML SearchableComboBox serveDrop;

  ObservableList<String> serveList =
      FXCollections.observableArrayList(
          "Meal Request",
          "Conference Room Request",
          "Flowers Request",
          "Office Supplies Request",
          "Furniture Request");

  EmployeeDAO empDao = new EmployeeDAO();
  AccountDAO accDao = new AccountDAO();

  @FXML
  public void initialize() throws SQLException {
    empSubmit.setOnMouseClicked(event -> allDataFilled());
    FirstName.getText();
    lastName.getText();
    emailName.getText();
    userName.getText();
    Password.getText();
    serveDrop.setItems(serveList);
  };

  private void allDataFilled() {
      if(!(FirstName.getText().equals(""))
        || lastName.getText().equals("")
        || emailName.getText().equals("")
        || userName.getText().equals("")
        || Password.getText().equals("")
        || serveDrop == null){
          try{
              storeEmployeeData();
          } catch (SQLException e) {
              e.printStackTrace();
          }
          Navigation.navigate(Screen.ADMIN_SIGNAGE_PAGE);
      }
  }
  private void storeEmployeeData() throws SQLException{
      Employee emp = new Employee();
  }
}
