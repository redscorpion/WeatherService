package com.tieto.services.weather.model.provider;

import java.util.Set;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;

/**
 * Weather provider is used by weather service to get the weather data.
 * 
 * @author sirokkam
 * 
 */
public interface IWeatherProvider {

	/**
	 * Returns all known locations.
	 * 
	 * @return {@link ILocation} set of known locations.
	 */
	public Set<ILocation> getKnownLocation();

	/**
	 * Returns weather for specified <strong>location</strong>.
	 * 
	 * @return {@link com.tieto.services.weather.model.IWeather} location
	 *         weather or <code>null</code> if weather data are not available
	 *         for specified location.
	 */
	public IWeather getWeather(ILocation location);

}
