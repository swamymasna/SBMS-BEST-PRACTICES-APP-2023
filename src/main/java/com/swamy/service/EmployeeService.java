package com.swamy.service;

import java.util.List;

import com.swamy.dto.EmployeeResponse;
import com.swamy.dto.EmployeeApiResponse;
import com.swamy.dto.EmployeeRequest;

public interface EmployeeService {

	EmployeeResponse saveEmployee(EmployeeRequest employeeRequest);

	List<EmployeeResponse> getAllEmployees();

	EmployeeResponse getEmployeeById(Integer employeeId);

	EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest employeeRequest);

	String deleteEmployee(Integer employeeId);
	
	EmployeeApiResponse getAllEmployees(Integer pageNo, Integer pageSize, String sortBy);
}
