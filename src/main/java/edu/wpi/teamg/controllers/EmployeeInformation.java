package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.ORMClasses.Employee;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EmployeeInformation {
  @FXML Text EmployeeName;
  @FXML Text EmployeeFirstName;
  @FXML Text EmployeeLastName;
  @FXML Text EmployeeEmail;
  @FXML MFXButton edit;
  @FXML MFXButton save;
  @FXML MFXTextField EmpEmailTF;
  @FXML Text EMPID;

  public void initialize() {
    getEmployeeInformation();
    edit.setOnAction(event -> editEmployeeInformation());
    // Save.setOnMouseClicked(event -> saveEdit());
    EmpEmailTF.setVisible(false);
    save.setOnAction(
        event -> {
          try {
            saveEdit();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
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
    EmployeeEmail.setVisible(false);
    // Save.setVisible(true);
    // Save.setOnAction(event -> saveEmployeeInformation());
  }

  public void saveEdit() throws SQLException {
    Employee employee = new Employee();
    employee.setEmpID(Integer.parseInt(EMPID.getText()));
    EmployeeDAO employeeDAO = new EmployeeDAO();
    ;
    employeeDAO.update(employee, "email", EmpEmailTF.getText());
    EmployeeEmail.setText("" + EmpEmailTF.getText());
    EmpEmailTF.setVisible(false);
    EmployeeEmail.setVisible(true);
  }
}
  // Me actively trying to figure out how to update the database with the new information
    //  public void saveEmployeeInformation() {   //  EmployeeDAO employeeDAO = new EmployeeDAO();
