package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface LocationDAO<T> extends DAO {
DBConnection db = new DBConnection();
  public default void importCSV() throws SQLException{
    db.setConnection();
    JFileChooser chooser = new JFileChooser();
    int choice = chooser.showOpenDialog(null);
    if(choice==chooser.APPROVE_OPTION){
      try{
        String absPath = chooser.getSelectedFile().getAbsolutePath();
        String sql = "copy "+this.getTable()+" from ? delimiter ',' csv header";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setString(1,absPath);
        ps.executeUpdate();

      }catch (SQLException e){
        System.err.println("SQL Exception");
        e.printStackTrace();
      }
    }
    db.closeConnection();
  }

  public void exportCSV() throws SQLException;
}
