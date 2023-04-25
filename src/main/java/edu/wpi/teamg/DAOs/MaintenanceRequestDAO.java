package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.FlowerRequest;
import edu.wpi.teamg.ORMClasses.MaintenanceRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import oracle.ucp.proxy.annotation.Pre;

import java.sql.*;
import java.util.HashMap;

public class MaintenanceRequestDAO implements DAO {
    HashMap<Integer, MaintenanceRequest> maintenanceRequestHashMap= new HashMap<>();
    private static DBConnection db= new DBConnection();
    private String SQL_maxID;
    private String SQL_maintenceRequest;
    private String SQL_Request;
    NodeDAO nodeDAO= new NodeDAO();
    EmployeeDAO employeeDAO= new EmployeeDAO();
    @Override
    public HashMap getAll() throws SQLException {
        db.setConnection();
        PreparedStatement ps;
        ResultSet rs= null;
        SQL_maintenceRequest="Select * from iteration3.request join "+getTable()+" on iteration3.request.reqid = "+getTable()+".requid";
        try{
            ps=db.getConnection().prepareStatement(SQL_maintenceRequest);
            rs = ps.executeQuery();
        }catch(Exception e){
            System.err.println("SQL Exception");
            e.printStackTrace();
        }

        HashMap longNameHash = new HashMap<>();
        longNameHash = NodeDAO.getMandFLLongName();

        HashMap employeeHash = new HashMap<>();
        employeeHash = employeeDAO.getEmployeeFullName("Flowers Request");

        HashMap allEmployeeHash = new HashMap<>();
        allEmployeeHash = employeeDAO.getAllEmployeeFullName();
        while(rs.next()){
            int reqID= rs.getInt("reqID");
            String reqType= rs.getString("reqtype");

            int empID = rs.getInt("empID");
            String requestingEmployee = "ID " + empID + ": " + (String) allEmployeeHash.get(empID);

            int location = rs.getInt("location");
            String longName = (String) longNameHash.get(location);

            int serveBy = rs.getInt("serveby");
            String assignedEmployee = "ID " + serveBy + ": " + (String) employeeHash.get(serveBy);

            StatusTypeEnum status= StatusTypeEnum.valueOf((rs.getString("status")));
            Date requestdate = rs.getDate("requestdate");
            Time requesttime = rs.getTime("requesttime");
            String recipient= rs.getString("recipient");
            String phonNumber= rs.getString("phonenumber");
            String type= rs.getString("type");
            String specifed= rs.getString("specified");
            String note= rs.getString("note");
            MaintenanceRequest mainRequest= new
                    MaintenanceRequest(
                            reqType,
                    requestingEmployee,
                    longName,
                    assignedEmployee,
                    status,
                    requestdate,
                    requesttime,
                    recipient,
                    phonNumber,
                    type,
                    specifed,
                    note
            );
            mainRequest.setReqID(reqID);
            maintenanceRequestHashMap.put(reqID,mainRequest);
        }
        db.closeConnection();
        return maintenanceRequestHashMap;
    }

    @Override
    public void update(Object obj, String colName, Object obj2) throws SQLException {

    }

    @Override
    public void insert(Object obj) throws SQLException {
        MaintenanceRequest maintenanceRequest= (MaintenanceRequest) obj;
        db.setConnection();
        PreparedStatement ps_getMaxID;
        PreparedStatement ps_getMain;
        PreparedStatement ps_Req;

        ResultSet rs= null;
        SQL_maxID="select reqid from teamgdb.iteration3.request order by reqid desc limit 1";
        try {
            ps_getMaxID = db.getConnection().prepareStatement(SQL_maxID);
            rs = ps_getMaxID.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception");
            e.printStackTrace();
        }
        int maxID = 0;
        while (rs.next()) {
            maxID = rs.getInt("reqID");
            maxID++;
        }
        SQL_maintenceRequest="insert into "+getTable()+"(reqid, recipient, phoneNumber, type, specified, notes) values (?,?,?,?,?,?)";
        SQL_Request =
                "insert into teamgdb.iteration3.request(reqid,reqtype,empid,location, serveBy, status, requestdate, requesttime) values (?,?,?,?,?,?,?,?)";
        try{
            ps_Req = db.getConnection().prepareStatement(SQL_Request);
            ps_Req.setInt(1, maxID);
            ps_Req.setString(2, "FL");

            String requestingEmployee = ((FlowerRequest) obj).getEmpid();

            String[] split0 = requestingEmployee.split(":");

            int empid = Integer.parseInt(split0[0].substring(3));

            ps_Req.setInt(3, empid);

            int nodeID =
                    nodeDAO.getNodeIDbyLongName(
                            ((FlowerRequest) obj).getLocation(), new java.sql.Date(2023, 01, 01));

            ps_Req.setInt(4, nodeID);

            String assignedEmployee = ((FlowerRequest) obj).getServeBy();

            String[] split1 = new String[2];
            int serveBy = 0;

            if (assignedEmployee != null) {
                split1 = assignedEmployee.split(":");
                serveBy = Integer.parseInt(split1[0].substring(3));
            }

            if (serveBy == 0) {
                ps_Req.setObject(5, null);
            } else {
                ps_Req.setInt(5, serveBy);
            }

            ps_Req.setObject(6, ((FlowerRequest) obj).getStatus(), java.sql.Types.OTHER);
            ps_Req.setDate(7, ((FlowerRequest) obj).getRequestDate());
            ps_Req.setTime(8, ((FlowerRequest) obj).getRequestTime());
            ps_Req.executeUpdate();

            ps_getMain= db.getConnection().prepareStatement(SQL_maintenceRequest);
            ps_getMain.setInt(1,maintenanceRequest.getReqId());
            ps_getMain.setString(2,maintenanceRequest.getRecipient());
            ps_getMain.setString(3, maintenanceRequest.getPhoneNumber());
            ps_getMain.setString(4,maintenanceRequest.getType());
            ps_getMain.setString(5,maintenanceRequest.getSpecified());
            ps_getMain.setString(6,maintenanceRequest.getNotes());
            ps_getMain.executeQuery();
        }catch(SQLException e){
            System.err.println("SQL Exception");
            e.printStackTrace();
        }
        maintenanceRequestHashMap.put(maintenanceRequest.getReqId(),maintenanceRequest);
        db.closeConnection();

    }

    @Override
    public void delete(Object obj) throws SQLException {

    }

    @Override
    public void importCSV(String path) throws SQLException {

    }

    @Override
    public String getTable() {
        return "iteration3.maintenancerequest";
    }
}
