package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EmployeeInformation {
  @FXML
  Text EmployeeName;
  @FXML
  Text EmployeeFirstName;
  @FXML
  Text EmployeeLastName;
  @FXML
  Text EmployeeEmail;
  @FXML
  MFXButton edit;
  @FXML
  MFXButton Save;
  @FXML
  MFXTextField EmpEmailTF;
  @FXML
  Text EMPID;

  public void initialize() {
    getEmployeeInformation();
    edit.setOnAction(event -> editEmployeeInformation());
    // Save.setOnMouseClicked(event -> saveEdit());
  }

  public void getEmployeeInformation() {
    EmployeeName.setText("" + App.employee.getFirstName() + " " + App.employee.getLastName());
    EmployeeFirstName.setText("" + App.employee.getFirstName());
    EmployeeLastName.setText("" + App.employee.getLastName());
    EmployeeEmail.setText("" + App.employee.getEmail());
    EmpEmailTF.setText("" + App.employee.getEmail());
    EMPID.setText("" + App.employee.getEmpID());
    // make an edit button that will allow you to edit the information
  }

  public void editEmployeeInformation() {
    EmpEmailTF.setVisible(true);
    EmpEmailTF.setEditable(true);
    // Save.setVisible(true);
    edit.setVisible(false);
    // Save.setOnAction(event -> saveEmployeeInformation());
  }

//  public void saveEdit() throws SQLException {
//    EmployeeDAO employeeDAO = new EmployeeDAO();
//    HashMap<Integer, Employee> emp = employeeDAO.getAll();
//    EmpEmailTF.setVisible(false);
//    EmpEmailTF.setEditable(false);
//    edit.setVisible(true);
//    // Save.setVisible(false);
//    App.employee.setEmail(EmpEmailTF.getText());
//    EmployeeEmail.setText("" + App.employee.getEmail());
//    //  EmployeeDAO.update(App.employee);
//
//  }
//}
  //Me actively trying to figure out how to update the database with the new information
}