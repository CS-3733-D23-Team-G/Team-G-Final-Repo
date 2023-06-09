package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Employee {
  @Getter @Setter private int empID;
  @Getter @Setter private String firstName;
  @Getter @Setter private String lastName;
  @Getter @Setter private String email;
  @Getter @Setter private String can_serve;

  @Getter @Setter private Boolean is_admin;

  public Employee() {
    this.setIs_admin(false);
  }

  public Employee(String firstName, String lastName, String email, String can_serve) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.can_serve = can_serve;
  }

  public Employee(int empID, String firstName, String lastName, String email, String can_serve) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.can_serve = can_serve;
    this.empID = empID;
  }
}
