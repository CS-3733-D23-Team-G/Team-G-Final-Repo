package edu.wpi.teamg.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  MEAL_REQUEST("views/MealRequestPage.fxml"),
  ROOM_REQUEST("views/ConRoomRequestPage.fxml"),
  FLOWERS_REQUEST("views/FlowersRequestPage.fxml"),
  FURNITURE_REQUEST("views/FurnitureRequestPage.fxml"),
  SUPPLIES_REQUEST("views/OfficeSuppRequestPage.fxml"),

  SIGNAGE_PAGE("views/SignagePage.fxml"),
  ADMIN_SIGNAGE_PAGE("views/SignageAdmin.fxml"),
  ROOM_REQUEST_SUBMIT("views/ConRoomRequestConfirmationPage.fxml"),
  MEAL_REQUEST_SUBMIT("views/MealRequestConfirmationPage.fxml"),
  FLOWERS_REQUEST_SUBMIT("views/FlowersRequestConfirmationPage.fxml"),
  FURNITURE_REQUEST_SUBMIT("views/FurnitureRequestConfirmationPage.fxml"),
  SUPPLIES_REQUEST_SUBMIT("views/OfficeSuppRequestConfirmationPage.fxml"),
  STATUS_PAGE("views/FormStatus.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
