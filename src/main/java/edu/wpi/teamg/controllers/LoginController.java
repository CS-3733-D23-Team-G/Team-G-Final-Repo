package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.AccountDAO;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import edu.wpi.teamg.ORMClasses.TwoFactorAuth;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javax.mail.MessagingException;

public class LoginController {
  @FXML MFXButton loginButton;
  @FXML MFXTextField username;
  @FXML MFXPasswordField password;

  // @FXML Label userInc;

  @FXML Label passInc;

  // @FXML MFXButton addAccounts;

  DBConnection db = new DBConnection();
  String query;
  String employeeQuery;

  public void initialize() {
    // userInc.setVisible(false);
    passInc.setVisible(false);
    loginButton.setOnAction(event -> loginCheck());
    //  addAccounts.setOnMouseClicked(
    //        event -> {
    //          try {
    //            addAccount();
    //          } catch (NoSuchAlgorithmException e) {
    //            throw new RuntimeException(e);
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });
    password.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER) {
            loginCheck();
          }
        });
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
        tableUser = rs.getString("username");
        tableEmp = rs.getInt("empid");
        tablePass = rs.getString("hashpassword");
        tableSalt = rs.getBytes("salt");
        tableAdmin = rs.getBoolean("is_admin");
      }

      Account account = new Account();
      account.setPassword(pass);

      if (account.getHashedPassword(tableSalt).equals(tablePass)) {
        password.setEditable(false);
        TwoFactorAuth twoFac = new TwoFactorAuth();
        twoFac.sendEmail(tableUser);

        FactorPopUp popUp = new FactorPopUp();
        App.setUser(tableUser);
        App.setAdmin(tableAdmin);
        App.setEmp(tableEmp);
        App.setCode(twoFac.getCode());
        Navigation.navigate(Screen.TWO_FAC);
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
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public void incorrectPassword() {
    // userInc.setFont(Font.font(20));
    passInc.setFont(Font.font(20));

    // .setVisible(true);
    passInc.setVisible(true);
  }

  public void addAccount() throws NoSuchAlgorithmException, SQLException {
    Account admin = new Account("admin", "admin", true);
    Account staff = new Account("staff", "staff", false);
    byte[] saltAdmin = admin.getSalt();
    byte[] saltStaff = staff.getSalt();

    admin.setEmpID(0);
    staff.setEmpID(1);
    String hashedAdmin = admin.getHashedPassword(saltAdmin);
    String hashedStaff = admin.getHashedPassword(saltStaff);

    AccountDAO accountDAO = new AccountDAO();
    accountDAO.insertAccount(admin, hashedAdmin, true);
    accountDAO.insertAccount(staff, hashedStaff, false);
  }
}
