package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AccountDAO implements DAO {

  private String query;
  private DBConnection conn = new DBConnection();
  private HashMap<String, Account> accountHash = new HashMap<String, Account>();

  @Override
  public HashMap getAll() throws SQLException {
    conn.setConnection(App.getWhichDB());
    PreparedStatement ps;
    ResultSet rs = null;

    query = "Select * from " + this.getTable();

    try {
      ps = conn.getConnection().prepareStatement(query);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL error exception");
    }

    while (rs.next()) {

      String userName = rs.getString("username");
      int empID = rs.getInt("empid");
      String hashPassword = rs.getString("hashPassword");
      byte[] salt = rs.getBytes("salt");
      boolean is_admin = rs.getBoolean("is_admin");

      Account account = new Account(userName, hashPassword, is_admin);
      accountHash.put(account.getUsername(), account);
    }
    conn.closeConnection();
    return accountHash;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {};

  public void insertAccount(Object obj, String pass, Boolean admin) throws SQLException {
    Account account = (Account) obj;
    conn.setConnection(App.getWhichDB());

    query =
        "INSERT INTO "
            + this.getTable()
            + "(username,empid,hashPassword,salt,is_admin) VALUES (?,?,?,?,?)";

    PreparedStatement ps;
    try {
      ps = conn.getConnection().prepareStatement(query);
      ps.setString(1, account.getUsername());
      ps.setInt(2, account.getEmpID());
      account.setPassword(pass);
      byte[] salt = account.getSalt();
      ps.setString(3, account.getHashedPassword(salt));
      ps.setBytes(4, salt);
      account.setAdmin(admin);
      ps.setBoolean(5, account.getAdmin());
      ps.executeUpdate();

    } catch (SQLException e) {
      System.out.println("SQL exception");
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    accountHash.put(account.getUsername(), account);
    conn.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public void importCSV(String path) throws SQLException {}

  public void deleteAccount(Object obj) throws SQLException {

    Account account = (Account) obj;
    conn.setConnection(App.getWhichDB());
    query = "DELETE FROM " + this.getTable() + " WHERE username = ?";
    PreparedStatement ps = conn.getConnection().prepareStatement(query);

    try {
      ps.setString(1, account.getUsername());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
    }
    conn.closeConnection();
    accountHash.remove(account.getUsername());
  }

  @Override
  public String getTable() {

    return "iteration4_presentation.account";
  }
}
