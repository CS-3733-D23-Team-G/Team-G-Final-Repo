package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.AccountDAO;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import edu.wpi.teamg.ORMClasses.TwoFactorAuth;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;
import javax.mail.MessagingException;
import org.controlsfx.control.PopOver;

public class LoginController {
  @FXML MFXButton loginButton;
  @FXML MFXTextField username;
  @FXML MFXPasswordField password;

  // @FXML Label userInc;

  @FXML Label passInc;

  // @FXML MFXButton addAccounts;

  DBConnection db = new DBConnection();
  String query;
  static PopOver window = new PopOver();
  PatientTopBannerController topBanner = new PatientTopBannerController();

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
    ResultSet rs1 = null;

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
        if (username.getText().equals("admin")
            || username.getText().equals("staff")
            || App.usernames.contains(tableUser)) {
          Navigation.Logout();
          if (tableAdmin) {
            Navigation.setAdmin();
            App.employee.setIs_admin(true);
          }
          EmployeeDAO employeeDAO = new EmployeeDAO();

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

          db.closeConnection();
          Navigation.navigate(Screen.HOME);
          topBanner.window.hide();
        } else {
          password.setEditable(false);
          TwoFactorAuth twoFac = new TwoFactorAuth();
          twoFac.sendEmail(tableUser);

          App.setUser(tableUser);
          App.setAdmin(tableAdmin);
          App.setEmp(tableEmp);
          App.setCode(twoFac.getCode());

          db.closeConnection();
          twoFactor();
          App.usernames.add(tableUser);
          // Navigation.navigate(Screen.TWO_FAC);
        }

      } else {
        db.closeConnection();
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
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void twoFactor() throws IOException {

    var loader = new FXMLLoader(App.class.getResource("views/2FactorPopUp.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    window.setTitle("2FA Panel");

    window.setHeaderAlwaysVisible(true);
    FactorPopUp controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

    window.setOnHidden(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent event) {
            topBanner.window.getRoot().setDisable(false);
          }
        });
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
