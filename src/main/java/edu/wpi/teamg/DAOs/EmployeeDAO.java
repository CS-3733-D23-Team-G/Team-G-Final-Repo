package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDAO implements DAO {

  private String query;
  private DBConnection conn = new DBConnection();

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
      ps.setString(2, employee.getLastName());
      ps.setString(1, employee.getEmail());
      ps.setString(1, employee.getCan_serve());
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

    return "teamgdb.iteration2.employee";
  }

  public HashMap getEmplyeeFullName(String can_serve) {
    try {
      getAll();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }
    HashMap<Integer, String> temp = new HashMap<>();
    for (Map.Entry<Integer, Employee> holder : employeeHash.entrySet()) {
      if (holder.getValue().getCan_serve().equals(can_serve)) {
        temp.put(
            holder.getValue().getEmpID(),
            holder.getValue().getFirstName() + " " + holder.getValue().getLastName());
      }
    }

    return temp;
  }
}
