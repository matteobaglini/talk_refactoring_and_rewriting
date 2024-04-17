package io.doubleloop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class LoadEmployeesCelebratingBirthdayOn {

  private final String fileName;
  private final LocalDate today;

  public LoadEmployeesCelebratingBirthdayOn(String fileName, LocalDate today) {
    this.fileName = fileName;
    this.today = today;
  }

  public List<Employee> execute() throws IOException {
    final int HEADER = 1;
    try {
      return Files.readAllLines(Path.of(fileName)).stream()
          .skip(HEADER)
          .map(Employee::parseLine)
          .filter(e -> e.isBirthday(today))
          .toList();
    } catch (NoSuchFileException e) {
      throw new FileNotFoundException("Invalid file path: " + fileName);
    }
  }
}
