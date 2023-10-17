package com.swamy.service.impl;

import static com.swamy.utils.AppConstants.EMPLOYEE_DELETION_SUCCEEDED;
import static com.swamy.utils.AppConstants.EMPLOYEE_NOT_FOUND;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_FETCHALL_BUSINESS_EXCEPTION;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_FETCH_ALL_PAGINATION_BUSINESS_EXCEPTION;
import static com.swamy.utils.AppConstants.EMPLOYEE_SERVICE_SAVE_BUSINESS_EXCEPTION;
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

import com.swamy.dto.EmployeeApiResponse;
import com.swamy.dto.EmployeeRequest;
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
	public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {

		EmployeeResponse employeeResponse = null;

		try {
			Employee employee = modelMapper.map(employeeRequest, Employee.class);

			Employee savedEmployee = employeeRepository.save(employee);

			employeeResponse = modelMapper.map(savedEmployee, EmployeeResponse.class);

		} catch (EmployeeServiceBusinessException e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_SAVE_BUSINESS_EXCEPTION));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeeResponse;
	}

	@Override
	public List<EmployeeResponse> getAllEmployees() {

		List<EmployeeResponse> employeesList = null;
		try {
			List<Employee> employees = employeeRepository.findAll();

			if (!employees.isEmpty()) {
				employeesList = employees.stream().map(employee -> modelMapper.map(employee, EmployeeResponse.class))
						.collect(Collectors.toList());
			} else {
				employeesList = Collections.emptyList();
			}

		} catch (EmployeeServiceBusinessException e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_FETCHALL_BUSINESS_EXCEPTION));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeesList;

	}

	@Cacheable(key = KEY, value = VALUE)
	@Override
	public EmployeeResponse getEmployeeById(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId));
		return modelMapper.map(employee, EmployeeResponse.class);

	}

	@CachePut(key = KEY, value = VALUE)
	@Override
	public EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest employeeRequest) {

		EmployeeResponse updateEmployeeResponse = null;

		try {
			Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(
					appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId));

			employee.setEmployeeName(employeeRequest.getEmployeeName());
			employee.setEmployeeSalary(employeeRequest.getEmployeeSalary());
			employee.setEmployeeAddress(employeeRequest.getEmployeeAddress());

			Employee updatedEmployee = employeeRepository.save(employee);
			updateEmployeeResponse = modelMapper.map(updatedEmployee, EmployeeResponse.class);
		} catch (EmployeeServiceBusinessException e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId);

		} catch (Exception e) {
			e.printStackTrace();
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
		} catch (EmployeeServiceBusinessException e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_NOT_FOUND) + employeeId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deletedEmployee;
	}

	@Override
	public EmployeeApiResponse getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {

		EmployeeApiResponse employeeResponse = null;

		try {
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

			Page<Employee> page = employeeRepository.findAll(pageable);

			List<Employee> employees = page.getContent();

			List<EmployeeResponse> employeesList = employees.stream()
					.map(employee -> modelMapper.map(employee, EmployeeResponse.class)).collect(Collectors.toList());

			employeeResponse = EmployeeApiResponse.builder().employees(employeesList).pageNo(pageNo).pageSize(pageSize)
					.sortBy(sortBy).totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
					.isFirst(page.isFirst()).isLast(page.isLast()).build();

		} catch (EmployeeServiceBusinessException e) {
			throw new EmployeeServiceBusinessException(
					appProperties.getMessages().get(EMPLOYEE_SERVICE_FETCH_ALL_PAGINATION_BUSINESS_EXCEPTION));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeeResponse;

	}

}
