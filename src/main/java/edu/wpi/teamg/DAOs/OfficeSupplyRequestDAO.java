package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.OfficeSupplyRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;

public class OfficeSupplyRequestDAO implements DAO{
    HashMap<Integer, OfficeSupplyRequest> supplyRequestHashMap = new HashMap<>();
    private static DBConnection db = new DBConnection();
    private  String SQL;
    private String SQL_REQUEST;
    private String SQL_MAXID;
    NodeDAO nodeDAO = new NodeDAO();
    EmployeeDAO empDao = new EmployeeDAO();
    @Override
    public HashMap getAll() throws SQLException {
        db.setConnection();
        PreparedStatement ps;
        ResultSet rs = null;
        SQL = "select * from teamgdb.iteration2.request"+" join "+this.getTable()+" on teamgdb.iteration2.request.reqid= teamgdb.iteration2.officesupplyrequest.reqid";
        try{
            ps = db.getConnection().prepareStatement(SQL);
            rs= ps.executeQuery();

        }catch (SQLException e){
            System.err.println("SQL Exception");
            e.printStackTrace();
        }

        HashMap longNameHash = new HashMap<>();
        longNameHash = NodeDAO.getMandFLLongName();

        HashMap employeeHash = new HashMap<>();
        employeeHash = empDao.getEmployeeFullName("Office Supply Request");

        HashMap allEmpHash = new HashMap<>();
        allEmpHash = empDao.getAllEmployeeFullName();

        while(rs.next()){
            int reqID = rs.getInt("reqid");
            String reqtype = rs.getString("reqtype");
            int empId = rs.getInt("empid");

            String requestingEmp = "ID "+ empId+": "+(String) allEmpHash.get(empId);
            int location = rs.getInt("location");
            String longName = (String) longNameHash.get(location);

            int servBy = rs.getInt("serveby");
            String assignedEmp = "ID "+servBy+": "+(String) employeeHash.get(servBy);

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
            supplyRequestHashMap.put(reqID,supplyRequest);
        }

        db.closeConnection();
        return supplyRequestHashMap;
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
