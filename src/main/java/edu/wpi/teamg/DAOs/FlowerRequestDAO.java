package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.FlowerRequest;
import edu.wpi.teamg.ORMClasses.MealRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.HashMap;

public class FlowerRequestDAO implements DAO{
    HashMap<Integer,FlowerRequest> flowerHashMap= new HashMap<>();
    private static DBConnection db = new DBConnection();
    private String SQL_maxID;
    private String SQL_flowerRequest;
    private String SQL_Request;
    @Override
    public HashMap<Integer,FlowerRequest> getAll() throws SQLException {
        db.setConnection();
        PreparedStatement ps;
        ResultSet rs=null;
        SQL_flowerRequest=
                "select * from iteration1.request join iteration1.flowerrequest on iteration1.request.reid= iteration1.flowerrequest.reqid";
        try{
            ps= db.getConnection().prepareStatement(SQL_flowerRequest);
            rs= ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        while(rs.next()){
            int reqID = rs.getInt("reqID");
            int empID = rs.getInt("empID");
            int location = rs.getInt("location");
            int serv_by = rs.getInt("serv_by");
            StatusTypeEnum status = StatusTypeEnum.valueOf(rs.getString("status"));
            String recipient = rs.getString("recipient");
            Date deliveryDate = rs.getDate("deliverydate");
            Time deliveryTime = rs.getTime("deliverytime");
            String flowerType= rs.getString("flowertype");
            String note= rs.getString("note");
            int numFlower= rs.getInt("numflower");
            FlowerRequest flowerReq=
                    new FlowerRequest(empID,location,serv_by,status,deliveryDate,deliveryTime,flowerType,numFlower,note,recipient);
            flowerHashMap.put(reqID,flowerReq);

        }
        db.closeConnection();


        return flowerHashMap;
    }

    @Override
    public void update(Object obj, Object update) throws SQLException {

    }

    @Override
    public void insert(Object obj) throws SQLException {
        db.setConnection();

        PreparedStatement ps_getMaxID;
        PreparedStatement ps_flowerRequest;
        PreparedStatement ps_Request;

        ResultSet rs = null;

        SQL_maxID = "select reqID from iteration1.request order by reqid desc limit 1";

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

        SQL_flowerRequest =
                "insert into iteration1.mealrequest(reqid, deliverydate, deliverytime, recipient, mealOrder, note) values (?, ?, ?, ?, ?, ?)";
        SQL_Request =
                "insert into iteration1.request(reqid, empid, location, serv_by, status) values (?, ?, ?, ?, ?)";

        try {

            ps_Request = db.getConnection().prepareStatement(SQL_Request);
            ps_Request.setInt(1, maxID);
            ps_Request.setInt(2, ((MealRequest) obj).getEmpid());
            ps_Request.setInt(3, ((MealRequest) obj).getLocation());
            ps_Request.setInt(4, ((MealRequest) obj).getServ_by());
            ps_Request.setObject(5, ((MealRequest) obj).getStatus(), java.sql.Types.OTHER);
            ps_Request.executeUpdate();

            ps_flowerRequest = db.getConnection().prepareStatement(SQL_flowerRequest);
            ps_mealRequest.setInt(1, maxID);
            ps_mealRequest.setDate(2, ((MealRequest) obj).getDeliveryDate());
            ps_mealRequest.setTime(3, ((MealRequest) obj).getDeliveryTime());
            ps_mealRequest.setString(4, ((MealRequest) obj).getRecipient());
            ps_mealRequest.setString(5, ((MealRequest) obj).getOrder());
            ps_mealRequest.setString(6, ((MealRequest) obj).getNote());
            ps_mealRequest.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL exception");
            e.printStackTrace();
            // printSQLException(e);
        }
        flowerHashMap.put(((MealRequest) obj).getReqid(), (MealRequest) obj);

        db.closeConnection();
    }

    @Override
    public void delete(Object obj) throws SQLException {

        db.setConnection();

        PreparedStatement ps_mealrequest;
        PreparedStatement ps_request;

        String SQL_mealrequest = "delete from iteration1.mealrequest where reqId = ?";
        String SQL_request = "delete from iteration1.request where reqId = ?";

        try {
            ps_mealrequest = db.getConnection().prepareStatement(SQL_mealrequest);
            ps_mealrequest.setInt(1, ((MealRequest) obj).getReqid());
            ps_mealrequest.executeUpdate();

            ps_request = db.getConnection().prepareStatement(SQL_request);
            ps_request.setInt(1, ((MealRequest) obj).getReqid());
            ps_request.executeUpdate();

            flowerHashMap.remove(((MealRequest) obj).getReqid());

        } catch (SQLException e) {
            System.err.println("SQL exception");
            e.printStackTrace();
            // printSQLException(e);
        }

        db.closeConnection();
    }
}

