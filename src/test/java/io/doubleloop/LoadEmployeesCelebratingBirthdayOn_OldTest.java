package io.doubleloop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoadEmployeesCelebratingBirthdayOn_OldTest
    extends LoadEmployeesCelebratingBirthdayOnContractTest {

  @Test
  void noEmployeesCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com")
    );
    final var employees = new LoadEmployeesCelebratingBirthdayOn_Old(filePath, date("2024/10/08")).execute();

    assertThat(employees.size()).isEqualTo(0);
  }

}
