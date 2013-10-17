package com.tieto.services.weather.response.mapper.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeatherModelFactory;
import com.tieto.services.weather.response.mapper.ILocationMapper;

@Component
public class PropertiesBasedLocationMapper implements ILocationMapper {

	private static final String LOCATION_MAPPING_DELIMITER = "/";

	private final Map<String, ILocation> requestLocationMapping = new HashMap<>();

	private final Map<ILocation, String> responseLocationMapping = new HashMap<>();

	private final IWeatherModelFactory weatherModelFactory;

	@Autowired
	public PropertiesBasedLocationMapper(final Properties requestMapping,
			final Properties responseMapping,
			final IWeatherModelFactory weatherModelFactory) {
		this.weatherModelFactory = weatherModelFactory;
		initializeRequestLocationMapping(requestMapping);
		initializeResponseLocationMapping(responseMapping);
	}

	public String mapResponseLocation(final ILocation location) {

		if (location == null) {
			return null;
		}

		return responseLocationMapping.get(location);
	}

	public ILocation mapRequestLocation(final String locationCode) {

		if (locationCode == null) {
			return null;
		}

		final String key = getLocationMappingKey(locationCode);

		final ILocation location = requestLocationMapping.get(key);

		if (location == null) {
			return null;
		}

		return location.copy();
	}

	private String getLocationMappingKey(final String location) {
		return location;
	}

	private ILocation buildLocationFromMapping(final String mapping) {
		try {
			String[] parts = mapping.split(LOCATION_MAPPING_DELIMITER);

			final String country = parts[0];
			final String state = parts[1];
			final String city = parts[2];

			final ILocation location = weatherModelFactory.createLocation();
			location.setCity(city);
			location.setState(state);
			location.setCountry(country);

			return location;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("Invalid location mapping: '" + mapping
					+ "'", e);
		}
	}

	private void initializeResponseLocationMapping(
			final Properties locationMapping) {
		final Enumeration<?> propNames = locationMapping.propertyNames();
		while (propNames.hasMoreElements()) {
			final String key = (String) propNames.nextElement();
			final String value = locationMapping.getProperty(key);
			final ILocation location = buildLocationFromMapping(key);
			responseLocationMapping.put(location, value);
		}
	}

	private void initializeRequestLocationMapping(
			final Properties locationMapping) {
		final Enumeration<?> propNames = locationMapping.propertyNames();
		while (propNames.hasMoreElements()) {
			final String key = (String) propNames.nextElement();
			final String value = locationMapping.getProperty(key);
			final ILocation location = buildLocationFromMapping(value);
			requestLocationMapping.put(key, location);
		}
	}
}
