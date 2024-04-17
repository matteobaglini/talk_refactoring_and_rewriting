package io.doubleloop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoadEmployeesCelebratingBirthdayOn_Old {
  private final String fileName;
  private final LocalDate today;

  public LoadEmployeesCelebratingBirthdayOn_Old(String fileName, LocalDate today) {
    this.fileName = fileName;
    this.today = today;
  }

  public List<Employee> execute() throws IOException {
    final var result = new ArrayList<Employee>();

    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String str = in.readLine(); // skip header
    while ((str = in.readLine()) != null) {
      var employee = Employee.parseLine(str);

      // Check birthday
      if (employee.isBirthday(today)) {
        result.add(employee);
      }
    }
    return result;
  }
}