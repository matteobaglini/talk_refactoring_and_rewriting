package io.doubleloop;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import java.io.IOException;

// REFACTORING: gain more coverage with ApprovalTests

public class LoadEmployeesCelebratingBirthdayOn_OldTest extends LoadEmployeesCelebratingBirthdayOnContractTest {
  @Override
  protected LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, String today) {
    return new LoadEmployeesCelebratingBirthdayOn_Old(filePath, date(today));
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
        new String[] {"2024/10/08", "2024/10/09", "2024/03/11"});
  }
}
