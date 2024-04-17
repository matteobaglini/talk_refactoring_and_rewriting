package io.doubleloop;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LoadEmployeesCelebratingBirthdayOn {

  private final LoadEmployeesCelebratingBirthdayOn_Old old;
  private final LoadEmployeesCelebratingBirthdayOn_New candidate;

  public LoadEmployeesCelebratingBirthdayOn(String fileName, LocalDate today) {
    old = new LoadEmployeesCelebratingBirthdayOn_Old(fileName, today);
    candidate = new LoadEmployeesCelebratingBirthdayOn_New(fileName, today);
  }

  public List<Employee> execute() throws IOException {
    // replace or not?
    // let's do an experiment!
    return new Experiment("LoadEmployeesCelebratingBirthdayOn")
        .use(() -> old.execute())
        .candidate(() -> candidate.execute())
        .publishResult((useResult, candidateResult) -> { /* write result data somewhere */ })
        .run();
  }
}
