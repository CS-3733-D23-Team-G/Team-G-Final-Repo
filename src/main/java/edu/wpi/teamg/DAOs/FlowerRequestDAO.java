package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.FlowerRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class FlowerRequestDAO implements DAO {
  HashMap<Integer, FlowerRequest> flowerHashMap = new HashMap<>();
  private static DBConnection db = new DBConnection();
  private String SQL_maxID;
  private String SQL_flowerRequest;
  private String SQL_Request;
  NodeDAO nodeDAO = new NodeDAO();
  EmployeeDAO employeeDAO = new EmployeeDAO();

  @Override
  public HashMap<Integer, FlowerRequest> getAll() throws SQLException {
    db.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    SQL_flowerRequest =
        "select * from iteration3.request join iteration3.flowerrequest on iteration3.request.reqid = iteration3.flowerrequest.reqid";
    try {
      ps = db.getConnection().prepareStatement(SQL_flowerRequest);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    HashMap longNameHash = new HashMap<>();
    longNameHash = NodeDAO.getMandFLLongName();

    HashMap employeeHash = new HashMap<>();
    employeeHash = employeeDAO.getEmployeeFullName("Flowers Request");

    HashMap allEmployeeHash = new HashMap<>();
    allEmployeeHash = employeeDAO.getAllEmployeeFullName();

    while (rs.next()) {
      int reqID = rs.getInt("reqID");
      String reqType = rs.getString("reqtype");

      int empID = rs.getInt("empID");
      String requestingEmployee = "ID " + empID + ": " + (String) allEmployeeHash.get(empID);

      int location = rs.getInt("location");
      String longName = (String) longNameHash.get(location);

      int serveBy = rs.getInt("serveby");
      String assignedEmployee = "ID " + serveBy + ": " + (String) employeeHash.get(serveBy);

      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));
      String recipient = rs.getString("recipient");
      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      String flowerType = rs.getString("flowertype");
      String note = rs.getString("note");
      int numFlower = rs.getInt("numflower");
      FlowerRequest flowerReq =
          new FlowerRequest(
              reqType,
              requestingEmployee,
              longName,
              assignedEmployee,
              status,
              requestdate,
              requesttime,
              flowerType,
              numFlower,
              note,
              recipient);

      flowerReq.setReqid(reqID);
      flowerHashMap.put(reqID, flowerReq);
    }
    db.closeConnection();

    return flowerHashMap;
  }

  @Override
  public void update(Object obj, String colName, Object value) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();
    PreparedStatement ps_getMaxID;
    PreparedStatement ps_getFlowerReq;
    PreparedStatement ps_Req;

    ResultSet rs = null;
    SQL_maxID = "select reqid from teamgdb.iteration3.request order by reqid desc limit 1";

    try {
      ps_getMaxID = db.getConnection().prepareStatement(SQL_maxID);
      rs = ps_getMaxID.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    int maxID = 0;
    while (rs.next()) {
      maxID = rs.getInt("reqID");
      maxID++;
    }

    SQL_flowerRequest =
        "insert into teamgdb.iteration3.flowerrequest(reqid, flowertype, numflower, recipient, note) values (?,?,?,?,?)";
    SQL_Request =
        "insert into teamgdb.iteration3.request(reqid,reqtype,empid,location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";

    try {
      ps_Req = db.getConnection().prepareStatement(SQL_Request);
      ps_Req.setInt(1, maxID);
      ps_Req.setString(2, "FL");

      String requestingEmployee = ((FlowerRequest) obj).getEmpid();
      String assignedEmployee = ((FlowerRequest) obj).getServeBy();

      String[] split0 = requestingEmployee.split(":");
      String[] split1 = assignedEmployee.split(":");

      int empid = Integer.parseInt(split0[0].substring(3));
      int serveBy = Integer.parseInt(split1[0].substring(3));

      ps_Req.setInt(3, empid);

      int nodeID =
          nodeDAO.getNodeIDbyLongName(
              ((FlowerRequest) obj).getLocation(), new java.sql.Date(2023, 01, 01));

      ps_Req.setInt(4, nodeID);
      ps_Req.setInt(5, serveBy);
      ps_Req.setObject(6, ((FlowerRequest) obj).getStatus(), java.sql.Types.OTHER);
      ps_Req.setDate(7, ((FlowerRequest) obj).getRequestDate());
      ps_Req.setTime(8, ((FlowerRequest) obj).getRequestTime());
      ps_Req.executeUpdate();

      ps_getFlowerReq = db.getConnection().prepareStatement(SQL_flowerRequest);
      ps_getFlowerReq.setInt(1, maxID);
      ps_getFlowerReq.setString(2, ((FlowerRequest) obj).getFlowerType());
      ps_getFlowerReq.setInt(3, ((FlowerRequest) obj).getNumFlower());
      ps_getFlowerReq.setString(4, ((FlowerRequest) obj).getRecipient());
      ps_getFlowerReq.setString(5, ((FlowerRequest) obj).getNote());

      ps_getFlowerReq.executeUpdate();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    flowerHashMap.put(((FlowerRequest) obj).getReqid(), (FlowerRequest) obj);
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

      while((line= br.readLine())!=null){
        String[] data = line.split(",");
        int id = Integer.parseInt(data[0]);
        String types=data[1];
        int num = Integer.parseInt(data[2]);
        String recipient = data[3];
        String note = data[4];
        FlowerRequest flowerReq = new FlowerRequest(id,types,num,recipient,note);
        this.insert(flowerReq);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public String getTable() {
    return "teamgdb.iteration3.flowerrequest";
  }
}
