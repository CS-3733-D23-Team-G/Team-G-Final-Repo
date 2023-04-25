package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Signage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SignageDAO implements DAO {
  DBConnection db = new DBConnection();
  String sql = null;
  HashMap<Integer, Signage> signageHash = new HashMap<>();

  @Override
  public HashMap getAll() throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;
    sql = "select * from" + this.getTable();
    ps = db.getConnection().prepareStatement(sql);
    rs = ps.executeQuery();

    while (rs.next()) {
      int kiosk = rs.getInt("kiosknum");
      Date date = rs.getDate("date");
      String direction = rs.getString("arrow");

      Signage signage = new Signage(kiosk, date, direction);
      signageHash.put(signage.getKioskNum(), signage);
    }

    db.closeConnection();
    return signageHash;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    sql = "update " + this.getTable() + " set " + colName + " = ? where kiosknum = ?";
    int kiosk = ((Signage) obj).getKioskNum();
    ps = db.getConnection().prepareStatement(sql);

    ps.setObject(1, obj2);
    ps.setInt(2, kiosk);
    ps.executeUpdate();
    ps.close();

    db.closeConnection();
  }

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    sql = "insert into " + this.getTable() + " (kiosknum, date, arrow) values (?,?,?)";
    ps = db.getConnection().prepareStatement(sql);
    ps.setInt(1, ((Signage) obj).getKioskNum());
    ps.setDate(2, ((Signage) obj).getDate());
    ps.setString(3, ((Signage) obj).getArrow());
    ps.executeUpdate();
    signageHash.put(((Signage) obj).getKioskNum(), (Signage) obj);

    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    sql = "delete from " + this.getTable() + " where kiosknum = ?";
    ps = db.getConnection().prepareStatement(sql);
    ps.setInt(1, ((Signage) obj).getKioskNum());
    ps.executeUpdate();
    signageHash.remove(((Signage) obj).getKioskNum());

    db.closeConnection();
  }

  @Override
  public void importCSV(String path) throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    sql = "insert into " + this.getTable() + " (kiosknum, date, arrow) values (?,?,?)";
    ps = db.getConnection().prepareStatement(sql);
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String line = null;
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        String kiosk = data[0];
        String date = data[1];
        String directions = data[2];

        ps.setInt(1, Integer.parseInt(kiosk));
        ps.setDate(2, Date.valueOf(date));
        ps.setString(3, directions);
        ps.addBatch();
      }

      br.close();
      ps.executeBatch();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    db.closeConnection();
  }

  @Override
  public String getTable() {
    return "teamgdb.iteration3.signage";
  }
}
