package com.greatlearning.ems.Repository;

import com.greatlearning.ems.Model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
