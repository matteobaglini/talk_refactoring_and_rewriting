package io.doubleloop;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoadEmployeesCelebratingBirthdayOnTest {
  private Path testDataPath;

  @BeforeEach
  void setUp() {
    testDataPath = Paths.get("test-data-bs");
    final var testDataDir = testDataPath.toFile();
    testDataDir.deleteOnExit();
    testDataDir.mkdirs();
  }

  protected String employeeFile(String... lines) throws IOException {
    final var filePath = Paths.get(testDataPath.toString(), "employee_data.txt");
    filePath.toFile().deleteOnExit();
    Files.write(filePath, Arrays.asList(lines));
    return filePath.toString();
  }

  @Test
  void noEmployeeCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com")
    );
    final var employees = new LoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();

    assertThat(employees.size()).isEqualTo(0);
  }

  @Test
  void missingFile() throws Exception {
    final var filePath = "unknown.txt";

    assertThatThrownBy(() -> {
       new LoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();
    }).isInstanceOf(FileNotFoundException.class)
        .hasMessageContaining(filePath);
  }

  protected String line(final String text) {
    return text;
  }

  protected String header() {
    return "last_name, first_name, date_of_birth, email";
  }

  protected LocalDate date(String value) {
    return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }

  @Test
  void oneEmployeeCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Doe, John, 1982/10/08, john.doe@foobar.com"),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com")
    );
    final var employees = new LoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();

    assertThat(employees.size()).isEqualTo(1);
    assertThat(employees.get(0).firstName()).isEqualTo("John");
    assertThat(employees.get(0).email()).isEqualTo("john.doe@foobar.com");
  }

  @Test
  void manyEmployeesCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Doe, John, 1982/10/08, john.doe@foobar.com"),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com"),
        line("Rossi, Mario, 2000/10/08, mario.rossi@foobar.com")
    );
    final var employees = new LoadEmployeesCelebratingBirthdayOn(filePath, date("2024/10/08")).execute();

    assertThat(employees.size()).isEqualTo(2);
    assertThat(employees.get(0).firstName()).isEqualTo("John");
    assertThat(employees.get(1).firstName()).isEqualTo("Mario");
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
            return new LoadEmployeesCelebratingBirthdayOn(file, date(today)).execute();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        },
        new String[] {filePath},
        new String[] {"2024/10/08", "2024/10/09", "2024/03/11"});
  }
}
