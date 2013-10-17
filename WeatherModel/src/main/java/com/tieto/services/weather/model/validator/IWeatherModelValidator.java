package com.tieto.services.weather.model.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import com.tieto.services.weather.model.IWeather;

public interface IWeatherModelValidator {

	/**
	 * Validates all constraints on {@code weather}.
	 * 
	 * @param weather
	 *            weather to validate
	 * @return constraint violations or an empty set if none
	 * @throws IllegalArgumentException
	 *             if weather is {@code null}
	 * @throws ValidationException
	 *             if a non recoverable error happens during the validation
	 *             process
	 */
	public Set<ConstraintViolation<IWeather>> validate(IWeather weather)
			throws IllegalArgumentException, ValidationException;

}
