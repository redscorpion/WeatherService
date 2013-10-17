package com.tieto.services.weather.model;

/**
 * Factory for weather data.
 * 
 * @author sirokkam
 * 
 */
public interface IWeatherModelFactory {
	public IWeather createWeather();

	public ILocation createLocation();

}
