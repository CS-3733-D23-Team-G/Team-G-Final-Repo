package edu.wpi.teamg;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class DBConnection {
  static Connection connection;

  public void setConnection() {
    try {
      Class.forName("org.postgresql.Driver");
      connection =
          DriverManager.getConnection(
              getDBCreds().get(0), getDBCreds().get(1), getDBCreds().get(2));
    } catch (SQLException e) {
      System.err.println("SQL Exception");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Connection is Set");
  }

  private List<String> getDBCreds() {
    List<String> creds = new LinkedList<>();
    InputStream is =
        this.getClass().getClassLoader().getResourceAsStream("edu/wpi/teamg/creds.yml");
    Yaml yaml = new Yaml();
    Map<String, Object> data = yaml.load(is);
    creds.add(data.get("url").toString());
    creds.add(data.get("username").toString());
    creds.add(data.get("password").toString());
    creds.add(data.get("awsurl").toString());
    creds.add(data.get("awsusername").toString());
    creds.add(data.get("awspassword").toString());
    return creds;
  }

  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Connection is closed");
  }

  public static Connection getConnection() {
    return connection;
  }
}
