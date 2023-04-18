package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.FlowerRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;

public class FlowersRequestController {

  @FXML ChoiceBox<String> bouquetSizeChoiceBox;
  @FXML CheckComboBox<String> flowerTypeCheckBox;
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
  @FXML TextField bouquetNote;

  @FXML ImageView selectedSunflower;
  @FXML ImageView sunflowerOption;

  @FXML ImageView selectedPurpleflower;
  @FXML ImageView purpleflowerOption;
  @FXML ImageView selectedRedflower;
  @FXML ImageView redflowerOption;

  @FXML Label flowerChoice;

  String Order = "";

  @FXML SearchableComboBox locationSearchDropdown;

  @FXML SearchableComboBox employeeSearchDropdown;

  @FXML Label checkFields;

  /*
   TODO: figure out how to get correct datatype to give to DB
  */

  ObservableList<String> listFlowers =
      FXCollections.observableArrayList(
          "Carnations", "Daisies", "Lilacs", "Orchids", "Peonies", "Roses", "Sunflowers");
  ObservableList<String> listSizes =
      FXCollections.observableArrayList("6 Stems (small)", "12 Stems (medium)", "24 Stems (large)");

  ObservableList<String> listEmployee =
      FXCollections.observableArrayList(
          "Tom", "Kristine", "Raj", "Professor Wong", "Mo", "Andrew", "Hung");

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {

    bouquetSizeChoiceBox.setItems(listSizes);

    checkFields.setVisible(false);

    clearAll.setOnAction(event -> clearFlowers());

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

    FlowerRequest flower =
        new FlowerRequest(
            "FL",
            "ID 1: John Doe",
            (String) locationSearchDropdown.getValue(),
            (String) employeeSearchDropdown.getValue(),
            StatusTypeEnum.blank,
            Date.valueOf(deliveryDate.getValue()),
            StringToTime(deliveryTime.getText()),
            Order,
            flowerConvert(bouquetSizeChoiceBox.getValue()),
            bouquetNote.getText(),
            recipient.getText());

    // System.out.println(Order);

    dao.insertFlowerRequest(flower);
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
        || employeeSearchDropdown.getValue() == null
        || Order.equals("")
        || deliveryDate.getValue() == null
        || locationSearchDropdown.getValue() == null
        // || deliveryLocation.getText().equals("")
        || recipient.getText().equals("")
        || deliveryTime.getText().equals("")
        || bouquetNote.getText().equals(""))) {
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
