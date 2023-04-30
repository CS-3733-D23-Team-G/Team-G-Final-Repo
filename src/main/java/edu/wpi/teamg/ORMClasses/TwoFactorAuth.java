package edu.wpi.teamg.ORMClasses;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DBConnection;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  final String mailHost = "smtp.wpi.edu";
  String sender = "brighmanwomenautosender@gmail.com";
  @Getter @Setter String recipient;
  @Getter int code;

  public TwoFactorAuth() {
    code = (int) (Math.random() * 900000 + 100000);
  }

  public void sendEmail(String user) throws MessagingException, SQLException {
    DBConnection db = new DBConnection();
    db.setConnection(App.getWhichDB());
    String email = null;
    int id = 0;
    String retrieveAccount = "select * from teamgdb.iteration4.account where username = ?";
    PreparedStatement ps = db.getConnection().prepareStatement(retrieveAccount);
    ps.setString(1, user);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      id = rs.getInt("empid");
    }

    String retrieveEmployee = "select * from teamgdb.iteration4.employee where empid = ?";
    PreparedStatement ps2 = db.getConnection().prepareStatement(retrieveEmployee);
    ps2.setInt(1, id);
    ResultSet rs2 = ps2.executeQuery();
    while (rs2.next()) {
      email = rs2.getString("email");
    }
    db.closeConnection();

    String message = "Your 6 digit code is: " + code;
    setRecipient(email);
    String password = getEmailCred().get(0);
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", mailHost);
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    props.put("mail.smtp.port", 587);

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
