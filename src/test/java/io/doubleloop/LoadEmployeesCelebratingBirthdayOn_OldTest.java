package io.doubleloop;


import java.time.LocalDate;

class LoadEmployeesCelebratingBirthdayOn_OldTest
    extends LoadEmployeesCelebratingBirthdayOnContractTest {

  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, LocalDate today) {
    return new LoadEmployeesCelebratingBirthdayOn_Old(filePath, today);
  }

}
