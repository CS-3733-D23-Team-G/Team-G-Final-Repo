package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Edge;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.*;

public class EdgeDAO implements LocationDAO {
  static DBConnection connection = new DBConnection();
  private static String sql;
  private HashMap<String, Edge> edgeHash = new HashMap<String, Edge>();

  @Override
  public  HashMap<String, Edge> getAll() throws SQLException {
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
  public void update(Object obj, String colName, Object value) {
    connection.setConnection();
    sql =
        "update "
            + this.getTable()
            + " set "
            + colName
            + " = ? where startNode = ? AND endNode = ?";

    try {
      PreparedStatement ps = db.getConnection().prepareStatement(sql);
      ps.setObject(1, value);
      ps.setInt(2, ((Edge) obj).getStartNode());
      ps.setInt(3, ((Edge) obj).getEndNode());
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
    }
    connection.closeConnection();
  }

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
  public void importCSV(String filename) throws SQLException {
    connection.setConnection();
    sql = "";
    sql = "insert into teamgdb.iteration1.edge (startnode, endnode) values (?,?)";
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
}
