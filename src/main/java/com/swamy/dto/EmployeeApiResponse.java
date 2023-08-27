package com.swamy.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeApiResponse {

	private List<EmployeeResponse> employees;
	private Integer pageNo;
	private Integer pageSize;
	private String sortBy;
	private Long totalElements;
	private Integer totalPages;
	private Boolean isFirst;
	private Boolean isLast;
}
