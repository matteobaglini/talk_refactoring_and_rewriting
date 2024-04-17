package io.doubleloop;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class LoadEmployeesCelebratingBirthdayOn_New implements LoadEmployeesCelebratingBirthdayOnContract {

  public LoadEmployeesCelebratingBirthdayOn_New(String fileName, LocalDate today) {
  }

  @Override
  public List<Employee> execute() throws IOException {
    return Collections.emptyList();
  }
}
