package io.doubleloop;

public class LoadEmployeesCelebratingBirthdayOn_NewTest extends LoadEmployeesCelebratingBirthdayOnContractTest {
  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, String today) {
    return new LoadEmployeesCelebratingBirthdayOn_New(filePath, date(today));
  }
}
