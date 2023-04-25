package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.Employee;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;

public class MealRequestController {
  @FXML MFXButton mealSubmitButton;

  @FXML MFXButton mealClearAll;

  @FXML MFXDatePicker mealDate;

  // TextFields
  @FXML MFXTextField mealTimeOfDeliver;

  @FXML MFXTextField mealPersonOrderingForData;
  @FXML TextArea mealNotesData;
  @FXML ImageView image1;
  @FXML ImageView image2;
  @FXML ImageView image3;
  @FXML ImageView image4;
  @FXML ImageView image5;
  @FXML ImageView image6;
  @FXML Text text1;
  @FXML Text text2;
  @FXML Text text3;
  @FXML Text text4;
  @FXML Text text5;
  @FXML Text text6;
  @FXML Pane pane1;
  @FXML Pane pane2;
  @FXML Pane pane3;
  @FXML Pane pane4;
  @FXML Pane pane5;
  @FXML Pane pane6;

  @FXML MFXButton mealButton;
  @FXML MFXButton snackButton;
  @FXML MFXButton drinkButton;

  // @FXML ChoiceBox<String> mealFoodChoice;
  //  @FXML Label mealFoodChoice;

  @FXML SearchableComboBox locationSearchDropdown;
  @FXML SearchableComboBox employeeSearchDropdown;
  @FXML Label checkFields;
  @FXML Line assignToLine;
  @FXML Text assignToText;
  @FXML VBox vboxWithAssignTo;

  String Order = "";

  ObservableList<String> locationList;
  ObservableList<String> employeeList;

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    MealPressed();
    mealSubmitButton.setOnMouseClicked(
        event -> {
          MealOrder();
          allDataFilled();
        });
    mealButton.setOnMouseClicked(event -> MealPressed());
    snackButton.setOnMouseClicked(event -> SnackPressed());
    drinkButton.setOnMouseClicked(event -> DrinkPressed());

    if (!App.employee.getIs_admin()) {
      vboxWithAssignTo.getChildren().remove(assignToLine);
      vboxWithAssignTo.getChildren().remove(assignToText);
      vboxWithAssignTo.getChildren().remove(employeeSearchDropdown);
    }

    checkFields.setVisible(false);

    mealClearAll.setOnAction(event -> clearAllData());
    mealPersonOrderingForData.getText();
    mealNotesData.getText();
    // mealFoodChoice.setItems(foodList);
    //    mealFoodChoice.getText();
    mealDate.getValue();
    mealTimeOfDeliver.getText();

    ArrayList<String> employeeNames = new ArrayList<>();
    HashMap<Integer, String> employeeLongName = this.getHashMapEmployeeLongName("Meal Request");

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
        });

    Collections.sort(locationNames, String.CASE_INSENSITIVE_ORDER);

    locationList = FXCollections.observableArrayList(locationNames);

    // Hung this is where it sets the list - Andrew
    employeeSearchDropdown.setItems(employeeList);
    locationSearchDropdown.setItems(locationList);
    checkFields.getText();
  }

  public void MealPressed() {
    Image burger = new Image(App.class.getResourceAsStream("Images/burger.jpg"));

    Image dog = new Image(App.class.getResourceAsStream("Images/dog.jpg"));

    Image pizza = new Image(App.class.getResourceAsStream("Images/pizza.jpg"));

    Image sushi = new Image(App.class.getResourceAsStream("Images/sushi.jpg"));

    Image taco = new Image(App.class.getResourceAsStream("Images/taco.jpg"));

    Image sandwich = new Image(App.class.getResourceAsStream("Images/sandwich.jpg"));
    text1.setText("Burger");
    text2.setText("dog");
    text3.setText("pizza");
    text4.setText("sushi");
    text5.setText("taco");
    text6.setText("sandwich");
    image1.setImage(burger);
    image2.setImage(dog);
    image3.setImage(pizza);
    image4.setImage(sushi);
    image5.setImage(taco);
    image6.setImage(sandwich);
  }

  public void DrinkPressed() {
    Image OJ = new Image(App.class.getResourceAsStream("Images/OJ.jpg"));

    Image coffee = new Image(App.class.getResourceAsStream("Images/coffee.jpg"));

    Image water = new Image(App.class.getResourceAsStream("Images/water.jpg"));

    Image soda = new Image(App.class.getResourceAsStream("Images/soda.jpg"));

    Image smoothie = new Image(App.class.getResourceAsStream("Images/smoothie.jpg"));

    Image tea = new Image(App.class.getResourceAsStream("Images/tea.jpg"));
    text1.setText("OJ");
    text2.setText("coffee");
    text3.setText("pizza");
    text4.setText("water");
    text5.setText("smoothie");
    text6.setText("tea");
    image1.setImage(OJ);
    image2.setImage(coffee);
    image3.setImage(water);
    image4.setImage(soda);
    image5.setImage(smoothie);
    image6.setImage(tea);
  }

  public void SnackPressed() {
    Image frenchfries = new Image(App.class.getResourceAsStream("Images/frenchfries.jpg"));

    Image chips = new Image(App.class.getResourceAsStream("Images/chips.jpg"));

    Image bacon = new Image(App.class.getResourceAsStream("Images/bacon.jpg"));

    Image avocadotoast = new Image(App.class.getResourceAsStream("Images/avocadotoast.jpg"));

    Image goldfish = new Image(App.class.getResourceAsStream("Images/goldfish.jpg"));

    Image pretzels = new Image(App.class.getResourceAsStream("Images/pretzels.jpg"));
    text1.setText("frenchfries");
    text2.setText("chips");
    text3.setText("bacon");
    text4.setText("avocado toast");
    text5.setText("goldfish");
    text6.setText("pretzels");
    image1.setImage(frenchfries);
    image2.setImage(chips);
    image3.setImage(bacon);
    image4.setImage(avocadotoast);
    image5.setImage(goldfish);
    image6.setImage(pretzels);
  }

  public void exit() {
    Platform.exit();
  }

  public void MealOrder() {}

  public void storeMealValues() throws SQLException {

    HashMap<Integer, Employee> employeeHash = dao.getAllEmployees();

    Employee signedIn = employeeHash.get(App.employee.getEmpID());

    MealRequest mr =
        new MealRequest(
            "M",
            "ID "
                + App.employee.getEmpID()
                + ": "
                + signedIn.getFirstName()
                + " "
                + signedIn.getLastName(),
            // assume for now they are going to input a node number, so parseInt
            (String) locationSearchDropdown.getValue(),
            (String) employeeSearchDropdown.getValue(),
            StatusTypeEnum.blank,
            Date.valueOf(mealDate.getValue()),
            StringToTime(mealTimeOfDeliver.getText()),
            mealPersonOrderingForData.getText(),
            Order,
            mealNotesData.getText());

    dao.insertMealRequest(mr);
    App.mealRefresh();
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
      checkFields.setVisible(true);
    }
  }

  public void clearAllData() {
    // mealDeliveryLocationData.setText("");
    mealPersonOrderingForData.setText("");
    mealNotesData.setText("");
    mealDate.setText("");
    mealTimeOfDeliver.setText("");
    //    mealFoodChoice.setText("");

    locationSearchDropdown.setValue(null);
    employeeSearchDropdown.setValue(null);
    return;
  }
}
