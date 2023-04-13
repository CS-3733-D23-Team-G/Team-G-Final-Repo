package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.sql.*;
import java.util.HashMap;

public class RequestDAO implements DAO {

  private static DBConnection db = new DBConnection();
  private String sql;
  private HashMap<Integer, Request> requestHash = new HashMap<Integer, Request>();

  private NodeDAO nodeDao = new NodeDAO();

  @Override
  public HashMap<Integer, Request> getAll() throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    sql = "select * from teamgdb.iteration1.request";

    try {
      ps = db.getConnection().prepareStatement(sql);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("SQL Exception");
    }

    HashMap longNameHash = new HashMap<>();

    longNameHash = nodeDao.getAllLongName();

    while (rs.next()) {

      int reqID = rs.getInt("reqid");
      String reqType = rs.getString("reqtype");
      int empID = rs.getInt("empid");

      String location = (String) longNameHash.get(rs.getInt("location"));

      int serveBy = rs.getInt("serveBy");
      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));

      Request cReq =
          new Request(reqType, empID, location, serveBy, status, requestdate, requesttime);

      cReq.setReqid(reqID);

      requestHash.put(reqID, cReq);
    }

    db.closeConnection();
    return requestHash;
  }

  @Override
  public void update(Object obj, String colName, Object value) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {}

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public String getTable() {
    return "teamgdb.iteration1.request";
  }
}
