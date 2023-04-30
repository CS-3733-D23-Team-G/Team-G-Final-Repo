package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.ConferenceRoomRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;

public class ConferenceRoomRequestDAO implements DAO {

  private static DBConnection db = new DBConnection();
  private String SQL_maxID;
  private String SQL_confRoomRequest;
  private String SQL_Request;
  private HashMap<Integer, ConferenceRoomRequest> conferenceRequestHash =
      new HashMap<Integer, ConferenceRoomRequest>();

  NodeDAO nodeDAO = new NodeDAO();
  EmployeeDAO employeeDAO = new EmployeeDAO();

  @Override
  public HashMap<Integer, ConferenceRoomRequest> getAll() throws SQLException {
    db.setConnection();

    System.out.println("Connection Set");

    PreparedStatement ps;
    ResultSet rs = null;

    SQL_confRoomRequest =
        "select * from iteration4.request join iteration4.conferenceroomrequest "
            + "on iteration4.request.reqid = iteration4.conferenceroomrequest.reqid";

    try {
      ps = db.getConnection().prepareStatement(SQL_confRoomRequest);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("SQL Exception");
    }

    HashMap longNameHash = new HashMap<>();
    longNameHash = NodeDAO.getCRLongName();

    HashMap employeeHash = new HashMap<>();
    employeeHash = employeeDAO.getEmployeeFullName("Conference Room Request");

    HashMap allEmployeeHash = new HashMap<>();
    allEmployeeHash = employeeDAO.getAllEmployeeFullName();

    while (rs.next()) {

      int reqID = rs.getInt("reqid");

      int empID = rs.getInt("empid");
      String requestingEmployee = "ID " + empID + ": " + (String) allEmployeeHash.get(empID);

      int location = rs.getInt("location");
      String longName = (String) longNameHash.get(location);

      int serveBy = rs.getInt("serveBy");
      String assignedEmployee = "ID " + serveBy + ": " + (String) employeeHash.get(serveBy);

      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));

      Date reqDate = rs.getDate("requestdate");
      Time reqTime = rs.getTime("requesttime");
      Time endTime = rs.getTime("endtime");
      String confPurpose = rs.getString("purpose");

      ConferenceRoomRequest cReq =
          new ConferenceRoomRequest(
              "CR",
              requestingEmployee,
              longName,
              assignedEmployee,
              status,
              reqDate,
              reqTime,
              endTime,
              confPurpose);

      cReq.setReqid(reqID);

      conferenceRequestHash.put(reqID, cReq);
    }

    //    conferenceRequestHash.forEach(
    //            (i, m) -> {
    //                  System.out.println("Reqid: " + m.getReqid());
    //                  System.out.println("Meeting Date: " + m.getRequestDate());
    //                  System.out.println("Meeting time: " + m.getRequestTime());
    //                  System.out.println("Purpose: " + m.getPurpose());
    //            });

    db.closeConnection();
    return conferenceRequestHash;
  }

  @Override
  public void update(Object obj, String colName, Object value) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();
    PreparedStatement ps_getMaxID;
    PreparedStatement ps_getRoomReq;
    PreparedStatement ps_Req;

    ResultSet rs = null;

    SQL_maxID = "select reqID from iteration4.request order by reqid desc limit 1";

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
    SQL_confRoomRequest =
        "insert  into iteration4.conferenceroomrequest(reqid, endtime, purpose) values (?,?,?)";
    SQL_Request =
        "insert into iteration4.request(reqid, reqtype, empid, location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";

    try {
      ps_Req = db.getConnection().prepareStatement(SQL_Request);
      ps_Req.setInt(1, maxID);
      ps_Req.setString(2, "CR");

      String requestingEmployee = ((ConferenceRoomRequest) obj).getEmpid();

      String[] split0 = requestingEmployee.split(":");

      int empid = Integer.parseInt(split0[0].substring(3));

      ps_Req.setInt(3, empid);

      int nodeID =
          nodeDAO.getNodeIDbyLongName(
              ((ConferenceRoomRequest) obj).getLocation(), new java.sql.Date(2023, 01, 01));

      ps_Req.setInt(4, nodeID);

      String assignedEmployee = ((ConferenceRoomRequest) obj).getServeBy();

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

      ps_Req.setObject(6, ((ConferenceRoomRequest) obj).getStatus(), java.sql.Types.OTHER);
      ps_Req.setDate(7, ((ConferenceRoomRequest) obj).getRequestDate());
      ps_Req.setTime(8, ((ConferenceRoomRequest) obj).getRequestTime());

      ps_Req.executeUpdate();
      db.closeConnection();
      db.setConnection();

      ps_getRoomReq = db.getConnection().prepareStatement(SQL_confRoomRequest);
      ps_getRoomReq.setInt(1, maxID);
      ps_getRoomReq.setTime(2, ((ConferenceRoomRequest) obj).getEndtime());
      ps_getRoomReq.setString(3, ((ConferenceRoomRequest) obj).getPurpose());
      ps_getRoomReq.executeUpdate();

      conferenceRequestHash.put(
          ((ConferenceRoomRequest) obj).getReqid(), (ConferenceRoomRequest) obj);
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps_confReq;
    PreparedStatement ps_Req;

    String SQL_confReq = "delete from iteration4.conferenceroomrequest where reqID = ?";
    String SQL_Req = "delete from iteration4.request where reqID = ?";
    try {
      ps_confReq = db.getConnection().prepareStatement(SQL_confReq);
      ps_confReq.setInt(1, ((ConferenceRoomRequest) obj).getReqid());
      ps_confReq.executeUpdate();

      ps_Req = db.getConnection().prepareStatement(SQL_Req);
      ps_Req.setInt(1, ((ConferenceRoomRequest) obj).getReqid());
      ps_Req.executeUpdate();

      conferenceRequestHash.remove(((ConferenceRoomRequest) obj).getReqid());

    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    db.closeConnection();
  }

  @Override
  public void importCSV(String path) throws SQLException {
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String line = null;
      br.readLine();

      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        int id = Integer.parseInt(data[0]);
        Time time = Time.valueOf(data[1]);
        String purpose = data[2];
        ConferenceRoomRequest confRoom = new ConferenceRoomRequest(id, time, purpose);
        this.insert(confRoom);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getTable() {
    return "iteration4.conferenceroomrequest";
  }
}
