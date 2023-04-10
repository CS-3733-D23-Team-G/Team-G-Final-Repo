package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Login;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LoginDao implements DAO {

  HashMap<String, Login> logins = new HashMap<String, Login>();
  DBConnection conn = new DBConnection();
  private String query;

  @Override
  public HashMap getAll() throws SQLException {
    conn.setConnection();

    PreparedStatement ps;
    ResultSet rs = null;

    query = "select * from iteration1.login";

    try {
      ps = conn.getConnection().prepareStatement(query);
      rs = ps.executeQuery();

    } catch (SQLException e) {
      System.err.println("SQL exception");
    }

    while (rs.next()) {

      String email = rs.getString("email");
      String password = rs.getString("password");

      Login login = new Login(email, password);

      logins.put(login.getEmail(), login);
      conn.closeConnection();
      return logins;
    }
    conn.closeConnection();
    return logins;
  }

  @Override
  public void update(Object obj, Object update) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    conn.setConnection();

    PreparedStatement ps;
    query = "insert into iteration1.login(email, password) values (?, ?)";

    try {
      ps = conn.getConnection().prepareStatement(query);
      ps.setString(1, ((Login) obj).getEmail());
      ps.setString(2, ((Login) obj).getPassword());
      ps.executeUpdate();
      logins.put(((Login) obj).getEmail(), (Login) obj);

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }
    conn.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    conn.setConnection();

    PreparedStatement ps;

    query = "delete from iteration1.login where email = ?";

    try {
      ps = conn.getConnection().prepareStatement(query);
      ps.setString(1, ((Login) obj).getEmail());
      ps.executeUpdate();
      logins.remove(((Login) obj).getEmail());

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    conn.closeConnection();
  }

  @Override
  public String getTable() {
    return null;
  }
}
