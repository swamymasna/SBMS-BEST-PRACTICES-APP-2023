package com.swamy.controller;

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

import com.swamy.dto.EmployeeDto;
import com.swamy.dto.EmployeeResponse;
import com.swamy.service.EmployeeService;
import static com.swamy.utils.AppConstants.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	private EmployeeService employeeService;

	@PreAuthorize(ADMIN_ROLE)
	@PostMapping
	public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
		return new ResponseEntity<>(employeeService.saveEmployee(employeeDto), HttpStatus.CREATED);
	}

	@PreAuthorize(ADMIN_AND_USER_ROLES)
	@GetMapping
	public ResponseEntity<List<EmployeeDto>> findAllEmployees() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@PreAuthorize(ADMIN_AND_USER_ROLES)
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Integer employeeId) {
		return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
	}

	@PreAuthorize(ADMIN_ROLE)
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Integer employeeId,
			@RequestBody @Valid EmployeeDto employeeDto) {
		return new ResponseEntity<>(employeeService.updateEmployee(employeeId, employeeDto), HttpStatus.OK);
	}

	@PreAuthorize(ADMIN_ROLE)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer employeeId) {
		return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
	}

	@PreAuthorize(ADMIN_AND_USER_ROLES)
	@GetMapping("/all")
	public ResponseEntity<EmployeeResponse> findAllEmployees(

			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO, required = false) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy) {
		return ResponseEntity.ok(employeeService.getAllEmployees(pageNo, pageSize, sortBy));
	}

}
