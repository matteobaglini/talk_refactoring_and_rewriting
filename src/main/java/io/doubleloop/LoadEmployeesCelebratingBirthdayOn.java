package io.doubleloop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    final var result = new ArrayList<Employee>();

    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String str = in.readLine(); // skip header
    while ((str = in.readLine()) != null) {

      // REFACTORING: extract method/function

      // Parse employee data
      var employee = parseLine(str);

      // Check birthday
      if (employee.isBirthday(today)) {
        result.add(employee);
      }
    }
    return result;
  }

  private static Employee parseLine(String str) {
    String[] employeeData = str.split(", ");
    Employee employee = new Employee(
        employeeData[1],
        employeeData[0],
        LocalDate.parse(employeeData[2], DateTimeFormatter.ofPattern("yyyy/MM/dd")),
        employeeData[3]);
    return employee;
  }
}
