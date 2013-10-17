package com.tieto.services.weather.endpoint.soap.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.endpoint.soap.mapper.ISoapWeatherEndpointMapper;
import com.tieto.services.weather.response.beans.GetWeatherResponse;

@Aspect
@Component
public class SoapWeatherEndpointErrorHandlerAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(SoapWeatherEndpointErrorHandlerAspect.class);

	@Autowired
	private ISoapWeatherEndpointMapper mapper;

	@Around("@annotation(com.tieto.services.weather.endpoint.soap.SoapWeatherEndpointMethod)")
	public GetWeatherResponse handleGetWeatherExceptions(
			ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return (GetWeatherResponse) joinPoint.proceed();
		} catch (Exception exception) {
			logger.error("Exception in "
					+ joinPoint.getSignature().toShortString(), exception);
			return mapper.mapException(exception);
		}
	}
}
