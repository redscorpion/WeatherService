package com.tieto.services.weather.wu.service.mapper.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.IWeatherModelFactory;
import com.tieto.services.weather.model.IWeather.WindDirection;
import com.tieto.services.weather.wu.client.json.beans.WundergroundLocation;
import com.tieto.services.weather.wu.client.json.beans.WundergroundObservation;
import com.tieto.services.weather.wu.client.json.beans.WundergroundWeather;
import com.tieto.services.weather.wu.service.mapper.IWundergroundMapper;

/**
 * Converts from Weather Underground weather data to the {@link IWeather}.
 * 
 * @author sirokkam
 * 
 */
@Component
public final class WundergroundMapper implements IWundergroundMapper {

	private static final String LOCATION_MAPPING_DELIMITER = "/";

	private final Map<ILocation, String> requestLocationMapping = new HashMap<>();
	private final Map<String, ILocation> responseLocationMapping = new HashMap<>();

	private final IWeatherModelFactory weatherModelFactory;

	@Autowired
	public WundergroundMapper(final IWeatherModelFactory weatherModelFactory) {
		this.weatherModelFactory = weatherModelFactory;
	}

	@Override
	public IWeather mapResponse(final WundergroundWeather response) {

		if (response == null || response.getObservation() == null
				|| response.getObservation().getLocation() == null) {
			return null;
		}

		final ILocation location = mapResponseLocation(response
				.getObservation().getLocation());

		final IWeather weather = weatherModelFactory.createWeather();

		weather.setLocation(location);
		weather.setObservationTime(mapObservationTime(response.getObservation()));
		weather.setRelativeHumidity(response.getObservation().getHumidity());
		weather.setTemperature(new BigDecimal(response.getObservation()
				.getTemperature()));
		weather.setWeatherDescription(response.getObservation()
				.getWeatherDescription());
		weather.setWindDirection(mapWindDirection(response.getObservation()
				.getWindDirection()));
		weather.setWindDescription(response.getObservation()
				.getWindDescription());

		return weather;
	}

	@Override
	public String mapRequest(final ILocation requestedLocation) {

		if (requestedLocation == null) {
			return null;
		}

		return mapRequestLocation(requestedLocation);
	}

	@Override
	public Set<ILocation> getKnownLocations() {
		return Collections.unmodifiableSet(requestLocationMapping.keySet());
	}

	/*
	 * Converts "epoch" to Gregorian calendar.
	 */
	protected GregorianCalendar mapObservationTime(
			final WundergroundObservation observation) {
		GregorianCalendar dateTime = new GregorianCalendar(
				TimeZone.getTimeZone(observation.getTimeZone()));
		dateTime.setTimeInMillis(observation.getTime() * 1000);
		return dateTime;
	}

	protected WindDirection mapWindDirection(final String windDir) {
		return WindDirectionMapping.valueOf(windDir).map();
	}

	protected String mapRequestLocation(final ILocation location) {

		if (location == null) {
			return null;
		}

		return requestLocationMapping.get(location);
	}

	protected ILocation mapResponseLocation(
			final WundergroundLocation wuLocation) {

		if (wuLocation == null) {
			return null;
		}

		final String key = getLocationMappingKey(wuLocation);

		final ILocation location = responseLocationMapping.get(key);

		if (location == null) {
			return null;
		}

		return location.copy();
	}

	@Autowired
	public void setRequestLocationMapping(
			@Qualifier(value = "wu_config_requestLocationMapping") final Properties locationMapping) {
		final Enumeration<?> propNames = locationMapping.propertyNames();
		while (propNames.hasMoreElements()) {
			final String key = (String) propNames.nextElement();
			final String value = locationMapping.getProperty(key);
			final ILocation location = buildLocationFromMapping(key);
			requestLocationMapping.put(location, value);
		}
	}

	@Autowired
	public void setResponseLocationMapping(
			@Qualifier(value = "wu_config_responseLocationMapping") final Properties locationMapping) {
		final Enumeration<?> propNames = locationMapping.propertyNames();
		while (propNames.hasMoreElements()) {
			final String key = (String) propNames.nextElement();
			final String value = locationMapping.getProperty(key);
			final ILocation location = buildLocationFromMapping(value);
			responseLocationMapping.put(key, location);
		}
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

	private String getLocationMappingKey(final WundergroundLocation wuLocation) {
		StringBuilder key = new StringBuilder();

		if (wuLocation.getCountry() != null
				&& !wuLocation.getCountry().isEmpty()) {
			key.append(wuLocation.getCountry());
		}

		key.append(LOCATION_MAPPING_DELIMITER);

		if (wuLocation.getState() != null && !wuLocation.getState().isEmpty()) {
			key.append(wuLocation.getState());
		}

		key.append(LOCATION_MAPPING_DELIMITER);

		if (wuLocation.getCity() != null && !wuLocation.getCity().isEmpty()) {
			key.append(wuLocation.getCity());
		}

		return key.toString();
	}
}
