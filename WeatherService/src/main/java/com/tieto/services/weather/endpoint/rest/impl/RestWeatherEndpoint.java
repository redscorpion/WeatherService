package com.tieto.services.weather.endpoint.rest.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tieto.services.weather.endpoint.rest.IRestWeatherService;
import com.tieto.services.weather.endpoint.rest.RestWeatherEndpointMethod;
import com.tieto.services.weather.endpoint.rest.mapper.IRestWeatherEndpointMapper;
import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.response.beans.GetWeatherResponse;
import com.tieto.services.weather.service.IWeatherService;
import com.tieto.services.weather.service.exception.client.ClientException;

/**
 * REST weather service endpoint.
 * 
 * @author sirokkam
 * 
 */
@Controller
public class RestWeatherEndpoint implements IRestWeatherService {

	@Autowired
	private IRestWeatherEndpointMapper mapper;

	@Autowired
	private IWeatherService weatherService;

	public RestWeatherEndpoint() {
	}

	@Autowired
	public RestWeatherEndpoint(final IWeatherService weatherProvider,
			final IRestWeatherEndpointMapper mapper) {
		this.weatherService = weatherProvider;
		this.mapper = mapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = ALL_WEATHER_MAPPING)
	@ResponseBody
	@RestWeatherEndpointMethod
	public GetWeatherResponse getWeather() {
		final Collection<IWeather> weather = weatherService.getWeather(null);
		return mapper.mapResponse(weather);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tieto.services.weather.rest.RestWeatherService#getWeather(java.lang
	 * .String)
	 */
	@Override
	@RequestMapping(value = CITY_WEATHER_MAPPING)
	@ResponseBody
	@RestWeatherEndpointMethod
	public GetWeatherResponse getWeather(@PathVariable final String city)
			throws ClientException {
		final Collection<ILocation> locations = mapper.mapRequest(city);
		final Collection<IWeather> weather = weatherService
				.getWeather(locations);
		return mapper.mapResponse(weather);
	}

}
