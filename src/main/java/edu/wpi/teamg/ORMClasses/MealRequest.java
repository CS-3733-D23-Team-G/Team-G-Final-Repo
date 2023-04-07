package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class MealRequest extends Request {

  @Getter @Setter private Date deliveryDate;
  @Getter @Setter private Time deliveryTime;

  @Getter @Setter private String recipient;
  @Getter @Setter private String order;
  @Getter @Setter private String note;

  public MealRequest() {}
}

// AL<Request>
// AL<MealRequest>
// AL<Request> = AL<MealRequest> + AL<FlowerRequest>
