package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Employee {
  @Getter @Setter private int empID;
  @Getter @Setter private String firstName;
  @Getter @Setter private String lastName;
  @Getter @Setter private String email;
  @Getter @Setter private String can_serve;

  public Employee() {}
}
