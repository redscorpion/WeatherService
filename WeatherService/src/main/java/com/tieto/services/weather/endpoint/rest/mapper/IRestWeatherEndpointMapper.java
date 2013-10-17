package com.tieto.services.weather.endpoint.rest.mapper;

import java.util.List;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.response.mapper.IWeatherEndpointMapper;
import com.tieto.services.weather.service.exception.client.InvalidLocationClientException;

/**
 * Converts {@link IWeather} to the weather service response
 * {@link GetWeatherResponse} format and {@link GetWeatherRequest} to the weather
 * service request format.
 * 
 * @author sirokkam
 * 
 */
public interface IRestWeatherEndpointMapper extends IWeatherEndpointMapper {

	public List<ILocation> mapRequest(String city)
			throws InvalidLocationClientException;

}