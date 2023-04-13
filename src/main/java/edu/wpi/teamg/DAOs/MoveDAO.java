package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Move;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javax.swing.*;

public class MoveDAO implements LocationMoveDao {
  static DBConnection db = new DBConnection();
  private String sql;

  @Override
  public List getAll() throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    sql = "select * from teamgdb.iteration1.move";

    try {
      ps = db.getConnection().prepareStatement(sql);
      rs = ps.executeQuery();

    } catch (SQLException e) {
      System.err.println("SQL exception");
    }

    List<Move> moves = new ArrayList<Move>();

    while (rs.next()) {

      int node_id = rs.getInt("nodeid");
      String longname = rs.getString("longname");
      Date date = rs.getDate("date");

      Move move = new Move(node_id, longname, date);

      moves.add(move);
    }
    db.closeConnection();
    return moves;
  }

  @Override
  public void update(Object obj, String colName, Object value) {
    db.setConnection();
    PreparedStatement ps;
    sql =
        "update teamgdb.iteration1.move set "
            + colName
            + " = ? where nodeID=? AND longname=? AND Date=?";
    try {
      ps = db.getConnection().prepareStatement(sql);
      switch (colName) {
          /*
          case "nodeid":
            ps.setInt(1,((Move)obj).getNodeID());
            break;
          case "longname":
            ps.setString(1,((Move)obj).getLongName());
            break;*/
        case "date":
          ps.setDate(1, ((Move) obj).getDate());
          break;
      }
      ps.setInt(2, ((Move) obj).getNodeID());
      ps.setString(3, ((Move) obj).getLongName());
      ps.setDate(3, ((Move) obj).getDate());
      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      System.err.println("SQL exception");
    }
    db.closeConnection();
  }

  @Override
  public void insert(Object obj) throws SQLException {
    Move move = (Move) obj;
    db.setConnection();

    sql = "INSERT INTO teamgdb.iteration1.move (nodeid, longname, date) VALUES (?,?,?);";

    PreparedStatement ps = db.getConnection().prepareStatement(sql);

    try {
      ps.setInt(1, move.getNodeID());
      ps.setString(2, move.getLongName());
      ps.setDate(3, move.getDate());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    db.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    Move move = (Move) obj;
    db.setConnection();
    PreparedStatement ps = db.getConnection().prepareStatement(sql);

    sql = "DELETE FROM teamgdb.iteration1.move WHERE nodeID = ?";

    try {
      ps.setInt(1, move.getNodeID());
    } catch (SQLException e) {
      System.err.println("SQL Exception");
    }
    db.closeConnection();
  }

  public Date stringToDate(String input) {
    String[] data = input.split("/");
    Date returnDate =
        new Date(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
    return returnDate;
  }

  @Override
  public void importCSV() throws SQLException {}

  @Override
  public void exportCSV() throws SQLException, IOException {
    db.setConnection();

    Statement statement = null;
    String sql = "select * from teamgdb.iteration1.move";
    ResultSet rs = null;
    FileWriter writer = null;

    try {
      statement = db.getConnection().createStatement();
      rs = statement.executeQuery(sql);

      FileChooser fc = new FileChooser();
      fc.setTitle("Save CSV Table");
      fc.setInitialFileName("move.csv");
      File file = fc.showSaveDialog(null);

      if (file != null) {
        writer = new FileWriter(file);

        int colCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= colCount; i++) {
          String colLabel = rs.getMetaData().getColumnLabel(i);
          writer.append(colLabel);
          if (i < colCount) writer.append(",");
        }
        writer.append("\n");

        while (rs.next()) {
          for (int i = 1; i <= colCount; i++) {
            String val = rs.getString(i);
            writer.append(val);
            if (i < colCount) writer.append(",");
          }
          writer.append("\n");
        }
      }

    } catch (SQLException | IOException e) {
      System.err.println("SQL Export error occurred");
      e.printStackTrace();
    }

    rs.close();
    statement.close();
    writer.close();
    db.closeConnection();
  }

  @Override
  public void importCSV(String filePath) throws SQLException {
    db.setConnection();
    sql = "insert into teamgdb.iteration1.move (nodeid, longname, date) values (?,?,?)";
    PreparedStatement ps = db.getConnection().prepareStatement(sql);
    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line = null;

      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");

        String nodeID = data[0];
        String longname = data[1];
        String dateString = data[2];

        int inodeid = Integer.parseInt(nodeID);
        ps.setInt(1, inodeid);

        ps.setString(2, longname);

        ps.setDate(3, Date.valueOf(dateString));

        ps.addBatch();
      }
      br.close();
      ps.executeBatch();

    } catch (FileNotFoundException e) {
      System.err.println("File not found");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Input output exception");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
    }
    db.closeConnection();
  }
}
