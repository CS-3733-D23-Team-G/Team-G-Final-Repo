package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.*;
import edu.wpi.teamg.ORMClasses.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.controlsfx.control.PopOver;

public class AdminFormStatusController {

  // Heading

  // Tables
  @FXML TableView<Request> mainTable;
  @FXML TableView<MealRequest> mealTable;
  @FXML TableView<ConferenceRoomRequest> roomTable;
  @FXML TableView<FlowerRequest> flowerTable;
  @FXML TableView<FurnitureRequest> furnTable;
  @FXML TableView<OfficeSupplyRequest> suppTable;
  @FXML TableView<MaintenanceRequest> maintenanceTable;

  // Main Table
  @FXML TableColumn<Request, String> empID;
  @FXML TableColumn<Request, String> reqType;
  @FXML TableColumn<Request, String> location1;
  @FXML TableColumn<Request, Integer> reqID;
  @FXML TableColumn<Request, String> serveBy;
  @FXML TableColumn<Request, StatusTypeEnum> status;
  @FXML TableColumn<Request, Date> reqDate;
  @FXML TableColumn<Request, Time> reqTime;

  // Meal Table
  @FXML TableColumn<MealRequest, String> mealEmpID;
  @FXML TableColumn<MealRequest, String> mealLocation1;
  @FXML TableColumn<MealRequest, Integer> mealReqID;
  @FXML TableColumn<MealRequest, String> mealServeBy;
  @FXML TableColumn<MealRequest, StatusTypeEnum> mealStatus;
  @FXML TableColumn<MealRequest, String> mealRecipient;
  @FXML TableColumn<MealRequest, String> mealOrder;
  @FXML TableColumn<MealRequest, String> mealNote;
  @FXML TableColumn<MealRequest, Date> mealDate;
  @FXML TableColumn<MealRequest, Time> mealTime;

  // room Table
  @FXML TableColumn<ConferenceRoomRequest, String> roomEmpID;
  @FXML TableColumn<ConferenceRoomRequest, String> roomLocation1;
  @FXML TableColumn<ConferenceRoomRequest, Integer> roomReqID;
  @FXML TableColumn<ConferenceRoomRequest, String> roomServeBy;
  @FXML TableColumn<ConferenceRoomRequest, StatusTypeEnum> roomStatus;
  @FXML TableColumn<ConferenceRoomRequest, Date> roomDate;
  @FXML TableColumn<ConferenceRoomRequest, Time> roomTime;
  @FXML TableColumn<ConferenceRoomRequest, Time> endTime;
  @FXML TableColumn<ConferenceRoomRequest, String> roomPurpose;

  // flower Table
  @FXML TableColumn<FlowerRequest, String> flowerEmpID;
  @FXML TableColumn<FlowerRequest, String> flowerLocation1;
  @FXML TableColumn<FlowerRequest, Integer> flowerReqID;
  @FXML TableColumn<FlowerRequest, String> flowerServeBy;
  @FXML TableColumn<FlowerRequest, StatusTypeEnum> flowerStatus;
  @FXML TableColumn<FlowerRequest, String> flowerType;
  @FXML TableColumn<FlowerRequest, Integer> flowerNumber;
  @FXML TableColumn<FlowerRequest, String> flowerRecipient;
  @FXML TableColumn<FlowerRequest, String> flowerNote;

  @FXML TableColumn<ConferenceRoomRequest, Date> flowerDate;
  @FXML TableColumn<ConferenceRoomRequest, Time> flowerTime;

  // furniture Table
  @FXML TableColumn<FurnitureRequest, String> furnEmpID;
  @FXML TableColumn<FurnitureRequest, String> furnLocation1;
  @FXML TableColumn<FurnitureRequest, Integer> furnReqID;
  @FXML TableColumn<FurnitureRequest, String> furnServeBy;
  @FXML TableColumn<FurnitureRequest, StatusTypeEnum> furnStatus;
  @FXML TableColumn<FurnitureRequest, String> furnType;
  @FXML TableColumn<FurnitureRequest, String> furnRecipient;
  @FXML TableColumn<FurnitureRequest, String> furnNote;

  @FXML TableColumn<FurnitureRequest, Date> furnDate;
  @FXML TableColumn<FurnitureRequest, Time> furnTime;

