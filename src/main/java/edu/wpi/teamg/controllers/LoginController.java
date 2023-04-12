package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class LoginController {
  @FXML MFXButton loginButton;
  @FXML MFXTextField EmployeeID;
  @FXML MFXTextField Password;

  public void initialize() {
    loginButton.setOnAction(event -> loginCheck());
  }

  public void loginCheck() {
    String IDCheck = EmployeeID.getText();
    String PasswordCheck = Password.getText();
    if (IDCheck.equals("69420") && PasswordCheck.equals("yourtellingmeashrimpfriedthisrice?")) {
      Navigation.navigate(Screen.HOME);
    } else {
      Password.setText("Error");
    }
  }
}
