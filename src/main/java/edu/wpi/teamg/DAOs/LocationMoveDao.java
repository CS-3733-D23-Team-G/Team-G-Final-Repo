package edu.wpi.teamg.DAOs;

import java.sql.SQLException;

public interface LocationMoveDao<T> extends IMoveDao {
  public void importCSV(String path) throws SQLException;

  public void exportCSV() throws SQLException;
}
