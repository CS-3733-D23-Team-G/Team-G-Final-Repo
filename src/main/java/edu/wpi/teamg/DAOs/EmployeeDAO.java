package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class EmployeeDAO implements DAO {

  private static String query;
  private static DBConnection conn = new DBConnection();

  private HashMap<Integer, Employee> employeeHash = new HashMap<Integer, Employee>();

  @Override
  public HashMap getAll() throws SQLException {
    conn.setConnection();
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

      int empID = rs.getInt("empid");
      String firstName = rs.getString("firstname");
      String lastName = rs.getString("lastname");
      String email = rs.getString("email");
      String can_serve = rs.getString("can_serve");

      Employee employee = new Employee(empID, firstName, lastName, email, can_serve);

      employeeHash.put(employee.getEmpID(), employee);
    }
    conn.closeConnection();
    return employeeHash;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    Employee employee = (Employee) obj;
    conn.setConnection();
    query =
        "INSERT INTO "
            + this.getTable()
            + "(empid, firstname, lastname, email, can_serve) VALUES (?,?,?,?,?)";

    PreparedStatement ps;
    try {
      ps = conn.getConnection().prepareStatement(query);
      ps.setInt(1, employee.getEmpID());
      ps.setString(2, employee.getFirstName());
      ps.setString(3, employee.getLastName());
      ps.setString(4, employee.getEmail());
      ps.setString(5, employee.getCan_serve());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("SQL exception");
      e.printStackTrace();
    }

    conn.closeConnection();
  }

  @Override
  public void delete(Object obj) throws SQLException {
    Employee employee = (Employee) obj;
    conn.setConnection();
    PreparedStatement ps = conn.getConnection().prepareStatement(query);

    query = "DELETE FROM " + this.getTable() + " WHERE empID = ?";

    try {
      ps.setInt(1, employee.getEmpID());
    } catch (SQLException e) {
      System.err.println("SQL Exception");
    }
    conn.closeConnection();
  }

  @Override
  public String getTable() {
    return "teamgdb.iteration3.employee";
  }

  public static HashMap<Integer, String> getEmployeeFullName(String canServe) throws SQLException {

    HashMap<Integer, String> longNameHash = new HashMap<>();

    conn.setConnection();
    PreparedStatement ps;

    ResultSet rs = null;

    query =
        "SELECT empid, firstname, lastname\n"
            + "FROM iteration3.employee\n"
            + "WHERE can_serve = ?;";

    try {
      ps = conn.getConnection().prepareStatement(query);
      ps.setString(1, canServe);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int empid = rs.getInt("empid");
      String fullname = rs.getString("firstname") + " " + rs.getString("lastname");

      longNameHash.put(empid, fullname);
    }

    conn.closeConnection();

    return longNameHash;
  }

  public static HashMap<Integer, String> getAllEmployeeFullName() throws SQLException {

    HashMap<Integer, String> longNameHash = new HashMap<>();

    conn.setConnection();
    PreparedStatement ps;

    ResultSet rs = null;

    query = "SELECT empid, firstname, lastname\n" + "FROM iteration3.employee;";

    try {
      ps = conn.getConnection().prepareStatement(query);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int empid = rs.getInt("empid");
      String fullname = rs.getString("firstname") + " " + rs.getString("lastname");

      longNameHash.put(empid, fullname);
    }

    conn.closeConnection();

    return longNameHash;
  }
}
