package com.swamy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Around(value = "execution(* com.swamy.controller.*.*(..))")
	public Object aroundAdviceForController(ProceedingJoinPoint joinPoint) throws Throwable {

		ObjectMapper mapper = new ObjectMapper();

		String methodName = joinPoint.getSignature().getName();

		String className = joinPoint.getTarget().getClass().toString();

		Object[] args = joinPoint.getArgs();

		log.info("Method Invoked : " + className + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(args));

		Object object = joinPoint.proceed();

		log.info(className + " : " + methodName + "() " + "Response : " + mapper.writeValueAsString(object));

		log.info("Returning Back from : " + className + " : " + methodName + "() method");

		return object;
	}
	
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

		log.info("Returning Back from : " + className + " : " + methodName + "() method");
		
		return object;
	}
}
