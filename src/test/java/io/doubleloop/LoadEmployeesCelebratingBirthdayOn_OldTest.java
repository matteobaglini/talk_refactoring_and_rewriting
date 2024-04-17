package io.doubleloop;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// REFACTORING: introduce contract tests

public class LoadEmployeesCelebratingBirthdayOn_OldTest extends LoadEmployeesCelebratingBirthdayOnContractTest {

  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, String today) {
    return new LoadEmployeesCelebratingBirthdayOn_Old(filePath, date(today));
  }

  @Test
  void oneEmployeeCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Doe, John, 1982/10/08, john.doe@foobar.com"),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com")
    );
    final var employees = createLoadEmployeesCelebratingBirthdayOn(filePath, "2024/10/08").execute();

    assertThat(employees.size()).isEqualTo(1);
    assertThat(employees.get(0).firstName()).isEqualTo("John");
    assertThat(employees.get(0).email()).isEqualTo("john.doe@foobar.com");
  }

  @Test
  void manyEmployeesCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Doe, John, 1982/10/08, john.doe@foobar.com"),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com"),
        line("Rossi, Mario, 2000/10/08, mario.rossi@foobar.com")
    );
    final var employees =createLoadEmployeesCelebratingBirthdayOn(filePath, "2024/10/08").execute();

    assertThat(employees.size()).isEqualTo(2);
    assertThat(employees.get(0).firstName()).isEqualTo("John");
    assertThat(employees.get(1).firstName()).isEqualTo("Mario");
  }
}
