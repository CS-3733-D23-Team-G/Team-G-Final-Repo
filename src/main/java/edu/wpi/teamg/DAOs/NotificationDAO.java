package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Node;
import edu.wpi.teamg.ORMClasses.Notification;
import java.sql.*;
import java.util.HashMap;

public class NotificationDAO implements DAO {

  private static HashMap<Integer, Node> notifHash = new HashMap<Integer, Node>();
  private static DBConnection db = new DBConnection();
  private static String SQL;

  @Override
  public HashMap getAll() throws SQLException {
    return null;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {
    db.setConnection();

    PreparedStatement ps;
    SQL =
        "insert into "
            + this.getTable()
            + "(alertid, notifdate, notiftime, notiftype, empid, recipients, message) values (?, ?, ?, ?, ?, ?, ?)";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setInt(1, ((Notification) obj).getAlertID());
      ps.setDate(2, ((Notification) obj).getNotifDate());
      ps.setTime(3, ((Notification) obj).getNotifTime());
      ps.setString(4, ((Notification) obj).getNotiftype());
      ps.setInt(5, ((Notification) obj).getEmpid());
      ps.setString(6, ((Notification) obj).getRecipients());
      ps.setString(7, ((Notification) obj).getMessage());

      ps.executeUpdate();
      notifHash.put(((Notification) obj).getAlertID(), (Node) obj);

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    db.closeConnection();
  }

  public static HashMap getAllNotificationOf(int recipient) throws SQLException {
    db.setConnection();

    HashMap<Integer, Notification> filteredNotifHash = new HashMap<>();

    PreparedStatement ps;
    ResultSet rs = null;

    SQL = "select * from iteration3.notification where recipients like '%" + recipient + "%'";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL exception");
      // printSQLException(e);
    }

    while (rs.next()) {

      int alertID = rs.getInt("alertid");
      int empid = rs.getInt("empid");
      String message = rs.getString("message");
      String notiftype = rs.getString("notiftype");

      String recipients = rs.getString("recipients");
      Date notifDate = rs.getDate("notifdate");
      Time notifTime = rs.getTime("notiftime");

      Notification notif =
          new Notification(alertID, empid, message, notiftype, recipients, notifDate, notifTime);

      filteredNotifHash.put(alertID, notif);
    }

    db.closeConnection();

    return filteredNotifHash;
  }

  @Override
  public void delete(Object obj) throws SQLException {}

  @Override
  public void importCSV(String path) throws SQLException {}

  @Override
  public String getTable() {
    return "teamgdb.iteration3.notification";
  }
}
