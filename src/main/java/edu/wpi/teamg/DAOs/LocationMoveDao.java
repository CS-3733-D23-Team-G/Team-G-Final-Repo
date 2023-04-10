package edu.wpi.teamg.DAOs;

import java.sql.SQLException;

public interface LocationMoveDao<T> extends IMoveDao {
  public void importCSV() throws SQLException;

  public void exportCSV() throws SQLException;

  void importCSV(String filePath) throws SQLException;
}
