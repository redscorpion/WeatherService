package com.tieto.services.weather.model.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.model.IWeather;

@Component
public class WeatherModelValidator implements IWeatherModelValidator {

	@Autowired
	private javax.validation.Validator validator;

	public WeatherModelValidator() {
	}

	@Override
	public Set<ConstraintViolation<IWeather>> validate(@Valid IWeather weather)
			throws IllegalArgumentException, ValidationException {
		return validator.validate(weather);
	}

}
