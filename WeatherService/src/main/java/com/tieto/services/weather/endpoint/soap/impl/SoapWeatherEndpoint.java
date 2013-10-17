package com.tieto.services.weather.endpoint.soap.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.tieto.services.weather.endpoint.soap.ISoapWeatherEndpoint;
import com.tieto.services.weather.endpoint.soap.SoapWeatherEndpointMethod;
import com.tieto.services.weather.endpoint.soap.mapper.ISoapWeatherEndpointMapper;
import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.response.beans.GetWeatherRequest;
import com.tieto.services.weather.response.beans.GetWeatherResponse;
import com.tieto.services.weather.service.IWeatherService;
import com.tieto.services.weather.service.exception.client.ClientException;

/**
 * SOAP weather service endpoint.
 * 
 * @author sirokkam
 * 
 */
@Endpoint
public class SoapWeatherEndpoint implements ISoapWeatherEndpoint {

	@Autowired
	private ISoapWeatherEndpointMapper mapper;

	@Autowired
	private IWeatherService weatherService;

	public SoapWeatherEndpoint() {
	}

	@Autowired
	public SoapWeatherEndpoint(final IWeatherService weatherService,
			final ISoapWeatherEndpointMapper mapper) {
		this.weatherService = weatherService;
		this.mapper = mapper;
	}

	@PayloadRoot(localPart = GET_WEATHER_REQUEST, namespace = NAMESPACE)
	@ResponsePayload
	@SoapWeatherEndpointMethod
	public GetWeatherResponse getWeather(
			@RequestPayload final GetWeatherRequest request)
			throws ClientException {
		final Collection<ILocation> locations = mapper.mapRequest(request);
		final Collection<IWeather> weather = weatherService
				.getWeather(locations);
		return mapper.mapResponse(weather);
	}

}