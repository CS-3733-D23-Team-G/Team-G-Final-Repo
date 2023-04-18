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
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;
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
  @FXML TextField roomField;
  @FXML TextField bouquetNote;

  @FXML SearchableComboBox locationSearchDropdown;
  @FXML SearchableComboBox EmployeeSearchBox;

  @FXML SearchableComboBox employeeSearchDropdown;

  /*
   TODO: figure out how to get correct datatype to give to DB
  */

  ObservableList<String> listFlowers =
      FXCollections.observableArrayList(
          "Carnations", "Daisies", "Lilacs", "Orchids", "Peonies", "Roses", "Sunflowers");
  ObservableList<String> listSizes =
      FXCollections.observableArrayList(
          "10 Stems (small)", "20 Stems (medium)", "30 Stems (large)");

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {

    bouquetSizeChoiceBox.setItems(listSizes);
    flowerTypeCheckBox.getItems().addAll(listFlowers);
    EmployeeSearchBox.getItems().addAll(listEmployee);

    ArrayList<String> employeeNames = new ArrayList<>();
    HashMap<Integer, String> employeeLongName = this.getHashMapEmployeeLongName("Flowers Request");

    employeeLongName.forEach(
        (i, m) -> {
          employeeNames.add("ID " + i + ": " + m);
        });

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
    clearAll.setOnAction(event -> clearFlowers());
    submit.setOnAction(
        event -> {
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
            mutipleFlowers(flowerTypeCheckBox.getCheckModel()),
            flowerConvert(bouquetSizeChoiceBox.getValue()),
            bouquetNote.getText(),
            roomField.getText());

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

  public HashMap<Integer, String> getHashMapEmployeeLongName(String canServe) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getEmployeeFullName(canServe);
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  public int bouquetSizeToInt(String s) {
    return -1;
  }

  // TODO: figure out clear for flowerTypeCheckBox
  public void clearFlowers() {
    bouquetSizeChoiceBox.setValue("");
    flowerTypeCheckBox.setCheckModel(null);
    EmployeeSearchBox.setValue(null);
    flowerTypeCheckBox.getCheckModel().clearChecks();
    bouquetSizeChoiceBox.setValue(null);
    // deliveryLocation.setText("");
    locationSearchDropdown.setValue(null);
    deliveryTime.setText("");
    roomField.setText("");
    bouquetNote.setText("");
    deliveryDate.setValue(null);
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
        || flowerTypeCheckBox == null
        // || deliveryLocation.getText().equals("")
        || roomField.getText().equals("")
        || deliveryTime.getText().equals("")
        || bouquetNote.getText().equals(""))) {
      try {
        storeFlowerValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT);
    } else {
      // checkFields.setText("Not All Fields Are Filled");
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
      case "10 Stems (small)":
        i = 10;
        break;
      case "20 Stems (medium)":
        i = 20;
        break;
      case "30 Stems (large)":
        i = 30;
        break;
      default:
        i = -1;
    }
    return i;
  }

  public String mutipleFlowers(IndexedCheckModel<String> f1) {
    String s1 = "";
    for (int i = 0; i < f1.getItemCount(); i++) {
      if (!(f1.getCheckedItems().get(i) == null)) {
        s1 += f1.getCheckedItems().get(i) + ", ";
      }
    }
    return s1;
  }

  public void exit() {
    Platform.exit();
  }
}
