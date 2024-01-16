package io.doubleloop;

import java.time.LocalDate;

public record Employee(String firstName, String lastName, LocalDate birthDate, String email) {
  public boolean isBirthday(LocalDate now) {
    return birthDate.getMonth() == now.getMonth()
        && birthDate.getDayOfMonth() == now.getDayOfMonth();
  }
}
