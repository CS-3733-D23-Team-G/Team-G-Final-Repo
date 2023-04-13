package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.OfficeSupplyRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.sql.*;
import java.util.HashMap;

public class OfficeSupplyRequestDAO implements DAO {
  HashMap<Integer, OfficeSupplyRequest> officeSupplyHash = new HashMap<>();
  private static DBConnection db = new DBConnection();
  private String SQL_maxID;
  private String SQL_OfficeSupplyRequest;
  private String SQL_Request;
  NodeDAO nodeDAO = new NodeDAO();

  @Override
  public HashMap getAll() throws SQLException {
    db.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    SQL_OfficeSupplyRequest =
        "select * from iteration2.request join iteration2.officesupplyrequest on iteration2.request.reqid = iteration2.officesupplyrequest.reqid";

    try {
      ps = db.getConnection().prepareStatement(SQL_OfficeSupplyRequest);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    while (rs.next()) {
      int reqid = rs.getInt("reqid");
      String reqType = rs.getString("reqtype");
      int empID = rs.getInt("empid");
      int location = rs.getInt("location");

      HashMap longNameHash = new HashMap<>();
      longNameHash = nodeDAO.getMandFLLongName();

      String lName = (String) longNameHash.get(location);
      int servBy = rs.getInt("serveby");
      StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));
      String recipient = rs.getString("recipient");
      Date requestdate = rs.getDate("requestdate");
      Time requesttime = rs.getTime("requesttime");
      String supplyType = rs.getString("officesupplytype");
      String note = rs.getString("note");
      int numSupply = rs.getInt("numsupply");

      OfficeSupplyRequest officeSupplyRequest =
          new OfficeSupplyRequest(
              reqType,
              empID,
              lName,
              servBy,
              status,
              requestdate,
              requesttime,
              supplyType,
              numSupply,
              recipient,
              note);
      officeSupplyRequest.setReqid(reqid);
      officeSupplyHash.put(reqid, officeSupplyRequest);
    }

    db.closeConnection();
    return officeSupplyHash;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps_getMaxID;
    PreparedStatement ps_getSupplyReq;
    PreparedStatement ps_Req;

    ResultSet rs = null;
    SQL_maxID = "select reqid from teamgdb.iteration2.request order by reqid desc limit 1";

    try {
      ps_getMaxID = db.getConnection().prepareStatement(SQL_maxID);
      rs = ps_getMaxID.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    int maxId = 0;
    while (rs.next()) {
      maxId = rs.getInt("reqID");
      maxId++;
    }
    SQL_OfficeSupplyRequest =
        "insert into "
            + this.getTable()
            + "(reqid,officesupplytype,numsupply,recipient,note) values (?,?,?,?,?)";
    SQL_Request =
        "insert into teamgdb.iteration2.request(reqid,reqtype,empid,location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";

    try {
      ps_Req = db.getConnection().prepareStatement(SQL_Request);
      ps_Req.setInt(1, maxId);
      ps_Req.setString(2, "OS");
      ps_Req.setInt(3, ((OfficeSupplyRequest) obj).getEmpid());

      int nodeID = nodeDAO.getNodeIDbyLongName(((OfficeSupplyRequest) obj).getLocation());

      ps_Req.setInt(4, nodeID);
      ps_Req.setInt(5, ((OfficeSupplyRequest) obj).getServeBy());
      ps_Req.setObject(6, ((OfficeSupplyRequest) obj).getStatus(), java.sql.Types.OTHER);
      ps_Req.setDate(7, ((OfficeSupplyRequest) obj).getRequestDate());
      ps_Req.setTime(8, ((OfficeSupplyRequest) obj).getRequestTime());
      ps_Req.executeUpdate();

      ps_getSupplyReq = db.getConnection().prepareStatement(SQL_OfficeSupplyRequest);
      ps_getSupplyReq.setInt(1, maxId);
      ps_getSupplyReq.setString(2, ((OfficeSupplyRequest) obj).getOfficeSupplyType());
      ps_getSupplyReq.setInt(3, ((OfficeSupplyRequest) obj).getNumSupply());
      ps_getSupplyReq.setString(4, ((OfficeSupplyRequest) obj).getRecipient());
      ps_getSupplyReq.setString(5, ((OfficeSupplyRequest) obj).getNote());
      ps_getSupplyReq.executeUpdate();

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
    }
    officeSupplyHash.put(((OfficeSupplyRequest) obj).getReqid(), (OfficeSupplyRequest) obj);
    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public String getTable() {
    return "teamgdb.iteration2.officesupplyrequest";
  }
}
