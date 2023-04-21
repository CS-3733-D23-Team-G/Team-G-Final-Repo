package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

  @FXML Label EmpName;

  @FXML
  public void initialize() throws SQLException {
    String fName = "";
    String lName = "";
    EmployeeDAO dao = new EmployeeDAO();
    DBConnection db = new DBConnection();
    db.setConnection();
    String sql = "select * from " + dao.getTable() + " where empid = ?";
    PreparedStatement ps = db.getConnection().prepareStatement(sql);
    ps.setInt(1, App.employee.getEmpID());
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      fName = rs.getString("firstname");
      lName = rs.getString("lastname");
    }
    db.closeConnection();
    EmpName.setText("Welcome " + fName + " " + lName);
  }
}
