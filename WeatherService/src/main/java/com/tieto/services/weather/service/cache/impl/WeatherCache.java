package com.tieto.services.weather.service.cache.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.service.cache.CacheMissException;
import com.tieto.services.weather.service.cache.IWeatherCache;

/**
 * Implementation of the weather cache.
 * 
 * @author sirokkam
 * 
 */
@Component
public class WeatherCache implements IWeatherCache {

	@Override
	@Cacheable(value = "weatherCache", key = "#location")
	public IWeather get(final ILocation location) throws CacheMissException {
		throw new CacheMissException();
	}

	@Override
	@CachePut(value = "weatherCache", key = "#location")
	public IWeather put(final ILocation location, final IWeather weather) {
		return weather;
	}
}
