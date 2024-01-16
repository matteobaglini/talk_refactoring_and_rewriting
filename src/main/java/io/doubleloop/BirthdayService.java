package io.doubleloop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BirthdayService {

  public void sendGreetings(String fileName, LocalDate today, String smtpHost, int smtpPort)
      throws IOException, MessagingException {

    // REFACTORING: extract method/function (https://refactoring.com/catalog/extractFunction.html)

    // REFACTOR STEP: extract primary bottleneck
    final var employees = loadEmployeesCelebratingBirthdayOn(fileName, today);

    for (final var employee : employees) {
      createAndSendSmtpMessage(smtpHost, smtpPort, employee);
    }
  }

  // REFACTOR STEP: use the method as indirection level
  private static ArrayList<Employee> loadEmployeesCelebratingBirthdayOn(String fileName, LocalDate today) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String str = in.readLine(); // skip header
    final var employees = new ArrayList<Employee>();
    while ((str = in.readLine()) != null) {
      // Parse employee data
      String[] employeeData = str.split(", ");
      Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
      if (employee.isBirthday(today)) {
        employees.add(employee);
      }
    }
    return employees;
  }

  private static void createAndSendSmtpMessage(String smtpHost, int smtpPort, Employee employee) throws MessagingException {
    // Create message
    String recipient = employee.getEmail();
    String body = "Happy Birthday, dear %NAME%".replace("%NAME%", employee.getFirstName());
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
