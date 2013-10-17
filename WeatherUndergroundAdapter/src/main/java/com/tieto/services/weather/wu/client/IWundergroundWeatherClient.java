package com.tieto.services.weather.wu.client;

import com.tieto.services.weather.wu.client.json.beans.WundergroundWeather;

/**
 * Client for the Weather Underground weather API.
 * 
 * @author sirokkam
 * 
 */
public interface IWundergroundWeatherClient {

	/**
	 * Returns weather in requested location.
	 * 
	 * @param locationCode
	 *            WU location code
	 * @return {@link WundergroundWeather} weather in requested location
	 */
	public WundergroundWeather getWeather(String locationCode);

}