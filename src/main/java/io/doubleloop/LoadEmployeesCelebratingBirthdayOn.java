package io.doubleloop;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LoadEmployeesCelebratingBirthdayOn {
  private final String fileName;
  private final LocalDate today;

  public LoadEmployeesCelebratingBirthdayOn(String fileName, LocalDate today) {
    this.fileName = fileName;
    this.today = today;
  }

  public List<Employee> execute() throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String str = in.readLine(); // skip header
    final var employees = new ArrayList<Employee>();
    while ((str = in.readLine()) != null) {
      final var employee = Employee.parse(str);
      if (employee.isBirthday(today)) {
        employees.add(employee);
      }
    }
    return employees;
  }

  private enum Headers {
    last_name,
    first_name,
    date_of_birth,
    email
  }

  public List<Employee> execute_rewrite_with_lib() throws IOException {
    final var csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader(Headers.class)
        .setSkipHeaderRecord(true)
        .setTrim(true)
        .setIgnoreEmptyLines(true)
        .setIgnoreSurroundingSpaces(true)
        .build();
    final var reader = Files.newBufferedReader(Path.of(fileName));

    return CsvRecords.from(reader)
        .parse(csvFormat)
        .map(x ->
                new Employee(
                    x.get(Headers.last_name),
                    x.get(Headers.first_name),
                    x.get(Headers.date_of_birth),
                    x.get(Headers.email)))
        .filter(e -> e.isBirthday(today))
        .toList();
  }

  private static class CsvRecords {
    private final Reader reader;

    private CsvRecords(Reader reader) {
      this.reader = reader;
    }

    public static CsvRecords from(Reader reader) {
      return new CsvRecords(reader);
    }

    public Stream<CSVRecord> parse(CSVFormat format) throws IOException {
      final var records = format.parse(reader);
      return StreamSupport.stream(records.spliterator(), false);
    }
  }
}
