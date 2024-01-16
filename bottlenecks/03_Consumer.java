
// BEFORE

channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {
  String birthDate = new String(delivery.getBody(), "UTF-8");
  final var today = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

  BufferedReader in = new BufferedReader(new FileReader(fileName));
  String str = in.readLine(); // skip header
  final var employees = new ArrayList<Employee>();
  while ((str = in.readLine()) != null) {
    // Parse employee data
    String[] employeeData = str.split(", ");
    Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
    if (employee.isBirthday(today)) {
      employees.add(employee);
    }
  }
  // do something with employees
});

// AFTER

channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {
  String birthDate = new String(delivery.getBody(), "UTF-8");
  final var today = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  final var employees = new LoadEmployeesCelebratingBirthdayOn(fileName, today).execute();
  // do something with employees
});