package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class Request {

  @Getter @Setter private int reqid;
  @Getter @Setter private String reqtype;

  @Getter @Setter private String empid;
  @Getter @Setter private String location;
  @Getter @Setter private String serveBy;
  @Getter @Setter private StatusTypeEnum status;
  @Getter @Setter private Date requestDate;
  @Getter @Setter private Time requestTime;

  public Request() {}

  public Request(
      String reqtype,
      String empid,
      String location,
      String serveBy,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime) {
    this.reqtype = reqtype;
    this.empid = empid;
    this.location = location;
    this.serveBy = serveBy;
    this.status = status;
    this.requestDate = deliveryDate;
    this.requestTime = deliveryTime;
  }
}
