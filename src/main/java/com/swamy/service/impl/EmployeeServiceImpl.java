package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swamy.dto.EmployeeDto;
import com.swamy.dto.EmployeeResponse;
import com.swamy.entity.Employee;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.props.AppProperties;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.EmployeeService;
import static com.swamy.utils.AppConstants.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	private ModelMapper modelMapper;

	private AppProperties appProperties;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

		Employee employee = modelMapper.map(employeeDto, Employee.class);

		Employee savedEmployee = employeeRepository.save(employee);

		return modelMapper.map(savedEmployee, EmployeeDto.class);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		List<Employee> employees = employeeRepository.findAll();

		return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@Cacheable(key = KEY, value = VALUE)
	@Override
	public EmployeeDto getEmployeeById(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId));

		return modelMapper.map(employee, EmployeeDto.class);
	}

	@CachePut(key = KEY, value = VALUE)
	@Override
	public EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employeeDto) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId));

		employee.setEmployeeName(employeeDto.getEmployeeName());
		employee.setEmployeeSalary(employeeDto.getEmployeeSalary());
		employee.setEmployeeAddress(employeeDto.getEmployeeAddress());

		Employee updatedEmployee = employeeRepository.save(employee);

		return modelMapper.map(updatedEmployee, EmployeeDto.class);
	}

	@CacheEvict(key = KEY, value = VALUE)
	@Override
	public String deleteEmployee(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId));

		employeeRepository.delete(employee);

		return appProperties.getMessages().get(EMPLOYEE_DELETION_SUCCEEDED) + employeeId;
	}

	@Override
	public EmployeeResponse getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Employee> page = employeeRepository.findAll(pageable);

		List<Employee> employees = page.getContent();

		List<EmployeeDto> employeesList = employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());

		return EmployeeResponse.builder().employees(employeesList).pageNo(pageNo).pageSize(pageSize).sortBy(sortBy)
				.totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).isFirst(page.isFirst())
				.isLast(page.isLast()).build();
	}

}
