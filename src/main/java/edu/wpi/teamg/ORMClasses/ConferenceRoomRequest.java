package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class ConferenceRoomRequest extends Request {
  @Getter @Setter private int reqid;
  @Getter @Setter private String purpose;
  @Getter @Setter private Time endtime;

  public ConferenceRoomRequest() {};

  public ConferenceRoomRequest(
      String reqtype,
      int empid,
      String location,
      int serveBy,
      StatusTypeEnum status,
      Date meetingDate,
      Time meetingTime,
      Time endtime,
      String purpose) {
    super(reqtype, empid, location, serveBy, status, meetingDate, meetingTime);
    this.endtime = endtime;
    this.purpose = purpose;
  }

  public ConferenceRoomRequest(int reqid, Time endtime, String purpose) {
    this.reqid = reqid;
    this.endtime = endtime;
    this.purpose = purpose;
  }
}
