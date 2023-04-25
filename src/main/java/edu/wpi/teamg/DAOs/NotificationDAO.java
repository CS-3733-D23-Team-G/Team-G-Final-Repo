package edu.wpi.teamg.DAOs;

import edu.wpi.teamg.DBConnection;
import edu.wpi.teamg.ORMClasses.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationDAO implements DAO {

  private static HashMap<Integer, Notification> notifHash = new HashMap<Integer, Notification>();
  private static DBConnection db = new DBConnection();
  private static String SQL;
  private static String SQL_AlertOnly;

  private String SQL_maxID;

  @Override
  public HashMap getAll() throws SQLException {
    return null;
  }

  @Override
  public void update(Object obj, String colName, Object obj2) throws SQLException {}

  @Override
  public void insert(Object obj) throws SQLException {

    db.setConnection();

    PreparedStatement ps_getMaxID;

    ResultSet rs = null;

    SQL_maxID = "select alertid from teamgdb.iteration3.notification order by alertid desc limit 1";

    try {
      ps_getMaxID = db.getConnection().prepareStatement(SQL_maxID);
      rs = ps_getMaxID.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    int maxID = 0;
    while (rs.next()) {
      maxID = rs.getInt("alertid");
      maxID++;
    }

    PreparedStatement ps;
    SQL =
        "insert into "
            + this.getTable()
            + "(alertid, notifdate, notiftime, notiftype, empid, recipients, message) values (?, ?, ?, ?, ?, ?, ?)";

    try {
      ps = db.getConnection().prepareStatement(SQL);
      ps.setInt(1, maxID);
      ps.setDate(2, ((Notification) obj).getNotifDate());
      ps.setTime(3, ((Notification) obj).getNotifTime());
      ps.setString(4, ((Notification) obj).getNotiftype());
      ps.setInt(5, ((Notification) obj).getEmpid());
      ps.setString(6, ((Notification) obj).getRecipients());
      ps.setString(7, ((Notification) obj).getMessage());

      ps.executeUpdate();
      notifHash.put(((Notification) obj).getAlertID(), (Notification) obj);

    } catch (SQLException e) {
      System.err.println("SQL exception");
      e.printStackTrace();
      // printSQLException(e);
    }

    db.closeConnection();
  }

  public static ArrayList<Notification> getAllNotificationOf(int recipient) throws SQLException {

    db.setConnection();

    ArrayList<Notification> filteredNotifList = new ArrayList<>();

    PreparedStatement ps;
    ResultSet rs = null;

    SQL =
        "select * from iteration3.notification where notiftype <> 'Alert' and recipients like '%"
            + recipient
            + "%' order by alertid desc";

    SQL_AlertOnly =
        "select * from iteration3.notification where notiftype = 'Alert' and recipients like '%"
            + recipient
            + "%' order by alertid desc";

    try {
      ps = db.getConnection().prepareStatement(SQL_AlertOnly);
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
          new Notification(empid, message, notiftype, recipients, notifDate, notifTime);

      notif.setAlertID(alertID);

      filteredNotifList.add(notif);
    }

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
          new Notification(empid, message, notiftype, recipients, notifDate, notifTime);

      notif.setAlertID(alertID);

      filteredNotifList.add(notif);
    }

    db.closeConnection();

    return filteredNotifList;
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