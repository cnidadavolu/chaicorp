package com.chaicorp.emp.repository;

import com.chaicorp.emp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//public interface EmployeeRepository extends JpaRepository {

    @Query("select emp from Employee emp where emp.name = name")
    List<Employee> findByName(String name);

}
