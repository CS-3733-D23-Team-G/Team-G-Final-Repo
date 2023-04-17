package edu.wpi.teamg.controllers;

import static javafx.scene.paint.Color.RED;

import edu.wpi.teamg.DAOs.AccountDAO;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controlsfx.control.textfield.CustomTextField;

public class LoginController {
  @FXML MFXButton loginButton;
  @FXML CustomTextField username;
  @FXML CustomTextField password;

  @FXML Label userInc;

  @FXML Label passInc;

  DBConnection db = new DBConnection();
  String query;

  public void initialize() {
    userInc.setVisible(false);
    passInc.setVisible(false);
    loginButton.setOnAction(event -> loginCheck());
  }

  public void loginCheck() {
    String user = username.getText(); // the fxid is still employeeid
    String pass = password.getText();

    AccountDAO accountDAO = new AccountDAO();
    db.setConnection();
    query = "select from " + accountDAO.getTable() + " where username = ?";
    try {
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      String tableUser = "";
      int tableEmp = 0;
      String tablePass = "";
      byte[] tableSalt = null;
      boolean tableAdmin = false;

      while (rs.next()) {

        tablePass = rs.getString("hashpassword");
        tableSalt = rs.getBytes("salt");
        // tableAdmin = rs.getBoolean("is_admin");
      }

      Account account = new Account();
      account.setPassword(pass);

      if (account.getHashedPassword(tableSalt).equals(tablePass)) {
        Navigation.navigate(Screen.HOME);
      } else {
        incorrectPassword();
      }

    } catch (SQLException e) {
      incorrectPassword();
      System.err.println("SQL Exception on Account");
      e.printStackTrace();
    }
  }

  public void incorrectPassword() {
    userInc.setVisible(true);
    passInc.setVisible(true);
    userInc.setTextFill(RED);
    passInc.setTextFill(RED);
  }
}
