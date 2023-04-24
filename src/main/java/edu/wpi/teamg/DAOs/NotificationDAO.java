package edu.wpi.teamg.DAOs;

import java.sql.SQLException;
import java.util.HashMap;

public class NotificationDAO implements DAO {

  @Override
  public HashMap getAll() throws SQLException {
    return null;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {}

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public void importCSV(String path) throws SQLException {}

  @Override
  public String getTable() {
    return null;
  }
}
