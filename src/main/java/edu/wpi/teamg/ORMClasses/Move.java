package edu.wpi.teamg.ORMClasses;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class Move extends Node {
  @Getter @Setter public int nodeID;
  @Getter @Setter public String longName;
  @Getter @Setter public Date date;

  public Move(int nodeID, String longName, Date date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }

  public Move() {}
}
