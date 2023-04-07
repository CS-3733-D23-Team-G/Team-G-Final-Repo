package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Account extends Employee {
  @Getter @Setter private int empid;
  @Getter @Setter private String password;
  @Getter @Setter private boolean is_admin;

  public Account() {}

  public Account(int empid, String password, boolean is_admin) {
    this.empid = empid;
    this.password = password;
    this.is_admin = is_admin;
  }
}
