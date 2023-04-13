package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class Request {

  @Getter @Setter private int reqid;
  @Getter @Setter private String reqtype;
  @Getter @Setter private int empid;
  @Getter @Setter private String location;
  @Getter @Setter private int serveBy;
  @Getter @Setter private String serve_By;

  @Getter @Setter private StatusTypeEnum status;
  @Getter @Setter private Date requestDate;
  @Getter @Setter private Time requestTime;

  public Request() {}

  public Request(
      String reqtype,
      int empid,
      String location,
      int serveBy,
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

  private Request(
      int reqid,
      int empid,
      String location,
      StatusTypeEnum status,
      String serve_By,
      Date date,
      Time time) {
    this.reqid = reqid;
    this.empid = empid;
    this.location = location;
    this.serve_By = serve_By;
    this.status = status;
    this.requestDate = date;
    this.requestTime = time;
  }
}
