package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AccountDAO implements DAO {

  private String query;
  private DBConnection conn = new DBConnection();
  private HashMap<Integer, Account> accountHash = new HashMap<Integer, Account>();

  @Override
  public HashMap getAll() throws SQLException {
    conn.setConnection();
    PreparedStatement ps;
    ResultSet rs = null;
    query = "Select * from teamgdb.iteration2.account";

    try {
      ps = conn.getConnection().prepareStatement(query);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL error exception");
    }

    while (rs.next()) {

      int empid = rs.getInt("empid");
      String password = rs.getString("firstname");
      boolean is_admin = rs.getBoolean("is_admin");

      Account account = new Account(empid, password, is_admin);
      accountHash.put(account.getEmpid(), account);
    }
    conn.closeConnection();
    return accountHash;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    Account account = (Account) obj;
    conn.setConnection();
    query = "INSERT INTO teamgdb.iteration2.account(empid,password,is_admin) VALUES (?,?,?)";
    PreparedStatement ps;
    try {
      ps = conn.getConnection().prepareStatement(query);
      ps.setInt(1, account.getEmpID());
      ps.setString(2, account.getPassword());
      ps.setString(3, account.getCan_serve());
    } catch (SQLException e) {
      System.out.println("SQL exception");
      e.printStackTrace();
    }

    conn.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {

    Account account = (Account) obj;
    conn.setConnection();
    PreparedStatement ps = conn.getConnection().prepareStatement(query);

    query = "DELETE FROM teamgdb.iteration2.account WHERE empID = ?";

    try {
      ps.setInt(1, account.getEmpID());
    } catch (SQLException e) {
      System.err.println("SQL Exception");
    }
    conn.closeConnection();
  }

  @Override
  public String getTable() {
    return "teamgdb.iteration2.account";
  }
}
