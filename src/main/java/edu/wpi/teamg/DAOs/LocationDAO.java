package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public interface LocationDAO<T> extends DAO {
  DBConnection db = new DBConnection();

  public default void importCSV() throws SQLException, IOException {
    db.setConnection();

    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
    int choice = chooser.showOpenDialog(null);

    if (choice == JFileChooser.APPROVE_OPTION) {
      File chosenFile = chooser.getSelectedFile();

      BufferedReader br = new BufferedReader(new FileReader(chosenFile));
      String line = br.readLine();
      String[] colNames = line.split(",");
      int numCol = colNames.length;

      String[] colTypes = new String[numCol];
      ResultSetMetaData rsmd =
          db.getConnection()
              .createStatement()
              .executeQuery("select * from " + this.getTable() + " limit 0")
              .getMetaData();

      StringBuilder insertStatement = new StringBuilder("insert into " + this.getTable() + " (");
      for (int j = 0; j < numCol; j++) {
        insertStatement.append(colNames[j]);
        if (j < numCol - 1) insertStatement.append(",");
      }
      insertStatement.append(") values (");
      for (int i = 0; i < numCol; i++) {
        insertStatement.append("?");
        if (i < numCol - 1) {
          insertStatement.append(", ");
        }
      }
      insertStatement.append(")");

      PreparedStatement ps = db.getConnection().prepareStatement(insertStatement.toString());

      while ((line = br.readLine()) != null) {
        String[] fields = line.split(",");
        for (int k = 0; k < numCol; k++) {
          switch (colTypes[k]) {
            case "int4":
              ps.setInt(k + 1, Integer.parseInt(fields[k]));
              break;
            case "float4":
              ps.setFloat(k + 1, Float.parseFloat(fields[k]));
              break;
            case "float8":
              ps.setDouble(k + 1, Double.parseDouble(fields[k]));
              break;
            case "bool":
              ps.setBoolean(k + 1, Boolean.parseBoolean(fields[k]));
              break;
            default:
              ps.setString(k + 1, fields[k]);
              break;
          }
        }
        ps.executeUpdate();
      }
      ps.close();
      br.close();
    }

    db.closeConnection();
  }
}
