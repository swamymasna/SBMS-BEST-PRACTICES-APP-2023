package com.swamy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_TBL")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_ID")
	private Integer employeeId;

	@Column(name = "EMP_NAME")
	private String employeeName;

	@Column(name = "EMP_SAL")
	private Double employeeSalary;

	@Column(name = "EMP_ADDR")
	private String employeeAddress;

}
