package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.Employee;
import edu.wpi.teamg.ORMClasses.MealRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.SearchableComboBox;

public class MealRequestController {
  @FXML MFXButton mealSubmitButton;

  @FXML MFXButton mealClearAll;

  @FXML MFXDatePicker mealDate;

  // TextFields
  @FXML MFXTextField mealTimeOfDeliver;

  @FXML MFXTextField mealPersonOrderingForData;
  @FXML TextArea mealNotesData;
  @FXML ImageView imageM1;
  @FXML ImageView imageM2;
  @FXML ImageView imageM3;
  @FXML ImageView imageM4;
  @FXML ImageView imageM5;
  @FXML ImageView imageM6;
  @FXML ImageView imageS1;
  @FXML ImageView imageS2;
  @FXML ImageView imageS3;
  @FXML ImageView imageS4;
  @FXML ImageView imageS5;
  @FXML ImageView imageS6;
  @FXML ImageView imageD1;
  @FXML ImageView imageD2;
  @FXML ImageView imageD3;
  @FXML ImageView imageD4;
  @FXML ImageView imageD5;
  @FXML ImageView imageD6;

  @FXML Text textM1;
  @FXML Text textM2;
  @FXML Text textM3;
  @FXML Text textM4;
  @FXML Text textM5;
  @FXML Text textM6;
  @FXML Text textS1;
  @FXML Text textS2;
  @FXML Text textS3;
  @FXML Text textS4;
  @FXML Text textS5;
  @FXML Text textS6;

  @FXML Text textD1;
  @FXML Text textD2;
  @FXML Text textD3;
  @FXML Text textD4;
  @FXML Text textD5;
  @FXML Text textD6;

  @FXML Pane paneM1;
  @FXML Pane paneM2;
  @FXML Pane paneM3;
  @FXML Pane paneM4;
  @FXML Pane paneM5;
  @FXML Pane paneM6;
  @FXML Pane paneS1;
  @FXML Pane paneS2;
  @FXML Pane paneS3;
  @FXML Pane paneS4;
  @FXML Pane paneS5;
  @FXML Pane paneS6;
  @FXML Pane paneD1;
  @FXML Pane paneD2;
  @FXML Pane paneD3;
  @FXML Pane paneD4;
  @FXML Pane paneD5;
  @FXML Pane paneD6;

  @FXML AnchorPane forms;
  @FXML BorderPane borderTest;

  //  AnchorPane rect = new AnchorPane();
  //  Text completionText = new Text("You Are All Set!");
  //  Text completionTextSecondRow = new Text("Conference Room Request Sent Successfully.");
  //
  //  Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
  //  ImageView completionImage = new ImageView(checkmarkImage);

  @FXML MFXButton mealButton;
  @FXML MFXButton snackButton;
  @FXML MFXButton drinkButton;

  @FXML SearchableComboBox locationSearchDropdown;
  @FXML SearchableComboBox employeeSearchDropdown;
  @FXML Label checkFields;
  @FXML Line assignToLine;
  @FXML Text assignToText;
  @FXML VBox vboxWithAssignTo;

  String Order = "";

  ObservableList<String> locationList;
  ObservableList<String> employeeList;

  Hashtable<String, Integer> selectedPanes = new Hashtable<>();
  HashMap<String, Integer> food = new HashMap<>();

  DAORepo dao = new DAORepo();

  boolean isSelectedMealPane1;
  boolean isSelectedMealPane2;
  boolean isSelectedMealPane3;
  boolean isSelectedMealPane4;
  boolean isSelectedMealPane5;
  boolean isSelectedMealPane6;

  boolean isSelectedSnackPane1;
  boolean isSelectedSnackPane2;
  boolean isSelectedSnackPane3;
  boolean isSelectedSnackPane4;
  boolean isSelectedSnackPane5;
  boolean isSelectedSnackPane6;

  boolean isSelectedDrinkPane1;
  boolean isSelectedDrinkPane2;
  boolean isSelectedDrinkPane3;
  boolean isSelectedDrinkPane4;
  boolean isSelectedDrinkPane5;
  boolean isSelectedDrinkPane6;

  @FXML HBox MealHBox;
  @FXML HBox DrinkHBox;
  @FXML HBox SnackHBox;

