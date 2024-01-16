package io.doubleloop;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class LoadEmployeesCelebratingBirthdayOn_New
    implements LoadEmployeesCelebratingBirthdayOnContract {
  private final String fileName;
  private final LocalDate today;

  public LoadEmployeesCelebratingBirthdayOn_New(String fileName, LocalDate today) {
    this.fileName = fileName;
    this.today = today;
  }

  @Override
  public List<Employee> execute() throws IOException {
    return Collections.emptyList();
  }
}
