package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class MealRequest extends Request {

  @Getter @Setter private int reqId;
  @Getter @Setter private String recipient;
  @Getter @Setter private String order;
  @Getter @Setter private String note;

  public MealRequest() {}

  public MealRequest(
      String reqtype,
      String empid,
      String location,
      String serveBy,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime,
      String recipient,
      String order,
      String note) {
    super(reqtype, empid, location, serveBy, status, deliveryDate, deliveryTime);
    this.recipient = recipient;
    this.order = order;
    this.note = note;
  }

  public MealRequest(int reqId, String recipient, String order, String note) {
    this.reqId = reqId;
    this.recipient = recipient;
    this.order = order;
    this.note = note;
  }
}
