package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class MaintenanceRequestConfirmationController {

  @FXML
  public void initialize() {
    App.bool = false;
    //    FadeTransition fade1 = new FadeTransition();
    //    fade1.setNode(confettiEffect1);
    //    fade1.setDuration(Duration.millis(1000));
    //    fade1.setCycleCount(TranslateTransition.INDEFINITE);
    //    fade1.setInterpolator(Interpolator.LINEAR);
    //    fade1.setFromValue(1);
    //    fade1.setFromValue(0);
    //
    //    FadeTransition fade2 = new FadeTransition();
    //    fade2.setNode(confettiEffect2);
    //    fade2.setDuration(Duration.millis(1000));
    //    fade2.setCycleCount(TranslateTransition.INDEFINITE);
    //    fade2.setInterpolator(Interpolator.LINEAR);
    //    fade2.setFromValue(1);
    //    fade2.setFromValue(0);
    //
    //    FadeTransition fade3 = new FadeTransition();
    //    fade3.setNode(confettiEffect3);
    //    fade3.setDuration(Duration.millis(1000));
    //    fade3.setCycleCount(TranslateTransition.INDEFINITE);
    //    fade3.setInterpolator(Interpolator.LINEAR);
    //    fade3.setFromValue(1);
    //    fade3.setFromValue(0);

    // fade3.play();

    //    confettiEffect1.setVisible(false);
    //    confettiEffect2.setVisible(false);
    //    confettiEffect3.setVisible(false);
    //
    //    translate1.stop();
    //    fade1.stop();
    //    translate2.stop();
    //    fade2.stop();
    //    translate3.stop();
    //    fade3.stop();
  }

  public void exit() {
    Platform.exit();
  }
}
