package edu.wpi.teamg.ORMClasses;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

  public Account(String email, String password) {
    email = getEmail();
    this.password = password;
  }

  public Account(int empid, String password, boolean is_admin) {
    this.empid = empid;
    this.password = password;
    this.is_admin = is_admin;
  }

  @Override
  public String getEmail() {
    return super.getEmail();
  }

  public String getHashedPassword(byte[] salt) {
    String generatedPass = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt);

      byte[] bytes = md.digest(getPassword().getBytes());
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++)
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

      generatedPass = sb.toString();

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPass;
  }

  public static byte[] getSalt() throws NoSuchAlgorithmException {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }
}
