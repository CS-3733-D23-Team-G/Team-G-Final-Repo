package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class Signage {

  @Getter @Setter int kioskNum;

  @Getter @Setter String location;
  @Getter @Setter Date specdate;
  @Getter @Setter String arrow;
  @Getter @Setter String month;
  @Getter @Setter boolean specified;

  public Signage() {}

  public Signage(String location, Date date, String arrow, String month, boolean specified) {
    // this.kioskNum = kioskNum;
    this.location = location;
    this.specdate = date;
    this.arrow = arrow;
    this.month = month;
    this.specified = specified;
  }
}
