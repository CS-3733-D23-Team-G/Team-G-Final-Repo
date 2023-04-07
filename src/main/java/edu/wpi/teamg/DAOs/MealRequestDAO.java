package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.MealRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.sql.*;
import java.util.HashMap;

public class MealRequestDAO implements DAO {

  private static DBConnection db = new DBConnection();
  private String SQL_maxID;
  private String SQL_mealRequest;
  private String SQL_Request;

  private HashMap<Integer, MealRequest> mealRequestHash = new HashMap<Integer, MealRequest>();

  @Override
  public HashMap<Integer, MealRequest> getAll() throws SQLException {

    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    SQL_mealRequest =
        "select * from proto2.request join proto2.mealrequest on proto2.request.reqid = proto2.mealrequest.reqid";

    try {
      ps = db.getConnection().prepareStatement(SQL_mealRequest);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    while (rs.next()) {
      MealRequest mealReq = new MealRequest();

      int reqID = rs.getInt("reqID");
      mealReq.setReqid(reqID);

      int empID = rs.getInt("empID");
      mealReq.setEmpid(empID);

      int location = rs.getInt("location");
      mealReq.setLocation(location);

      int serv_by = rs.getInt("serv_by");
      mealReq.setServ_by(serv_by);

      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));

      mealReq.setStatus(status);

      String recipient = rs.getString("recipient");
      mealReq.setRecipient(recipient);

      Date deliveryDate = rs.getDate("deliveryDate");
      mealReq.setDeliveryDate(deliveryDate);

      Time deliveryTime = rs.getTime("deliveryTime");
      mealReq.setDeliveryTime(deliveryTime);

      String order = rs.getString("mealOrder");
      mealReq.setOrder(order);

      String note = rs.getString("note");
      mealReq.setNote(note); // check when merge

      mealRequestHash.put(reqID, mealReq);
    }

    db.closeConnection();

    return mealRequestHash;
  }

  @Override
  public void update(Object obj, Object update) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps_getMaxID;
    PreparedStatement ps_mealRequest;
    PreparedStatement ps_Request;

    ResultSet rs = null;

    SQL_maxID = "select reqID from proto2.request order by reqid desc limit 1";

    try {
      ps_getMaxID = db.getConnection().prepareStatement(SQL_maxID);
      rs = ps_getMaxID.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    int maxID = 0;

    while (rs.next()) {
      maxID = rs.getInt("reqID");
      maxID++;
    }

    SQL_mealRequest =
        "insert into proto2.mealrequest(reqid, deliverydate, deliverytime, recipient, mealOrder, note) values (?, ?, ?, ?, ?, ?)";
    SQL_Request =
        "insert into proto2.request(reqid, empid, location, serv_by, status) values (?, ?, ?, ?, ?)";

    try {

      ps_Request = db.getConnection().prepareStatement(SQL_Request);
      ps_Request.setInt(1, maxID);
      ps_Request.setInt(2, ((MealRequest) obj).getEmpid());
      ps_Request.setInt(3, ((MealRequest) obj).getLocation());
      ps_Request.setInt(4, ((MealRequest) obj).getServ_by());
      ps_Request.setObject(5, ((MealRequest) obj).getStatus(), java.sql.Types.OTHER);
      ps_Request.executeUpdate();

      ps_mealRequest = db.getConnection().prepareStatement(SQL_mealRequest);
      ps_mealRequest.setInt(1, maxID);
      ps_mealRequest.setDate(2, ((MealRequest) obj).getDeliveryDate());
      ps_mealRequest.setTime(3, ((MealRequest) obj).getDeliveryTime());
      ps_mealRequest.setString(4, ((MealRequest) obj).getRecipient());
      ps_mealRequest.setString(5, ((MealRequest) obj).getOrder());
      ps_mealRequest.setString(6, ((MealRequest) obj).getNote());
      ps_mealRequest.executeUpdate();

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }
    mealRequestHash.put(((MealRequest) obj).getReqid(), (MealRequest) obj);

    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {

    db.setConnection();

    PreparedStatement ps_mealrequest;
    PreparedStatement ps_request;

    String SQL_mealrequest = "delete from proto2.mealrequest where reqId = ?";
    String SQL_request = "delete from proto2.request where reqId = ?";

    try {
      ps_mealrequest = db.getConnection().prepareStatement(SQL_mealrequest);
      ps_mealrequest.setInt(1, ((MealRequest) obj).getReqid());
      ps_mealrequest.executeUpdate();

      ps_request = db.getConnection().prepareStatement(SQL_request);
      ps_request.setInt(1, ((MealRequest) obj).getReqid());
      ps_request.executeUpdate();

      mealRequestHash.remove(((MealRequest) obj).getReqid());

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    db.closeConnection();
  }
}
