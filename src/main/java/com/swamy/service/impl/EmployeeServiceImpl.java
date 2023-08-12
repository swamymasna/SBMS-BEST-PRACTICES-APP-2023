package com.swamy.service.impl;

import static com.swamy.utils.AppConstants.EMPLOYEE_DELETION_SUCCEEDED;
import static com.swamy.utils.AppConstants.EMPLOYEE_NOT_FOUND;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_DELETE_BUSINESS_EXCEPTION;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_FETCHALL_BUSINESS_EXCEPTION;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_FETCH_ALL_PAGINATION_BUSINESS_EXCEPTION;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_SAVE_BUSINESS_EXCEPTION;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_UPDATE_BUSINESS_EXCEPTION;
import static com.swamy.utils.AppConstants.KEY;
import static com.swamy.utils.AppConstants.VALUE;

import java.util.Collections;
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
import com.swamy.exception.EmployeeServiceBusinessException;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.props.AppProperties;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.EmployeeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	private ModelMapper modelMapper;

	private AppProperties appProperties;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

		EmployeeDto employeeResponse = null;

		try {
			Employee employee = modelMapper.map(employeeDto, Employee.class);

			Employee savedEmployee = employeeRepository.save(employee);

			employeeResponse = modelMapper.map(savedEmployee, EmployeeDto.class);

		} catch (Exception e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_SAVE_BUSINESS_EXCEPTION));
		}

		return employeeResponse;
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		List<EmployeeDto> employeesList = null;
		try {
			List<Employee> employees = employeeRepository.findAll();

			if (!employees.isEmpty()) {
				employeesList = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
						.collect(Collectors.toList());
			} else {
				employeesList = Collections.emptyList();
			}

		} catch (Exception e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_FETCHALL_BUSINESS_EXCEPTION));

		}

		return employeesList;

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

		EmployeeDto updateEmployeeResponse = null;

		try {
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(
					appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId));

			employee.setEmployeeName(employeeDto.getEmployeeName());
			employee.setEmployeeSalary(employeeDto.getEmployeeSalary());
			employee.setEmployeeAddress(employeeDto.getEmployeeAddress());

			Employee updatedEmployee = employeeRepository.save(employee);
			updateEmployeeResponse = modelMapper.map(updatedEmployee, EmployeeDto.class);
		} catch (Exception e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_UPDATE_BUSINESS_EXCEPTION));

		}

		return updateEmployeeResponse;
	}

	@CacheEvict(key = KEY, value = VALUE)
	@Override
	public String deleteEmployee(Integer employeeId) {

		String deletedEmployee = null;

		try {
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(
					appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId));

			employeeRepository.delete(employee);

			deletedEmployee = appProperties.getMessages().get(EMPLOYEE_DELETION_SUCCEEDED) + employeeId;
		} catch (Exception e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_DELETE_BUSINESS_EXCEPTION));

		}

		return deletedEmployee;
	}

	@Override
	public EmployeeResponse getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {

		EmployeeResponse employeeResponse = null;

		try {
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

			Page<Employee> page = employeeRepository.findAll(pageable);

			List<Employee> employees = page.getContent();

			List<EmployeeDto> employeesList = employees.stream()
					.map(employee -> modelMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());

			employeeResponse = EmployeeResponse.builder().employees(employeesList).pageNo(pageNo).pageSize(pageSize)
					.sortBy(sortBy).totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
					.isFirst(page.isFirst()).isLast(page.isLast()).build();

		} catch (Exception e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_FETCH_ALL_PAGINATION_BUSINESS_EXCEPTION));

		}

		return employeeResponse;

	}

}
