package com.swamy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Pointcut(value = "execution(* com.swamy.service.*.*(..)) || execution(* com.swamy.controller.*.*(..))")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

		ObjectMapper mapper = new ObjectMapper();

		String methodName = joinPoint.getSignature().getName();

		String className = joinPoint.getTarget().getClass().getName();

		Object[] args = joinPoint.getArgs();

		log.info("Method Invoked : " + className + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(args));

		Object object = joinPoint.proceed();

		log.info(className + " : " + methodName + "() " + "Response : " + mapper.writeValueAsString(object));

		log.info("Returning Back from : " + className + " : " + methodName + "() method");

		return object;
	}

}
