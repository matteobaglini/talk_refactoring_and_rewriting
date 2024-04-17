package io.doubleloop;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

// REFACTORING: branch by abstraction (https://martinfowler.com/bliki/BranchByAbstraction.html)

// REFACTOR STEP: replace code with "old" implementation
public class LoadEmployeesCelebratingBirthdayOn {

  private final LoadEmployeesCelebratingBirthdayOn_Old old;

  public LoadEmployeesCelebratingBirthdayOn(String fileName, LocalDate today) {
    old = new LoadEmployeesCelebratingBirthdayOn_Old(fileName, today);
  }

  public List<Employee> execute() throws IOException {
    return old.execute();
  }
}
