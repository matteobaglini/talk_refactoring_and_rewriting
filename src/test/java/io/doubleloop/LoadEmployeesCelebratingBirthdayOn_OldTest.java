package io.doubleloop;

import java.io.IOException;
import java.time.LocalDate;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

class LoadEmployeesCelebratingBirthdayOn_OldTest
    extends LoadEmployeesCelebratingBirthdayOnContractTest {

  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(
      String filePath, LocalDate today) {
    return new LoadEmployeesCelebratingBirthdayOn_Old(filePath, today);
  }

  @Test
  void combinationTests() throws Exception {
    final var filePath =
        employeeFile(
            header(),
            line("Doe, John, 1982/10/08, john.doe@foobar.com"),
            line("Ann, Mary, 1975/03/11, mary.ann@foobar.com"),
            line("Rossi, Mario, 2000/10/08, mario.rossi@foobar.com"));

    CombinationApprovals.verifyAllCombinations(
        (file, today) -> {
          try {
            return createLoadEmployeesCelebratingBirthdayOn(file, today).execute();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        },
        new String[] {filePath},
        new LocalDate[] {date("2024/10/08"), date("2024/10/09"), date("2024/03/11")});
  }
}
