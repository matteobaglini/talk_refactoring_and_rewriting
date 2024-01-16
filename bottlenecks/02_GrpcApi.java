public class EmployeeServer {

  // generated server start/stop boilerplate code...

  public static void main(String[] args) throws Exception {
    EmployeeServer server = new EmployeeServer(8980);
    server.start();
    server.blockUntilShutdown();
  }

  private static class EmployeeService extends EmployeeGrpc.EmployeeImplBase {
    private final String fileName;

    EmployeeService(String fileName) {
      this.fileName = fileName;
    }

    // BEFORE

    @Override
    public void listEmployees(LocalDate today, StreamObserver<Employee> responseObserver) {
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      String str = in.readLine(); // skip header
      while ((str = in.readLine()) != null) {
        // Parse employee data
        String[] employeeData = str.split(", ");
        Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
        if (employee.isBirthday(today)) {
          responseObserver.onNext(employee);
        }
      }
      responseObserver.onCompleted();
    }

    // AFTER

    @Override
    public void listEmployees(LocalDate today, StreamObserver<Employee> responseObserver) {
      final var employees = new LoadEmployeesCelebratingBirthdayOn(fileName, today).execute();
      employees.forEach(responseObserver::onNext);
      responseObserver.onCompleted();
    }
  }
}