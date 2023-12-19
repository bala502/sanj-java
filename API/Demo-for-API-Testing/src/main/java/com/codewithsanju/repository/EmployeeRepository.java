package com.codewithsanju.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsanju.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
