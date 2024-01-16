package io.doubleloop;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LoadEmployeesCelebratingBirthdayOn {
  private final LoadEmployeesCelebratingBirthdayOn_Old old;

  public LoadEmployeesCelebratingBirthdayOn(String fileName, LocalDate today) {
    // REFACTORING: just a copy type :-)

    // REFACTOR STEP: create an "old" implementation
    // REFACTOR STEP: extract interface as secondary bottleneck
    // REFACTOR STEP: create a "new" empty implementation
    old = new LoadEmployeesCelebratingBirthdayOn_Old(fileName, today);
  }

  public List<Employee> execute() throws IOException {
    return old.execute();
  }
}
