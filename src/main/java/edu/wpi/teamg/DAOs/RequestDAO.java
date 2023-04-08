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

  @Override
  public HashMap<Integer, Request> getAll() throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    sql = "select * from iteration1.request";

    try {
      ps = db.getConnection().prepareStatement(sql);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("SQL Exception");
    }

    while (rs.next()) {

      int reqID = rs.getInt("reqid");
      int empID = rs.getInt("empid");
      int location = rs.getInt("location");
      int serv_by = rs.getInt("serv_by");
      Date deliveryDate= rs.getDate("deliveryDate");
      Time deliverytime=rs.getTime("deliveryTime");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));

      Request cReq = new Request(empID, location, serv_by, status, deliveryDate,deliverytime);
      cReq.setReqid(reqID);

      requestHash.put(reqID, cReq);
    }

    db.closeConnection();
    return requestHash;
  }

  @Override
  public void update(Object obj, Object update) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {}

  @Override
  public void delete(Object obj) throws SQLException {}
}
