package com.tieto.services.weather.endpoint.soap.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.endpoint.soap.mapper.ISoapWeatherEndpointMapper;
import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeatherModelFactory;
import com.tieto.services.weather.response.beans.GetWeatherRequest;
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
public class SoapWeatherEndpointMapper extends AbstractWeatherEndpointMapper
		implements ISoapWeatherEndpointMapper {

	protected final Logger logger = LoggerFactory
			.getLogger(SoapWeatherEndpointMapper.class);

	@Autowired
	public SoapWeatherEndpointMapper(
			final IWeatherModelFactory weatherModelFactory,
			final ILocationMapper locationMapper) {
		super(weatherModelFactory, locationMapper);
	}

	@Override
	public List<ILocation> mapRequest(final GetWeatherRequest weatherRequest)
			throws InvalidLocationClientException {

		if (weatherRequest == null) {
			return null;
		}

		final List<String> locations = weatherRequest.getLocation();

		if (locations.size() == 0) {
			return null;
		}

		final List<ILocation> result = new ArrayList<>();

		for (String loc : locations) {
			final ILocation location = mapRequestLocation(loc);
			if (location == null) {
				throw new InvalidLocationClientException(
						"No location mapping found for location '" + loc + "'.");
			}
			result.add(location);
		}

		return result;
	}

}
