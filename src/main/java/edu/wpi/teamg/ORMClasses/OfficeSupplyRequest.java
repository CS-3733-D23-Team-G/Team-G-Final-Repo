package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class OfficeSupplyRequest extends Request {
  @Getter @Setter private int reqID;
  @Getter @Setter private String officeSupplyType;
  @Getter @Setter private int numSupply;
  @Getter @Setter private String recipient;
  @Getter @Setter private String note;

  public OfficeSupplyRequest() {};

  public OfficeSupplyRequest(
      String officeSupplyType, int numSupply, String recipient, String note) {
    this.officeSupplyType = officeSupplyType;
    this.numSupply = numSupply;
    this.recipient = recipient;
    this.note = note;
  }

  public OfficeSupplyRequest(
      String reqType,
      int empID,
      String location,
      int serveBy,
      StatusTypeEnum status,
      Date requestDate,
      Time requestTime,
      String officeSupplyType,
      int numSupply,
      String recipient,
      String note) {
    super(reqType, empID, location, serveBy, status, requestDate, requestTime);
    this.officeSupplyType = officeSupplyType;
    this.numSupply = numSupply;
    this.recipient = recipient;
    this.note = note;
  }

  public OfficeSupplyRequest(
      int reqID, String officeSupplyType, int numSupply, String recipient, String note) {
    this.officeSupplyType = officeSupplyType;
    this.numSupply = numSupply;
    this.recipient = recipient;
    this.note = note;
    this.reqID = reqID;
  }
}
