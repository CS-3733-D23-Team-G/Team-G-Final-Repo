package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.AccountDAO;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import edu.wpi.teamg.ORMClasses.Employee;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

  @FXML TableView<Employee> empTableView;

  @FXML TableColumn<Employee, String> empFirstName;
  @FXML TableColumn<Employee, String> empLastName;
  @FXML TableColumn<Employee, String> empUsername;
  @FXML TableColumn<Employee, String> empEmail;
  @FXML TableColumn<Employee, String> empCanServe;

  ObservableList<String> serveList =
      FXCollections.observableArrayList(
          "Meal Request",
          "Conference Room Request",
          "Flowers Request",
          "Office Supplies Request",
          "Furniture Request");

  EmployeeDAO empDao = new EmployeeDAO();
  AccountDAO accDao = new AccountDAO();

  ObservableList<Employee> testEmpList;

  public void initialize() throws SQLException {
    empSubmit.setOnMouseClicked(event -> allDataFilled());
    FirstName.getText();
    lastName.getText();
    emailName.getText();
    userName.getText();
    Password.getText();
    serveDrop.setItems(serveList);

    HashMap<String, Account> accUsernames = accDao.getAll();

    HashMap<Integer, Employee> testingEmps = App.testingEmps;
    ArrayList<Employee> emps = new ArrayList<>(testingEmps.values());

    testEmpList = FXCollections.observableArrayList(emps);

    empTableView.setItems(testEmpList);

    empFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    empLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    empEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    empUsername.setCellValueFactory(
        (row) -> {
          try {
            return new SimpleStringProperty(accDao.getUsernameFromEmpID(row.getValue().getEmpID()));
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    empCanServe.setCellValueFactory(new PropertyValueFactory<>("can_serve"));
  };

  private void allDataFilled() {
    if (!(FirstName.getText().equals(""))
        || lastName.getText().equals("")
        || emailName.getText().equals("")
        || userName.getText().equals("")
        || Password.getText().equals("")
        || serveDrop == null) {
      try {
        storeEmployeeData();
      } catch (SQLException | NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
      Navigation.navigate(Screen.EMPLOYEE_CONFIRMATION);
    }
  }

  private void storeEmployeeData() throws SQLException, NoSuchAlgorithmException {
    DBConnection conn = new DBConnection();
    conn.setConnection();
    int empid = 0;
    int maxid = 0;
    String sql = "select empid from teamgdb.iteration4.employee order by empid desc limit 1";
    PreparedStatement ps_max = conn.getConnection().prepareStatement(sql);
    ResultSet rs_max = ps_max.executeQuery();
    while (rs_max.next()) {
      maxid = rs_max.getInt("empid");
      maxid++;
    }
    conn.closeConnection();

    Employee emp = new Employee();
    emp.setEmpID(maxid);
    emp.setFirstName(FirstName.getText());
    emp.setLastName(lastName.getText());
    emp.setEmail(emailName.getText());
    emp.setCan_serve(
        (String) serveDrop.getValue()); // This is just to get it running and see if it executes
    empDao.insert(emp);

    Account acc = new Account();
    acc.setUsername(userName.getText());
    acc.setPassword(Password.getText());
    acc.setEmpID(emp.getEmpID());
    accDao.insertAccount(acc, acc.getPassword(), false);
  }
}
