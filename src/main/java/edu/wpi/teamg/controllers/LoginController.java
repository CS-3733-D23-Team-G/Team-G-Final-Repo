package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.AccountDAO;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.controlsfx.control.textfield.CustomTextField;

public class LoginController {
  @FXML MFXButton loginButton;
  @FXML CustomTextField username;
  @FXML MFXPasswordField password;

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
    ResultSet rs = null;
    db.setConnection();
    query = "select * from " + accountDAO.getTable() + " where username = ?";

    try {
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setString(1, user);
      rs = ps.executeQuery();
      String tableUser = "";
      int tableEmp = 0;
      String tablePass = "";
      byte[] tableSalt = null;
      boolean tableAdmin = false;

      while (rs.next()) {

        tablePass = rs.getString("hashpassword");
        tableSalt = rs.getBytes("salt");
        tableAdmin = rs.getBoolean("is_admin");
      }

      Account account = new Account();
      account.setPassword(pass);

      if (account.getHashedPassword(tableSalt).equals(tablePass)) {
        Navigation.Logout();
        if (tableAdmin) Navigation.setAdmin();
        Navigation.setLoggedin();
        Navigation.navigate(Screen.HOME);
      } else {
        incorrectPassword();
      }

    } catch (SQLException e) {
      incorrectPassword();
      System.err.println("SQL Exception on Account");
      e.printStackTrace();
    } catch (NullPointerException e) {
      incorrectPassword();
      System.err.println("Chose neither username nor password that existed in the db");
      e.printStackTrace();
    }
  }

  public void incorrectPassword() {
    userInc.setFont(Font.font(20));
    passInc.setFont(Font.font(20));

    userInc.setVisible(true);
    passInc.setVisible(true);
  }
}
