package edu.wpi.teamname.DAOs;

import edu.wpi.teamname.DBConnection;
import edu.wpi.teamname.ORMClasses.Node;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NodeDAO implements LocationDAO {

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
  public List<Node> getAll() throws SQLException {

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

    List<Node> allNodes = new ArrayList<Node>();

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

      allNodes.add(node);
    }

    db.closeConnection();

    return allNodes;
  }
}
