package edu.wpi.teamg.ORMClasses;

import javax.mail.MessagingException;

public class twoFacTest {
  public static void main(String[] args) throws MessagingException {
    TwoFactorAuth auth = new TwoFactorAuth();
    auth.sendEmail();
  }
}
