package io.doubleloop;

import static org.assertj.core.api.Assertions.assertThat;

// REFACTORING: introduce contract tests

public class LoadEmployeesCelebratingBirthdayOn_OldTest extends LoadEmployeesCelebratingBirthdayOnContractTest {

  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, String today) {
    return new LoadEmployeesCelebratingBirthdayOn_Old(filePath, date(today));
  }

}
