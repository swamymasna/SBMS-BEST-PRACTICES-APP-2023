package com.swamy.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeResponse {

	private Integer employeeId;

	@NotEmpty(message = "Employee Name Must Not Be Null Or Empty")
	@Size(min = 2, message = "Employee Name should have Atleast 2 charecters")
	private String employeeName;

	@Min(value = 5000, message = "Employee Salary Can't be Less Than 5000")
	@Max(value = 500000, message = "Employee Salary Can't be Greater Than 500000")
	private Double employeeSalary;

	@NotEmpty(message = "Employee Address Must Not Be Null Or Empty")
	@Size(min = 2, message = "Employee Address should have Atleast 2 charecters")
	private String employeeAddress;

}
