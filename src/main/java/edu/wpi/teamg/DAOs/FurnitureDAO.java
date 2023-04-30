package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.FurnitureRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class FurnitureDAO implements DAO {
  HashMap<Integer, FurnitureRequest> furnitureRequestHashMap = new HashMap<>();
  private static DBConnection db = new DBConnection();
  private String SQL;
  private String SQL_Request;
  private String SQL_MAXID;
  NodeDAO nodeDAO = new NodeDAO();
  EmployeeDAO employeeDAO = new EmployeeDAO();

  @Override
  public HashMap<Integer, FurnitureRequest> getAll() throws SQLException {
    db.setConnection(App.getWhichDB());
    PreparedStatement ps;
    ResultSet rs = null;
    SQL =
        "select * from "
            + getRequest()
            + " join "
            + getTable()
            + " on iteration4.request.reqid= iteration4.furniturerequest.reqid";
    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    HashMap longNameHash = new HashMap<>();
    longNameHash = NodeDAO.getMandFLLongName();

    HashMap employeeHash = new HashMap<>();
    employeeHash = employeeDAO.getEmployeeFullName("Furniture Request");

    HashMap allEmployeeHash = new HashMap<>();
    allEmployeeHash = employeeDAO.getAllEmployeeFullName();

    while (rs.next()) {
      int reqID = rs.getInt("reqID");
      String reqType = rs.getString("reqtype");
      int empID = rs.getInt("empID");

      String requestingEmp = "ID " + empID + ": " + (String) allEmployeeHash.get(empID);

      int location = rs.getInt("location");

      String longName = (String) longNameHash.get(location);

      int serv_by = rs.getInt("serveby");
      String assignedEmployee = "ID " + serv_by + ": " + (String) employeeHash.get(serv_by);

      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));
      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      String furnitureType = rs.getString("furnituretype");
      String recipient = rs.getString("recipient");
      String note = rs.getString("note");
      FurnitureRequest furnitureRequest =
          new FurnitureRequest(
              reqType,
              requestingEmp,
              longName,
              assignedEmployee,
              status,
              requestdate,
              requesttime,
              recipient,
              furnitureType,
              note);
      furnitureRequest.setReqid(reqID);
      furnitureRequestHashMap.put(reqID, furnitureRequest);
    }
    db.closeConnection();
    return furnitureRequestHashMap;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection(App.getWhichDB());
    PreparedStatement ps_MaxID;
    PreparedStatement ps_Furniture;
    PreparedStatement ps_Req;
    ResultSet rs = null;
    FurnitureRequest furn = (FurnitureRequest) obj;

    SQL_MAXID = "select reqid from " + this.getRequest() + " order by reqid desc limit 1";
    try {
      ps_MaxID = db.getConnection().prepareStatement(SQL_MAXID);
      rs = ps_MaxID.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    int maxID = 0;
    while (rs.next()) {
      maxID = rs.getInt("reqID");
      maxID++;
    }
    SQL =
        "insert into "
            + this.getTable()
            + " (reqid, furnitureType, note, recipient) values(?,?,?,?)";

    SQL_Request =
        "insert into "
            + getRequest()
            + "(reqid,reqtype,empid,location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";

    try {
      ps_Req = db.getConnection().prepareStatement(SQL_Request);
      ps_Req.setInt(1, maxID);
      ps_Req.setString(2, "FR");

      String reqEmp = ((FurnitureRequest) obj).getEmpid();

      String[] split0 = reqEmp.split(":");

      int empid = Integer.parseInt(split0[0].substring(3));

      ps_Req.setInt(3, empid);
      int nodeID = nodeDAO.getNodeIDbyLongName(furn.getLocation(), new java.sql.Date(2023, 01, 01));

      ps_Req.setInt(4, nodeID);

      String assignedEmployee = furn.getServeBy();

      String[] split1 = new String[2];
      int serveBy = 0;

      if (assignedEmployee != null) {
        split1 = assignedEmployee.split(":");
        serveBy = Integer.parseInt(split1[0].substring(3));
      }

      if (serveBy == 0) {
        ps_Req.setObject(5, null);
      } else {
        ps_Req.setInt(5, serveBy);
      }

      ps_Req.setObject(6, furn.getStatus(), java.sql.Types.OTHER);
      ps_Req.setDate(7, furn.getRequestDate());
      ps_Req.setTime(8, furn.getRequestTime());
      ps_Req.executeUpdate();

      db.closeConnection();
      db.setConnection(App.getWhichDB());

      ps_Furniture = db.getConnection().prepareStatement(SQL);
      ps_Furniture.setInt(1, maxID);
      ps_Furniture.setString(2, furn.getFurnitureType());
      ps_Furniture.setString(3, furn.getNote());
      ps_Furniture.setString(4, furn.getRecipient());
      ps_Furniture.executeUpdate();

    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    furnitureRequestHashMap.put(furn.getReqid(), furn);
    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public void importCSV(String path) throws SQLException {
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String line = null;
      br.readLine();

      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        int id = Integer.parseInt(data[0]);
        String type = data[1];
        String note = data[2];
        String recipient = data[3];
        FurnitureRequest furnReq = new FurnitureRequest(id, type, note, recipient);
        this.insert(furnReq);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getTable() {

    return "iteration4.furniturerequest";
  }

  public String getRequest() {
    return "iteration4.request";
  }
}
