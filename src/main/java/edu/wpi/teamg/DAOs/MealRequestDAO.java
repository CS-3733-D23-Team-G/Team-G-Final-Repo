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

  NodeDAO nodeDAO = new NodeDAO();

  @Override
  public HashMap<Integer, MealRequest> getAll() throws SQLException {

    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    SQL_mealRequest =
        "select * from teamgdb.iteration2.request join teamgdb.iteration2.mealrequest on teamgdb.iteration2.request.reqid = teamgdb.iteration2.mealrequest.reqid";

    try {
      ps = db.getConnection().prepareStatement(SQL_mealRequest);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    while (rs.next()) {

      int reqID = rs.getInt("reqID");
      int empID = rs.getInt("empID");

      int location = rs.getInt("location");

      HashMap longNameHash = new HashMap<>();

      longNameHash = NodeDAO.getMandFLLongName();

      String longName = (String) longNameHash.get(location);

      int serveBy = rs.getInt("serveBy");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));
      String recipient = rs.getString("recipient");
      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      String order = rs.getString("mealOrder");
      String note = rs.getString("note");
      MealRequest mealReq =
          new MealRequest(
              "M",
              empID,
              longName,
              serveBy,
              status,
              requestdate,
              requesttime,
              recipient,
              order,
              note);

      mealReq.setReqid(reqID);

      mealRequestHash.put(reqID, mealReq);
    }

    db.closeConnection();

    return mealRequestHash;
  }

  @Override
  public void update(Object obj, String colName, Object value) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps_getMaxID;
    PreparedStatement ps_mealRequest;
    PreparedStatement ps_Request;

    ResultSet rs = null;

    SQL_maxID = "select reqID from teamgdb.iteration2.request order by reqid desc limit 1";

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
        "insert into "
            + this.getTable()
            + "(reqid, recipient, mealOrder, note) values (?, ?, ?, ?)";
    SQL_Request =
        "insert into teamgdb.iteration2.request(reqid, reqtype, empid, location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";

    try {

      ps_Request = db.getConnection().prepareStatement(SQL_Request);
      ps_Request.setInt(1, maxID);
      ps_Request.setString(2, "M");
      ps_Request.setInt(3, ((MealRequest) obj).getEmpid());

      int nodeID = nodeDAO.getNodeIDbyLongName(((MealRequest) obj).getLocation());

      ps_Request.setInt(4, nodeID);

      ps_Request.setInt(5, ((MealRequest) obj).getServeBy());
      ps_Request.setObject(6, ((MealRequest) obj).getStatus(), java.sql.Types.OTHER);
      ps_Request.setDate(7, ((MealRequest) obj).getRequestDate());
      ps_Request.setTime(8, ((MealRequest) obj).getRequestTime());
      ps_Request.executeUpdate();

      ps_mealRequest = db.getConnection().prepareStatement(SQL_mealRequest);
      ps_mealRequest.setInt(1, maxID);
      ps_mealRequest.setString(2, ((MealRequest) obj).getRecipient());
      ps_mealRequest.setString(3, ((MealRequest) obj).getOrder());
      ps_mealRequest.setString(4, ((MealRequest) obj).getNote());
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

    String SQL_mealrequest = "delete from " + this.getTable() + " where reqId = ?";
    String SQL_request = "delete from teamgdb.iteration2.request where reqId = ?";

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

  @Override
  public String getTable() {
    return "teamgdb.iteration2.mealrequest";
  }
}
