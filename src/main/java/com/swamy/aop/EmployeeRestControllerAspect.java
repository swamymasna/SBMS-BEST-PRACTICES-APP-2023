package com.swamy.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.swamy.dto.EmployeeDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class EmployeeRestControllerAspect {

	// Before Advice for All the Methods
	@Before(value = "execution(* com.swamy.controller.EmployeeRestController.*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		log.info("Entered into " + joinPoint.getSignature());
	}

	// After Advice for All the Methods
	@After(value = "execution(* com.swamy.controller.EmployeeRestController.*(..))")
	public void afterAdvice(JoinPoint joinPoint) {
		log.info("Returning Back From " + joinPoint.getSignature());
	}

	// After Returning Advice for saveEmployee()
	@AfterReturning(value = "execution(* com.swamy.controller.EmployeeRestController.addEmployee(..))", returning = "employeeDto")
	public void afterReturningAdviceForSaveEmployee(JoinPoint joinPoint, EmployeeDto employeeDto) {
		log.info("Employee Saved With Id : {}", employeeDto.getEmployeeId());
	}

	// After Returning Advice for findAllEmployees()
	@AfterReturning(value = "execution(* com.swamy.controller.EmployeeRestController.findAllEmployees(..))", returning = "employees")
	public void afterReturningAdviceForFindAllEmployees(JoinPoint joinPoint, List<EmployeeDto> employees) {
		log.info("Fetching Employees from DB : {}", employees);
	}

	// After Returning Advice for getEmployeeById()
	@AfterReturning(value = "execution(* com.swamy.controller.EmployeeRestController.getEmployeeById(..))", returning = "employee")
	public void afterReturningAdviceForFindAllEmployees(JoinPoint joinPoint, EmployeeDto employee) {
		log.info("Fetching Employee By Id from DB : {}", employee);
	}

	// After Returning Advice for updateEmployee()
	@AfterReturning(value = "execution(* com.swamy.controller.EmployeeRestController.updateEmployee(..))", returning = "employee")
	public void afterReturningAdviceForUpdateEmployee(JoinPoint joinPoint, EmployeeDto employee) {
		log.info("Updating Employee By Id : {}", employee);
	}

	// After Returning Advice for deleteEmployee()
	@AfterReturning(value = "execution(* com.swamy.controller.EmployeeRestController.deleteEmployee(..))", returning = "message")
	public void afterReturningAdviceForDeleteEmployee(JoinPoint joinPoint, String message) {
		log.info("Deleting Employee By Id : {}", message);
	}

	// After Throwing Advice for saveEmployee()
	@AfterThrowing(value = "execution(* com.swamy.controller.EmployeeRestController.addEmployee(..))", throwing = "exception")
	public void afterThrowingAdviceForSaveEmployee(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Saving Employee : {}", exception.getMessage());
	}

	// After Throwing Advice for findAllEmployees()
	@AfterThrowing(value = "execution(* com.swamy.controller.EmployeeRestController.findAllEmployees(..))", throwing = "exception")
	public void afterThrowingAdviceForFindAllEmployees(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Fetching Employees from DB : {}", exception.getMessage());
	}

	// After Throwing Advice for getEmployeeById()
	@AfterThrowing(value = "execution(* com.swamy.controller.EmployeeRestController.getEmployeeById(..))", throwing = "exception")
	public void afterThrowingAdviceForGetEmployeeById(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Fetching Employee By Id from DB : {}", exception.getMessage());
	}

	// After Throwing Advice for updateEmployee()
	@AfterThrowing(value = "execution(* com.swamy.controller.EmployeeRestController.updateEmployee(..))", throwing = "exception")
	public void afterThrowingAdviceForUpdateEmployee(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Updating Employee By Id : {}", exception.getMessage());
	}

	// After Throwing Advice for deleteEmployee()
	@AfterThrowing(value = "execution(* com.swamy.controller.EmployeeRestController.deleteEmployee(..))", throwing = "exception")
	public void afterThrowingAdviceForDeleteEmployee(JoinPoint joinPoint, Exception exception) {
		log.info("Exception Occured While Deleting Employee By Id : {}", exception.getMessage());
	}

}
