package com.swamy.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.swamy.dto.EmployeeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class EmployeeServiceAspect {

	// Before Advice for All the Methods
	@Before(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		log.info("Entered into " + joinPoint.getSignature());
	}

	// After Advice for All the Methods
	@After(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.*(..))")
	public void afterAdvice(JoinPoint joinPoint) {
		log.info("Returning Back From " + joinPoint.getSignature());
	}

	// After Returning Advice Logic for saveEmployee()
	@AfterReturning(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.saveEmployee(..))", returning = "employeeDto")
	public void afterReturningAdviceForSaveEmployee(JoinPoint joinPoint, EmployeeDto employeeDto) {
		log.info("Employee Saved With Id : {}", employeeDto.getEmployeeId());
	}

	// After Returning Advice Logic for getAllEmployees()
	@AfterReturning(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.getAllEmployees(..))", returning = "employees")
	public void afterReturningAdviceForGetAllEmployees(JoinPoint joinPoint, List<EmployeeDto> employees) {
		log.info("Fetching Employees From DB : {}", employees);
	}

	// After Returning Advice Logic for getEmployeeById()
	@AfterReturning(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.getEmployeeById(..))", returning = "employee")
	public void afterReturningAdviceForGetEmployeeById(JoinPoint joinPoint, EmployeeDto employee) {
		log.info("Fetching Employee By Id From DB : {}", employee);
	}

	// After Returning Advice Logic for updateEmployee()
	@AfterReturning(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.updateEmployee(..))", returning = "employee")
	public void afterReturningAdviceForUpdateEmployee(JoinPoint joinPoint, EmployeeDto employee) {
		log.info("Updating Employee By Id : {}", employee);
	}

	// After Returning Advice Logic for deleteEmployee()
	@AfterReturning(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.deleteEmployee(..))", returning = "message")
	public void afterReturningAdviceForDeleteEmployee(JoinPoint joinPoint, String message) {
		log.info("Deleting Employee By Id : {}", message);
	}

	// After Throwing Advice Logic for saveEmployee()
	@AfterThrowing(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.saveEmployee(..))", throwing = "exception")
	public void afterThrowingAdviceForSaveEmployee(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Saving Employee : {}", exception.getMessage());
	}

	// After Throwing Advice Logic for getAllEmployees()
	@AfterThrowing(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.getAllEmployees(..))", throwing = "exception")
	public void afterThrowingAdviceForGetAllEmployees(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Fetching Employees From DB : {}", exception.getMessage());
	}

	// After Throwing Advice Logic for getEmployeeById()
	@AfterThrowing(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.getEmployeeById(..))", throwing = "exception")
	public void afterThrowingAdviceForGetEmployeeById(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Fetching Employee By Id From DB : {}", exception.getMessage());
	}

	// After Throwing Advice Logic for updateEmployee()
	@AfterThrowing(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.updateEmployee(..))", throwing = "exception")
	public void afterThrowingAdviceForUpdateEmployee(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Updating Employee By Id : {}", exception.getMessage());
	}

	// After Throwing Advice Logic for deleteEmployee()
	@AfterThrowing(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.deleteEmployee(..))", throwing = "exception")
	public void afterThrowingAdviceForDeleteEmployee(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Deleting Employee By Id : {}", exception.getMessage());
	}

	// Around Advice for Save Employee
	@SuppressWarnings("unchecked")
	@Around(value = "execution(* com.swamy.service.impl.EmployeeServiceImpl.saveEmployee(..))")
	public EmployeeDto aroundAdviceForSaveEmployee(ProceedingJoinPoint joinPoint) {

		try {
			return (EmployeeDto) joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return null;

	}

}
