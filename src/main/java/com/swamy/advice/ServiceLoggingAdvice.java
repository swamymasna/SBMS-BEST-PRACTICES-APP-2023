package com.swamy.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAdvice {

	@Around(value = "execution(* com.swamy.service.*.*(..))")
	public Object aroundAdviceForService(ProceedingJoinPoint joinPoint) throws Throwable {

		ObjectMapper mapper = new ObjectMapper();

		String methodName = joinPoint.getSignature().getName();

		String className = joinPoint.getTarget().getClass().toString();

		Object[] args = joinPoint.getArgs();

		log.info("Method Invoked : " + className + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(args));

		Object object = joinPoint.proceed();

		log.info(className + " : " + methodName + "() " + "Response : " + mapper.writeValueAsString(object));

		return object;
	}
}
