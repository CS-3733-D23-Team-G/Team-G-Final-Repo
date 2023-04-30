package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.LocationName;
import java.io.*;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class LocationNameDAO implements LocationDAO {
  private static DBConnection connection = new DBConnection();
  private String SQL;
  private HashMap<String, LocationName> Location = new HashMap<String, LocationName>();

  public LocationNameDAO() {}

  @Override
  public HashMap<String, LocationName> getAll() throws SQLException {
    connection.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;

    SQL = "select * from " + this.getTable();

    try {
      ps = connection.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exeption");
    }
    while (rs.next()) {

      String longname = rs.getString("longname");

      String shortname = rs.getString("shortname");

      String nodeT = rs.getString("nodetype");

      LocationName loc = new LocationName(longname, shortname, nodeT);

      Location.put(longname, loc);
    }
    connection.closeConnection();

    return Location;
  }

  public ArrayList getLNgivenRequestType(String requestType) throws SQLException {

    ArrayList<String> LNgivenReqType = new ArrayList<>();

    db.setConnection();

    PreparedStatement ps;

    ResultSet rs = null;

    switch (requestType) {
      default:
        SQL =
            "SELECT LocationName.longName\n"
                + "           FROM iteration4.LocationName\n"
                + "            WHERE LocationName.nodeType = 'CONF'\n"
                + "                OR LocationName.nodeType = 'DEPT'\n"
                + "                OR LocationName.nodeType = 'INFO'\n"
                + "                OR LocationName.nodeType = 'SERV'\n"
                + "                OR LocationName.nodeType = 'LABS'\n"
                + "                OR LocationName.nodeType = 'RETL';";
        break;
      case "CR":
        SQL =
            "SELECT LocationName.longName\n"
                + "           FROM iteration4.LocationName\n"
                + "            WHERE LocationName.nodeType = 'CONF';";
        break;
    }

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {
      String longname = rs.getString("longname");

      LNgivenReqType.add(longname);
    }

    db.closeConnection();

    return LNgivenReqType;
  }

  @Override
  public void update(Object obj, String colName, Object value) {
    db.setConnection();
    SQL = "update  " + this.getTable() + " set " + colName + " = ? where longname = ?";
    String lnameID = ((LocationName) obj).getLongName();

    try {
      PreparedStatement ps = db.getConnection().prepareStatement(SQL);
      ps.setObject(1, value);
      ps.setString(2, lnameID);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      System.err.println("SQL Exception found");
      e.printStackTrace();
    }
    db.closeConnection();
  }

  @Override
  public void insert(Object obj) throws SQLException {
    connection.setConnection();
    PreparedStatement ps;
    LocationName l1 = (LocationName) obj;

    SQL = "INSERT INTO " + this.getTable() + " (longname, shortname, nodetype) VALUES (?,?,?)";

    try {
      ps = connection.getConnection().prepareStatement(SQL);
      ps.setString(2, l1.getShortName());
      ps.setString(3, l1.getNodeType());
      ps.setString(1, l1.getLongName());
      ps.executeUpdate();

      Location.put(l1.getLongName(), l1);
    } catch (SQLException e) {
      e.printStackTrace();

      System.err.println("SQL exception");
    }
    connection.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    connection.setConnection();
    PreparedStatement ps;
    LocationName l1 = (LocationName) obj;

    SQL = "DELETE FROM " + this.getTable() + " WHERE longname=? OR shortname=?";

    try {
      ps = connection.getConnection().prepareStatement(SQL);
      ps.setString(1, l1.getLongName());
      ps.setString(2, l1.getShortName());
      ps.executeQuery();
      Location.remove(l1.getLongName());

    } catch (SQLException e) {
      System.err.println("SQL exeption");
    }
    connection.closeConnection();
  }

  @Override
  public String getTable() {
    return "iteration4.locationname";
  }

  @Override
  public void importCSV(String filename) throws SQLException {
    connection.setConnection();

    try {

      SQL = "insert into " + this.getTable() + " (longname,shortname,nodetype) values (?,?,?)";

      PreparedStatement ps = connection.getConnection().prepareStatement(SQL);

      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line = null;
      br.readLine();

      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");

        String longname = data[0];
        String shortname = data[1];
        String nodetype = data[2];

        ps.setString(1, longname);
        ps.setString(2, shortname);
        ps.setString(3, nodetype);

        ps.addBatch();
      }
      br.close();
      ps.executeBatch();

    } catch (FileNotFoundException e) {
      System.err.println("File Not Found Exception");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("IO Exception");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    connection.closeConnection();
  }
}
