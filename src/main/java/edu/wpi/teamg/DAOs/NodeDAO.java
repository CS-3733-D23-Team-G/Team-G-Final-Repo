package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Node;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class NodeDAO implements LocationDAO {
  private static HashMap<Integer, Node> nodeHash = new HashMap<Integer, Node>();
  private static DBConnection db = new DBConnection();
  private static String SQL;

  @Override
  public void update(Object obj, String colName, Object value) {
    db.setConnection(App.getWhichDB());
    SQL = "update " + this.getTable() + " set " + colName + " = ? where nodeid = ?";
    int nodeID = ((Node) obj).getNodeID();

    try {
      PreparedStatement ps = db.getConnection().prepareStatement(SQL);
      ps.setObject(1, value);
      ps.setInt(2, nodeID);
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      System.err.println("SQL Exception found");
      e.printStackTrace();
    }

    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    db.setConnection(App.getWhichDB());

    PreparedStatement ps;

    SQL = "delete from " + this.getTable() + " where nodeid = ?";

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

    return "iteration4_presentation.node";

  }

  @Override
  public void importCSV(String path) throws SQLException {
    db.setConnection(App.getWhichDB());
    try {

      SQL =
          "INSERT INTO "
              + this.getTable()
              + " (nodeid, xcoord, ycoord, floor, building) VALUES (?,?,?,?,?)";

      PreparedStatement ps = db.getConnection().prepareStatement(SQL);

      BufferedReader br = new BufferedReader(new FileReader(path));
      String line = null;

      br.readLine(); // skip line
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");

        String nodeID = data[0];
        String xcoord = data[1];
        String ycoord = data[2];
        String floor = data[3];
        String building = data[4];

        int iNodeID = Integer.parseInt(nodeID);
        ps.setInt(1, iNodeID);

        int iXCoord = Integer.parseInt(xcoord);
        ps.setInt(2, iXCoord);

        int iYCoord = Integer.parseInt(ycoord);
        ps.setInt(3, iYCoord);

        ps.setString(4, floor);
        ps.setString(5, building);
        ps.addBatch();
      }
      br.close();
      ps.executeBatch();
    } catch (IOException e) {
      System.err.println(e);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
  }

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection(App.getWhichDB());

    PreparedStatement ps;
    SQL =
        "insert into "
            + this.getTable()
            + "(nodeid, xcoord, ycoord, floor, building) values (?, ?, ?, ?, ?)";

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
  public HashMap<Integer, Node> getAll() throws SQLException {
    db.setConnection(App.getWhichDB());

    PreparedStatement ps;
    ResultSet rs = null;

    SQL = "select * from " + this.getTable();

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

  public static HashMap<Integer, String> getCRLongName() throws SQLException {

    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL =
        "SELECT Move.nodeID, LocationName.longName\n"
            + "FROM iteration4_presentation.Move\n"
            + "JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "JOIN iteration4_presentation.Node ON Move.nodeID = Node.nodeID\n"
            + "WHERE LocationName.nodeType = 'CONF';";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");

      longNameHash.put(node_id, longname);
    }

    db.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, String> getMandFLLongName() throws SQLException {
    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL =
        "SELECT Move.nodeID, LocationName.longName\n"
            + "           FROM iteration4_presentation.Move\n"
            + "            JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "            WHERE LocationName.nodeType = 'CONF'\n"
            + "                OR LocationName.nodeType = 'DEPT'\n"
            + "                OR LocationName.nodeType = 'INFO'\n"
            + "                OR LocationName.nodeType = 'SERV'\n"
            + "                OR LocationName.nodeType = 'LABS'\n"
            + "                OR LocationName.nodeType = 'RETL';";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");

      longNameHash.put(node_id, longname);
    }

    db.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, String> getL1LongNames() throws SQLException {
    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL =
        "SELECT Move.nodeID, LocationName.longName\n"
            + "             FROM iteration4_presentation.Move\n"
            + "             JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "             JOIN iteration4_presentation.node ON move.nodeid = node.nodeid\n"
            + "             WHERE node.floor = 'L1';";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");

      longNameHash.put(node_id, longname);
    }

    db.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, String> getL2LongNames() throws SQLException {
    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL =
        "SELECT Move.nodeID, LocationName.longName\n"
            + "             FROM iteration4_presentation.Move\n"
            + "             JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "             JOIN iteration4_presentation.node ON move.nodeid = node.nodeid\n"
            + "             WHERE node.floor = 'L2';";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");

      longNameHash.put(node_id, longname);
    }

    db.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, String> getF1LongNames() throws SQLException {
    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL =
        "SELECT Move.nodeID, LocationName.longName\n"
            + "             FROM iteration4_presentation.Move\n"
            + "             JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "             JOIN iteration4_presentation.node ON move.nodeid = node.nodeid\n"
            + "             WHERE node.floor = '1 ';";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");

      longNameHash.put(node_id, longname);
    }

    db.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, String> getShortName(String floor) throws SQLException {
    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL =
        "SELECT Move.nodeID, LocationName.shortname\n"
            + "             FROM iteration4_presentation.Move\n"
            + "             JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "             JOIN iteration4_presentation.node ON move.nodeid = node.nodeid\n"
            + "             WHERE node.floor = ?;";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setString(1, floor);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String shortname = rs.getString("shortname");

      longNameHash.put(node_id, shortname);
    }

    db.closeConnection();

    return longNameHash;
  }

  // passing in a nodeID --> nodeType

  public static HashMap<Integer, String> getAllLongName() throws SQLException {
    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL = "select nodeid, longname from iteration4_presentation.Move;";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");

      longNameHash.put(node_id, longname);
    }

    db.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, String> getSNgivenFloorExceptHall(String floor)
      throws SQLException {
    HashMap<Integer, String> shortNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;

    SQL =
        "SELECT move.nodeid, locationname.nodetype, locationname.shortname\n"
            + "FROM iteration4_presentation.Move INNER JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "                      INNER JOIN iteration4_presentation.node ON node.nodeid = move.nodeid\n"
            + "WHERE nodetype <> 'HALL' AND node.floor = ?;";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setString(1, floor);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String shortname = rs.getString("shortname");

      shortNameHash.put(node_id, shortname);
    }

    db.closeConnection();

    return shortNameHash;
  }

  public int getNodeIDbyLongName(String longname, Date date) throws SQLException {
    db.setConnection(App.getWhichDB());
    PreparedStatement ps;

    ResultSet rs = null;
    SQL = "select nodeid from iteration4_presentation.Move where longname = ?"; // AND where date>=?

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setString(1, longname);
      // ps.setDate(2, date);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
    }

    int node_id = 0;

    while (rs.next()) {

      node_id = rs.getInt("nodeid");
    }

    db.closeConnection();
    return node_id;
  }

  public static HashMap<Integer, String> getLongNames(String floor) throws SQLException {

    HashMap<Integer, String> longNameHash = new HashMap<>();

    db.setConnection(App.getWhichDB());
    SQL =
        "SELECT Move.nodeID, LocationName.longname\n"
            + "             FROM iteration4_presentation.Move\n"
            + "             JOIN iteration4_presentation.LocationName ON Move.longName = LocationName.longName\n"
            + "             JOIN iteration4_presentation.node ON move.nodeid = node.nodeid\n"
            + "             WHERE node.floor = ?;";

    PreparedStatement ps;

    ResultSet rs = null;

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setString(1, floor);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");

      longNameHash.put(node_id, longname);
    }

    db.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, Node> getNodeIDsGivenShortnames(
      ArrayList<String> shortNames, String floorFilter) throws SQLException {

    HashMap<Integer, Node> filteredNodeHash = new HashMap();
    db.setConnection(App.getWhichDB());

    PreparedStatement ps;
    ResultSet rs = null;

    SQL =
        "select node.nodeid, "
            + "node.xcoord, node.ycoord, node.floor, node.building, locationname.shortname "
            + "from iteration4_presentation.move join iteration4_presentation.locationname on move.longname = locationname.longname "
            + "join iteration4_presentation.node on move.nodeid = node.nodeid "
            + "where node.floor = ? and locationname.shortname in ";

    for (int i = 0; i < shortNames.size(); i++) {
      if (i == 0) {
        SQL += ("(");
      }

      if (i != shortNames.size() - 1) {
        SQL += "'" + shortNames.get(i) + "'" + ", ";
      } else {
        SQL += "'" + shortNames.get(i) + "'";
      }

      if (i == (shortNames.size() - 1)) {
        SQL += (")");
      }
    }

    System.out.println(SQL);

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setString(1, floorFilter);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    // Just Debugged it right below this comment is the issue. It should have stopped at 63
    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      int xcoord = rs.getInt("xcoord");
      int ycoord = rs.getInt("ycoord");
      String floor = rs.getString("floor");
      String building = rs.getString("building");

      Node node = new Node(node_id, xcoord, ycoord, floor, building);

      filteredNodeHash.put(node.getNodeID(), node);
    }
    db.closeConnection();

    return filteredNodeHash;
  }
}