  @FXML
  public void initialize() throws SQLException {
    App.bool = false;
    MealPressed();
    MealHBox.toFront();

    mealClearAll.setOnAction(event -> completeAnimation());

    mealButton.setOnMouseClicked(event -> MealPressed());
    snackButton.setOnMouseClicked(event -> SnackPressed());
    drinkButton.setOnMouseClicked(event -> DrinkPressed());

    mealSubmitButton.setOnMouseClicked(
        event -> {
          getOrderItems();
          MealOrder();
          allDataFilled();
        });

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
    MealHBox.toFront();

    Image burger = new Image(App.class.getResourceAsStream("Images/burger.jpg"));

    Image dog = new Image(App.class.getResourceAsStream("Images/dog.jpg"));

    Image pizza = new Image(App.class.getResourceAsStream("Images/pizza.jpg"));

    Image sushi = new Image(App.class.getResourceAsStream("Images/sushi.jpg"));

    Image taco = new Image(App.class.getResourceAsStream("Images/taco.jpg"));

    Image sandwich = new Image(App.class.getResourceAsStream("Images/sandwich.jpg"));

    textM1.setText("Burger");
    textM2.setText("Hot Dog");
    textM3.setText("Pizza");
    textM4.setText("Sushi");
    textM5.setText("Taco");
    textM6.setText("Sandwich");
    imageM1.setImage(burger);
    imageM2.setImage(dog);
    imageM3.setImage(pizza);
    imageM4.setImage(sushi);
    imageM5.setImage(taco);
    imageM6.setImage(sandwich);

    isSelectedMealPane1 = false;
    isSelectedMealPane2 = false;
    isSelectedMealPane3 = false;
    isSelectedMealPane4 = false;
    isSelectedMealPane5 = false;
    isSelectedMealPane6 = false;

    //    pane1.setStyle(
    //        "-fx-border-color: #F6BD38;" + " -fx-border-width: 8px;" + " -fx-border-radius: 15;");

    paneM1.setOnMouseClicked(
        event -> {
          System.out.println("pane 1 pressed");
          selectItems(textM1);
          isSelectedMealPane1 = selectPaneHighlight(paneM1, isSelectedMealPane1);
          System.out.println(isSelectedMealPane1);
        });
    paneM2.setOnMouseClicked(
        event -> {
          System.out.println("pane 2 pressed");
          selectItems(textM3);
          isSelectedMealPane2 = selectPaneHighlight(paneM2, isSelectedMealPane2);
        });
    paneM3.setOnMouseClicked(
        event -> {
          System.out.println("pane 3 pressed");
          selectItems(textM5);
          isSelectedMealPane3 = selectPaneHighlight(paneM3, isSelectedMealPane3);
        });
    paneM4.setOnMouseClicked(
        event -> {
          System.out.println("pane 4 pressed");
          selectItems(textM2);
          isSelectedMealPane4 = selectPaneHighlight(paneM4, isSelectedMealPane4);
        });
    paneM5.setOnMouseClicked(
        event -> {
          System.out.println("pane 5 pressed");
          selectItems(textM4);
          isSelectedMealPane5 = selectPaneHighlight(paneM5, isSelectedMealPane5);
        });
    paneM6.setOnMouseClicked(
        event -> {
          System.out.println("pane 6 pressed");
          selectItems(textM6);
          isSelectedMealPane6 = selectPaneHighlight(paneM6, isSelectedMealPane6);
        });
  }

