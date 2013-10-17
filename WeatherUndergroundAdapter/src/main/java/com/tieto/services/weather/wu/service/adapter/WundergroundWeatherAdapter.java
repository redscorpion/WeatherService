package com.tieto.services.weather.wu.service.adapter;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.provider.IWeatherProvider;
import com.tieto.services.weather.wu.client.IWundergroundWeatherClient;
import com.tieto.services.weather.wu.client.json.beans.WundergroundWeather;
import com.tieto.services.weather.wu.service.mapper.IWundergroundMapper;

/**
 * Implementation of the {@link IWeatherProvider} on top of the Weather
 * Underground REST API.
 * 
 * @author sirokkam
 */
@Component
public class WundergroundWeatherAdapter implements IWeatherProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(WundergroundWeatherAdapter.class);

	private final IWundergroundWeatherClient wuWeatherClient;

	private final IWundergroundMapper mapper;

	@Autowired
	public WundergroundWeatherAdapter(
			final IWundergroundWeatherClient wuWeatherClient,
			final IWundergroundMapper mapper) {
		this.wuWeatherClient = wuWeatherClient;
		this.mapper = mapper;
	}

	@Override
	public IWeather getWeather(final ILocation location) {

		String locationCode = mapper.mapRequest(location);

		if (locationCode == null) {
			logger.error("Unknown location: \"" + location + "\"");
			return null;
		}

		final WundergroundWeather weatherData = wuWeatherClient
				.getWeather(locationCode);

		if (weatherData == null) {
			return null;
		}

		return mapper.mapResponse(weatherData);
	}

	@Override
	public Set<ILocation> getKnownLocation() {
		return mapper.getKnownLocations();
	}

}