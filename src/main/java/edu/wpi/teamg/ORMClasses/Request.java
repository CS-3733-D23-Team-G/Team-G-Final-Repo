package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class Request {

  @Getter @Setter private int reqid;
  @Getter @Setter private int empid;
  @Getter @Setter private int location;
  @Getter @Setter private int serv_by;

  @Getter @Setter private StatusTypeEnum status;
  @Getter @Setter private Date deliveryDate;
  @Getter @Setter private Time deliveryTime;

  public Request() {}

  public Request(
      int empid,
      int location,
      int serv_by,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime) {
    this.empid = empid;
    this.location = location;
    this.serv_by = serv_by;
    this.status = status;
    this.deliveryDate = deliveryDate;
    this.deliveryTime = deliveryTime;
  }
}
