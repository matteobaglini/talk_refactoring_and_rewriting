
Button button = new Button("Load");

// BEFORE

button.setOnAction(e -> {
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

    textField.setText(String.valueOf(employees.size()));
});

// AFTER

button.setOnAction(e -> {
    final var employees = new LoadEmployeesCelebratingBirthdayOn(fileName, today).execute();
    
    textField.setText(String.valueOf(employees.size()));
});