package com.tieto.services.weather.endpoint.rest.mapper.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.endpoint.rest.mapper.IRestWeatherEndpointMapper;
import com.tieto.services.weather.endpoint.soap.mapper.ISoapWeatherEndpointMapper;
import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeatherModelFactory;
import com.tieto.services.weather.response.mapper.ILocationMapper;
import com.tieto.services.weather.response.mapper.impl.AbstractWeatherEndpointMapper;
import com.tieto.services.weather.service.exception.client.InvalidLocationClientException;

/**
 * Implementation of {@link ISoapWeatherEndpointMapper}
 * 
 * @author sirokkam
 * 
 */
@Component
public class RestWeatherEndpointMapper extends AbstractWeatherEndpointMapper
		implements IRestWeatherEndpointMapper {

	protected final Logger logger = LoggerFactory
			.getLogger(RestWeatherEndpointMapper.class);

	@Autowired
	public RestWeatherEndpointMapper(
			final IWeatherModelFactory weatherModelFactory,
			final ILocationMapper locationMapper) {
		super(weatherModelFactory, locationMapper);
	}

	@Override
	public List<ILocation> mapRequest(final String locationCode)
			throws InvalidLocationClientException {
		if (locationCode == null) {
			return null;
		}

		final ILocation location = mapRequestLocation(locationCode);

		if (location != null) {
			return Collections.singletonList(location);
		} else {
			throw new InvalidLocationClientException(
					"No location mapping found for location '" + locationCode
							+ "'.");
		}
	}
}
