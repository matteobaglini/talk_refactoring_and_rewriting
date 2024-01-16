package io.doubleloop;

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
  void missingFile() throws Exception {
    final var filePath = "unknown.txt";

    assertThatThrownBy(() -> {
      createLoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();
    }).isInstanceOf(FileNotFoundException.class)
        .hasMessageContaining(filePath);
  }

}
