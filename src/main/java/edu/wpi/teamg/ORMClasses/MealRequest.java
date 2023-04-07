package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class MealRequest extends Request {

  @Getter @Setter private Date deliveryDate;
  @Getter @Setter private Time deliveryTime;
  @Getter @Setter private String recipient;
  @Getter @Setter private String order;
  @Getter @Setter private String note;

  public MealRequest(
      int empid,
      int location,
      int serve_by,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime,
      String recipient,
      String order,
      String note) {
    super(empid, location, serve_by, status);
    this.deliveryDate = deliveryDate;
    this.deliveryTime = deliveryTime;
    this.recipient = recipient;
    this.order = order;
    this.note = note;
  }
}

// AL<Request>
// AL<MealRequest>
// AL<Request> = AL<MealRequest> + AL<FlowerRequest>
