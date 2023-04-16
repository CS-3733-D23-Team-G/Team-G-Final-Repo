package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.MealRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.controlsfx.control.SearchableComboBox;

public class MealRequestController {
  @FXML MFXButton mealSubmitButton;

  @FXML MFXButton mealClearAll;

  @FXML MFXDatePicker mealDate;

  // TextFields
  @FXML MFXTextField mealTimeOfDeliver;

  @FXML MFXTextField mealPersonOrderingForData;
  @FXML MFXTextField mealNotesData;

  @FXML ImageView selectedBurger;
  @FXML ImageView burgerOption;
  @FXML ImageView selectedCornDog;
  @FXML ImageView corndogOption;
  @FXML ImageView selectedFriedRice;
  @FXML ImageView friedRiceOption;

  // @FXML ChoiceBox<String> mealFoodChoice;
  @FXML Label mealFoodChoice;

  @FXML SearchableComboBox locationSearchDropdown;
  @FXML Label checkFields;

  String Order = "";

  ObservableList<String> locationList;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  ObservableList<String> foodList =
      FXCollections.observableArrayList(
          "Fenway Franks",
          "Choco Taco",
          "Salt-Based Steak",
          "Bisquit",
          "Shrimp Fried Rice",
          "Beef Wellington",
          "Spaghetii Taco",
          "Mac and Cheese Pizza",
          "Cavatappi",
          "One Singular Oyster",
          "CC Buritto Bowl (w/ Siracha)");

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    mealSubmitButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST_SUBMIT));
    //    mealSubmitButton.setOnMouseClicked(
    //        event -> {
    //          foodOrder();
    //        });
    mealSubmitButton.setOnMouseClicked(
        event -> {
          allDataFilled();
        });

    mealClearAll.setOnAction(event -> clearAllData());

    mealPersonOrderingForData.getText();
    mealNotesData.getText();
    // mealFoodChoice.setItems(foodList);
    mealFoodChoice.getText();
    mealDate.getValue();
    mealTimeOfDeliver.getText();

    selectedBurger.setVisible(false);
    selectedBurger.setDisable(true);
    selectedCornDog.setVisible(false);
    selectedCornDog.setDisable(true);
    selectedFriedRice.setVisible(false);
    selectedFriedRice.setDisable(true);
    burgerOption.setOnMouseClicked(event -> selectBurgerOption());
    friedRiceOption.setOnMouseClicked(event -> selectFriedRiceOption());
    corndogOption.setOnMouseClicked(event -> selectCorndogOption());

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
    locationSearchDropdown.setItems(locationList);
    checkFields.getText();
  }

  public void exit() {
    Platform.exit();
  }

  public void selectBurgerOption() {
    if (selectedBurger.isVisible() == false) {
      selectedBurger.setVisible(true);
      Order += "Cheeseburger, ";
    } else if (selectedBurger.isVisible() == true) {
      selectedBurger.setVisible(false);
      Order.replace("Cheeseburger, ", "");
    }
  }

  public void selectCorndogOption() {
    if (selectedCornDog.isVisible() == false) {
      selectedCornDog.setVisible(true);
      Order += "Corndog, ";
    } else if (selectedCornDog.isVisible() == true) {
      selectedCornDog.setVisible(false);
      Order.replace("Corndog, ", "");
    }
  }

  public void selectFriedRiceOption() {
    if (selectedFriedRice.isVisible() == false) {
      selectedFriedRice.setVisible(true);
      Order += "Shrimp Fried Rice, ";
    } else if (selectedFriedRice.isVisible() == true) {
      selectedFriedRice.setVisible(false);
      Order.replace("Shrimp Fried Rice, ", "");
    }
  }

  //  public void foodOrder() {
  //    if (selectedBurger.isVisible()) {
  //      Order += "Cheeseburger, ";
  //    }
  //    if (selectedCornDog.isVisible()) {
  //      Order += "Corndog, ";
  //    }
  //    if (selectedFriedRice.isVisible()) {
  //      Order += "Shrinmp Fried Rice, ";
  //    }
  //    mealFoodChoice.setText(Order);
  //    System.out.println(mealFoodChoice.getText());
  //  }

  public void storeMealValues() throws SQLException {
    MealRequest mr =
        new MealRequest(
            "M",
            1,
            // assume for now they are going to input a node number, so parseInt
            (String) locationSearchDropdown.getValue(),
            1,
            StatusTypeEnum.blank,
            Date.valueOf(mealDate.getValue()),
            StringToTime(mealTimeOfDeliver.getText()),
            mealPersonOrderingForData.getText(),
            Order,
            mealNotesData.getText());

    //    mr.setEmpid(1);
    //    mr.setServ_by(1);
    //    mr.setStatus(StatusTypeEnum.blank);
    //
    //    mr.setLocation(Integer.parseInt(mealDeliveryLocationData.getText()));
    //    mr.setRecipient(mealPersonOrderingForData.getText());
    //    mr.setNote(mealNotesData.getText());
    //    mr.setDeliveryDate(Date.valueOf(mealDate.getValue()));
    //    mr.setDeliveryTime(StringToTime(mealTimeOfDeliver.getText()));
    //    mr.setOrder(mealFoodChoice.getValue());

    //    System.out.println(
    //        "Employee ID: "
    //            + mr.getEmpid()
    //            + "\nDelivery Location: "
    //            + mr.getLocation()
    //            + "\nOrder: "
    //            + mr.getOrder()
    //            + "\nNote: "
    //            + mr.getNote()
    //            + "\nRecipient: "
    //            + mr.getRecipient()
    //            + "\nDelivery Date: "
    //            + mr.getDeliveryDate()
    //            + "\nDelivery Time: "
    //            + mr.getDeliveryTime()
    //            + "\nStatus: "
    //            + mr.getStatus());

    DAORepo dao = new DAORepo();
    dao.insertMealRequest(mr);
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

  public Time StringToTime(String s) {

    String[] hourMin = s.split(":", 2);
    Time t = new Time(Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), 00);
    return t;
  }

  public void allDataFilled() {
    if (!(mealPersonOrderingForData.getText().equals("")
        || mealNotesData.getText().equals("")
        || mealDate.getText().equals("")
        || mealTimeOfDeliver.getText().equals("")
        || Order.equals("")
        || locationSearchDropdown.getValue() == null)) {
      try {
        storeMealValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.MEAL_REQUEST_SUBMIT);
    } else {
      checkFields.setText("Not All Fields Are Filled");
    }
  }

  public void clearAllData() {
    // mealDeliveryLocationData.setText("");
    mealPersonOrderingForData.setText("");
    mealNotesData.setText("");
    mealDate.setText("");
    mealTimeOfDeliver.setText("");
    mealFoodChoice.setText("");
    locationSearchDropdown.setValue(null);
    return;
  }
}
