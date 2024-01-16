package io.doubleloop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee {
  private LocalDate date;
  private String lastName;
  private String firstName;
  private String email;

  public Employee(String firstName, String lastName, String birthDate, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    this.email = email;
  }

  public static Employee parse(String value) {
    final var parts = value.split(", ");
    return new Employee(parts[1], parts[0], parts[2], parts[3]);
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public boolean isBirthday(LocalDate now) {
    return date.getMonth() == now.getMonth() && date.getDayOfMonth() == now.getDayOfMonth();
  }

  @Override
  public String toString() {
    return "Employee{" +
        "date=" + date +
        ", lastName='" + lastName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
