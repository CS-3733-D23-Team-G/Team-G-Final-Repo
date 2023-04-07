package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.LocationName;
import java.io.*;
import java.sql.*;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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

    SQL = "select * from teamgdb.proto2.locationname";

    try {
      ps = connection.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exeption");
    }
    while (rs.next()) {
      LocationName loc = new LocationName();

      String longname = rs.getString("longname");
      loc.setLongName(longname);

      String shortname = rs.getString("shortname");
      loc.setShortName(shortname);

      String nodeT = rs.getString("nodetype");
      loc.setNodeType(nodeT);

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
        "UPDATE proto2.locationname SET shortname=?, nodetype=?, longname=? Where longname=? AND shortname=? AND nodetype=? ";
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
    SQL = "INSERT INTO teamgdb.proto2.locationname (longname, shortname, nodetype) VALUES (?,?,?)";

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
    SQL = "DELETE FROM proto2.locationname WHERE longname=? OR shortname=?";

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
  public void importCSV(String filename) throws SQLException {
    connection.setConnection();

    try {
      SQL = "insert into teamgdb.proto2.locationname (longname,shortname,nodetype) values (?,?,?)";
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

  @Override
  public void exportCSV() throws SQLException {

    connection.setConnection();
    ResultSet rs = null;
    FileWriter fw = null;

    try {
      Statement statement = connection.getConnection().createStatement();
      rs = statement.executeQuery("select * from teamgdb.proto2.locationname");

      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", ".csv");
      chooser.setFileFilter(filter);

      int result = chooser.showSaveDialog(null);
      if (result == JFileChooser.APPROVE_OPTION) {
        File savedFile = chooser.getSelectedFile();
        String path = savedFile.getAbsolutePath();
        fw = new FileWriter(path);

        int colCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= colCount; i++) {
          String colLabel = rs.getMetaData().getColumnLabel(i);
          fw.append(colLabel);
          if (i < colCount) fw.append(",");
        }
        fw.append("\n");

        while (rs.next()) {
          for (int j = 1; j <= colCount; j++) {
            String cellVal = rs.getString(j);
            fw.append(cellVal);
            if (j < colCount) fw.append(",");
          }
          fw.append("\n");
        }
      }

      rs.close();
      statement.close();
      fw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    connection.closeConnection();
  }
}
