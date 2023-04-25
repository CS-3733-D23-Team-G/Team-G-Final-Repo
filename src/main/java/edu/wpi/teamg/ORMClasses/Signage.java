package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class Signage {
  @Getter @Setter int kioskNum;
  @Getter @Setter Date specdate;
  @Getter @Setter String arrow;
  @Getter @Setter String month;
  @Getter @Setter boolean specified;

  public Signage() {}

  public Signage(int kioskNum, Date date, String arrow, String month, boolean specified) {
    this.kioskNum = kioskNum;
    this.specdate = date;
    this.arrow = arrow;
    this.month = month;
    this.specified = specified;
  }
}
