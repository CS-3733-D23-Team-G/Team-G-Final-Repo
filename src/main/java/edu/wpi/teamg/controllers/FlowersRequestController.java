package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.Employee;
import edu.wpi.teamg.ORMClasses.FlowerRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FlowersRequestController {

  @FXML MFXComboBox bouquetSizeChoiceBox;
  @FXML MFXButton submit;
  @FXML MFXButton clearAll;
  // @FXML TextField deliveryLocation;
  // @FXML Label checkFields;

  // Hung This is the name and list associated with test searchable list
  //  @FXML SearchableComboBox locationSearchDropdown;

  // @FXML TextField orderingFor;
  // @FXML TextArea notes;

  ObservableList<String> locationList;
  ObservableList<String> employeeList;

  @FXML MFXDatePicker deliveryDate;

  @FXML TextField deliveryTime;
  @FXML TextField recipient;
  @FXML TextArea bouquetNote;

  @FXML ImageView selectedSunflower;
  @FXML ImageView sunflowerOption;

  @FXML ImageView selectedPurpleflower;
  @FXML ImageView purpleflowerOption;
  @FXML ImageView selectedRedflower;
  @FXML ImageView redflowerOption;

  @FXML AnchorPane forms;

  @FXML Label flowerChoice;

  String Order = "";

  @FXML MFXFilterComboBox locationSearchDropdown;

  @FXML MFXFilterComboBox employeeSearchDropdown;

  @FXML VBox vboxWithAssignTo;

  @FXML Label checkFields;

  @FXML Line assignToLine;
  @FXML Text assignToText;

  /*
   TODO: figure out how to get correct datatype to give to DB
  */

  ObservableList<String> listFlowers =
      FXCollections.observableArrayList(
          "Carnations", "Daisies", "Lilacs", "Orchids", "Peonies", "Roses", "Sunflowers");
  ObservableList<String> listSizes =
      FXCollections.observableArrayList("6 Stems (small)", "12 Stems (medium)", "24 Stems (large)");

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {

    bouquetSizeChoiceBox.setItems(listSizes);

    checkFields.setVisible(false);

    clearAll.setOnAction(event -> clearFlowers());

    if (!App.employee.getIs_admin()) {

      vboxWithAssignTo.getChildren().remove(assignToLine);
      vboxWithAssignTo.getChildren().remove(assignToText);
      vboxWithAssignTo.getChildren().remove(employeeSearchDropdown);
    }

    ArrayList<String> employeeNames = new ArrayList<>();
    HashMap<Integer, String> employeeLongName = this.getHashMapEmployeeLongName("Flowers Request");

    employeeLongName.forEach(
        (i, m) -> {
          employeeNames.add("ID " + i + ": " + m);
        });

    selectedSunflower.setVisible(false);
    selectedSunflower.setDisable(true);
    selectedPurpleflower.setVisible(false);
    selectedPurpleflower.setDisable(true);
    selectedRedflower.setVisible(false);
    selectedRedflower.setDisable(true);

    sunflowerOption.setOnMouseClicked(event -> selectSunFlowerOption());
    purpleflowerOption.setOnMouseClicked(event -> selectPurpleFlowerOption());
    redflowerOption.setOnMouseClicked(event -> selectRedFlowerOption());

    Collections.sort(employeeNames, String.CASE_INSENSITIVE_ORDER);

    employeeList = FXCollections.observableArrayList(employeeNames);

    ArrayList<String> locationNames = new ArrayList<>();
    HashMap<Integer, String> testingLongName = this.getHashMapMLongName();

    testingLongName.forEach(
        (i, m) -> {
          locationNames.add(m);
          //          System.out.println("Request ID:" + m.getReqid());
          //          System.out.println("Employee ID:" + m.getEmpid());
          //          System.out.println("Status:" + m.getStatus());
          //          System.out.println("Location:" + m.getLocation());
          //          System.out.println("Serve By:" + m.getServ_by());
          //          System.out.println();
        });

    Collections.sort(locationNames, String.CASE_INSENSITIVE_ORDER);

    locationList = FXCollections.observableArrayList(locationNames);

    // Hung this is where it sets the list - Andrew
    employeeSearchDropdown.setItems(employeeList);
    locationSearchDropdown.setItems(locationList);

    // checkFields.getText();

    submit.setOnAction(
        event -> {
          flowerOrder();
          completeAnimation();
          allDataFilled();
        });
    //    deliveryLocation.getText();
    //    orderingFor.getText();
    //    notes.setText("");

    // Hung this is where it sets the list - Andrew

    // locationSearchDropdown.setItems(locationList);

    //    serviceRequestChoiceBox.setOnMouseClicked(
    //        event -> {
    //          loadServiceRequestForm();
    //        });
    //    submit.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT));

  }

  /*
  String reqtype,
        int empid,
        String location,
        int serv_by,
        StatusTypeEnum status,
        Date deliveryDate,
        Time deliveryTime,
        String flowerType,
        int numFlower,
        String note,
        String roomField
   */
  public void storeFlowerValues() throws SQLException {

    HashMap<Integer, Employee> employeeHash = dao.getAllEmployees();

    Employee signedIn = employeeHash.get(App.employee.getEmpID());

    FlowerRequest flower =
        new FlowerRequest(
            "FL",
            "ID "
                + App.employee.getEmpID()
                + ": "
                + signedIn.getFirstName()
                + " "
                + signedIn.getLastName(),
            (String) locationSearchDropdown.getValue(),
            (String) employeeSearchDropdown.getValue(),
            StatusTypeEnum.blank,
            Date.valueOf(deliveryDate.getValue()),
            StringToTime(deliveryTime.getText()),
            Order,
            flowerConvert((String) bouquetSizeChoiceBox.getValue()),
            bouquetNote.getText(),
            recipient.getText());

    // System.out.println(Order);

    dao.insertFlowerRequest(flower);
    App.flowerRefresh();
    /*
    System.out.println(
        "Delivery Location: "
            + deliveryLocation.getText()
            + "\nroomField: "
            + roomField.getText()
            + "\nBouquet Note: "
            + bouquetNote.getText()
            + "\nDelivery Time: "
            + deliveryTime.getText()
            + "\nDelivery Date: "
            + deliveryDate.getText()
            + "\nBouquet Size: "
            + bouquetSizeChoiceBox.getValue());*/
  }

  public void selectSunFlowerOption() {
    if (selectedSunflower.isVisible() == false) {
      selectedSunflower.setVisible(true);
      // Order += "Sunflower, ";
      System.out.println(Order);
    } else if (selectedSunflower.isVisible() == true) {
      selectedSunflower.setVisible(false);
      // Order.replace("Sunflower, ", "");
      System.out.println(Order);
    }
  }

  public void selectPurpleFlowerOption() {
    if (selectedPurpleflower.isVisible() == false) {
      selectedPurpleflower.setVisible(true);
      // Order += "Purpleflower, ";
      // System.out.println(Order);
    } else if (selectedPurpleflower.isVisible() == true) {
      selectedPurpleflower.setVisible(false);
      // Order.replace("Purpleflower, ", "");
      System.out.println(Order);
    }
  }

  public void selectRedFlowerOption() {
    if (selectedRedflower.isVisible() == false) {
      selectedRedflower.setVisible(true);
      // Order += "Redflower, ";
      System.out.println(Order);
    } else if (selectedRedflower.isVisible() == true) {
      selectedRedflower.setVisible(false);
      // Order.replace("Redflower, ", "");
      System.out.println(Order);
    }
  }

  public void flowerOrder() {
    if (selectedRedflower.isVisible()) {
      Order += "Red Flower, ";
    }

    if (selectedPurpleflower.isVisible()) {
      Order += "Purple Flower, ";
    }

    if (selectedSunflower.isVisible()) {
      Order += "Sunflower, ";
    }
  }

  public HashMap<Integer, String> getHashMapEmployeeLongName(String canServe) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getEmployeeFullName(canServe);
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  // TODO: figure out clear for flowerTypeCheckBox
  public void clearFlowers() {
    bouquetSizeChoiceBox.setValue("");
    //    flowerTypeCheckBox.setCheckModel(null);
    //    flowerTypeCheckBox.getCheckModel().clearChecks();
    bouquetSizeChoiceBox.setValue(null);
    // deliveryLocation.setText("");
    locationSearchDropdown.setValue(null);
    deliveryTime.setText("");
    recipient.setText("");
    bouquetNote.setText("");
    deliveryDate.setValue(null);

    employeeSearchDropdown.setValue(null);

    selectedSunflower.setVisible(false);
    selectedSunflower.setDisable(true);
    selectedPurpleflower.setVisible(false);
    selectedPurpleflower.setDisable(true);
    selectedRedflower.setVisible(false);
    selectedRedflower.setDisable(true);
  }

  public HashMap<Integer, String> getHashMapMLongName() throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getMandFLLongName();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  public void allDataFilled() {
    if (!(bouquetSizeChoiceBox == null
        || Order.equals("")
        || deliveryDate.getValue() == null
        || locationSearchDropdown.getValue() == null
        // || deliveryLocation.getText().equals("")
        || recipient.getText().equals("")
        || deliveryTime.getText().equals(""))) {
      try {
        storeFlowerValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT);
    } else {
      checkFields.setVisible(true);
    }
  }

  public Time StringToTime(String s) {

    String[] hourMin = s.split(":", 2);
    Time t = new Time(Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), 00);
    return t;
  }

  public int flowerConvert(String t) {
    int i = 0;
    switch (t) {
      case "6 Stems (small)":
        i = 6;
        break;
      case "12 Stems (medium)":
        i = 12;
        break;
      case "24 Stems (large)":
        i = 24;
        break;
      default:
        i = -1;
    }
    return i;
  }

  public void completeAnimation() {

    // Form Completion PopUp
    AnchorPane rect = new AnchorPane();
    rect.setLayoutX(325);
    rect.setStyle(
        "-fx-pref-width: 440; -fx-pref-height: 100; -fx-background-color: #d9d9d9; -fx-border-radius: 5; -fx-background-insets: 5; -fx-border-insets: 5; -fx-padding: 5;"
            + "-fx-border-color: #000000;"
            + "-fx-border-width: 3;");
    rect.setLayoutY(800);
    rect.toFront();

    Text completionText = new Text("You Are All Set!");
    completionText.setLayoutX(445);
    completionText.setLayoutY(850);
    completionText.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 25;"
            + "-fx-font-weight: 500;");
    completionText.toFront();

    Text completionTextSecondRow = new Text("Conference Room Request Sent Successfully.");
    completionTextSecondRow.setLayoutX(445);
    completionTextSecondRow.setLayoutY(870);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 15;"
            + "-fx-font-weight: 500;");
    completionTextSecondRow.toFront();

    Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
    ImageView completionImage = new ImageView(checkmarkImage);

    completionImage.setFitHeight(120);
    completionImage.setFitWidth(120);
    completionImage.setLayoutX(320);
    completionImage.setLayoutY(790);
    completionImage.toFront();

    rect.setOpacity(0.0);
    completionImage.setOpacity(0.0);
    completionText.setOpacity(0.0);
    completionTextSecondRow.setOpacity(0.0);

    forms.getChildren().add(rect);
    forms.getChildren().add(completionText);
    forms.getChildren().add(completionImage);
    forms.getChildren().add(completionTextSecondRow);

    FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(1), rect);
    fadeIn1.setFromValue(0.0);
    fadeIn1.setToValue(1.0);

    FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(1), completionImage);
    fadeIn2.setFromValue(0.0);
    fadeIn2.setToValue(1.0);

    FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(1), completionText);
    fadeIn3.setFromValue(0.0);
    fadeIn3.setToValue(1.0);

    FadeTransition fadeIn4 = new FadeTransition(Duration.seconds(1), completionTextSecondRow);
    fadeIn4.setFromValue(0.0);
    fadeIn4.setToValue(1.0);

    ParallelTransition parallelTransition =
        new ParallelTransition(fadeIn1, fadeIn2, fadeIn3, fadeIn4);

    parallelTransition.play();

    parallelTransition.setOnFinished(
        (event) -> {
          FadeTransition fadeOut1 = new FadeTransition(Duration.seconds(1), rect);
          fadeOut1.setDelay(Duration.seconds(3));
          fadeOut1.setFromValue(1.0);
          fadeOut1.setToValue(0.0);

          FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(1), completionImage);
          fadeOut2.setDelay(Duration.seconds(3));
          fadeOut2.setFromValue(1.0);
          fadeOut2.setToValue(0.0);

          FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(1), completionText);
          fadeOut3.setDelay(Duration.seconds(3));
          fadeOut3.setFromValue(1.0);
          fadeOut3.setToValue(0.0);

          FadeTransition fadeOut4 =
              new FadeTransition(Duration.seconds(1), completionTextSecondRow);
          fadeOut4.setDelay(Duration.seconds(3));
          fadeOut4.setFromValue(1.0);
          fadeOut4.setToValue(0.0);

          fadeOut1.play();
          fadeOut2.play();
          fadeOut3.play();
          fadeOut4.play();
        });
  }

  //  public String mutipleFlowers(IndexedCheckModel<String> f1) {
  //    String s1 = "";
  //    for (int i = 0; i < f1.getItemCount(); i++) {
  //      if (!(f1.getCheckedItems().get(i) == null)) {
  //        s1 += f1.getCheckedItems().get(i) + ", ";
  //      }
  //    }
  //    return s1;
  //  }

  public void exit() {
    Platform.exit();
  }
}
