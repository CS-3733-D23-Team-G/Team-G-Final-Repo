package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

public class FactorPopUp {
  @FXML Label factorIncorrect;
  @FXML MFXButton factorSubmit;
  @FXML MFXTextField factorText;

  String user;
  boolean tableAdmin;
  int tableEmp;

  PatientTopBannerController topBanner = new PatientTopBannerController();
  LoginController loginController = new LoginController();

  @FXML
  public void initialize() {

    tableEmp = App.getEmp();
    tableAdmin = App.getAdmin();
    user = App.getUser();
    factorText.setEditable(true);

    factorText.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER) {
            try {
              checkCode();
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    factorSubmit.setOnAction(
        event -> {
          try {
            checkCode();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    factorIncorrect.setVisible(false);

    topBanner.window.getRoot().setDisable(true);
  }

  private void checkCode() throws SQLException {
    if (factorText.getText().equals(App.getCode() + "")) {
      correctCode();
    } else {
      incorrectCode();
    }
  }

  public void correctCode() throws SQLException {
    DBConnection db = new DBConnection();
    db.setConnection();
    ResultSet rs1 = null;
    EmployeeDAO employeeDAO = new EmployeeDAO();

    Navigation.Logout();

    if (tableAdmin) {
      Navigation.setAdmin();
      App.employee.setIs_admin(true);
    }

    // if logged in, create employee ORM with user info
    String employeeQuery = "select * from " + employeeDAO.getTable() + " where empid = ?";
    try {
      PreparedStatement ps1 = db.getConnection().prepareStatement(employeeQuery);
      ps1.setInt(1, tableEmp);
      rs1 = ps1.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception on Account");
      e.printStackTrace();
    }

    while (rs1.next()) {
      App.employee.setEmpID(rs1.getInt("empid"));
      App.employee.setCan_serve(rs1.getString("can_serve"));
      App.employee.setEmail(rs1.getString("email"));
      App.employee.setFirstName(rs1.getString("firstname"));
      App.employee.setLastName(rs1.getString("lastname"));
    }

    db.closeConnection();
    Navigation.setLoggedin();

    // App.employee.setEmpID(tableEmp);

    topBanner.window.hide();
    loginController.window.hide();
    Navigation.navigate(Screen.HOME);
  }

  public void incorrectCode() {
    factorIncorrect.setVisible(true);
  }
}
