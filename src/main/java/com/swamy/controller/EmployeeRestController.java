package com.swamy.controller;

import static com.swamy.utils.AppConstants.ADMIN_AND_USER_ROLES;
import static com.swamy.utils.AppConstants.ADMIN_ROLE;
import static com.swamy.utils.AppConstants.DEFAULT_PAGE_NO;
import static com.swamy.utils.AppConstants.DEFAULT_PAGE_SIZE;
import static com.swamy.utils.AppConstants.DEFAULT_SORT_BY;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.EmployeeApiResponse;
import com.swamy.dto.EmployeeRequest;
import com.swamy.dto.EmployeeResponse;
import com.swamy.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	private EmployeeService employeeService;

	@PreAuthorize(ADMIN_ROLE)
	@PostMapping
	public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
		return new ResponseEntity<>(employeeService.saveEmployee(employeeRequest), HttpStatus.CREATED);
	}

	@PreAuthorize(ADMIN_AND_USER_ROLES)
	@GetMapping
	public ResponseEntity<List<EmployeeResponse>> findAllEmployees() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@PreAuthorize(ADMIN_AND_USER_ROLES)
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") Integer employeeId) {
		return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
	}

	@PreAuthorize(ADMIN_ROLE)
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable("id") Integer employeeId,
			@RequestBody @Valid EmployeeRequest employeeRequest) {
		return new ResponseEntity<>(employeeService.updateEmployee(employeeId, employeeRequest), HttpStatus.OK);
	}

	@PreAuthorize(ADMIN_ROLE)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer employeeId) {
		return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
	}

	@PreAuthorize(ADMIN_AND_USER_ROLES)
	@GetMapping("/all")
	public ResponseEntity<EmployeeApiResponse> findAllEmployees(

			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO, required = false) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy) {
		return ResponseEntity.ok(employeeService.getAllEmployees(pageNo, pageSize, sortBy));
	}

}
