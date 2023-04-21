package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.OfficeSupplyRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;

public class OfficeSupplyRequestDAO implements DAO {
  HashMap<Integer, OfficeSupplyRequest> supplyRequestHashMap = new HashMap<>();
  private static DBConnection db = new DBConnection();
  private String SQL;
  private String SQL_REQUEST;
  private String SQL_MAXID;
  NodeDAO nodeDAO = new NodeDAO();
  EmployeeDAO empDao = new EmployeeDAO();

  @Override
  public HashMap getAll() throws SQLException {
    db.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    SQL =
        "select * from teamgdb.iteration3.request"
            + " join "
            + this.getTable()
            + " on teamgdb.iteration3.request.reqid= teamgdb.iteration3.officesupplyrequest.reqid";
    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();

    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    HashMap longNameHash = new HashMap<>();
    longNameHash = NodeDAO.getMandFLLongName();

    HashMap employeeHash = new HashMap<>();
    employeeHash = empDao.getEmployeeFullName("Office Supply Request");

    HashMap allEmpHash = new HashMap<>();
    allEmpHash = empDao.getAllEmployeeFullName();

    while (rs.next()) {
      int reqID = rs.getInt("reqid");
      String reqtype = rs.getString("reqtype");
      int empId = rs.getInt("empid");

      String requestingEmp = "ID " + empId + ": " + (String) allEmpHash.get(empId);
      int location = rs.getInt("location");
      String longName = (String) longNameHash.get(location);

      int servBy = rs.getInt("serveby");
      String assignedEmp = "ID " + servBy + ": " + (String) employeeHash.get(servBy);

      StatusTypeEnum status = StatusTypeEnum.blank.valueOf(rs.getString("status"));
      Date reqDate = rs.getDate("requestdate");
      Time reqTime = rs.getTime("requesttime");
      String supplyType = rs.getString("supplytype");
      String recip = rs.getString("recipient");
      String note = rs.getString("note");
      OfficeSupplyRequest supplyRequest =
          new OfficeSupplyRequest(
              reqtype,
              requestingEmp,
              longName,
              assignedEmp,
              status,
              reqDate,
              reqTime,
              recip,
              supplyType,
              note);

      supplyRequest.setReqid(reqID);
      supplyRequestHashMap.put(reqID, supplyRequest);
    }

    db.closeConnection();
    return supplyRequestHashMap;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();
    PreparedStatement ps_maxid;
    PreparedStatement ps_supply;
    PreparedStatement ps_req;
    ResultSet rs = null;
    OfficeSupplyRequest supplyRequest = (OfficeSupplyRequest) obj;

    SQL_MAXID = "select reqid from teamgdb.iteration3.request order by reqid desc limit 1";
    try {
      ps_maxid = db.getConnection().prepareStatement(SQL_MAXID);
      rs = ps_maxid.executeQuery();

    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    int maxid = 0;
    while (rs.next()) {
      maxid = rs.getInt("reqID");
      maxid++;
    }
    SQL =
        "insert into " + this.getTable() + " (reqid, supplytype, note, recipient) values(?,?,?,?)";
    SQL_REQUEST =
        "insert into teamgdb.iteration3.request (reqid,reqtype,empid,location,serveBy,status,requestdate,requesttime) values (?,?,?,?,?,?,?,?)";

    try {
      ps_req = db.getConnection().prepareStatement(SQL_REQUEST);
      ps_req.setInt(1, maxid);
      ps_req.setString(2, "OS");

      String reqEmp = ((OfficeSupplyRequest) obj).getEmpid();
      String assignedEmp = ((OfficeSupplyRequest) obj).getServeBy();

      String[] split0 = reqEmp.split(":");
      String[] split1 = assignedEmp.split(":");
      int nodeID =
          nodeDAO.getNodeIDbyLongName(supplyRequest.getLocation(), new java.sql.Date(2023, 01, 01));

      int empid = Integer.parseInt(split0[0].substring(3));
      int servby = Integer.parseInt(split1[0].substring(3));

      ps_req.setInt(3, empid);
      ps_req.setInt(4, nodeID);
      ps_req.setInt(5, servby);
      ps_req.setObject(6, supplyRequest.getStatus(), java.sql.Types.OTHER);
      ps_req.setDate(7, supplyRequest.getRequestDate());
      ps_req.setTime(8, supplyRequest.getRequestTime());
      ps_req.executeUpdate();

      ps_supply = db.getConnection().prepareStatement(SQL);
      ps_supply.setInt(1, maxid);
      ps_supply.setString(2, supplyRequest.getSupplyType());
      ps_supply.setString(3, supplyRequest.getNote());
      ps_supply.setString(4, supplyRequest.getRecipient());
      ps_supply.executeUpdate();

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
    }

    supplyRequestHashMap.put(supplyRequest.getReqid(), supplyRequest);
    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public String getTable() {
    return "teamgdb.iteration3.officesupplyrequest";
  }
}