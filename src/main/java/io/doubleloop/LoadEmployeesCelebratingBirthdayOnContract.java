package io.doubleloop;

import java.io.IOException;
import java.util.List;

// REFACTORING: branch by abstraction (https://martinfowler.com/bliki/BranchByAbstraction.html)

// REFACTOR STEP: create empty "new" implementation
public interface LoadEmployeesCelebratingBirthdayOnContract {
  List<Employee> execute() throws IOException;
}
