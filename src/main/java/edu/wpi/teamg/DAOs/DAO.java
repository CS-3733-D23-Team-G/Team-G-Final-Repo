package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javafx.stage.FileChooser;

public interface DAO<T, K> {
  DBConnection db = new DBConnection();

  public HashMap<T, T> getAll() throws SQLException;

  public void update(T obj, String colName, K obj2) throws SQLException;

  public void insert(T obj) throws SQLException;

  public void delete(T obj) throws SQLException;

  public default void exportCSV() throws SQLException, IOException {
    db.setConnection();

    Statement statement = null;
    String sql = "select * from " + this.getTable();
    ResultSet rs = null;
    FileWriter writer = null;

    try {
      statement = db.getConnection().createStatement();
      rs = statement.executeQuery(sql);

      FileChooser fc = new FileChooser();
      fc.setTitle("Save CSV Table");
      fc.setInitialFileName(this.getTable() + ".csv");
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

  public String getTable();
}
