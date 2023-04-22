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

public class FactorPopUp {
  @FXML Label factorIncorrect;
  @FXML MFXButton factorSubmit;
  @FXML MFXTextField factorText;

  String user;
  boolean tableAdmin;
  int tableEmp;

  @FXML
  public void initialize() {
    tableEmp = App.getEmp();
    tableAdmin = App.getAdmin();
    user = App.getUser();
    factorText.setEditable(true);
    factorSubmit.setOnAction(
        event -> {
          try {
            checkCode();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    factorIncorrect.setVisible(false);
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
    if (tableAdmin) Navigation.setAdmin();

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

    Navigation.setLoggedin();
    App.employee.setEmpID(tableEmp);
    PatientTopBannerController topBanner = new PatientTopBannerController();
    Navigation.navigate(Screen.HOME);
    topBanner.window.hide();
  }

  public void incorrectCode() {
    factorIncorrect.setVisible(true);
  }
}
