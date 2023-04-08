package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class FlowerRequest extends Request {
  @Getter @Setter private String flowerType;
  @Getter @Setter private int numFlower;
  @Getter @Setter private String note;
  @Getter @Setter private String recipient;

  public FlowerRequest(String flowerType, int numFlower, String note, String recipient) {
    this.flowerType = flowerType;
    this.numFlower = numFlower;
    this.note = note;
    this.recipient = recipient;
  }

  public FlowerRequest(
      int empid,
      int location,
      int serv_by,
      StatusTypeEnum status,
      Date deliveryDate,
      Time deliveryTime,
      String flowerType,
      int numFlower,
      String note,
      String recipient) {
    super(empid, location, serv_by, status, deliveryDate, deliveryTime);
    this.flowerType = flowerType;
    this.numFlower = numFlower;
    this.note = note;
    this.recipient = recipient;
  }
}
