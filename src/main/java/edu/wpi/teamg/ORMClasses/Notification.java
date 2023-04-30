package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class Notification {
  @Getter @Setter private int alertID;
  @Getter @Setter private int empid;
  @Getter @Setter private String notifheader;
  @Getter @Setter private String message;
  @Getter @Setter private String notiftype;

  @Getter @Setter private Boolean dismissible;

  @Getter @Setter private String recipients;
  @Getter @Setter private Date notifDate;
  @Getter @Setter private Time notifTime;

  public Notification() {}

  public Notification(
      int empid,
      String header,
      String message,
      String notiftype,
      String recipients,
      Date notifDate,
      Time notifTime,
      Boolean dismissible) {
    this.notifheader = header;
    this.notiftype = notiftype;
    this.empid = empid; // TODO figure this out
    this.message = message;
    this.recipients = recipients;
    this.notifDate = notifDate;
    this.notifTime = notifTime;
    this.dismissible = dismissible;
  }
}
