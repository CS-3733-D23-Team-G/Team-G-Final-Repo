package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class FurnitureRequest extends Request {
  @Getter @Setter private String furnitureType;
  @Getter @Setter private String note;
  @Getter @Setter private String recipient;

  public FurnitureRequest() {}

  public FurnitureRequest(
      String reqtype,
      int empid,
      String location,
      int serveBy,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime,
      String recipient,
      String furnitureType,
      String note) {
    super(reqtype, empid, location, serveBy, status, deliveryDate, deliveryTime);
    this.furnitureType = furnitureType;
    this.note = note;
    this.recipient = recipient;
  }
}
