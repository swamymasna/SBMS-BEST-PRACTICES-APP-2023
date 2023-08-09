package com.swamy.service;

import java.util.List;

import com.swamy.dto.EmployeeDto;
import com.swamy.dto.EmployeeResponse;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);

	List<EmployeeDto> getAllEmployees();

	EmployeeDto getEmployeeById(Integer employeeId);

	EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employeeDto);

	String deleteEmployee(Integer employeeId);
	
	EmployeeResponse getAllEmployees(Integer pageNo, Integer pageSize, String sortBy);
}
