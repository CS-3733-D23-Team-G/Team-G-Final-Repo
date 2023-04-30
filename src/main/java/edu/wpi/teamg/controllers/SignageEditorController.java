package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.SignageDAO;
import edu.wpi.teamg.ORMClasses.Signage;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SignageEditorController {
  Image westArrow = new Image("edu/wpi/teamg/Images/WestArrow.png");
  Image eastArrow = new Image("edu/wpi/teamg/Images/EastArrow.png");
  Image southArrow = new Image("edu/wpi/teamg/Images/SouthArrow.png");
  Image northArrow = new Image("edu/wpi/teamg/Images/NorthArrow.png");
  Image noArrow = new Image("edu/wpi/teamg/Images/NoArrow.png");

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

  @FXML MFXFilterComboBox signageDropDown1;
  @FXML MFXFilterComboBox signageDropDown2;
  @FXML MFXFilterComboBox signageDropDown3;
  @FXML MFXFilterComboBox signageDropDown4;
  @FXML MFXFilterComboBox signageDropDown5;
  @FXML MFXFilterComboBox signageDropDown6;
  @FXML MFXFilterComboBox signageDropDown7;
  @FXML MFXFilterComboBox signageDropDown8;
  @FXML MFXFilterComboBox signageDropDown9;
  @FXML MFXFilterComboBox signageDropDown10;

  @FXML MFXButton clearField1;
  @FXML MFXButton clearField2;
  @FXML MFXButton clearField3;
  @FXML MFXButton clearField4;
  @FXML MFXButton clearField5;
  @FXML MFXButton clearField6;
  @FXML MFXButton clearField7;
  @FXML MFXButton clearField8;
  @FXML MFXButton clearField9;
  @FXML MFXButton clearField10;
  @FXML MFXButton submitSignage;
  @FXML MFXCheckbox daySpecify;
  @FXML MFXDatePicker date;

  @FXML MFXFilterComboBox locationDrop;

  @FXML MFXFilterComboBox monthDrop;

  @FXML MFXFilterComboBox yearDrop;
  // @FXML MFXComboBox kioskDrop;

  static boolean attribute = false;

  public static String[] locationNameSave = new String[10];
  ObservableList<String> locationList;

  ArrayList<ImageView> arrows;

  public static int[] arrowDirectionNumber = new int[10];
  public static LocalDate dateSave = LocalDate.now();

  ObservableList<String> monthChoice =
      FXCollections.observableArrayList(
          "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

  ObservableList<String> yearChoice =
      FXCollections.observableArrayList(
          App.getYearNum() + "",
          App.getYearNum() + 1 + "",
          App.getYearNum() + 2 + "",
          App.getYearNum() + 3 + "",
          App.getYearNum() + 4 + "");

  ObservableList<String> kioskChoice = FXCollections.observableArrayList(1 + "", 2 + "");

  @FXML
  public void initialize() throws SQLException {
    DAORepo dao = new DAORepo();
    monthDrop.setItems(monthChoice);
    yearDrop.setItems(yearChoice);

    HashMap<Integer, String> locationHash = App.ln;

    ArrayList<String> locationArrayList = new ArrayList<String>(locationHash.values());

    ObservableList<String> locationList = FXCollections.observableArrayList(locationArrayList);

    locationDrop.setItems(locationList);

    // kioskDrop.setItems(kioskChoice);
    //    for (MFXFilterComboBox box : comboBoxes) {
    //      box.setValue("");
    //    }
    arrows =
        new ArrayList<>(
            Arrays.asList(
                arrow1, arrow2, arrow3, arrow4, arrow5, arrow6, arrow7, arrow8, arrow9, arrow10));
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            arrowSetUp(arrowDirectionNumber);
          }
        });

    daySpecify.setOnAction(
        event -> {
          if (date.isVisible()) {
            date.setVisible(false);
          } else if (!date.isVisible()) {
            date.setVisible(true);
          }
        });

    submitSignage.setOnAction(
        event -> {
          try {
            addSignage();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    //    if (!attribute) {
    //      arrow2.setImage(westArrow);
    //      arrowDirectionNumber[1] = 1;
    //      locationNameSave[1] = "75 Lobby";
    //      arrow3.setImage(northArrow);
    //      arrowDirectionNumber[2] = 2;
    //      locationNameSave[2] = "Bathroom 75 Lobby";
    //      arrow7.setImage(eastArrow);
    //      arrowDirectionNumber[6] = 3;
    //      locationNameSave[6] = "Garden Cafe";
    //      arrow8.setImage(eastArrow);
    //      arrowDirectionNumber[7] = 3;
    //      locationNameSave[7] = "75 Lobby Information Desk";
    //    }

    date.setValue(dateSave);

    //    signageDropDown1.setValue(locationNameSave[0]);
    //    signageDropDown2.setValue(locationNameSave[1]);
    //    signageDropDown3.setValue(locationNameSave[2]);
    //    signageDropDown4.setValue(locationNameSave[3]);
    //    signageDropDown5.setValue(locationNameSave[4]);
    //    signageDropDown6.setValue(locationNameSave[5]);
    //    signageDropDown7.setValue(locationNameSave[6]);
    //    signageDropDown8.setValue(locationNameSave[7]);
    //    signageDropDown9.setValue(locationNameSave[8]);
    //    signageDropDown10.setValue(locationNameSave[9]);

    ArrayList<String> locationNames = new ArrayList<>();
    HashMap<Integer, String> testingLongName = dao.getAllLongName();

    testingLongName.forEach(
        (i, m) -> {
          locationNames.add(m);
        });

    Collections.sort(locationNames, String.CASE_INSENSITIVE_ORDER);

    locationList = FXCollections.observableArrayList(locationNames);

    signageDropDown1.setItems(locationList);
    signageDropDown2.setItems(locationList);
    signageDropDown3.setItems(locationList);
    signageDropDown4.setItems(locationList);
    signageDropDown5.setItems(locationList);
    signageDropDown6.setItems(locationList);
    signageDropDown7.setItems(locationList);
    signageDropDown8.setItems(locationList);
    signageDropDown9.setItems(locationList);
    signageDropDown10.setItems(locationList);

    arrow1.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 0));
    arrow2.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 1));
    arrow3.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 2));
    arrow4.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 3));
    arrow5.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 4));
    arrow6.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 5));
    arrow7.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 6));
    arrow8.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 7));
    arrow9.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 8));
    arrow10.setOnMouseClicked(event -> arrowSwap((ImageView) event.getSource(), 9));

    signageDropDown1.setOnAction(event -> saveName(0));
    signageDropDown2.setOnAction(event -> saveName(1));
    signageDropDown3.setOnAction(event -> saveName(2));
    signageDropDown4.setOnAction(event -> saveName(3));
    signageDropDown5.setOnAction(event -> saveName(4));
    signageDropDown6.setOnAction(event -> saveName(5));
    signageDropDown7.setOnAction(event -> saveName(6));
    signageDropDown8.setOnAction(event -> saveName(7));
    signageDropDown9.setOnAction(event -> saveName(8));
    signageDropDown10.setOnAction(event -> saveName(9));

    clearField1.setOnAction(event -> clearAll(0));
    clearField2.setOnAction(event -> clearAll(1));
    clearField3.setOnAction(event -> clearAll(2));
    clearField4.setOnAction(event -> clearAll(3));
    clearField5.setOnAction(event -> clearAll(4));
    clearField6.setOnAction(event -> clearAll(5));
    clearField7.setOnAction(event -> clearAll(6));
    clearField8.setOnAction(event -> clearAll(7));
    clearField9.setOnAction(event -> clearAll(8));
    clearField10.setOnAction(event -> clearAll(9));

    date.setOnAction(event -> saveTheDate());
  }

  public void arrowSwap(ImageView image, int index) {
    switch (++arrowDirectionNumber[index]) {
      default:
        image.setImage(noArrow);
        break;
      case 1:
        image.setImage(westArrow);
        break;
      case 2:
        image.setImage(northArrow);
        break;
      case 3:
        image.setImage(eastArrow);
        break;
      case 4:
        image.setImage(southArrow);
        break;
      case 5:
        arrowDirectionNumber[index] = -1;
        break;
    }
    attribute = true; // Test
  }

  public void saveName(int index) {
    switch (index) {
      case 0:
        locationNameSave[0] = signageDropDown1.getValue().toString();
        break;

      case 1:
        locationNameSave[1] = signageDropDown2.getValue().toString();
        break;

      case 2:
        locationNameSave[2] = signageDropDown3.getValue().toString();
        break;

      case 3:
        locationNameSave[3] = signageDropDown4.getValue().toString();
        break;

      case 4:
        locationNameSave[4] = signageDropDown5.getValue().toString();
        break;

      case 5:
        locationNameSave[5] = signageDropDown6.getValue().toString();
        break;

      case 6:
        locationNameSave[6] = signageDropDown7.getValue().toString();
        break;

      case 7:
        locationNameSave[7] = signageDropDown8.getValue().toString();
        break;

      case 8:
        locationNameSave[8] = signageDropDown9.getValue().toString();
        break;

      case 9:
        locationNameSave[9] = signageDropDown10.getValue().toString();
        break;
    }
    attribute = true;
  }

  public void arrowSetUp(int[] list) {
    for (int i = 0; i <= 9; i++) {

      switch (list[i]) {
        case 0:
          arrows.get(i).setImage(noArrow);
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

  public void clearAll(int index) {
    switch (index) {
      case 0:
        signageDropDown1.setValue(null);
        locationNameSave[0] = "";
        arrowDirectionNumber[0] = 0;
        arrow1.setImage(noArrow);
        break;

      case 1:
        signageDropDown2.setValue(null);
        locationNameSave[index] = "";
        arrow2.setImage(noArrow);
        arrowDirectionNumber[1] = 0;
        break;

      case 2:
        signageDropDown3.setValue(null);
        locationNameSave[index] = "";
        arrow3.setImage(noArrow);
        arrowDirectionNumber[2] = 0;
        break;

      case 3:
        signageDropDown4.setValue(null);
        locationNameSave[index] = "";
        arrow4.setImage(noArrow);
        arrowDirectionNumber[3] = 0;
        break;

      case 4:
        signageDropDown5.setValue(null);
        locationNameSave[index] = "";
        arrow5.setImage(noArrow);
        arrowDirectionNumber[4] = 0;
        break;

      case 5:
        signageDropDown6.setValue(null);
        locationNameSave[index] = "";
        arrow6.setImage(noArrow);
        arrowDirectionNumber[5] = 0;
        break;

      case 6:
        signageDropDown7.setValue(null);
        locationNameSave[index] = "";
        arrow7.setImage(noArrow);
        arrowDirectionNumber[6] = 0;
        break;

      case 7:
        signageDropDown8.setValue(null);
        locationNameSave[index] = "";
        arrow8.setImage(noArrow);
        arrowDirectionNumber[7] = 0;
        break;

      case 8:
        signageDropDown9.setValue(null);
        locationNameSave[index] = "";
        arrow9.setImage(noArrow);
        arrowDirectionNumber[8] = 0;
        break;

      case 9:
        signageDropDown10.setValue(null);
        locationNameSave[index] = "";
        arrow10.setImage(noArrow);
        arrowDirectionNumber[9] = 0;
        break;
    }
    attribute = true;
  }

  public void saveTheDate() {
    dateSave = date.getValue();
  }

  public int[] getArrowDirection() {
    return arrowDirectionNumber;
  }

  public boolean getAttribute() {
    return attribute;
  }

  public String[] getSavedNames() {
    return locationNameSave;
  }

  public String getDate() {
    return dateSave.toString();
  }

  private void addSignage() throws SQLException {
    // int kiNum = Integer.parseInt(kioskDrop.getText());

    MFXFilterComboBox[] comboBoxes = {
      signageDropDown1,
      signageDropDown2,
      signageDropDown3,
      signageDropDown4,
      signageDropDown5,
      signageDropDown6,
      signageDropDown7,
      signageDropDown8,
      signageDropDown9,
      signageDropDown10
    };

    StringBuilder sb1 = new StringBuilder();
    sb1.append(monthDrop.getText() + "-");
    sb1.append(yearDrop.getText());
    int j = -1;
    ImageView[] views = {
      arrow1, arrow2, arrow3, arrow4, arrow5, arrow6, arrow7, arrow8, arrow9, arrow10
    };
    for (MFXFilterComboBox box : comboBoxes) {
      StringBuilder sb = new StringBuilder();
      j++;
      if (box.getValue() == null) {
        continue;
      }

      sb.append(box.getValue() + "_");

      if (views[j].getImage() == westArrow) {
        sb.append("L_");
      }

      if (views[j].getImage() == northArrow) {
        sb.append("U_");
      }

      if (views[j].getImage() == southArrow) {
        sb.append("D_");
      }

      if (views[j].getImage() == eastArrow) {
        sb.append("R_");
      }

      sb.append(j + 1);
      Signage signage =
          new Signage((String) locationDrop.getValue(), null, sb.toString(), sb1.toString(), false);
      SignageDAO dao = new SignageDAO();
      dao.insert(signage);
      Navigation.navigate(Screen.SIGNAGE_EDITOR_SUBMIT);
    }
  }
}
