package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestDAO implements DAO {

  private static DBConnection db = new DBConnection();
  private String sql;
  private HashMap<Integer, Request> requestHash = new HashMap<Integer, Request>();

  private static HashMap<Integer, Request> outstandingRequestHash = new HashMap<Integer, Request>();

  private static NodeDAO nodeDao = new NodeDAO();

  static EmployeeDAO employeeDAO = new EmployeeDAO();

  @Override
  public HashMap<Integer, Request> getAll() throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    sql = "select * from " + this.getTable();

    try {
      ps = db.getConnection().prepareStatement(sql);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("SQL Exception");
    }

    HashMap longNameHash = new HashMap<>();
    longNameHash = nodeDao.getAllLongName();

    HashMap allEmployeeHash = new HashMap<>();
    allEmployeeHash = employeeDAO.getAllEmployeeFullName();

    while (rs.next()) {

      int reqID = rs.getInt("reqid");
      String reqType = rs.getString("reqtype");

      int empID = rs.getInt("empid");
      String requestingEmployee = "ID " + empID + ": " + (String) allEmployeeHash.get(empID);

      String location = (String) longNameHash.get(rs.getInt("location"));

      int serveBy = rs.getInt("serveBy");
      String assignedEmployee = "ID " + serveBy + ": " + (String) allEmployeeHash.get(serveBy);

      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));

      Request cReq =
          new Request(
              reqType,
              requestingEmployee,
              location,
              assignedEmployee,
              status,
              requestdate,
              requesttime);

      cReq.setReqid(reqID);

      requestHash.put(reqID, cReq);
    }

    db.closeConnection();
    return requestHash;
  }

  @Override
  public void update(Object obj, String colName, Object value) throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    sql = "update teamgdb.iteration4.request set " + colName + " = ? where reqid = ?";

    try {
      ps = db.getConnection().prepareStatement(sql);

      switch (colName) {
        case "status":
          ps.setObject(1, ((Request) obj).getStatus(), java.sql.Types.OTHER);
          break;
        case "empid":
          ps.setInt(1, (Integer) value);
          break;
        case "serveby":
          ps.setInt(1, (Integer) value);
          break;
        case "location":
          ps.setInt(1, (Integer) value);
          break;
      }
      ps.setInt(2, ((Request) obj).getReqid());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("SQL exception");
    }
    db.closeConnection();
  }

  @Override
  public void insert(Object obj) throws SQLException {}

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public void importCSV(String path) throws SQLException {
    db.setConnection();
    sql =
        "insert into "
            + this.getTable()
            + " (reqid, reqtype, empid, location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";
    PreparedStatement ps = db.getConnection().prepareStatement(sql);
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String line = null;
      br.readLine();

      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        int id = Integer.parseInt(data[0]);
        String type = data[1];
        int eID = Integer.parseInt(data[2]);
        int loc = Integer.parseInt(data[3]);
        int by = Integer.parseInt(data[4]);
        String status = data[5];
        Date date = Date.valueOf(data[6]);
        Time time = Time.valueOf(data[7]);

        ps.addBatch();
      }
      br.close();
      ps.executeUpdate();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getTable() {
    return "teamgdb.iteration4.request";
  }

  public static ArrayList getOutstandingRequest(int serveby) throws SQLException {
    db.setConnection();

    ArrayList<Request> oRequestList = new ArrayList<>();

    String oRequestSQL;
    ResultSet rs = null;

    PreparedStatement ps;
    //    oRequestSQL =
    //        "select * from iteration4.request where serveby = ? and (status = 'blank' or status =
    // 'processing');";

    oRequestSQL =
        "select * from iteration4.request where serveby = ? and status <> 'done' ORDER BY requestdate";

    try {
      ps = db.getConnection().prepareStatement(oRequestSQL);
      ps.setInt(1, serveby);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("SQL exception");
    }

    HashMap longNameHash = new HashMap<>();
    longNameHash = nodeDao.getAllLongName();

    HashMap allEmployeeHash = new HashMap<>();
    allEmployeeHash = employeeDAO.getAllEmployeeFullName();

    while (rs.next()) {

      int reqID = rs.getInt("reqid");
      String reqType = rs.getString("reqtype");

      int empID = rs.getInt("empid");
      String requestingEmployee = "ID " + empID + ": " + (String) allEmployeeHash.get(empID);

      String location = (String) longNameHash.get(rs.getInt("location"));

      int serveBy = rs.getInt("serveBy");
      String assignedEmployee = "ID " + empID + ": " + (String) allEmployeeHash.get(serveBy);

      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));

      Request cReq =
          new Request(
              reqType,
              requestingEmployee,
              location,
              assignedEmployee,
              status,
              requestdate,
              requesttime);

      cReq.setReqid(reqID);

      oRequestList.add(cReq);
      outstandingRequestHash.put(reqID, cReq);
    }

    db.closeConnection();
    return oRequestList;
  }

  public static void updateRequestStatus(StatusTypeEnum newStatus, int reqid) throws SQLException {
    db.setConnection();

    String RequestSQL;

    PreparedStatement ps;

    RequestSQL = "update teamgdb.iteration4.request set status = ? where reqid = ?";

    try {
      ps = db.getConnection().prepareStatement(RequestSQL);
      ps.setObject(1, newStatus, java.sql.Types.OTHER);
      ps.setInt(2, reqid);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      System.err.println("SQL exception");
    }

    db.closeConnection();
  }
}