  public void DrinkPressed() {
    DrinkHBox.toFront();
    Image OJ = new Image(App.class.getResourceAsStream("Images/OJ.jpg"));

    Image coffee = new Image(App.class.getResourceAsStream("Images/coffee.jpg"));

    Image water = new Image(App.class.getResourceAsStream("Images/water.jpg"));

    Image soda = new Image(App.class.getResourceAsStream("Images/soda.jpg"));

    Image smoothie = new Image(App.class.getResourceAsStream("Images/smoothie.jpg"));

    Image tea = new Image(App.class.getResourceAsStream("Images/tea.jpg"));
    textD1.setText("Orange Juice");
    textD2.setText("Coffee");
    textD3.setText("Pizza");
    textD4.setText("Water");
    textD5.setText("Smoothie");
    textD6.setText("Tea");
    imageD1.setImage(OJ);
    imageD2.setImage(coffee);
    imageD3.setImage(water);
    imageD4.setImage(soda);
    imageD5.setImage(smoothie);
    imageD6.setImage(tea);

    isSelectedDrinkPane1 = false;
    isSelectedDrinkPane2 = false;
    isSelectedDrinkPane3 = false;
    isSelectedDrinkPane4 = false;
    isSelectedDrinkPane5 = false;
    isSelectedDrinkPane6 = false;

    paneD1.setOnMouseClicked(
        event -> {
          System.out.println("pane 1 pressed");
          selectItems(textD1);
          isSelectedDrinkPane1 = selectPaneHighlight(paneD1, isSelectedDrinkPane1);
        });
    paneD2.setOnMouseClicked(
        event -> {
          System.out.println("pane 2 pressed");
          selectItems(textD3);
          isSelectedDrinkPane2 = selectPaneHighlight(paneD2, isSelectedDrinkPane2);
        });
    paneD3.setOnMouseClicked(
        event -> {
          System.out.println("pane 3 pressed");
          selectItems(textD5);
          isSelectedDrinkPane3 = selectPaneHighlight(paneD3, isSelectedDrinkPane3);
        });
    paneD4.setOnMouseClicked(
        event -> {
          System.out.println("pane 4 pressed");
          selectItems(textD2);
          isSelectedDrinkPane4 = selectPaneHighlight(paneD4, isSelectedDrinkPane4);
        });
    paneD5.setOnMouseClicked(
        event -> {
          System.out.println("pane 5 pressed");
          selectItems(textD4);
          isSelectedDrinkPane5 = selectPaneHighlight(paneD5, isSelectedDrinkPane5);
        });
    paneD6.setOnMouseClicked(
        event -> {
          System.out.println("pane 6 pressed");
          selectItems(textD6);
          isSelectedDrinkPane6 = selectPaneHighlight(paneD6, isSelectedDrinkPane6);
        });
  }

  public void SnackPressed() {

    SnackHBox.toFront();
    Image frenchFries = new Image(App.class.getResourceAsStream("Images/frenchfries.jpg"));

    Image chips = new Image(App.class.getResourceAsStream("Images/chips.jpg"));

    Image bacon = new Image(App.class.getResourceAsStream("Images/bacon.jpg"));

    Image avocadoToast = new Image(App.class.getResourceAsStream("Images/avocadotoast.jpg"));

    Image goldfish = new Image(App.class.getResourceAsStream("Images/goldfish.jpg"));

    Image pretzels = new Image(App.class.getResourceAsStream("Images/pretzels.jpg"));
    textS1.setText("French Fries");
    textS2.setText("Chips");
    textS3.setText("Bacon");
    textS4.setText("Avocado Toast");
    textS5.setText("Goldfish");
    textS6.setText("Pretzels");
    imageS1.setImage(frenchFries);
    imageS2.setImage(chips);
    imageS3.setImage(bacon);
    imageS4.setImage(avocadoToast);
    imageS5.setImage(goldfish);
    imageS6.setImage(pretzels);

    isSelectedSnackPane1 = false;
    isSelectedSnackPane2 = false;
    isSelectedSnackPane3 = false;
    isSelectedSnackPane4 = false;
    isSelectedSnackPane5 = false;
    isSelectedSnackPane6 = false;

    paneS1.setOnMouseClicked(
        event -> {
          System.out.println("pane 1 pressed");
          selectItems(textS1);
          isSelectedSnackPane1 = selectPaneHighlight(paneS1, isSelectedSnackPane1);
        });
    paneS2.setOnMouseClicked(
        event -> {
          System.out.println("pane 2 pressed");
          selectItems(textS3);
          isSelectedSnackPane2 = selectPaneHighlight(paneS2, isSelectedSnackPane2);
        });
    paneS3.setOnMouseClicked(
        event -> {
          System.out.println("pane 3 pressed");
          selectItems(textS5);
          isSelectedSnackPane3 = selectPaneHighlight(paneS3, isSelectedSnackPane3);
        });
    paneS4.setOnMouseClicked(
        event -> {
          System.out.println("pane 4 pressed");
          selectItems(textS2);
          isSelectedSnackPane4 = selectPaneHighlight(paneS4, isSelectedSnackPane4);
        });
    paneS5.setOnMouseClicked(
        event -> {
          System.out.println("pane 5 pressed");
          selectItems(textS4);
          isSelectedSnackPane5 = selectPaneHighlight(paneS5, isSelectedSnackPane5);
        });
    paneS6.setOnMouseClicked(
        event -> {
          System.out.println("pane 6 pressed");
          selectItems(textS6);
          isSelectedSnackPane6 = selectPaneHighlight(paneS6, isSelectedSnackPane6);
        });
  }

