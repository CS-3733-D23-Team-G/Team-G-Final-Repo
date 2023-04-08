package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.LocationName;
import java.io.*;
import java.sql.*;
import java.sql.SQLException;
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

    SQL = "select * from teamgdb.iteration1.locationname";

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

  @Override
  public void update(Object old, Object update) throws SQLException {
    /*
    connection.setConnection();
    PreparedStatement ps;
    SQL =

        "UPDATE teamgdb.iteration1.locationname SET shortname=?, nodetype=?, longname=? Where longname=? AND shortname=? AND nodetype=? ";

    LocationName ol = (LocationName) old;
    LocationName up = (LocationName) update;
    try {
      ps = connection.getConnection().prepareStatement(SQL);
      ps.setString(1, up.getShortName());
      ps.setString(2, up.getNodeType());
      ps.setString(3, up.getLongName());
      ps.setString(4, ol.getLongName());
      ps.setString(5, ol.getShortName());
      ps.setString(6, ol.getNodeType());
      ps.executeQuery();
      Location.remove(ol.getLongName());
      Location.put(up.getLongName(), up);
    } catch (SQLException e) {
      System.err.println("SQL exeption");
    }
    connection.closeConnection();*/
  }

  @Override
  public void insert(Object obj) throws SQLException {
    connection.setConnection();
    PreparedStatement ps;
    LocationName l1 = (LocationName) obj;

    SQL =
        "INSERT INTO teamgdb.iteration1.locationname (longname, shortname, nodetype) VALUES (?,?,?)";

    try {
      ps = connection.getConnection().prepareStatement(SQL);
      ps.setString(2, l1.getShortName());
      ps.setString(3, l1.getNodeType());
      ps.setString(1, l1.getLongName());
      ps.executeQuery();

      Location.put(l1.getLongName(), l1);
    } catch (SQLException e) {
      System.err.println("SQL exeption");
    }
    connection.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    connection.setConnection();
    PreparedStatement ps;
    LocationName l1 = (LocationName) obj;

    SQL = "DELETE FROM teamgdb.iteration1.locationname WHERE longname=? OR shortname=?";

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
    return "teamgdb.iteration1.locationname";
  }

  @Override
  public void importCSV(String filename) throws SQLException {
    connection.setConnection();

    try {
      SQL =
          "insert into teamgdb.iteration1.locationname (longname,shortname,nodetype) values (?,?,?)";
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
