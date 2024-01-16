package io.doubleloop;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public abstract class LoadEmployeesCelebratingBirthdayOnContractTest {
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

  protected String line(final String text) {
    return text;
  }

  protected String header() {
    return "last_name, first_name, date_of_birth, email";
  }

  protected LocalDate date(String value) {
    return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }

  protected abstract LoadEmployeesCelebratingBirthdayOnContract createLoadEmployeesCelebratingBirthdayOn(String filePath, LocalDate today);
}
