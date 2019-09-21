package com.chaicorp.emp.service;

import com.chaicorp.emp.entity.Employee;
import com.chaicorp.emp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> findAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public boolean isEmployeeExist(Employee employee) {
        return false;
    }

    public Optional<Employee> findById(Long id) {
        Optional<Employee> empById = employeeRepository.findById(id);

        //    return empById.orElseThrow(() -> new CustomErrorType("Couldn't find a Emp with id:" +id));
        return empById;
    }

    public Employee findByName(String name) {
        List<Employee> byName = employeeRepository.findByName(name);
        return null;
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Optional<Employee> employee) {
        Employee emp = new Employee();
//        emp.setId(employee.getId());
//        emp.setName(employee.getName());
//        emp.setAge(employee.getAge());
//        emp.setSalary(employee.getSalary());
//        employeeRepository.save(emp);

    }

    @Override
    public void deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }


}
