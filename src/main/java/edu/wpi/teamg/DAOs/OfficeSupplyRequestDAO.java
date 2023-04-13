package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.OfficeSupplyRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;

import java.sql.*;
import java.util.HashMap;

public class OfficeSupplyRequestDAO implements DAO{
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

        try{
            ps = db.getConnection().prepareStatement(SQL_OfficeSupplyRequest);
            rs=ps.executeQuery();
        }catch (SQLException e){
            System.err.println("SQL Exception");
            e.printStackTrace();
        }

        while(rs.next()){
            int reqid = rs.getInt("reqid");
            String reqType = rs.getString("reqtype");
            int empID  = rs.getInt("empid");
            int location = rs.getInt("location");

            HashMap longNameHash = new HashMap<>();
            longNameHash=nodeDAO.getMandFLLongName();

            String lName = (String)longNameHash.get(location);
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
            officeSupplyHash.put(reqid,officeSupplyRequest);
        }

        db.closeConnection();
        return officeSupplyHash;
    }

    @Override
    public void update(Object obj, String colName, Object obj2) throws SQLException {

    }

    @Override
    public void insert(Object obj) throws SQLException {

    }

    @Override
    public void delete(Object obj) throws SQLException {

    }

    @Override
    public String getTable() {
        return "teamgdb.iteration2.officesupplyrequest";
    }
}
