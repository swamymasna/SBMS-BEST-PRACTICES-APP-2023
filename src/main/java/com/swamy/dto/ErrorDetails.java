package com.swamy.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {

	private String message;
	private HttpStatus status;
	private Integer statusCode;
	private LocalDateTime timeStamp;
	private String path;
}
