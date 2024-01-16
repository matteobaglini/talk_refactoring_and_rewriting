package io.doubleloop;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoadEmployeesCelebratingBirthdayOn_OldTest {
  private Path testDataPath;

  @BeforeEach
  void setUp() {
    testDataPath = Paths.get("test-data-bs");
    final var testDataDir = testDataPath.toFile();
    testDataDir.deleteOnExit();
    testDataDir.mkdirs();
  }

  @Test
  void noEmployeesCelebrateBirthday() throws Exception {
    final var filePath = employeeFile(
        header(),
        line("Ann, Mary, 1975/03/11, mary.ann@foobar.com")
    );
    final var employees = new LoadEmployeesCelebratingBirthdayOn_Old(filePath, date("2024/10/08")).execute();

    assertThat(employees.size()).isEqualTo(0);
  }

  private String employeeFile(String... lines) throws IOException {
    final var filePath = Paths.get(testDataPath.toString(), "employee_data.txt");
    filePath.toFile().deleteOnExit();
    Files.write(filePath, Arrays.asList(lines));
    return filePath.toString();
  }

  private String line(final String text) {
    return text;
  }

  private String header() {
    return "last_name, first_name, date_of_birth, email";
  }

  private LocalDate date(String value) {
    return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }
}
