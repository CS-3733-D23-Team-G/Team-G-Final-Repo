package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.SignageDAO;
import edu.wpi.teamg.ORMClasses.Signage;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SignageScreenSaverController {
  @FXML MFXButton ClickToPathFinding;

  //  @FXML ImageView westArrow;
  //  @FXML ImageView northArrow;
  //  @FXML ImageView southArrow;
  //  @FXML ImageView eastArrow;
  Image westArrow = new Image("edu/wpi/teamg/Images/WestArrow.png");
  Image eastArrow = new Image("edu/wpi/teamg/Images/EastArrow.png");
  Image southArrow = new Image("edu/wpi/teamg/Images/SouthArrow.png");
  Image northArrow = new Image("edu/wpi/teamg/Images/NorthArrow.png");
  Image noArrow = new Image("edu/wpi/teamg/Images/NoArrow.png");

  @FXML MFXButton goToKidsPage;

  @FXML ImageView arrow1 = new ImageView(noArrow);
  @FXML ImageView arrow2 = new ImageView(noArrow);
  @FXML ImageView arrow3 = new ImageView(noArrow);
  @FXML ImageView arrow4 = new ImageView(noArrow);
  @FXML ImageView arrow5 = new ImageView(noArrow);
  @FXML ImageView arrow6 = new ImageView(noArrow);
  @FXML ImageView arrow7 = new ImageView(noArrow);
  @FXML ImageView arrow8 = new ImageView(noArrow);
  @FXML ImageView arrow9 = new ImageView(noArrow);
  @FXML ImageView arrow10 = new ImageView(noArrow);

  @FXML Text nameLabel1;
  @FXML Text nameLabel2;
  @FXML Text nameLabel3;
  @FXML Text nameLabel4;
  @FXML Text nameLabel5;
  @FXML Text nameLabel6;
  @FXML Text nameLabel7;
  @FXML Text nameLabel8;
  @FXML Text nameLabel9;
  @FXML Text nameLabel10;

  ArrayList<ImageView> arrows;

  SignageDAO signageDAO = new SignageDAO();

  @FXML Text dateText;
  @FXML Text locationText;

  @FXML Text greetingText;

  @FXML Text timeText;
  public String date;

  public void initialize() throws SQLException {
    App.bool = false;
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e -> {
                  timeText.setText(
                      LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")) + ",");
                  timeText.setFill(Paint.valueOf("#012D5A"));
                  timeText.setStyle("-fx-font-weight: 800");
                }),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();

    getSavedDate(); // getting the proper date

    arrow1.setImage(null); // making everything null so only the proper fields will be initialized
    arrow2.setImage(null);
    arrow3.setImage(null);
    arrow4.setImage(null);
    arrow5.setImage(null);
    arrow6.setImage(null);
    arrow7.setImage(null);
    arrow8.setImage(null);
    arrow9.setImage(null);
    arrow10.setImage(null);
    nameLabel1.setText("");
    nameLabel2.setText("");
    nameLabel3.setText("");
    nameLabel4.setText("");
    nameLabel5.setText("");
    nameLabel6.setText("");
    nameLabel7.setText("");
    nameLabel8.setText("");
    nameLabel9.setText("");
    nameLabel10.setText("");

    int kinum = App.getKioskNumber();

    locationText.setText(App.getKioskLocation());
    locationText.setFill(Paint.valueOf("#012D5A"));
    locationText.setStyle("-fx-font-weight: 800");

    HashMap<String, Signage> page =
        signageDAO.getSignageGiveLNAndMonth(App.getKioskLocation(), App.getMonthFieldSignage());

    page.forEach(
        (i, m) -> {
          String[] data = m.getArrow().split("_");

          System.out.println(data[1] + " " + data[2]);

          setDirection(data[1], data[2]);
          setLoc(data[0], data[2]);
        });

    ClickToPathFinding.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_PAGE));
    arrows =
        new ArrayList<>(
            Arrays.asList(
                arrow1, arrow2, arrow3, arrow4, arrow5, arrow6, arrow7, arrow8, arrow9, arrow10));
    //    Platform.runLater(
    //            new Runnable() {
    //              @Override
    //              public void run() {
    //                arrowSetUp(arrowDirectionNumber);
    //              }
    //            });

    int[] arrowDirection = null;
    SignageEditorController controller = new SignageEditorController();
    arrowDirection = controller.getArrowDirection();

    for (int i = 0; i <= arrowDirection.length - 1; i++) {
      System.out.println(arrowDirection[i]);
    }

    // int[] testCase = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    //    if (controller.getAttribute()) {
    //      getArrowDirectionFromEditor();
    //      getSavedNamesFromEditor();
    //
    //    } else {
    //      getSavedDate();
    //
    //    }

    goToKidsPage.setOnMouseClicked(event -> Navigation.navigate(Screen.KIDS_PAGE));
  }

  public void getArrowDirectionFromEditor() {
    int[] arrowDirection;
    SignageEditorController controller = new SignageEditorController();
    arrowDirection = controller.getArrowDirection();
    for (int i = 0; i <= 9; i++) {

      switch (arrowDirection[i]) {
        case 0:
          arrows.get(i).setImage(null);
          break;
        case 1:
          arrows.get(i).setImage(westArrow);
          break;
        case 2:
          arrows.get(i).setImage(northArrow);
          break;
        case 3:
          arrows.get(i).setImage(eastArrow);
          break;
        case 4:
          arrows.get(i).setImage(southArrow);
          break;
      }
    }
  }

  public void getSavedNamesFromEditor() {
    String[] savedNames;
    SignageEditorController controller = new SignageEditorController();
    savedNames = controller.getSavedNames();

    nameLabel1.setText(savedNames[0]);
    nameLabel2.setText(savedNames[1]);
    nameLabel3.setText(savedNames[2]);
    nameLabel4.setText(savedNames[3]);
    nameLabel5.setText(savedNames[4]);
    nameLabel6.setText(savedNames[5]);
    nameLabel7.setText(savedNames[6]);
    nameLabel8.setText(savedNames[7]);
    nameLabel9.setText(savedNames[8]);
    nameLabel10.setText(savedNames[9]);
  }

  public void getSavedDate() {

    //    Calendar c = Calendar.getInstance();
    //    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
    //
    //    if (timeOfDay >= 0 && timeOfDay < 12) {
    //      greetingText.setText("Good Morning! It's");
    //    } else if (timeOfDay >= 12 && timeOfDay < 16) {
    //      greetingText.setText("Good Afternoon! It's");
    //    } else if (timeOfDay >= 16 && timeOfDay < 21) {
    //      greetingText.setText("Good Evening! It's");
    //    } else if (timeOfDay >= 21 && timeOfDay < 24) {
    //      greetingText.setText("Good Morning! It's");
    //    }

    SignageEditorController controller = new SignageEditorController();
    dateText.setText(controller.getDate());
    dateText.setFill(Paint.valueOf("#012D5A"));
    dateText.setStyle("-fx-font-weight: 800");
  }

  public void setDirection(String val, String index) {
    Image arrowImage = null;
    switch (val) {
      case "U":
        arrowImage = northArrow;
        break;
      case "R":
        arrowImage = eastArrow;
        break;
      case "L":
        arrowImage = westArrow;
        break;
      case "D":
        arrowImage = southArrow;
        break;
      default:
        arrowImage = noArrow;
        break;
    }

    switch (Integer.parseInt(index)) {
      case 1:
        arrow1.setImage(arrowImage);
        break;
      case 2:
        arrow2.setImage(arrowImage);
        break;
      case 3:
        arrow3.setImage(arrowImage);
        break;
      case 4:
        arrow4.setImage(arrowImage);
        break;
      case 5:
        arrow5.setImage(arrowImage);
        break;
      case 6:
        arrow6.setImage(arrowImage);
        break;
      case 7:
        arrow7.setImage(arrowImage);
        break;
      case 8:
        arrow8.setImage(arrowImage);
        break;
      case 9:
        arrow9.setImage(arrowImage);
        break;
      case 10:
        arrow10.setImage(arrowImage);
        break;
      default:
        break;
    }
  }

  public void setLoc(String loc, String index) {

    switch (Integer.parseInt(index)) {
      case 1:
        nameLabel1.setText(loc);
        break;
      case 2:
        nameLabel2.setText(loc);
        break;
      case 3:
        nameLabel3.setText(loc);
        break;
      case 4:
        nameLabel4.setText(loc);
        break;
      case 5:
        nameLabel5.setText(loc);
        break;
      case 6:
        nameLabel6.setText(loc);
        break;
      case 7:
        nameLabel7.setText(loc);
        break;
      case 8:
        nameLabel8.setText(loc);
        break;
      case 9:
        nameLabel9.setText(loc);
        break;
      case 10:
        nameLabel10.setText(loc);
        break;
      default:
        break;
    }
  }

  //  public void letsAGsnake() {
  //    new GameFrame();
  //  }
}
