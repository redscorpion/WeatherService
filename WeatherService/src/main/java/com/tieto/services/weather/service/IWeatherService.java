package com.tieto.services.weather.service;

import java.util.Collection;
import java.util.Set;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;

/**
 * Interface for weather service.
 * 
 * @author sirokkam
 * 
 */
public interface IWeatherService {

	/**
	 * Returns all available locations.
	 * 
	 * @return {@link ILocation} set of known locations.
	 */
	public Set<ILocation> getKnownLocations();

	/**
	 * Returns weather in requested <strong>locations</strong>. If the
	 * <strong>cities</strong> parameter is <strong>null</strong> then the
	 * method will return weather in all known location.
	 * 
	 * @param locations
	 *            requested locations, or <strong>null</strong>
	 * @return list of {@link IWeather} locations weather.
	 */
	public Collection<IWeather> getWeather(Collection<ILocation> cities);

}