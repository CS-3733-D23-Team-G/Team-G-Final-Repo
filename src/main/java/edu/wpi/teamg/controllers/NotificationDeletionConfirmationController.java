package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.NotificationDAO;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;

public class NotificationDeletionConfirmationController {

  @FXML MFXButton cancel;

  @FXML MFXButton delete;

  NotificationDAO notificationDAO = new NotificationDAO();

  public void initialize() throws SQLException {
    App.bool = false;
    int notifToBeDeleted = HomeController.notifToBeDeleted;

    delete.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            try {
              notificationDAO.delete(notifToBeDeleted);
              HomeController.deleteConfirmation.hide();

              Navigation.navigate(Screen.HOME);

            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    cancel.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            HomeController.deleteConfirmation.hide();
          }
        });

    System.out.println("Dismiss Notif " + notifToBeDeleted);
  }
}
