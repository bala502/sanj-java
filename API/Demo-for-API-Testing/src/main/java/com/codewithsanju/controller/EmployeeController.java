package com.codewithsanju.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithsanju.model.Employee;
import com.codewithsanju.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/getAllEmployees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/{id}")
	public Object getEmployeeById(@PathVariable Long id) {
		if(null == employeeService.getEmployeeById(id))
			return new String("No Records Found with ID: "+id);
		else
			return employeeService.getEmployeeById(id);
	}

	@PostMapping("/createEmployee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}

	@PutMapping("/{id}")
	public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		employee.setId(id);
		return employeeService.updateEmployee(employee);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}
	
	@PatchMapping("/{id}")
    public Employee partialUpdateEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws Exception {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();

            // Update the properties specified in the request body
            updates.forEach((key, value) -> {
                switch (key) {
                    case "firstName":
                        employee.setFirstName((String) value);
                        break;
                    case "lastName":
                        employee.setLastName((String) value);
                        break;
                    case "email":
                        employee.setEmail((String) value);
                        break;
                    // Add more properties as needed

                    default:
                        // Ignore unknown properties
                        break;
                }
            });

            return employeeService.updateEmployee(employee);
        } else {
            throw new Exception("Employee not found with id: " + id);
        }
    }
}

