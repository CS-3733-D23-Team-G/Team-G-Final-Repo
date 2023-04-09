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

  @Override
  public HashMap<Integer, FlowerRequest> getAll() throws SQLException {
    db.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    SQL_flowerRequest =
        "select * from iteration1.request join iteration1.flowerrequest on iteration1.request.reid= iteration1.flowerrequest.reqid";
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
      int location = rs.getInt("location");
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
              "",
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
  public void insert(Object obj) throws SQLException {}

  @Override
  public void delete(Object obj) throws SQLException {}
}
