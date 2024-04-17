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
import java.time.format.DateTimeFormatter;
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
    final var result = new ArrayList<Employee>();

    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String str = in.readLine(); // skip header
    while ((str = in.readLine()) != null) {
      var employee = Employee.parseLine(str);

      // Check birthday
      if (employee.isBirthday(today)) {
        result.add(employee);
      }
    }
    return result;
  }

  public List<Employee> execute_rewritten_with_external_lib() throws IOException {
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
                LocalDate.parse(x.get(Headers.date_of_birth), DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                x.get(Headers.email)))
        .filter(e -> e.isBirthday(today))
        .toList();
  }

  private enum Headers {
    last_name,
    first_name,
    date_of_birth,
    email
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