  // office supply Table
  @FXML TableColumn<OfficeSupplyRequest, String> suppEmpID;
  @FXML TableColumn<OfficeSupplyRequest, String> suppLocation1;
  @FXML TableColumn<OfficeSupplyRequest, Integer> suppReqID;
  @FXML TableColumn<OfficeSupplyRequest, String> suppServeBy;
  @FXML TableColumn<OfficeSupplyRequest, StatusTypeEnum> suppStatus;
  @FXML TableColumn<OfficeSupplyRequest, String> suppType;
  @FXML TableColumn<OfficeSupplyRequest, String> suppRecipient;
  @FXML TableColumn<OfficeSupplyRequest, String> suppNote;

  @FXML TableColumn<OfficeSupplyRequest, Date> suppDate;
  @FXML TableColumn<OfficeSupplyRequest, Time> suppTime;

  // maintenance Table
  @FXML TableColumn<MaintenanceRequest, String> maintenanceEmpID;
  @FXML TableColumn<MaintenanceRequest, String> maintenanceLocation1;
  @FXML TableColumn<MaintenanceRequest, Integer> maintenanceReqID;
  @FXML TableColumn<MaintenanceRequest, String> maintenanceServeBy;
  @FXML TableColumn<MaintenanceRequest, StatusTypeEnum> maintenanceStatus;
  @FXML TableColumn<MaintenanceRequest, String> maintenanceType;
  @FXML TableColumn<MaintenanceRequest, String> maintenanceSpecified;
  @FXML TableColumn<MaintenanceRequest, String> maintenanceRecipient;
  @FXML TableColumn<MaintenanceRequest, String> maintenanceNote;
  @FXML TableColumn<MaintenanceRequest, String> maintenancePhoneNumber;
  @FXML TableColumn<MaintenanceRequest, Date> maintenanceDate;
  @FXML TableColumn<MaintenanceRequest, Time> maintenanceTime;

  // Table Change Button
  @FXML MFXButton allRequestTableButton;
  @FXML MFXButton mealTableButton;
  @FXML MFXButton roomTableButton;
  @FXML MFXButton flowerTableButton;
  @FXML MFXButton furnTableButton;
  @FXML MFXButton suppTableButton;
  @FXML MFXButton maintenanceTableButton;

  @FXML MFXButton editTableForm;
  @FXML MFXButton cancelTableForm;

  @FXML ChoiceBox<String> exportService;
  ObservableList<String> serviceList =
      FXCollections.observableArrayList(
          "Conference Room", "Flowers", "Meal", "Furniture", "Office Supply");

  ObservableList<Request> testList;
  ObservableList<MealRequest> testMealList;
  ObservableList<ConferenceRoomRequest> testRoomList;
  ObservableList<FlowerRequest> testFlowerList;

  ObservableList<FurnitureRequest> testFurnList;

  ObservableList<OfficeSupplyRequest> testSuppList;
  ObservableList<MaintenanceRequest> testMaintainList;

  DAORepo dao = new DAORepo();

  String LocationUpdate = new String();

