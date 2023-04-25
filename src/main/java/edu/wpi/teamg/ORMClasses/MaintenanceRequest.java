package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class MaintenanceRequest extends Request {

  @Getter @Setter private int reqId;
  @Getter @Setter private String recipient;
  @Getter @Setter private String phoneNumber;

  public MaintenanceRequest() {}

  public MaintenanceRequest(
      String reqtype,
      String empid,
      String location,
      String serveBy,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime,
      String recipient,
      String phoneNumber) {
    super(reqtype, empid, location, serveBy, status, deliveryDate, deliveryTime);
    this.recipient = recipient;
    this.phoneNumber = phoneNumber;
  }

  public MaintenanceRequest(int reqId, String recipient, String phoneNumber) {
    this.reqId = reqId;
    this.recipient = recipient;
    this.phoneNumber = phoneNumber;
  }
}
