package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Node;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NodeDAO implements LocationDAO {
  private HashMap<Integer, Node> nodeHash = new HashMap<Integer, Node>();
  private static DBConnection db = new DBConnection();
  private String SQL;
  private HashMap<Integer, Node> Nodes = new HashMap<>();

  @Override
  public void exportCSV() throws SQLException {
    db.setConnection();
    ResultSet rs = null;
    FileWriter fw = null;

    try {
      Statement statement = db.getConnection().createStatement();
      rs = statement.executeQuery("select * from teamgdb.iteration1.node");

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

    db.closeConnection();
  }

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps;

    SQL =
        "insert into teamgdb.iteration1.node(nodeid, xcoord, ycoord, floor, building) values (?, ?, ?, ?, ?)";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setInt(1, ((Node) obj).getNodeID());
      ps.setInt(2, ((Node) obj).getXcoord());
      ps.setInt(3, ((Node) obj).getYcoord());
      ps.setString(4, ((Node) obj).getFloor());
      ps.setString(5, ((Node) obj).getBuilding());
      ps.executeUpdate();
      nodeHash.put(((Node) obj).getNodeID(), (Node) obj);

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    db.closeConnection();
  }

  @Override
  public void update(Object obj, Object update) throws SQLException {}

  @Override
  public void delete(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps;

    SQL = "delete from teamgdb.iteration1.node where nodeid = ?";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setInt(1, ((Node) obj).getNodeID());
      ps.executeUpdate();
      nodeHash.remove(((Node) obj).getNodeID());

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    db.closeConnection();
  }

  @Override
  public String getTable() {
    return "teamgdb.iteration1.node";
  }

  @Override
  public HashMap<Integer, Node> getAll() throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    SQL = "select * from teamgdb.iteration1.node";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      int xcoord = rs.getInt("xcoord");
      int ycoord = rs.getInt("ycoord");
      String floor = rs.getString("floor");
      String building = rs.getString("building");

      Node node = new Node(node_id, xcoord, ycoord, floor, building);

      nodeHash.put(node.getNodeID(), node);
    }
    db.closeConnection();

    return nodeHash;
  }
}
