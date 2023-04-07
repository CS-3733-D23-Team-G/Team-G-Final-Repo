package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Request {

  @Getter @Setter private int reqid;
  @Getter @Setter private int empid;
  @Getter @Setter private int location;
  @Getter @Setter private int serv_by;

  @Getter @Setter private StatusTypeEnum status;
}
