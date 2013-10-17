package com.tieto.services.weather.response.mapper;

import java.util.Collection;

import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.response.beans.GetWeatherResponse;

/**
 * Converts {@link IWeather} to the weather service response
 * {@link GetWeatherResponse} format.
 * 
 * @author sirokkam
 * 
 */
public interface IWeatherEndpointMapper {

	public GetWeatherResponse mapResponse(Collection<IWeather> response);

	public GetWeatherResponse mapException(Exception exception);

}