package io.doubleloop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Employee(String firstName, String lastName, LocalDate birthDate, String email) {
  static Employee parseLine(String str) {
    String[] employeeData = str.split(", ");
    Employee employee = new Employee(
        employeeData[1],
        employeeData[0],
        LocalDate.parse(employeeData[2], DateTimeFormatter.ofPattern("yyyy/MM/dd")),
        employeeData[3]);
    return employee;
  }

  public boolean isBirthday(LocalDate now) {
    return birthDate.getMonth() == now.getMonth()
        && birthDate.getDayOfMonth() == now.getDayOfMonth();
  }
}
