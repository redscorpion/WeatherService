package com.tieto.services.weather.service.cache;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;

/**
 * Interface for the weather cache.
 * 
 * @author sirokkam
 * 
 */
public interface IWeatherCache {

	/**
	 * Returns cached weather in specified location.
	 * 
	 * @param location
	 *            {@link ILocation} the location
	 * @return {@link IWeather} location weather
	 * @throws CacheMissException
	 *             if weather is not in cache
	 */
	public IWeather get(ILocation location) throws CacheMissException;

	/**
	 * Adds weather in specified location into cache.
	 * 
	 * @param location
	 *            {@link ILocation} the location
	 * @param weather
	 *            weather to be put to cache
	 * @return <strong>weather</strong>
	 */
	public IWeather put(ILocation location, IWeather weather);

}