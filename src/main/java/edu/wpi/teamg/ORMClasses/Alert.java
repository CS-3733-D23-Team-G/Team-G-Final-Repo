package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class Alert {
  @Getter @Setter private int alertID;
  @Getter @Setter private int empid;
  @Getter @Setter private String alertMessage;
  @Getter @Setter private String recipients;
  @Getter @Setter private Date alertDate;
  @Getter @Setter private Time alertTime;

  public Alert() {}

  public Alert(
      int alertID,
      int empid,
      String alertMessage,
      String recipients,
      Date alertDate,
      Time alertTime) {
    this.alertID = alertID; // TODO figure this out
    this.empid = empid; // TODO figure this out
    this.alertMessage = alertMessage;
    this.recipients = recipients;
    this.alertDate = alertDate;
    this.alertTime = alertTime;
  }
}
