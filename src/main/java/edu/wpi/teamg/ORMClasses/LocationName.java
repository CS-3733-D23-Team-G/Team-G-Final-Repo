package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class LocationName {

  @Getter @Setter private String longName;

  @Getter @Setter private String shortName;

  @Getter @Setter private String nodeType;

  public LocationName() {}

  public LocationName(String lo, String sho, String nodetype) {
    this.longName = lo;
    this.shortName = sho;
    this.nodeType = nodetype;
  }
}
