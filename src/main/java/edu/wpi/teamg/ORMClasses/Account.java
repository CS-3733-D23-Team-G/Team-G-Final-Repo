package edu.wpi.teamg.ORMClasses;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import lombok.Getter;
import lombok.Setter;

public class Account extends Employee {
  @Getter @Setter private String username;
  @Getter @Setter private String password;
  private boolean is_admin;

  public Account() {}

  public Account(
      String firstname,
      String lastname,
      String email,
      String can_serv,
      String username,
      String password,
      boolean is_admin) {
    super(firstname, lastname, email, can_serv);
    this.username = username;
    this.password = password;
    this.is_admin = is_admin;
  }

  public Account(String username, String password, boolean is_admin) {
    this.username = username;
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

  public boolean getAdmin() {
    return this.is_admin;
  }

  public void setAdmin(boolean admin) {
    this.is_admin = admin;
  }
}
