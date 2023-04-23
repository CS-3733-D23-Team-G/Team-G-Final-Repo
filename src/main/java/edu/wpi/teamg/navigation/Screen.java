package edu.wpi.teamg.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  MEAL_REQUEST("views/MealRequestPage.fxml"),
  ROOM_REQUEST("views/ConRoomRequestPage.fxml"),
  FLOWERS_REQUEST("views/FlowersRequestPage.fxml"),
  FURNITURE_REQUEST("views/FurnitureRequestPage.fxml"),
  SUPPLIES_REQUEST("views/OfficeSuppRequestPage.fxml"),

  PATHFINDING_PAGE("views/pathfinding.fxml"),

  SIGNAGE_PAGE("views/SignagePage.fxml"),

  SIGNAGE_SCREENSAVER_PAGE("views/SignageScreenSaver.fxml"),

  ADMIN_SIGNAGE_PAGE("views/SignageAdmin.fxml"),

  EDIT_SIGNAGE_PAGE("views/SignageEditor.fxml"),
  ROOM_REQUEST_SUBMIT("views/ConRoomRequestConfirmationPage.fxml"),
  MEAL_REQUEST_SUBMIT("views/MealRequestConfirmationPage.fxml"),
  FLOWERS_REQUEST_SUBMIT("views/FlowersRequestConfirmationPage.fxml"),
  FURNITURE_REQUEST_SUBMIT("views/FurnitureRequestConfirmationPage.fxml"),
  SUPPLIES_REQUEST_SUBMIT("views/OfficeSuppRequestConfirmationPage.fxml"),
  ADMIN_STATUS_PAGE("views/AdminFormStatus.fxml"),
  LOGIN_PAGE("views/Login.fxml"),
  EMPLOYEE_FORMS("views/EmployeeSelfFormView.fxml"),
  ADMIN_SIGNAGE_EDITOR("views/SignageAdmin.fxml"),
  ADMIN_MAP_EDITOR("views/MapEditor.fxml"),
  ADD_EMPLOYEE("views/AddEmployee.fxml"),
  EMPLOYEE_INFO("views/EmployeeInformation.fxml"),
  TWO_FAC("views/2FactorPopUp.fxml"),
  NODE_EDITOR("views/NodeViewAndEditor.fxml"),

  DICTIONARY("views/Dictionary.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
