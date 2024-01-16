package io.doubleloop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class LoadEmployeesCelebratingBirthdayOn_OldTest
    extends LoadEmployeesCelebratingBirthdayOnContractTest {

  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, LocalDate today) {
    return new LoadEmployeesCelebratingBirthdayOn_Old(filePath, today);
  }

  @Test
  void oneEmployeeCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Doe, John, 1982/10/08, john.doe@foobar.com"),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com")
    );
    final var employees = createLoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();

    assertThat(employees.size()).isEqualTo(1);
    assertThat(employees.get(0).getFirstName()).isEqualTo("John");
    assertThat(employees.get(0).getEmail()).isEqualTo("john.doe@foobar.com");
  }

  @Test
  void manyEmployeesCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Doe, John, 1982/10/08, john.doe@foobar.com"),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com"),
        line("Rossi, Mario, 2000/10/08, mario.rossi@foobar.com")
    );
    final var employees =createLoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();

    assertThat(employees.size()).isEqualTo(2);
    assertThat(employees.get(0).getFirstName()).isEqualTo("John");
    assertThat(employees.get(1).getFirstName()).isEqualTo("Mario");
  }

  @Test
  void missingFile() throws Exception {
    final var filePath = "unknown.txt";

    assertThatThrownBy(() -> {
      createLoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();
    }).isInstanceOf(FileNotFoundException.class)
        .hasMessageContaining(filePath);
  }

}
