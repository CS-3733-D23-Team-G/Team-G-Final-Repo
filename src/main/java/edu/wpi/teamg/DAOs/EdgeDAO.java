package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Edge;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EdgeDAO implements LocationDAO {
  static DBConnection connection = new DBConnection();
  private String sql;
  private HashMap<String, Edge> edgeHash = new HashMap<String, Edge>();

  @Override
  public HashMap<String, Edge> getAll() throws SQLException {
    connection.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    sql = "Select * from teamgdb.iteration1.edge";

    try {
      ps = connection.getConnection().prepareStatement(sql);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL error exception");
    }

    while (rs.next()) {

      int startNode = rs.getInt("startnode");
      int endNode = rs.getInt("endnode");

      String edgeID = startNode + "_" + endNode;

      Edge edge = new Edge(startNode, endNode);

      edge.setEdgeID(edgeID);

      edgeHash.put(edge.getEdgeID(), edge);
    }
    //
    //    edgeHash.forEach(
    //        (i, m) -> {
    //          System.out.println("Request ID:" + m.getEdgeID());
    //          System.out.println("Employee ID:" + m.getStartNode());
    //          System.out.println("meal:" + m.getEndNode());
    //          System.out.println();
    //        });

    connection.closeConnection();
    return edgeHash;
  }

  @Override
  public void update(Object obj, Object update) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    connection.setConnection();
    sql = "";
    sql = "INSERT INTO teamgdb.iteration1.edge (startnode, endnode) VALUES (?,?)";
    PreparedStatement ps;
    try {
      ps = connection.getConnection().prepareStatement(sql);
      ps.setInt(1, ((Edge) obj).getStartNode());
      ps.setInt(2, ((Edge) obj).getEndNode());
      ps.executeUpdate();
      edgeHash.put(((Edge) obj).getEdgeID(), (Edge) obj);

    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    connection.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    connection.setConnection();
    sql = "";
    sql = "DELETE FROM teamgdb.iteration1.edge WHERE startnode = ? AND endnode = ?";
    PreparedStatement ps = connection.getConnection().prepareStatement(sql);
    try {
      ps.setInt(1, ((Edge) obj).getStartNode());
      ps.setInt(2, ((Edge) obj).getEndNode());
      ps.executeUpdate();
      edgeHash.remove(((Edge) obj).getEdgeID());
    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
    }
    connection.closeConnection();
  }

  @Override

  public String getTable() {
    return "teamgdb.iteration1.edge";

  }


  @Override
  public void exportCSV() throws SQLException {
    connection.setConnection();
    ResultSet rs = null;
    FileWriter fw = null;

    try {
      Statement statement = connection.getConnection().createStatement();
      rs = statement.executeQuery("select * from teamgdb.iteration1.edge");

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
