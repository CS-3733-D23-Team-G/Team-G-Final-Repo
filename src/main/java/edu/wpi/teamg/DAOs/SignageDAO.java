package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Signage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SignageDAO implements DAO{
    DBConnection db = new DBConnection();
    String sql = null;
    HashMap<Integer, Signage> signageHash = new HashMap<>();

    @Override
    public HashMap getAll() throws SQLException {
        db.setConnection();

        PreparedStatement ps;
        ResultSet rs = null;
        sql = "select * from" +this.getTable();
        ps=db.getConnection().prepareStatement(sql);
        rs=ps.executeQuery();

        while(rs.next()){
            int kiosk = rs.getInt("kiosknum");
            Date date = rs.getDate("date");
            String direction = rs.getString("arrow");

            Signage signage = new Signage(kiosk,date,direction);
            signageHash.put(signage.getKioskNum(),signage);
        }

        db.closeConnection();
        return signageHash;
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
    public void importCSV(String path) throws SQLException {

    }

    @Override
    public String getTable() {
        return "teamgdb.iteration3.signage";
    }
}
