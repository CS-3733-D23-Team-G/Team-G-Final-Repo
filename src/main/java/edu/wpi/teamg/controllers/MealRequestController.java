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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

  @FXML AnchorPane forms;

  //  AnchorPane rect = new AnchorPane();
  //  Text completionText = new Text("You Are All Set!");
  //  Text completionTextSecondRow = new Text("Conference Room Request Sent Successfully.");
  //
  //  Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
  //  ImageView completionImage = new ImageView(checkmarkImage);

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

  Hashtable<String, Integer> selectedPanes = new Hashtable<>();
  HashMap<String, Integer> food = new HashMap<>();

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    MealPressed();

    mealClearAll.setOnAction(event -> completeAnimation());
    snackButton.setOnAction(event -> completeAnimation());

    mealSubmitButton.setOnMouseClicked(
        event -> {
          getOrderItems();
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
  }

  public void MealPressed() {

    Image burger = new Image(App.class.getResourceAsStream("Images/burger.jpg"));

    Image dog = new Image(App.class.getResourceAsStream("Images/dog.jpg"));

    Image pizza = new Image(App.class.getResourceAsStream("Images/pizza.jpg"));

    Image sushi = new Image(App.class.getResourceAsStream("Images/sushi.jpg"));

    Image taco = new Image(App.class.getResourceAsStream("Images/taco.jpg"));

    Image sandwich = new Image(App.class.getResourceAsStream("Images/sandwich.jpg"));
    text1.setText("Burger");
    text2.setText("Hot Dog");
    text3.setText("Pizza");
    text4.setText("Sushi");
    text5.setText("Taco");
    text6.setText("Sandwich");
    image1.setImage(burger);
    image2.setImage(dog);
    image3.setImage(pizza);
    image4.setImage(sushi);
    image5.setImage(taco);
    image6.setImage(sandwich);

    //              Enumeration<String> keys = selectedPanes.keys();
    //              while (keys.hasMoreElements()) {
    //                String key = keys.nextElement();
    //                System.out.println("Order Item: " + key + ", Num Selected: " +
    // selectedPanes.get(key));
    //              }

    pane1.setOnMouseClicked(
        event -> {
          System.out.println("pane 1 pressed");
          selectItems(text1);
        });
    pane2.setOnMouseClicked(
        event -> {
          System.out.println("pane 2 pressed");
          selectItems(text3);
        });
    pane3.setOnMouseClicked(
        event -> {
          System.out.println("pane 3 pressed");
          selectItems(text5);
        });
    pane4.setOnMouseClicked(
        event -> {
          System.out.println("pane 4 pressed");
          selectItems(text2);
        });
    pane5.setOnMouseClicked(
        event -> {
          System.out.println("pane 5 pressed");
          selectItems(text4);
        });
    pane6.setOnMouseClicked(
        event -> {
          System.out.println("pane 6 pressed");
          selectItems(text6);
        });
  }

  public void DrinkPressed() {
    Image OJ = new Image(App.class.getResourceAsStream("Images/OJ.jpg"));

    Image coffee = new Image(App.class.getResourceAsStream("Images/coffee.jpg"));

    Image water = new Image(App.class.getResourceAsStream("Images/water.jpg"));

    Image soda = new Image(App.class.getResourceAsStream("Images/soda.jpg"));

    Image smoothie = new Image(App.class.getResourceAsStream("Images/smoothie.jpg"));

    Image tea = new Image(App.class.getResourceAsStream("Images/tea.jpg"));
    text1.setText("Orange Juice");
    text2.setText("Coffee");
    text3.setText("Pizza");
    text4.setText("Water");
    text5.setText("Smoothie");
    text6.setText("Tea");
    image1.setImage(OJ);
    image2.setImage(coffee);
    image3.setImage(water);
    image4.setImage(soda);
    image5.setImage(smoothie);
    image6.setImage(tea);

    pane1.setOnMouseClicked(
        event -> {
          System.out.println("pane 1 pressed");
          selectItems(text1);
        });
    pane2.setOnMouseClicked(
        event -> {
          System.out.println("pane 2 pressed");
          selectItems(text3);
        });
    pane3.setOnMouseClicked(
        event -> {
          System.out.println("pane 3 pressed");
          selectItems(text5);
        });
    pane4.setOnMouseClicked(
        event -> {
          System.out.println("pane 4 pressed");
          selectItems(text2);
        });
    pane5.setOnMouseClicked(
        event -> {
          System.out.println("pane 5 pressed");
          selectItems(text4);
        });
    pane6.setOnMouseClicked(
        event -> {
          System.out.println("pane 6 pressed");
          selectItems(text6);
        });
  }

  public void SnackPressed() {
    Image frenchFries = new Image(App.class.getResourceAsStream("Images/frenchfries.jpg"));

    Image chips = new Image(App.class.getResourceAsStream("Images/chips.jpg"));

    Image bacon = new Image(App.class.getResourceAsStream("Images/bacon.jpg"));

    Image avocadoToast = new Image(App.class.getResourceAsStream("Images/avocadotoast.jpg"));

    Image goldfish = new Image(App.class.getResourceAsStream("Images/goldfish.jpg"));

    Image pretzels = new Image(App.class.getResourceAsStream("Images/pretzels.jpg"));
    text1.setText("French Fries");
    text2.setText("Chips");
    text3.setText("Bacon");
    text4.setText("Avocado Toast");
    text5.setText("Goldfish");
    text6.setText("Pretzels");
    image1.setImage(frenchFries);
    image2.setImage(chips);
    image3.setImage(bacon);
    image4.setImage(avocadoToast);
    image5.setImage(goldfish);
    image6.setImage(pretzels);

    pane1.setOnMouseClicked(
        event -> {
          System.out.println("pane 1 pressed");
          selectItems(text1);
        });
    pane2.setOnMouseClicked(
        event -> {
          System.out.println("pane 2 pressed");
          selectItems(text3);
        });
    pane3.setOnMouseClicked(
        event -> {
          System.out.println("pane 3 pressed");
          selectItems(text5);
        });
    pane4.setOnMouseClicked(
        event -> {
          System.out.println("pane 4 pressed");
          selectItems(text2);
        });
    pane5.setOnMouseClicked(
        event -> {
          System.out.println("pane 5 pressed");
          selectItems(text4);
        });
    pane6.setOnMouseClicked(
        event -> {
          System.out.println("pane 6 pressed");
          selectItems(text6);
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

    Text completionText = new Text("You Are All Set!");
    completionText.setLayoutX(445);
    completionText.setLayoutY(850);
    completionText.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 25;"
            + "-fx-font-weight: 500;");

    Text completionTextSecondRow = new Text("Conference Room Request Sent Successfully.");
    completionTextSecondRow.setLayoutX(445);
    completionTextSecondRow.setLayoutY(870);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 15;"
            + "-fx-font-weight: 500;");

    Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
    ImageView completionImage = new ImageView(checkmarkImage);

    completionImage.setFitHeight(120);
    completionImage.setFitWidth(120);
    completionImage.setLayoutX(320);
    completionImage.setLayoutY(790);

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
