package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class FlowerRequest extends Request {
  @Getter @Setter private int reqId;
  @Getter @Setter private String flowerType;
  @Getter @Setter private int numFlower;
  @Getter @Setter private String note;
  @Getter @Setter private String recipient;

  public FlowerRequest() {};

  public FlowerRequest(String flowerType, int numFlower, String note, String recipient) {
    this.flowerType = flowerType;
    this.numFlower = numFlower;
    this.note = note;
    this.recipient = recipient;
  }

  public FlowerRequest(
      String reqtype,
      int empid,
      String location,
      int serveBy,
      StatusTypeEnum status,
      Date requestdate,
      Time requesttime,
      String flowerType,
      int numFlower,
      String note,
      String recipient) {
    super(reqtype, empid, location, serveBy, status, requestdate, requesttime);
    this.flowerType = flowerType;
    this.numFlower = numFlower;
    this.note = note;
    this.recipient = recipient;
  }

  public FlowerRequest(int reqId, String flowerType, int numFlower, String recipient, String note) {
    this.flowerType = flowerType;
    this.numFlower = numFlower;
    this.note = note;
    this.recipient = recipient;
    this.reqId = reqId;
  }
}
