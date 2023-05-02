package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.AccountDAO;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import edu.wpi.teamg.ORMClasses.Employee;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.SearchableComboBox;

public class AddEmployee {
  @FXML MFXButton empSubmit;
  @FXML MFXButton empClear;

  @FXML MFXTextField FirstName;
  @FXML MFXTextField lastName;
  @FXML MFXTextField emailName;
  @FXML MFXTextField userName;
  @FXML MFXTextField Password;

  @FXML AnchorPane forms;

  @FXML SearchableComboBox serveDrop;

  ObservableList<String> serveList =
      FXCollections.observableArrayList(
          "Meal Request",
          "Conference Room Request",
          "Flowers Request",
          "Office Supplies Request",
          "Furniture Request",
          "Maintenance Request");

  EmployeeDAO empDao = new EmployeeDAO();
  AccountDAO accDao = new AccountDAO();

  // AddEmployee
  @FXML TableView<Employee> empTable;
  @FXML TableColumn<Employee, String> empLastName;
  @FXML TableColumn<Employee, String> empFirstName;
  @FXML TableColumn<Employee, String> empEmail;
  @FXML TableColumn<Employee, String> empCanServe;

  // TODO
  ObservableList<Employee> testEmployeeList;

  public void initialize() throws SQLException {
    App.bool = false;
    empSubmit.setOnMouseClicked(event -> allDataFilled());
    empClear.setOnMouseClicked(event -> empClearFields());
    FirstName.getText();
    lastName.getText();
    emailName.getText();
    userName.getText();
    Password.getText();
    serveDrop.setItems(serveList);

    // TODO
    HashMap<Integer, Employee> testingEmployee = App.testingEmps;
    ArrayList<Employee> emps = new ArrayList<>(testingEmployee.values());

    testEmployeeList = FXCollections.observableArrayList(emps);

    empTable.setItems(testEmployeeList);

    empLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    empFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    empEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    empCanServe.setCellValueFactory(new PropertyValueFactory<>("can_serve"));
  };

  private void allDataFilled() {
    if (!(FirstName.getText().equals("")
        || lastName.getText().equals("")
        || emailName.getText().equals("")
        || userName.getText().equals("")
        || Password.getText().equals("")
        || serveDrop == null)) {
      try {
        storeEmployeeData();
        empClearFields();
        completeAnimation("Employee added.");
      } catch (SQLException | NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
    }
  }

  private void storeEmployeeData() throws SQLException, NoSuchAlgorithmException {
    DBConnection conn = new DBConnection();
    conn.setConnection(App.getWhichDB());

    int maxid = 0;

    String sql =
        "select empid from iteration4_presentation.employee order by empid desc limit 1";

    PreparedStatement ps_max = conn.getConnection().prepareStatement(sql);
    ResultSet rs_max = ps_max.executeQuery();
    while (rs_max.next()) {
      maxid = rs_max.getInt("empid");
      maxid++;
    }
    conn.closeConnection();

    Employee emp = new Employee();
    emp.setEmpID(maxid);
    emp.setFirstName(FirstName.getText());
    emp.setLastName(lastName.getText());
    emp.setEmail(emailName.getText());
    emp.setCan_serve(
        (String) serveDrop.getValue()); // This is just to get it running and see if it executes
    empDao.insert(emp);

    Account acc = new Account();
    acc.setUsername(userName.getText());
    acc.setPassword(Password.getText());
    acc.setEmpID(emp.getEmpID());
    accDao.insertAccount(acc, acc.getPassword(), false);
  }

  public void completeAnimation(String message) {

    // Form Completion PopUp
    AnchorPane rect = new AnchorPane();
    rect.setLayoutX(500);
    rect.setStyle(
        "-fx-pref-width: 400; -fx-pref-height: 100; -fx-background-color: #97E198; -fx-background-radius: 10");
    rect.setLayoutY(800);
    rect.toFront();

    Text completionText = new Text("You Are All Set!");
    completionText.setLayoutX(625);
    completionText.setLayoutY(845);
    completionText.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 25;"
            + "-fx-font-weight: 500;");
    completionText.toFront();

    Text completionTextSecondRow = new Text(message);
    completionTextSecondRow.setLayoutX(625);
    completionTextSecondRow.setLayoutY(875);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #404040;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 20;"
            + "-fx-font-weight: 500;");
    completionTextSecondRow.toFront();

    // Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
    ImageView completionImage = new ImageView(App.checkmarkImage);

    completionImage.setFitHeight(50);
    completionImage.setFitWidth(50);
    completionImage.setLayoutX(525);
    completionImage.setLayoutY(825);
    completionImage.toFront();

    rect.setOpacity(0.0);
    completionImage.setOpacity(0.0);
    completionText.setOpacity(0.0);
    completionTextSecondRow.setOpacity(0.0);

    forms.getChildren().add(rect);
    forms.getChildren().add(completionText);
    forms.getChildren().add(completionImage);
    forms.getChildren().add(completionTextSecondRow);

    FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(0.5), rect);
    fadeIn1.setFromValue(0.0);
    fadeIn1.setToValue(1.0);

    FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(0.5), completionImage);
    fadeIn2.setFromValue(0.0);
    fadeIn2.setToValue(1.0);

    FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(0.5), completionText);
    fadeIn3.setFromValue(0.0);
    fadeIn3.setToValue(1.0);

    FadeTransition fadeIn4 = new FadeTransition(Duration.seconds(0.5), completionTextSecondRow);
    fadeIn4.setFromValue(0.0);
    fadeIn4.setToValue(1.0);

    ParallelTransition parallelTransition =
        new ParallelTransition(fadeIn1, fadeIn2, fadeIn3, fadeIn4);

    parallelTransition.play();

    parallelTransition.setOnFinished(
        (event) -> {
          FadeTransition fadeOut1 = new FadeTransition(Duration.seconds(0.5), rect);
          fadeOut1.setDelay(Duration.seconds(1.5));
          fadeOut1.setFromValue(1.0);
          fadeOut1.setToValue(0.0);

          FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(0.5), completionImage);
          fadeOut2.setDelay(Duration.seconds(1.5));
          fadeOut2.setFromValue(1.0);
          fadeOut2.setToValue(0.0);

          FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(0.5), completionText);
          fadeOut3.setDelay(Duration.seconds(1.5));
          fadeOut3.setFromValue(1.0);
          fadeOut3.setToValue(0.0);

          FadeTransition fadeOut4 =
              new FadeTransition(Duration.seconds(0.5), completionTextSecondRow);
          fadeOut4.setDelay(Duration.seconds(1.5));
          fadeOut4.setFromValue(1.0);
          fadeOut4.setToValue(0.0);

          fadeOut1.play();
          fadeOut2.play();
          fadeOut3.play();
          fadeOut4.play();
        });
  }

  public void empClearFields() {
    FirstName.setText("");
    lastName.setText("");
    emailName.setText("");
    userName.setText("");
    Password.setText("");
    serveDrop.setValue("");
  }
}
