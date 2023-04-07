package edu.wpi.teamg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
              "jdbc:postgresql://database.cs.wpi.edu:5432/teamgdb", "teamg", "teamg70");
    } catch (SQLException e) {
      System.err.println("SQL Exception");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private List<String> getDBCreds() {
    List<String> creds = new LinkedList<>();
    try {
      InputStream is = new FileInputStream("src/main/resources/edu/wpi/teamg/creds.yml");
      Yaml yaml = new Yaml();
      Map<String, Object> data = yaml.load(is);
      creds.add(data.get("url").toString());
      creds.add(data.get("username").toString());
      creds.add(data.get("password").toString());

      return creds;
    } catch (FileNotFoundException e) {
      return null;
    }
  }

  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection getConnection() {
    return connection;
  }
}
