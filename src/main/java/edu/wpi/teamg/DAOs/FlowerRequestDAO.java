package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.FlowerRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
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

  @Override
  public HashMap<Integer, FlowerRequest> getAll() throws SQLException {
    db.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    SQL_flowerRequest =
        "select * from iteration1.request join iteration1.flowerrequest on iteration1.request.reqid= iteration1.flowerrequest.reqid";
    try {
      ps = db.getConnection().prepareStatement(SQL_flowerRequest);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    while (rs.next()) {
      int reqID = rs.getInt("reqID");
      String reqType = rs.getString("reqtype");
      int empID = rs.getInt("empID");
      String location = rs.getString("location");
      int serv_by = rs.getInt("serv_by");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));
      String recipient = rs.getString("recipient");
      Date deliveryDate = rs.getDate("deliverydate");
      Time deliveryTime = rs.getTime("deliverytime");
      String flowerType = rs.getString("flowertype");
      String note = rs.getString("note");
      int numFlower = rs.getInt("numflower");
      FlowerRequest flowerReq =
          new FlowerRequest(
              reqType,
              empID,
              location,
              serv_by,
              status,
              deliveryDate,
              deliveryTime,
              flowerType,
              numFlower,
              note,
              recipient);
      flowerHashMap.put(reqID, flowerReq);
    }
    db.closeConnection();

    return flowerHashMap;
  }

  @Override
  public void update(Object obj, Object update) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();
    PreparedStatement ps_getMaxID;
    PreparedStatement ps_getFlowerReq;
    PreparedStatement ps_Req;

    ResultSet rs = null;
    SQL_maxID = "select reqid from teamgdb.iteration1.request order by reqid desc limit 1";

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
        "insert into teamgdb.iteration1.flowerrequest(reqid, flowertype, numflower, recipient, note) values (?,?,?,?,?)";
    SQL_Request =
        "insert into teamgdb.iteration1.request(reqid,reqtype,empid,location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";

    try {
      ps_Req = db.getConnection().prepareStatement(SQL_Request);
      ps_Req.setInt(1, maxID);
      ps_Req.setString(2, "FL");
      ps_Req.setInt(3, ((FlowerRequest) obj).getEmpid());

      int nodeID = nodeDAO.getNodeIDbyLongName(((FlowerRequest) obj).getLocation());

      ps_Req.setInt(4, nodeID);
      ps_Req.setInt(5, ((FlowerRequest) obj).getServeBy());
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
  public String getTable() {
    return "teamgdb.iteration1.flowerrequest";
  }
}
