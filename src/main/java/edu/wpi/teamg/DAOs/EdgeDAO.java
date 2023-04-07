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
    sql = "Select * from teamgdb.proto2.edge";

    try {
      ps = connection.getConnection().prepareStatement(sql);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL error exception");
    }

    while (rs.next()) {
      Edge edge = new Edge();

      int startNode = rs.getInt("startnode");
      edge.setStartNode(startNode);

      int endNode = rs.getInt("endnode");
      edge.setEndNode(endNode);

      String edgeID = startNode + "_" + endNode;

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
    sql = "INSERT INTO teamgdb.proto2.edge (startnode, endnode) VALUES (?,?)";
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
    sql = "DELETE FROM teamgdb.proto2.edge WHERE startnode = ? AND endnode = ?";
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
  public void importCSV(String filename) throws SQLException {
    connection.setConnection();
    sql = "";
    sql = "insert into teamgdb.proto2.edge (startnode, endnode) values (?,?)";
    PreparedStatement ps = connection.getConnection().prepareStatement(sql);
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line = null;
      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        int sNode = Integer.parseInt(data[0]);
        int eNode = Integer.parseInt(data[1]);

        ps.setInt(1, sNode);
        ps.setInt(2, eNode);

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
      rs = statement.executeQuery("select * from teamgdb.proto2.edge");

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
