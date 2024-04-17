package io.doubleloop;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;

public class BirthdayService {

  public void sendGreetings(String fileName, LocalDate today, String smtpHost, int smtpPort)
      throws IOException, MessagingException {

    // NOTE: use the extracted object as stronger primary bottleneck
    final var employeesCelebratingBirthday = new LoadEmployeesCelebratingBirthdayOn(fileName, today).execute();

    for (final var employee : employeesCelebratingBirthday) {
      // Create message
      String recipient = employee.email();
      String body = "Happy Birthday, dear %NAME%".replace("%NAME%", employee.firstName());
      String subject = "Happy Birthday!";

      // Create a mail session
      java.util.Properties props = new java.util.Properties();
      props.put("mail.smtp.host", smtpHost);
      props.put("mail.smtp.port", "" + smtpPort);
      Session session = Session.getInstance(props, null);

      // Construct the message
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("sender@here.com"));
      msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      msg.setSubject(subject);
      msg.setText(body);

      // Send the message
      Transport.send(msg);
    }
  }

}
