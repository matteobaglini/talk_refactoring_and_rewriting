package io.doubleloop;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

// REFACTORING: introduce contract tests

public class LoadEmployeesCelebratingBirthdayOn_OldTest extends LoadEmployeesCelebratingBirthdayOnContractTest {
  @Test
  void missingFile() throws Exception {
    final var filePath = "unknown.txt";

    assertThatThrownBy(() -> {
      createLoadEmployeesCelebratingBirthdayOn(filePath, "2024/10/08").execute();
    }).isInstanceOf(FileNotFoundException.class)
        .hasMessageContaining(filePath);
  }
  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, String today) {
    return new LoadEmployeesCelebratingBirthdayOn_Old(filePath, date(today));
  }

}