  @FXML
  public void initialize() throws SQLException {
    App.bool = false;
    exportService.setItems(serviceList);
    exportService.setOnAction(
        event -> {
          try {
            fileExporter();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    allRequestTableButton.setOnMouseClicked(event -> loadAllRequestTable());
    mealTableButton.setOnMouseClicked(event -> loadMealTable());
    roomTableButton.setOnMouseClicked(event -> loadRoomTable());
    flowerTableButton.setOnMouseClicked(event -> loadFlowerTable());
    furnTableButton.setOnMouseClicked(event -> loadFurnitureTable());
    suppTableButton.setOnMouseClicked(event -> loadOfficeSupplyTable());
    maintenanceTableButton.setOnMouseClicked(event -> loadMaintenanceTable());

    editTableForm.setOnMouseClicked(
        event -> {
          try {
            editAllTables();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    cancelTableForm.setOnMouseClicked(event -> cancelEditOfTables());

    HashMap<Integer, Request> testingRequest = App.testingRequest;
    ArrayList<Request> request1 = new ArrayList<>(testingRequest.values());

    HashMap<Integer, MealRequest> testingMealHash = App.testingMealHash;
    ArrayList<MealRequest> mealRequests1 = new ArrayList<>(testingMealHash.values());

    HashMap<Integer, ConferenceRoomRequest> testingConfRoom = App.testingConfRoom;
    ArrayList<ConferenceRoomRequest> confroom = new ArrayList<>(testingConfRoom.values());

    HashMap<Integer, FlowerRequest> testingFlower = App.testingFlower;
    ArrayList<FlowerRequest> flowerDel = new ArrayList<>(testingFlower.values());

    HashMap<Integer, FurnitureRequest> testingFurns = App.testingFurns;
    ArrayList<FurnitureRequest> furns = new ArrayList<>(testingFurns.values());

    HashMap<Integer, OfficeSupplyRequest> testingSupp = App.testingOSupps;
    ArrayList<OfficeSupplyRequest> oSupp = new ArrayList<>(testingSupp.values());

    HashMap<Integer, MaintenanceRequest> testingMaintain = App.testingMaintain;
    ArrayList<MaintenanceRequest> maintains = new ArrayList<>(testingMaintain.values());

    testList = FXCollections.observableArrayList(request1);
    testMealList = FXCollections.observableArrayList(mealRequests1);
    testRoomList = FXCollections.observableArrayList(confroom);
    testFlowerList = FXCollections.observableArrayList(flowerDel);
    testFurnList = FXCollections.observableArrayList(furns);

    testSuppList = FXCollections.observableArrayList(oSupp);

    testMaintainList = FXCollections.observableArrayList(maintains);

    mainTable.setItems(testList);
    mealTable.setItems(testMealList);
    roomTable.setItems(testRoomList);
    flowerTable.setItems(testFlowerList);
    furnTable.setItems(testFurnList);

    suppTable.setItems(testSuppList);

    maintenanceTable.setItems(testMaintainList);

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

    suppReqID.setCellValueFactory(new PropertyValueFactory<>("reqid"));
    suppEmpID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    suppLocation1.setCellValueFactory(new PropertyValueFactory<>("location"));
    suppServeBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    suppStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    suppType.setCellValueFactory(new PropertyValueFactory<>("supplyType"));
    suppDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    suppTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
    suppRecipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
    suppNote.setCellValueFactory(new PropertyValueFactory<>("note"));

    maintenanceReqID.setCellValueFactory(new PropertyValueFactory<>("reqId"));
    maintenanceEmpID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    maintenanceLocation1.setCellValueFactory(new PropertyValueFactory<>("location"));
    maintenanceServeBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    maintenanceStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    maintenanceType.setCellValueFactory(new PropertyValueFactory<>("type"));
    maintenanceSpecified.setCellValueFactory(new PropertyValueFactory<>("specified"));
    maintenanceDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    maintenanceTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
    maintenanceRecipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
    maintenanceNote.setCellValueFactory(new PropertyValueFactory<>("notes"));
    maintenancePhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
  }

  private void fileExporter() throws SQLException, IOException {
    switch (exportService.getValue()) {
      case "Conference Room":
        ConferenceRoomRequestDAO dao = new ConferenceRoomRequestDAO();
        dao.exportCSV();
        break;
      case "Flowers":
        FlowerRequestDAO flowerDao = new FlowerRequestDAO();
        flowerDao.exportCSV();
        break;
      case "Meal":
        MealRequestDAO mealDao = new MealRequestDAO();
        mealDao.exportCSV();
        break;
      case "Furniture":
        FurnitureDAO furnDao = new FurnitureDAO();
        furnDao.exportCSV();
        break;
      case "Office Supply":
        OfficeSupplyRequestDAO officeSupplyRequestDAO = new OfficeSupplyRequestDAO();
        officeSupplyRequestDAO.exportCSV();
      default:
        break;
    }
  }

  //  public HashMap getHashMapRequest() throws SQLException {
  //
  //    HashMap<Integer, Request> requestHashMap = new HashMap<Integer, Request>();
  //
  //    try {
  //      requestHashMap = dao.getAllRequest();
  //    } catch (SQLException e) {
  //      System.err.print(e.getErrorCode());
  //    }
  //
  //    return requestHashMap;
  //  }
  //
  //  public HashMap getHashMapMeal() throws SQLException {
  //
  //    HashMap mealRequestHashMap = new HashMap<Integer, MealRequest>();
  //
  //    try {
  //      mealRequestHashMap = dao.getAllMealRequest();
  //    } catch (SQLException e) {
  //      System.err.print(e.getErrorCode());
  //    }
  //
  //    return mealRequestHashMap;
  //  }
  //
  //  public HashMap getHashConfRoom() throws SQLException {
  //
  //    HashMap<Integer, ConferenceRoomRequest> confRoomHash =
  //        new HashMap<Integer, ConferenceRoomRequest>();
  //
  //    try {
  //      confRoomHash = dao.getAllConferenceRequest();
  //    } catch (SQLException e) {
  //      System.err.print(e.getErrorCode());
  //    }
  //
  //    return confRoomHash;
  //  }
  //
  //  public HashMap getHashMapFlowerRequest() throws SQLException {
  //
  //    HashMap<Integer, FlowerRequest> flowerHashMap = new HashMap<Integer, FlowerRequest>();
  //
  //    try {
  //      flowerHashMap = dao.getALLFlowerRequest();
  //    } catch (SQLException e) {
  //      System.err.print(e.getErrorCode());
  //    }
  //    return flowerHashMap;
  //  }
  //
  //  public HashMap getHashFurns() throws SQLException {
  //
  //    HashMap<Integer, FurnitureRequest> furnsHash = new HashMap<Integer, FurnitureRequest>();
  //
  //    try {
  //      furnsHash = dao.getAllFurniture();
  //    } catch (SQLException e) {
  //      System.err.print(e.getErrorCode());
  //    }
  //
  //    return furnsHash;
  //  }

  public void loadAllRequestTable() {
    mainTable.setVisible(true);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
    flowerTable.setVisible(false);
    furnTable.setVisible(false);
    suppTable.setVisible(false);
    maintenanceTable.setVisible(false);
  }

  public void loadMealTable() {
    mealTable.setVisible(true);
    mainTable.setVisible(false);
    roomTable.setVisible(false);
    flowerTable.setVisible(false);
    furnTable.setVisible(false);
    suppTable.setVisible(false);
    maintenanceTable.setVisible(false);
  }

  public void loadRoomTable() {
    roomTable.setVisible(true);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    flowerTable.setVisible(false);
    furnTable.setVisible(false);
    suppTable.setVisible(false);
    maintenanceTable.setVisible(false);
  }

  public void loadFlowerTable() {
    flowerTable.setVisible(true);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
    furnTable.setVisible(false);
    suppTable.setVisible(false);
    maintenanceTable.setVisible(false);
  }

  public void loadFurnitureTable() {
    furnTable.setVisible(true);
    flowerTable.setVisible(false);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
    suppTable.setVisible(false);
    maintenanceTable.setVisible(false);
  }

  public void loadOfficeSupplyTable() {
    furnTable.setVisible(false);
    maintenanceTable.setVisible(false);
    flowerTable.setVisible(false);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
    suppTable.setVisible(true);
  }

  public void loadMaintenanceTable() {
    furnTable.setVisible(false);
    maintenanceTable.setVisible(true);
    flowerTable.setVisible(false);
    mainTable.setVisible(false);
    mealTable.setVisible(false);
    roomTable.setVisible(false);
    suppTable.setVisible(false);
  }

  public void editAllTables() throws SQLException {
    RequestDAO requestDAO = new RequestDAO();

    //    reqID.setCellValueFactory(new PropertyValueFactory<>("reqid"));
    //    reqType.setCellValueFactory(new PropertyValueFactory<>("reqtype"));
    //    empID.setCellValueFactory(new PropertyValueFactory<>("empid"));
    //    location1.setCellValueFactory(new PropertyValueFactory<>("location"));
    //    serveBy.setCellValueFactory(new PropertyValueFactory<>("serveBy"));
    //    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    //    reqDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    //    reqTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));

    mainTable.setEditable(true);

    status.setCellFactory(
        TextFieldTableCell.forTableColumn(
            new StringConverter<StatusTypeEnum>() {
              @Override
              public String toString(StatusTypeEnum object) {
                return String.valueOf(object);
              }

              @Override
              public StatusTypeEnum fromString(String string) {
                return StatusTypeEnum.valueOf(string);
              }
            }));
    status.setOnEditCommit(
        event -> {
          Request obj = event.getRowValue();
          obj.setStatus(StatusTypeEnum.valueOf(String.valueOf(event.getNewValue())));
          try {
            requestDAO.update(obj, "status", event.getNewValue());
            App.requestRefresh(); // let me cook, enum sucks but I have been struggling with it
            // the
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          // m
        });
    // ok ill do the rest hopefully they are nicer
    // I gotta figure out why that top part isnt cooperating does the update work for the rest of
    // the things in the main table as well?

    // for any fields that is common between all requests we just need to call RequestDA0 on all
    // tables

    // Ok Ill figure out whats going on with this top part

    //    empID.setCellFactory(TextFieldTableCell.forTableColumn());
    //    empID.setOnEditCommit(
    //        event -> {
    //          Request obj = event.getRowValue();
    //          int updatedEmpID = extractEmpIDAndSeveBy(String.valueOf(event.getNewValue()));
    //          obj.setEmpid(String.valueOf(event.getNewValue()));
    //          try {
    //            requestDAO.update(obj, "empid", updatedEmpID);
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });

    location1.setCellFactory(TextFieldTableCell.forTableColumn());
    location1.setOnEditStart(
        event -> {
          Request obj = event.getRowValue();
          try {
            updateLoc(obj);
          } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
          }
        });

    //
    //    location1.setOnEditCommit(
    //        event -> {
    //          Request obj = event.getRowValue();
    //          obj.setLocation(LocationUpdate);
    //          try {
    //            requestDAO.update(
    //                obj,
    //                "location",
    //                nodeDAO.getNodeIDbyLongName(LocationUpdate, new java.sql.Date(2023, 01, 01)));
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });

    //        serveBy.setCellFactory(TextFieldTableCell.forTableColumn());
    //        serveBy.setOnEditCommit(
    //            event -> {
    //              Request obj = event.getRowValue();
    //              int updatedServeBy = extractEmpIDAndSeveBy(String.valueOf(event.getNewValue()));
    //              obj.setServeBy(String.valueOf(event.getNewValue()));
    //              try {
    //                requestDAO.update(obj, "serveby", updatedServeBy);
    //              } catch (SQLException e) {
    //                throw new RuntimeException(e);
    //              }
    //            });

    //        reqDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
    //        reqDate.setOnEditCommit(event -> {
    //          Request obj = event.getRowValue();
    //          obj.setRequestDate((java.sql.Date) event.getNewValue());
    //          requestDAO.update(obj, "requesttime", event.getNewValue());
    //        });
    //        reqTime.setCellFactory(TextFieldTableCell.forTableColumn());
    //        reqTime.setOnEditCommit(event -> {
    //          Request obj = event.getRowValue();
    //          obj.setRequestTime(Date.valueOf(String.valueOf(event.getNewValue())));
    //          requestDAO.update(obj, "requestdate", event.getNewValue());
    //        });
    //
    //    furnTable.setEditable(true);

    //    furnLocation1.setCellValueFactory(TextFieldTableCell.forTableColumn());
    //    furnLocation1.setOnEditCommit(event -> {
    //      FurnitureRequest obj = event.getRowValue();
    //      obj.setLocation(event.getNewValue());
    //    });
    //
    //    furnReqID.setCellValueFactory(TextFieldTableCell.forTableColumn());
    //    furnReqID.setOnEditCommit(event ->{
    //      FurnitureRequest obj = event.getRowValue();
    //      obj.setReqid(event.getNewValue());
    //
    //    });
    //    furnServeBy.setCellValueFactory(TextFieldTableCell.forTableColumn());
    //    furnReqID.setOnEditCommit(event ->{
    //      FurnitureRequest obj = event.getRowValue();
    //      obj.setReqid(event.getNewValue());
    //
    //    });
    //    furnServeBy.setCellValueFactory(TextFieldTableCell.forTableColumn());
    //    furnReqID.setOnEditCommit(event ->{
    //      FurnitureRequest obj = event.getRowValue();
    //      obj.setReqid(event.getNewValue());
    //
    //    });

    // furnEmpID furnLocation1///////  furnReqID furnServeBy furnStatus furnType furnRecipient,
    // furnNote, furnDate, furnTime
    //    nodeXcoord.setCellFactory(TextFieldTableCell.forTableColumn(new
    // IntegerStringConverter()));
    //    nodeXcoord.setOnEditCommit(
    //            event -> {
    //              Node obj = event.getRowValue();
    //              obj.setXcoord(event.getNewValue());
    //              nodeDAO.update(obj, "xcoord", event.getNewValue());
    //            });
    //
    //
    //    flowerTable.setEditable(true);
    //
    //    mealTable.setEditable(true);
    //    roomTable.setEditable(true);
  }

  public void cancelEditOfTables() {
    furnTable.setEditable(false);
    flowerTable.setEditable(false);
    mainTable.setEditable(false);
    mealTable.setEditable(false);
    roomTable.setEditable(false);
    maintenanceTable.setEditable(false);
  }

  public int extractEmpIDAndSeveBy(String empIDorSB) {
    String[] split = empIDorSB.split(":");
    int empidOrSB = Integer.parseInt(split[0].substring(3));

    return empidOrSB;
  }

  public void updateLoc(Request req) throws IOException, SQLException {
    String string = new String();

    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/LongNamePopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    LongNamePopoverController controller = loader.getController();
    controller.passObj(req);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void StringFromController(String string) {
    LocationUpdate = string;
  }

  public void exit() {
    Platform.exit();
  }
}
