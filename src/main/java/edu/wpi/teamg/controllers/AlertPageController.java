package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AlertPageController {
  @FXML MFXDatePicker alertDate;
  @FXML MFXTextField alertTime;
  @FXML MFXTextField alertRecipients;
  @FXML MFXTextField alertMessage;
  @FXML MFXButton alertClearAll;
  @FXML MFXButton alertSubmit;
  @FXML Label checkFields;
}
