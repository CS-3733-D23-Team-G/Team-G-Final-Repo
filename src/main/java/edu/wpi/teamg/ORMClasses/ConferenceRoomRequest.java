package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import java.sql.Time;
import lombok.Getter;
import lombok.Setter;

public class ConferenceRoomRequest extends Request {
  @Getter @Setter private Date meeting_date;
  @Getter @Setter private Time meeting_time;
  @Getter @Setter private String purpose;

  public ConferenceRoomRequest() {}

  public ConferenceRoomRequest(Date meeting_date, Time meeting_time, String purpose) {
    this.meeting_date = meeting_date;
    this.meeting_time = meeting_time;
    this.purpose = purpose;
  }
}
