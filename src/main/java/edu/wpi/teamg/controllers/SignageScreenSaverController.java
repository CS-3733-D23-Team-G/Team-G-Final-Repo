package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

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

  @FXML MFXButton snakeButton;

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

  @FXML Text dateText;
  public String date;

  public void initialize() {
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

    // HELP, I NEED SOMEBODY

    int[] arrowDirection = null;
    SignageEditorController controller = new SignageEditorController();
    arrowDirection = controller.getArrowDirection();

    for (int i = 0; i <= arrowDirection.length - 1; i++) {
      System.out.println(arrowDirection[i]);
    }

    // int[] testCase = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    if (controller.getAttribute()) {
      getArrowDirectionFromEditor();
      getSavedNamesFromEditor();
      getSavedDate();
    } else {
      getSavedDate();
      arrow2.setImage(westArrow);
      nameLabel2.setText("75 Lobby");
      arrow3.setImage(northArrow);
      nameLabel3.setText("Bathroom 75 Lobby");
      arrow7.setImage(eastArrow);
      nameLabel7.setText("Garden Cafe");
      arrow8.setImage(eastArrow);
      nameLabel8.setText("75 Lobby Information Desk");

      arrow1.setImage(null);
      arrow4.setImage(null);
      arrow5.setImage(null);
      arrow6.setImage(null);
      arrow9.setImage(null);
      arrow10.setImage(null);
      nameLabel1.setText("");
      nameLabel4.setText("");
      nameLabel5.setText("");
      nameLabel6.setText("");
      nameLabel9.setText("");
      nameLabel10.setText("");

      snakeButton.setOnMouseClicked(event -> letsAGo());
    }
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
    SignageEditorController controller = new SignageEditorController();
    dateText.setText(controller.getDate() + ".");
    dateText.setFill(Paint.valueOf("linear-gradient(to bottom left, #009FFD, #2A2A72)"));
    dateText.setStyle("-fx-font-weight: 800");
  }

  public void letsAGo() {
    new GameFrame();
  }
}
