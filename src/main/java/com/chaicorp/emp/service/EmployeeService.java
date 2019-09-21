package com.chaicorp.emp.service;

import com.chaicorp.emp.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {

    Optional<Employee> findById(Long id);

    Employee findByName(String name);

    List<Employee> findAllEmployees();

    boolean isEmployeeExist(Employee employee);

    void saveEmployee(Employee employee);

    void updateEmployee(Optional<Employee> employee);

    void deleteEmployeeById(long id);

    void deleteAllEmployees();

}
