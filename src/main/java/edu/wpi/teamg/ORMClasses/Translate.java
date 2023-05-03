package edu.wpi.teamg.ORMClasses;

import java.util.*;

public class Translate {
  String recordLanguage;
  String recordLocation;
  String recordEmployee;
  static ArrayList<Translate> request = new ArrayList<>();

  public Translate(String recordLanguage, String recordLocation, String recordEmployee) {
    this.recordLanguage = recordLanguage;
    this.recordLocation = recordLocation;
    this.recordEmployee = recordEmployee;
  }

  public String getLanguage() {
    return recordLanguage;
  }

  public String getLocation() {
    return recordLocation;
  }

  public String getEmployee() {
    return recordEmployee;
  }

  public static ArrayList<Translate> getRequest() {
    return request;
  }
}
