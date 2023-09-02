package com.swamy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmployeeResponse {

	private Integer employeeId;

	private String employeeName;

	private Double employeeSalary;

	private String employeeAddress;

}
