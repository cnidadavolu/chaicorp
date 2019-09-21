package com.chaicorp.emp.controller;

import com.chaicorp.emp.entity.Employee;
import com.chaicorp.emp.service.EmployeeService;
import com.chaicorp.emp.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class EmployeeController {

    public static final Logger Log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    //---------------retrieve all users---------------
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> emp = employeeService.findAllEmployees();
        if (emp.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Employee>>(emp,HttpStatus.OK);
    }

    //---------------retrieve single user---------------
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployee(@PathVariable("id") Long id) {
        Log.info("Fetching User with id{}", id);
        Optional<Employee> emp = employeeService.findById(id);
        if (emp == null) {
            Log.error("User with id{} not found ", id);
            return new ResponseEntity(new CustomErrorType("User with id" + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    //---------------Create a User---------------
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee emp, UriComponentsBuilder ucBuilder) {
        Log.info("creating User: {}", emp);

        if (employeeService.isEmployeeExist(emp)) {
            Log.error("Unable to create. A User with name {} already exist", emp.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Employee with name " + emp.getName() +
                    "already exist."), HttpStatus.CONFLICT);
        }
        employeeService.saveEmployee(emp);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/employee/{id}").buildAndExpand(emp.getId()).toUri());
        return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);
    }

    //---------------Update a User---------------
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee updateEmp) {
        Log.info("Updating Employee with id{}", id);
        Optional<Employee> currEmp = employeeService.findById(id);
        if (currEmp == null) {
            Log.error("Unable to update. Employee with id {} not found", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update, Employee with Id " + id + "not found"), HttpStatus.NOT_FOUND);
        }

        /*currEmp.setName(updateEmp.getName());
        currEmp.setAge(updateEmp.getAge());
        currEmp.setSalary(updateEmp.getSalary());
*/        employeeService.updateEmployee(currEmp);
        return new ResponseEntity<>(currEmp, HttpStatus.OK);
    }

    //--------------- Delete a User---------------
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        Log.info("Fetching and Deleting Employee with id {}", id);
        Optional<Employee> emp = employeeService.findById(id);
        if (emp == null) {
            Log.error("Unable to delete. Employee with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Employee with id " + id + "not found"), HttpStatus.NOT_FOUND);
        }
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

    //---------------Delete All Users---------------
    @RequestMapping(value = "/employees", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteAllEmployees() {
        Log.info("Deleting All Employees");
        employeeService.deleteAllEmployees();
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
}
