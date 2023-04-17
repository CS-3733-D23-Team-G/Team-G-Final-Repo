package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Login extends Account {
  @Getter @Setter private String email;

  @Getter @Setter private String password;

  public Login(String email, String password) {
    super(email, password);
  }

  public String getHashedPassword(byte[] salt){
    String generatedPass = null;

    return generatedPass;
  }
}
