package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.FurnitureRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.sql.*;
import java.util.HashMap;

public class FurnitureDAO implements DAO {
  HashMap<Integer, FurnitureRequest> furnitureRequestHashMap = new HashMap<>();
  private static DBConnection db = new DBConnection();
  private String SQL;
  private String SQL_Request;
  private String SQL_MAXID;
  NodeDAO nodeDAO = new NodeDAO();

  @Override
  public HashMap<Integer, FurnitureRequest> getAll() throws SQLException {
    db.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    SQL =
        "select * from"
            + getRequest()
            + " join "
            + getTable()
            + " on iteration2.request.reqid= iteration2.furniturerequest.reqid";
    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    while (rs.next()) {
      int reqID = rs.getInt("reqID");
      String reqType = rs.getString("reqtype");
      int empID = rs.getInt("empID");

      int location = rs.getInt("location");
      HashMap longNameHash = new HashMap<>();

      longNameHash = NodeDAO.getMandFLLongName();

      String longName = (String) longNameHash.get(location);

      int serv_by = rs.getInt("serveby");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));
      String recipient = rs.getString("recipient");
      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      String furnitureType = rs.getString("furnituretype");
      String color = rs.getString("color");
      String note = rs.getString("note");
      FurnitureRequest furnitureRequest =
          new FurnitureRequest(
              reqType,
              empID,
              longName,
              serv_by,
              status,
              requestdate,
              requesttime,
              furnitureType,
              color,
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
    db.getConnection();
    PreparedStatement ps_MaxID;
    PreparedStatement ps_Furniture;
    PreparedStatement ps_Req;
    ResultSet rs = null;
    FurnitureRequest furn = (FurnitureRequest) obj;

    SQL_MAXID = "select reqid from " + getRequest() + "order by desc limit 1";
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
    SQL = "insert into " + getTable() + "(reqid, furnitureType, color, note) values(?,?,?,?)";

    SQL_Request =
        "insert into "
            + getRequest()
            + "(reqid,reqtype,empid,location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";

    try {
      ps_Req = db.getConnection().prepareStatement(SQL_Request);
      ps_Req.setInt(1, maxID);
      ps_Req.setString(2, "FR");
      ps_Req.setInt(3, furn.getEmpid());
      int nodeID = nodeDAO.getNodeIDbyLongName(furn.getLocation());
      ps_Req.setInt(4, nodeID);
      ps_Req.setString(5, furn.getServe_By());
      ps_Req.setObject(6, furn.getStatus());
      ps_Req.setDate(7, furn.getRequestDate());
      ps_Req.setTime(8, furn.getRequestTime());
      ps_Req.executeUpdate();

      ps_Furniture = db.getConnection().prepareStatement(SQL);
      ps_Furniture.setInt(1, maxID);
      ps_Furniture.setString(2, furn.getFurnitureType());
      ps_Furniture.setString(3, furn.getColor());
      ps_Furniture.setString(4, furn.getNote());
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
  public String getTable() {

    return "teamgdb.iteration2.furniturerequest";
  }

  public String getRequest() {
    return "teamgdb.iteration2.request";
  }
}