  public void selectItems(Text text) {

    if (food.get(text.getText().toString()) == null || food.get(text.getText().toString()) == 0) {
      food.put(text.getText().toString(), 1);
    } else {
      food.put(text.getText().toString(), 0);
    }
    System.out.println(food.get(text.getText().toString()));
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

    Text completionTextSecondRow = new Text("Meal Request Sent Successfully.");
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

  public boolean selectPaneHighlight(Pane p, boolean b) {
    String bool = String.valueOf(b);
    switch (bool) {
      case "true":
        p.setStyle(
            "-fx-border-color: #F6BD38;" + " -fx-border-width: 0px;" + " -fx-border-radius: 10;");
        b = false;
        break;
      case "false":
        p.setStyle(
            "-fx-border-color: #F6BD38;" + " -fx-border-width: 8px;" + " -fx-border-radius: 10;");
        b = true;
        break;
    }
    return b;
  }

  public void getOrderItems() {
    ArrayList<String> foods = new ArrayList<>();
    String s = "";
    String done = "";

    food.forEach(
        (i, m) -> {
          if (m == 1) {
            foods.add(i);
          }
        });

    for (int i = 0; i < foods.size(); i++) {
      s = s.concat(foods.get(i)) + "_";
    }

    done = s.substring(0, s.length() - 1);
    Order = done;
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
        completeAnimation();
        clearAllData();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
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

//    // Form Completion PopUp
//    //      AnchorPane rect = new AnchorPane();
//    rect.setLayoutX(325);
//    rect.setStyle(
//        "-fx-pref-width: 440; -fx-pref-height: 100; -fx-background-color: #d9d9d9;
// -fx-border-radius: 5; -fx-background-insets: 5; -fx-border-insets: 5; -fx-padding: 5;"
//            + "-fx-border-color: #000000;"
//            + "-fx-border-width: 3;");
//    rect.setLayoutY(800);
//
//    //      Text completionText = new Text("You Are All Set!");
//    completionText.setLayoutX(445);
//    completionText.setLayoutY(850);
//    completionText.setStyle(
//        "-fx-stroke: #000000;"
//            + "-fx-fill: #012D5A;"
//            + "-fx-font-size: 25;"
//            + "-fx-font-weight: 500;");
//
//    //      Text completionTextSecondRow = new Text("Conference Room Request Sent
// Successfully.");
//    completionTextSecondRow.setLayoutX(445);
//    completionTextSecondRow.setLayoutY(870);
//    completionTextSecondRow.setStyle(
//        "-fx-stroke: #000000;"
//            + "-fx-fill: #012D5A;"
//            + "-fx-font-size: 15;"
//            + "-fx-font-weight: 500;");
//
//    Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
//    ImageView completionImage = new ImageView(checkmarkImage);
//
//    completionImage.setFitHeight(120);
//    completionImage.setFitWidth(120);
//    completionImage.setLayoutX(320);
//    completionImage.setLayoutY(790);
//
//    rect.setOpacity(0.0);
//    completionImage.setOpacity(0.0);
//    completionText.setOpacity(0.0);
//    completionTextSecondRow.setOpacity(0.0);
//
//    forms.getChildren().add(rect);
//    forms.getChildren().add(completionText);
//    forms.getChildren().add(completionImage);
//    forms.getChildren().add(completionTextSecondRow);

//  public Hashtable<String, Integer> selectItems(Text text) {
//
//    // trying to implement contains
//    if (selectedPanes.size() > 0) {
//      Enumeration<String> e = selectedPanes.keys();
//      Boolean contains = false;
//      while (e.hasMoreElements()) {
//        String item = e.nextElement();
//        if (text.getText().equals(item)) {
//          contains = true;
//          System.out.println(
//              selectedPanes.replace(item, selectedPanes.get(item), selectedPanes.get(item) +
// 1));
//              break;
//        }
//        System.out.println("boolean is: " + contains);
//        if (contains == false) {
//          selectedPanes.put(text.getText(), 0);
//          System.out.println(text.getText() + "added bc contains was false");
//        }
//      }
//    }
//
//    if (selectedPanes.size() == 0) {
//      selectedPanes.put(text.getText(), 1);
//    }
//
//    Enumeration<String> e = selectedPanes.keys();
//    while (e.hasMoreElements()) {
//
//      // Getting the key of a particular entry
//      String key = e.nextElement();
//
//      // Print and display the Rank and Name
//      System.out.println("Item: " + key + "\t\t Num of times Selected: " +
// selectedPanes.get(key));
//    }
//
//    return selectedPanes;
//  }
