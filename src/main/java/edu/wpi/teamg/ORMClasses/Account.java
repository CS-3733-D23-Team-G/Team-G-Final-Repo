package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Account extends Employee {
  @Getter @Setter private int empid;
  @Getter @Setter private String password;
  @Getter @Setter private boolean is_admin;

  public Account() {}

  public Account(
      String firstName,
      String lastName,
      String email,
      String can_serve,
      String password,
      boolean is_admin) {
    super(firstName, lastName, email, can_serve);
    this.password = password;
    this.is_admin = is_admin;
  }
}
