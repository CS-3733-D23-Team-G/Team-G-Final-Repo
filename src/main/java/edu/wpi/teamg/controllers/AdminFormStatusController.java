package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminFormStatusController {

  // Heading

  // Tables
  @FXML TableView<Request> mainTable;
  @FXML TableView<MealRequest> mealTable;
  @FXML TableView<ConferenceRoomRequest> roomTable;
  @FXML TableView<FlowerRequest> flowerTable;
  @FXML TableView<FurnitureRequest> furnTable;
  // Main Table
  @FXML TableColumn<Request, Integer> empID;
  @FXML TableColumn<Request, String> reqType;
  @FXML TableColumn<Request, Integer> location1;
  @FXML TableColumn<Request, Integer> reqID;
  @FXML TableColumn<Request, Integer> serveBy;
  @FXML TableColumn<Request, StatusTypeEnum> status;
  @FXML TableColumn<Request, Date> reqDate;
  @FXML TableColumn<Request, Time> reqTime;

  // Meal Table
  @FXML TableColumn<MealRequest, Integer> mealEmpID;
  @FXML TableColumn<MealRequest, Integer> mealLocation1;
  @FXML TableColumn<MealRequest, Integer> mealReqID;
  @FXML TableColumn<MealRequest, Integer> mealServeBy;
  @FXML TableColumn<MealRequest, StatusTypeEnum> mealStatus;
  @FXML TableColumn<MealRequest, String> mealRecipient;
  @FXML TableColumn<MealRequest, String> mealOrder;
  @FXML TableColumn<MealRequest, String> mealNote;
  @FXML TableColumn<MealRequest, Date> mealDate;
  @FXML TableColumn<MealRequest, Time> mealTime;

  // room Table
  @FXML TableColumn<ConferenceRoomRequest, Integer> roomEmpID;
  @FXML TableColumn<ConferenceRoomRequest, String> roomLocation1;
  @FXML TableColumn<ConferenceRoomRequest, Integer> roomReqID;
  @FXML TableColumn<ConferenceRoomRequest, Integer> roomServeBy;
  @FXML TableColumn<ConferenceRoomRequest, StatusTypeEnum> roomStatus;
  @FXML TableColumn<ConferenceRoomRequest, Date> roomDate;
  @FXML TableColumn<ConferenceRoomRequest, Time> roomTime;
  @FXML TableColumn<ConferenceRoomRequest, Time> endTime;
  @FXML TableColumn<ConferenceRoomRequest, String> roomPurpose;

  // flower Table
  @FXML TableColumn<FlowerRequest, Integer> flowerEmpID;
  @FXML TableColumn<FlowerRequest, String> flowerLocation1;
  @FXML TableColumn<FlowerRequest, Integer> flowerReqID;
  @FXML TableColumn<FlowerRequest, Integer> flowerServeBy;
  @FXML TableColumn<FlowerRequest, StatusTypeEnum> flowerStatus;
  @FXML TableColumn<FlowerRequest, String> flowerType;
  @FXML TableColumn<FlowerRequest, Integer> flowerNumber;
  @FXML TableColumn<FlowerRequest, String> flowerRecipient;
  @FXML TableColumn<FlowerRequest, String> flowerNote;

  @FXML TableColumn<ConferenceRoomRequest, Date> flowerDate;
  @FXML TableColumn<ConferenceRoomRequest, Time> flowerTime;

  // furniture Table
  @FXML TableColumn<FurnitureRequest, Integer> furnEmpID;
  @FXML TableColumn<FurnitureRequest, String> furnLocation1;
  @FXML TableColumn<FurnitureRequest, Integer> furnReqID;
  @FXML TableColumn<FurnitureRequest, Integer> furnServeBy;
  @FXML TableColumn<FurnitureRequest, StatusTypeEnum> furnStatus;
  @FXML TableColumn<FurnitureRequest, String> furnType;
  @FXML TableColumn<FurnitureRequest, String> furnRecipient;
  @FXML TableColumn<FurnitureRequest, String> furnNote;

  @FXML TableColumn<FurnitureRequest, Date> furnDate;
  @FXML TableColumn<FurnitureRequest, Time> furnTime;

  // Table Change Button
  @FXML MFXButton allRequestTableButton;
  @FXML MFXButton mealTableButton;
  @FXML MFXButton roomTableButton;
  @FXML MFXButton flowerTableButton;
  @FXML MFXButton furnTableButton;
  //  @FXML MFXButton FurnitureTableButton;
  //  @FXML MFXButton OfficeSupplyTableButton;

  ObservableList<Request> testList;
  ObservableList<MealRequest> testMealList;
  ObservableList<ConferenceRoomRequest> testRoomList;
  ObservableList<FlowerRequest> testFlowerList;

  ObservableList<FurnitureRequest> testFurnList;
  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    allRequestTableButton.setOnMouseClicked(event -> loadAllRequestTable());
    mealTableButton.setOnMouseClicked(event -> loadMealTable());
    roomTableButton.setOnMouseClicked(event -> loadRoomTable());
    flowerTableButton.setOnMouseClicked(event -> loadFlowerTable());
    furnTableButton.setOnMouseClicked(event -> loadFurnitureTable());

    ArrayList<Request> request1 = new ArrayList<>();

    HashMap<Integer, Request> testingRequest = this.getHashMapRequest();
    testingRequest.forEach(
        (i, m) -> {
          request1.add(m);
          //          System.out.println("Request ID:" + m.getReqid());
          //          System.out.println("Employee ID:" + m.getEmpid());
          //          System.out.println("Status:" + m.getStatus());
          //          System.out.println("Location:" + m.getLocation());
          //          System.out.println("Serve By:" + m.getServ_by());
          //          System.out.println();
        });

    ArrayList<MealRequest> mealRequests1 = new ArrayList<>();
    HashMap<Integer, MealRequest> testingMealHash = this.getHashMapMeal();
    testingMealHash.forEach(
        (i, m) -> {
          mealRequests1.add(m);
          //              System.out.println("Request ID:" + m.getReqid());
          //              System.out.println("Employee ID:" + m.getEmpid());
          //              System.out.println("Delivery date:" + m.getDeliveryDate());
          //              System.out.println("Delivery time:" + m.getDeliveryTime());
          //              System.out.println("note:" + m.getNote());
          //              System.out.println("meal:" + m.getEmpid());
          //              System.out.println();
        });
    ArrayList<ConferenceRoomRequest> confroom = new ArrayList<>();
    HashMap<Integer, ConferenceRoomRequest> testingConfRoom = this.getHashConfRoom();
    testingConfRoom.forEach(
        (i, m) -> {
          confroom.add(m);
          //              System.out.println("Reqid: " + m.getReqid());
          //              System.out.println("Meeting Date: " + m.getMeeting_date());
          //              System.out.println("Meeting time: " + m.getMeeting_time());
          //              System.out.println("Purpose: " + m.getPurpose());
        });
    ArrayList<FlowerRequest> flowerDel = new ArrayList<>();
    HashMap<Integer, FlowerRequest> testingFlower = this.getHashMapFlowerRequest();
    testingFlower.forEach(
        (i, m) -> {
          flowerDel.add(m);
          System.out.println("Reqid: " + m.getReqid());
          //              System.out.println("Meeting Date: " + m.getMeeting_date());
          //              System.out.println("Meeting time: " + m.getMeeting_time());
          //              System.out.println("Purpose: " + m.getPurpose());
        });

    ArrayList<FurnitureRequest> furns = new ArrayList<>();
    HashMap<Integer, FurnitureRequest> testingFurns = this.getHashFurns();
    testingFurns.forEach(
        (i, m) -> {
          furns.add(m);
          //              System.out.println("Reqid: " + m.getReqid());
          //              System.out.println("Meeting Date: " + m.getMeeting_date());
          //              System.out.println("Meeting time: " + m.getMeeting_time());
          //              System.out.println("Purpose: " + m.getPurpose());
        });

    testList = FXCollections.observableArrayList(request1);
    testMealList = FXCollections.observableArrayList(mealRequests1);
    testRoomList = FXCollections.observableArrayList(confroom);
    testFlowerList = FXCollections.observableArrayList(flowerDel);
    testFurnList = FXCollections.observableArrayList(furns);

    mainTable.setItems(testList);
    mealTable.setItems(testMealList);
    roomTable.setItems(testRoomList);
    flowerTable.setItems(testFlowerList);
    furnTable.setItems(testFurnList);

    reqID.setCellValueFactory(new PropertyValueFactory<>("reqid"));
    reqType.setCellValueFactory(new PropertyValueFactory<>("reqtype"));
    empID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    location1.setCellValueFactory(new PropertyValueFactory<>("location"));
    serveBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    reqDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    reqTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));

    mealReqID.setCellValueFactory(new PropertyValueFactory<>("reqid"));
    mealEmpID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    mealLocation1.setCellValueFactory(new PropertyValueFactory<>("location"));
    mealServeBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    mealStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    mealRecipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
    mealOrder.setCellValueFactory(new PropertyValueFactory<>("order"));
    mealNote.setCellValueFactory(new PropertyValueFactory<>("note"));
    mealDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    mealTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));

    roomReqID.setCellValueFactory(new PropertyValueFactory<>("reqid"));
    roomEmpID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    roomLocation1.setCellValueFactory(new PropertyValueFactory<>("location"));
    roomServeBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    roomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    roomDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    roomTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
    endTime.setCellValueFactory(new PropertyValueFactory<>("endtime"));
    roomPurpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));

    flowerReqID.setCellValueFactory(new PropertyValueFactory<>("reqid"));
    flowerEmpID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    flowerLocation1.setCellValueFactory(new PropertyValueFactory<>("location"));
    flowerServeBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    flowerStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    flowerType.setCellValueFactory(new PropertyValueFactory<>("flowerType"));
    flowerNumber.setCellValueFactory(new PropertyValueFactory<>("numFlower"));
    flowerDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    flowerTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
    flowerRecipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
    flowerNote.setCellValueFactory(new PropertyValueFactory<>("note"));

    furnReqID.setCellValueFactory(new PropertyValueFactory<>("reqid"));
    furnEmpID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    furnLocation1.setCellValueFactory(new PropertyValueFactory<>("location"));
    furnServeBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    furnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    furnType.setCellValueFactory(new PropertyValueFactory<>("furnitureType"));
    furnDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    furnTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
    furnRecipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
    furnNote.setCellValueFactory(new PropertyValueFactory<>("note"));
  }

  public HashMap getHashMapRequest() throws SQLException {

    HashMap<Integer, Request> requestHashMap = new HashMap<Integer, Request>();

    try {
      requestHashMap = dao.getAllRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return requestHashMap;
  }

  public HashMap getHashMapMeal() throws SQLException {

    HashMap mealRequestHashMap = new HashMap<Integer, MealRequest>();

    try {
      mealRequestHashMap = dao.getAllMealRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return mealRequestHashMap;
  }

  public HashMap getHashConfRoom() throws SQLException {

    HashMap<Integer, ConferenceRoomRequest> confRoomHash =
        new HashMap<Integer, ConferenceRoomRequest>();

    try {
      confRoomHash = dao.getAllConferenceRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return confRoomHash;
  }

  public HashMap getHashMapFlowerRequest() throws SQLException {

    HashMap<Integer, FlowerRequest> flowerHashMap = new HashMap<Integer, FlowerRequest>();

    try {
      flowerHashMap = dao.getALLFlowerRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }
    return flowerHashMap;
  }

  public HashMap getHashFurns() throws SQLException {

    HashMap<Integer, FurnitureRequest> furnsHash = new HashMap<Integer, FurnitureRequest>();

    try {
      furnsHash = dao.getAllFurniture();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return furnsHash;
  }

  public void loadAllRequestTable() {
    mainTable.setVisible(true);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
    flowerTable.setVisible(false);
    furnTable.setVisible(false);
  }

  public void loadMealTable() {
    mealTable.setVisible(true);
    mainTable.setVisible(false);
    roomTable.setVisible(false);
    flowerTable.setVisible(false);
    furnTable.setVisible(false);
  }

  public void loadRoomTable() {
    roomTable.setVisible(true);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    flowerTable.setVisible(false);
    furnTable.setVisible(false);
  }

  public void loadFlowerTable() {
    flowerTable.setVisible(true);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
    furnTable.setVisible(false);
  }

  public void loadFurnitureTable() {
    furnTable.setVisible(true);
    flowerTable.setVisible(false);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
  }

  public void exit() {
    Platform.exit();
  }
}
