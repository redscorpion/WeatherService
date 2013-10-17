package com.tieto.services.weather.model.impl;

import org.springframework.stereotype.Component;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.IWeatherModelFactory;

/**
 * Default simple implementation of the {@link WeatherModelFactory}.
 * 
 * @author sirokkam
 */
@Component
public class WeatherModelFactory implements IWeatherModelFactory {

	@Override
	public IWeather createWeather() {
		return new Weather();
	}

	@Override
	public ILocation createLocation() {
		return new Location();
	}
}
