package io.doubleloop;

import java.time.LocalDate;

public class App {
  public static void main(String[] args) throws Exception {
    BirthdayService service = new BirthdayService();
    service.sendGreetings("employee_data.txt", LocalDate.now(), "localhost", 25);
  }
}
