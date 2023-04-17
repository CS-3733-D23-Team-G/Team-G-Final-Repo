package edu.wpi.teamg.ORMClasses;

import org.yaml.snakeyaml.Yaml;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TwoFactorAuth {
    String sender="brighmanwomenautosender@gmail.com";
    String recipient="";
    int code;
    String password = getEmailCred().get(0);//Uses API key in the google credential features for the email account

    public TwoFactorAuth(){
        code = (int)(Math.random()*900000+100000);
    }
    public void sendEmail() throws MessagingException {
        String message = "Your 6 digit 2 factor code is: "+code;

        String stringHost = "stmp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",stringHost);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender,password);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
        mimeMessage.setFrom(new InternetAddress(sender));
        mimeMessage.setSubject("Testing 2 Factor");
        mimeMessage.setText(message);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Transport.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private List<String> getEmailCred(){
        List<String> creds = new LinkedList<>();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("edu/wpi/teamg/emailCreds.yml");
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(is);
        creds.add(data.get("emailPass").toString());
        return creds;
    }
}
