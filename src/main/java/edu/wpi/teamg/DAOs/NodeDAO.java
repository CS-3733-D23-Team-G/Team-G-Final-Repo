package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Node;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NodeDAO implements LocationDAO {
  private HashMap<Integer,Node> nodeHash = new HashMap<Integer,Node>();
  private static DBConnection db = new DBConnection();
  private String SQL;

  @Override
  public void importCSV() {}

  @Override
  public void exportCSV() {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    SQL = "insert into proto2.node(nodeid, xcoord, ycoord, floor, building) values (?, ?, ?, ?, ?)";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setInt(1, ((Node) obj).getNodeID());
      ps.setInt(2, ((Node) obj).getXcoord());
      ps.setInt(3, ((Node) obj).getYcoord());
      ps.setString(4, ((Node) obj).getFloor());
      ps.setString(5, ((Node) obj).getBuilding());
      ps.executeUpdate();

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    db.closeConnection();
  }

  @Override
  public void update(Object obj) throws SQLException {}

  @Override
  public void delete(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps;

    SQL = "delete from proto2.node where nodeid = ?";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setInt(1, ((Node) obj).getNodeID());
      ps.executeUpdate();

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    db.closeConnection();
  }

  @Override
  public HashMap<Integer,Node> getAll() throws SQLException {

    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    SQL = "select * from proto2.node";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {
      Node node = new Node();

      int node_id = rs.getInt("nodeid");
      node.setNodeID(node_id);

      int xcoord = rs.getInt("xcoord");
      node.setXcoord(xcoord);

      int ycoord = rs.getInt("ycoord");
      node.setXcoord(ycoord);

      String floor = rs.getString("floor");
      node.setFloor(floor);

      String building = rs.getString("building");
      node.setBuilding(building);

      nodeHash.put(node.getNodeID(),node);
    }

    db.closeConnection();

    return nodeHash;
  }
}
