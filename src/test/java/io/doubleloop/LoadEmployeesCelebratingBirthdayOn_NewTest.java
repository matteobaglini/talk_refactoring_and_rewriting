package io.doubleloop;


import java.time.LocalDate;

public class LoadEmployeesCelebratingBirthdayOn_NewTest
    extends LoadEmployeesCelebratingBirthdayOnContractTest {
  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, LocalDate today) {
    return new LoadEmployeesCelebratingBirthdayOn_New(filePath, today);
  }
}
