
@Path("/employees")
public class GreetingResource {

  // BEFORE

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response employees(@QueryParam("day") today) {
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
    return Response.ok(employees).build();
  }

  // AFTER

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response employees(@QueryParam("day") today) {
    final var employees = new LoadEmployeesCelebratingBirthdayOn(fileName, today).execute();
    return Response.ok(employees).build();
  }
}