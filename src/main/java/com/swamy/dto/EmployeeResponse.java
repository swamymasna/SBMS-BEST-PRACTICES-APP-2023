package com.swamy.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {

	private List<EmployeeDto> employees;
	private Integer pageNo;
	private Integer pageSize;
	private String sortBy;
	private Long totalElements;
	private Integer totalPages;
	private Boolean isFirst;
	private Boolean isLast;
}
