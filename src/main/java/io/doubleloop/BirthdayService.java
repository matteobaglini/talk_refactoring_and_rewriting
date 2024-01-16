package io.doubleloop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BirthdayService {

  public void sendGreetings(String fileName, LocalDate today, String smtpHost, int smtpPort)
      throws IOException, MessagingException {

    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String str = in.readLine(); // skip header
    while ((str = in.readLine()) != null) {
      // Parse employee data
      String[] employeeData = str.split(", ");
      Employee employee = new Employee(
          employeeData[1],
          employeeData[0],
          LocalDate.parse(employeeData[2], DateTimeFormatter.ofPattern("yyyy/MM/dd")),
          employeeData[3]);

      // Check birthday
      if (employee.isBirthday(today)) {
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

}
