package edu.wpi.teamg.ORMClasses;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

public class TwoFactorAuth {
  final String mailHost = "smtp.gmail.com";
  String sender = "brighmanwomenautosender@gmail.com";
  @Getter @Setter String recipient;
  int code;
  final String password =
      getEmailCred().get(0); // Uses API key in the google credential features for the email account

  public TwoFactorAuth() {
    code = (int) (Math.random() * 900000 + 100000);
  }

  public void sendEmail() throws MessagingException {
    String message = "Your 6 code is: " + code;
    setRecipient("rganguli0753@gmail.com");
    String stringHost = "smtp.gmail.com";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", stringHost);
    props.put("mail.smtp.port", 25);

    javax.mail.Session session =
        Session.getInstance(
            props,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
              }
            });

    try {
      MimeMessage mimeMessage = new MimeMessage(session);
      mimeMessage.setSubject("Test Email");
      mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      mimeMessage.setText(message);
      Transport.send(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  private List<String> getEmailCred() {
    List<String> creds = new LinkedList<>();
    InputStream is =
        this.getClass().getClassLoader().getResourceAsStream("edu/wpi/teamg/emailCreds.yml");
    Yaml yaml = new Yaml();
    Map<String, Object> data = yaml.load(is);
    creds.add(data.get("emailPass").toString());
    return creds;
  }
}
