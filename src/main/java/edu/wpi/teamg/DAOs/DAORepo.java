package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.ORMClasses.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DAORepo {
  private NodeDAO nodeDao = new NodeDAO();
  private EdgeDAO edgeDao = new EdgeDAO();
  private MoveDAO moveDao = new MoveDAO();
  private RequestDAO requestDao = new RequestDAO();
  private LocationNameDAO locationNameDao = new LocationNameDAO();
  private ConferenceRoomRequestDAO conferenceRoomRequestDao = new ConferenceRoomRequestDAO();
  private MealRequestDAO mealRequestDao = new MealRequestDAO();
  private FlowerRequestDAO flowerRequestDAO = new FlowerRequestDAO();
  private EmployeeDAO employeeDAO = new EmployeeDAO();
  private LoginDao loginDAO = new LoginDao();

  public HashMap getAllNodes() throws SQLException {
    return nodeDao.getAll();
  }

  public HashMap getAllEdges() throws SQLException {
    return edgeDao.getAll();
  }

  public List getAllMoves() throws SQLException {
    return moveDao.getAll();
  }

  public HashMap getAllRequest() throws SQLException {
    return requestDao.getAll();
  }

  public HashMap getAllConferenceRequest() throws SQLException {
    return conferenceRoomRequestDao.getAll();
  }

  public HashMap getALLFlowerRequest() throws SQLException {
    return flowerRequestDAO.getAll();
  }

  public HashMap getAllMealRequest() throws SQLException {
    return mealRequestDao.getAll();
  }

  public HashMap getAllLocationNames() throws SQLException {
    return locationNameDao.getAll();
  }

  public void insertNode(Object obj) throws SQLException {
    nodeDao.insert(obj);
  }

  public void insertEdge(Object obj) throws SQLException {
    edgeDao.insert(obj);
  }

  public void insertMove(Object obj) throws SQLException {
    moveDao.insert(obj);
  }

  public void insertRequest(Object obj) throws SQLException {
    requestDao.insert(obj);
  }

  public void insertConferenceRoomRequest(Object obj) throws SQLException {
    conferenceRoomRequestDao.insert(obj);
  }

  public void insertMealRequest(Object obj) throws SQLException {
    mealRequestDao.insert(obj);
  }

  public void insertFlowerRequest(Object obj) throws SQLException {
    flowerRequestDAO.insert(obj);
  }

  public void insertLocationName(Object obj) throws SQLException {
    locationNameDao.insert(obj);
  }

  public void deleteNode(Object obj) throws SQLException {
    nodeDao.delete(obj);
  }

  public void deleteEdge(Object obj) throws SQLException {
    edgeDao.delete(obj);
  }

  public void deleteMove(Object obj) throws SQLException {
    moveDao.delete(obj);
  }

  public void deleteLocationName(Object obj) throws SQLException {
    locationNameDao.delete(obj);
  }

  public void deleteRequest(Object obj) throws SQLException {
    requestDao.delete(obj);
  }

  public void deleteConferenceRoomRequest(Object obj) throws SQLException {
    conferenceRoomRequestDao.delete(obj);
  }

  public void deleteMealRequest(Object obj) throws SQLException {
    mealRequestDao.delete(obj);
  }

  public void deleteFlowerRequest(Object obj) throws SQLException {
    flowerRequestDAO.delete(obj);
  }

  public void importNodeCSV(String path) throws SQLException {
    nodeDao.importCSV(path);
  }

  public void importMoveCSV(String path) throws SQLException {
    moveDao.importCSV(path);
  }

  public void importLocationNameCSV(String path) throws SQLException {
    locationNameDao.importCSV(path);
  }

  public void importEdgeCSV(String path) throws SQLException {
    edgeDao.importCSV(path);
  }

  public void exportNodeCSV() throws SQLException, IOException {
    nodeDao.exportCSV();
  }

  public void exportEdgeCSV() throws SQLException, IOException {
    edgeDao.exportCSV();
  }

  public void exportMoveCSV() throws SQLException, IOException {
    moveDao.exportCSV();
  }

  public void exportLocationNameCSV() throws SQLException, IOException {
    locationNameDao.exportCSV();
  }

  public HashMap getCRLongName() throws SQLException {
    return nodeDao.getCRLongName();
  }

  public HashMap getMandFLLongName() throws SQLException {
    return nodeDao.getMandFLLongName();
  }

  public HashMap getLongNames(String floor) throws SQLException {
    return nodeDao.getLongNames(floor);
  }

  public HashMap getL1LongNames() throws SQLException {
    return nodeDao.getL1LongNames();
  }

  public HashMap getL2LongNames() throws SQLException {
    return nodeDao.getL2LongNames();
  }

  public HashMap getF1LongNames() throws SQLException {
    return nodeDao.getF1LongNames();
  }

  public HashMap getShortName(String floor) throws SQLException {
    return nodeDao.getShortName(floor);
  }

  public HashMap getAllLongName() throws SQLException {
    return nodeDao.getAllLongName();
  }

  public int getNodeIDbyLongName(String longname) throws SQLException {
    return nodeDao.getNodeIDbyLongName(longname, new java.sql.Date(System.currentTimeMillis()));
  }

  public HashMap getEmployeeFullName(String canServe) throws SQLException {
    return employeeDAO.getEmployeeFullName(canServe);
  }
}
