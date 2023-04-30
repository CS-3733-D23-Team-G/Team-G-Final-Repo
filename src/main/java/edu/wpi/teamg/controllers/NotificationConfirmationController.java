package edu.wpi.teamg.controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class NotificationConfirmationController {

  @FXML private ImageView confettiEffect1;
  @FXML private ImageView confettiEffect2;
  @FXML private ImageView confettiEffect3;

  @FXML
  public void initialize() {

    TranslateTransition translate1 = new TranslateTransition();
    translate1.setNode(confettiEffect1);
    translate1.setDuration(Duration.millis(1000));
    translate1.setCycleCount(1);
    translate1.setByY(700);

    TranslateTransition translate2 = new TranslateTransition();
    translate2.setNode(confettiEffect2);
    translate2.setDuration(Duration.millis(1000));
    translate2.setCycleCount(1);
    translate2.setByY(700);

    TranslateTransition translate3 = new TranslateTransition();
    translate3.setNode(confettiEffect3);
    translate3.setDuration(Duration.millis(1000));
    translate3.setCycleCount(1);
    translate3.setByY(700);

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

    translate1.play();
    // fade1.play();
    translate2.play();
    // fade2.play();
    translate3.play();
    // fade3.play();
    translate1.setOnFinished(e -> confettiEffect1.setVisible(false));
    translate2.setOnFinished(e -> confettiEffect2.setVisible(false));
    translate3.setOnFinished(e -> confettiEffect3.setVisible(false));

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
