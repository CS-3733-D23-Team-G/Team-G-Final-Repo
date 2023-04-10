package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class MealRequest extends Request {

  @Getter @Setter private String recipient;
  @Getter @Setter private String order;
  @Getter @Setter private String note;

  public MealRequest() {}

  public MealRequest(
      String reqtype,
      int empid,
      String location,
      int serve_by,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime,
      String recipient,
      String order,
      String note) {
    super(reqtype, empid, location, serve_by, status, deliveryDate, deliveryTime);
    this.recipient = recipient;
    this.order = order;
    this.note = note;
  }
}
