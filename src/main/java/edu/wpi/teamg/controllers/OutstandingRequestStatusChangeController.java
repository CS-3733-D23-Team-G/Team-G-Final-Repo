package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;

public class OutstandingRequestStatusChangeController {

  @FXML MFXButton cancel;

  @FXML MFXButton confirm;

  @FXML MFXComboBox statusComboBox;

  ObservableList<StatusTypeEnum> statusList =
      FXCollections.observableArrayList(
          StatusTypeEnum.blank, StatusTypeEnum.processing, StatusTypeEnum.done);

  RequestDAO requestDAO = new RequestDAO();

  public void initialize() throws SQLException {

    statusComboBox.setItems(statusList);

    statusComboBox.getSelectionModel().selectItem(HomeController.currentStatus);

    App.bool = false;

    confirm.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            try {
              requestDAO.updateRequestStatus(
                  (StatusTypeEnum) statusComboBox.getValue(), HomeController.requestToBeChanged);

              switch (HomeController.reqTypeToBeChanged) {
                case "M":
                  App.mealRefresh();
                  break;
                case "CR":
                  App.confRefresh();
                  break;
                case "FL":
                  App.flowerRefresh();
                  break;
                case "MA":
                  App.maintainRefresh();
                  break;
                case "OS":
                  App.oSuppsRefresh();
                  break;
                case "FR":
                  App.fernsRefresh();
                  break;
              }

              if (statusComboBox.getValue() == StatusTypeEnum.done) {
                HomeController.changeStatusConfirmation.hide();
              }
              HomeController.playAnimationStatus = true;
              Navigation.navigate(Screen.HOME);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    cancel.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            HomeController.changeStatusConfirmation.hide();
          }
        });
  }
}
