package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class Notification {
  @Getter @Setter private int alertID;
  @Getter @Setter private int empid;
  @Getter @Setter private String message;
  @Getter @Setter private String recipients;
  @Getter @Setter private Date notifDate;
  @Getter @Setter private Time notifTime;

  public Notification() {}

  public Notification(
      int alertID, int empid, String message, String recipients, Date notifDate, Time notifTime) {
    this.alertID = alertID; // TODO figure this out
    this.empid = empid; // TODO figure this out
    this.message = message;
    this.recipients = recipients;
    this.notifDate = notifDate;
    this.notifTime = notifTime;
  }
}
