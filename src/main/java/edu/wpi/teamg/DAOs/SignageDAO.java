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
      Date date = rs.getDate("specdate");
      String direction = rs.getString("arrow");
      String month = rs.getString("month");
      boolean spec = rs.getBoolean("is_spec");

      Signage signage = new Signage(kiosk, date, direction, month, spec);
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
    sql =
        "insert into "
            + this.getTable()
            + " (kiosknum, specdate, arrow, month, is_spec) values (?,?,?,?,?)";
    ps = db.getConnection().prepareStatement(sql);
    ps.setInt(1, ((Signage) obj).getKioskNum());
    ps.setDate(2, ((Signage) obj).getSpecdate());
    ps.setString(3, ((Signage) obj).getArrow());
    ps.setString(4, ((Signage) obj).getMonth());
    ps.setBoolean(5, ((Signage) obj).isSpecified());
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
    sql =
        "insert into "
            + this.getTable()
            + " (kiosknum, specdate, arrow, month, is_spec) values (?,?,?,?,?)";
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
        String month = data[3];
        boolean specified = Boolean.parseBoolean(data[4]);

        ps.setInt(1, Integer.parseInt(kiosk));
        ps.setDate(2, Date.valueOf(date));
        ps.setString(3, directions);
        ps.setString(4, month);
        ps.setBoolean(5, specified);
        ps.addBatch();
      }

      br.close();
      ps.executeBatch();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    db.closeConnection();
  }

  public HashMap getSignageGivenKioskNumAndMonth(int givenKiosk, String givenMonth)
      throws SQLException {
    db.setConnection();
    HashMap<String, Signage> directions = new HashMap<>();
    PreparedStatement ps;
    ResultSet rs;

    sql =
        "select * from"
            + this.getTable()
            + "where iteration3.signage.month = ? and iteration3.signage.kiosknum = ?";
    ps = db.getConnection().prepareStatement(sql);
    ps.setString(1, givenMonth);
    ps.setInt(2, givenKiosk);
    rs = ps.executeQuery();

    while (rs.next()) {
      int kiosknum = rs.getInt("kiosknum");
      String key = rs.getString("month") + " " + rs.getString("arrow");
      String month = rs.getString("month");
      String arrow = rs.getString("arrow");

      Signage newSignage = new Signage(kiosknum, null, arrow, month, false);
      directions.put(key, newSignage);
    }

    db.closeConnection();
    return directions;
  }

  @Override
  public String getTable() {
    return "teamgdb.iteration3.signage";
  }
}
