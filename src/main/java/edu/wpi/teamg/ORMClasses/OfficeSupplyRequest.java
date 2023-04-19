package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class OfficeSupplyRequest extends Request {
  @Getter @Setter private String supplyType;
  @Getter @Setter private String note;
  @Getter @Setter private String recipient;

  public OfficeSupplyRequest() {}

  public OfficeSupplyRequest(
      String reqtype,
      String empid,
      String loc,
      String servby,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime,
      String recipient,
      String supplyType,
      String note) {
    super(reqtype, empid, loc, servby, status, deliveryDate, deliveryTime);
    this.supplyType = supplyType;
    this.recipient = recipient;
    this.note = note;
  }
}
