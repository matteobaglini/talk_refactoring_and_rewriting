package io.doubleloop;

import java.io.IOException;
import java.util.List;

public interface LoadEmployeesCelebratingBirthdayOnContract {
  List<Employee> execute() throws IOException;
}
