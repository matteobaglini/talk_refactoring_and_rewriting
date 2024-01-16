package io.doubleloop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoadEmployeesCelebratingBirthdayOn {
  private final String fileName;
  private final LocalDate today;

  public LoadEmployeesCelebratingBirthdayOn(String fileName, LocalDate today) {
    this.fileName = fileName;
    this.today = today;
  }

  public List<Employee> execute() throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String str = in.readLine(); // skip header
    final var employees = new ArrayList<Employee>();
    while ((str = in.readLine()) != null) {
      final var employee = Employee.parse(str);
      if (employee.isBirthday(today)) {
        employees.add(employee);
      }
    }
    return employees;
  }
}
