package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Request {

  @Getter @Setter private int reqid;
  @Getter @Setter private int empid;
  @Getter @Setter private int location;
  @Getter @Setter private int serv_by;

  @Getter @Setter private StatusTypeEnum status;

  public Request() {}

  public Request(int empid, int location, int serv_by, StatusTypeEnum status) {
    this.empid = empid;
    this.location = location;
    this.serv_by = serv_by;
    this.status = status;
  }
}
