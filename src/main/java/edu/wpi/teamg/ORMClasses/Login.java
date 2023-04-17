package edu.wpi.teamg.ORMClasses;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.Getter;
import lombok.Setter;

public class Login extends Account {
  @Getter @Setter private String email;

  @Getter @Setter private String password;

  public Login(String email, String password) {
    super(email, password);
  }

  public String getHashedPassword(byte[] salt) {
    String generatedPass = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt);

      

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPass;
  }
}
