package com.tieto.services.weather.endpoint.rest;

import com.tieto.services.weather.response.beans.GetWeatherResponse;
import com.tieto.services.weather.service.exception.client.ClientException;

/**
 * Interface for the REST weather service.
 * 
 * @author sirokkam
 * 
 */
public interface IRestWeatherService {

	public static final String CITY_WEATHER_MAPPING = "/weather/{city}";
	public static final String ALL_WEATHER_MAPPING = "/weather";

	/**
	 * Returns weather data for all known cities.
	 */
	public GetWeatherResponse getWeather();

	/**
	 * Returns weather data for specified city.
	 * 
	 * @throws ClientException
	 */
	public GetWeatherResponse getWeather(String city)
			throws ClientException;

}