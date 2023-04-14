package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import org.controlsfx.control.textfield.CustomTextField;

public class LoginController {
  @FXML MFXButton loginButton;
  @FXML CustomTextField EmployeeIDs;
  @FXML CustomTextField PasswordInput;

  public void initialize() {
    loginButton.setOnAction(event -> loginCheck());
  }

  public void loginCheck() {
    String IDCheck = EmployeeIDs.getText();
    String PasswordCheck = PasswordInput.getText();
    if (IDCheck.equals("69420") && PasswordCheck.equals("yourtellingmeashrimpfriedthisrice?")) {
      Navigation.navigate(Screen.HOME);
      System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaah");
    } else {
      System.out.println("naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaah");
      PasswordInput.setText("Incorrect");
    }
  }
}
