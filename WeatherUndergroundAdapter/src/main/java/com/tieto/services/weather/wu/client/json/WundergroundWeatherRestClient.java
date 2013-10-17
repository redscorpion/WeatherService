package com.tieto.services.weather.wu.client.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tieto.services.weather.wu.client.IWundergroundWeatherClient;
import com.tieto.services.weather.wu.client.json.beans.WundergroundWeather;

/**
 * Client for the Weather Underground REST weather API.
 * 
 * @author sirokkam
 * 
 */
@Component
public class WundergroundWeatherRestClient implements
		IWundergroundWeatherClient {

	private static final String JSON_EXTENSION = ".json";

	private static final Logger logger = LoggerFactory
			.getLogger(WundergroundWeatherRestClient.class);

	private static final String WEATHER_API_URL_TEMPLATE = "http://api.wunderground.com/api/${API_KEY}/forecast/geolookup/conditions/q/";

	private final String weatherApiUrlBase;

	@Autowired
	public WundergroundWeatherRestClient(
			@Qualifier(value = "wu_config_apiKey") final String apiKey)
			throws RuntimeException {

		if (apiKey == null || apiKey.isEmpty()) {
			throw new IllegalArgumentException("API key is null!");
		}

		weatherApiUrlBase = WEATHER_API_URL_TEMPLATE.replace("${API_KEY}",
				apiKey);
	}

	@Override
	public final WundergroundWeather getWeather(final String locationCode) {
		WundergroundWeather weatherData = null;

		try {
			final String url = weatherApiUrlBase + locationCode
					+ JSON_EXTENSION;
			final RestTemplate restTemplate = new RestTemplate();

			logger.info("Fetching weather data for '" + locationCode + "'.");
			weatherData = restTemplate.getForObject(url,
					WundergroundWeather.class);

			if (weatherData == null || weatherData.getObservation() == null
					|| weatherData.getObservation().getLocation() == null) {
				logger.error("Error when parsing weather data, locationCode: \""
						+ locationCode + "\"");
				return null;
			}

		} catch (RestClientException e) {
			logger.error("Error when fetching weather data, locationCode: \""
					+ locationCode + "\"", e);
			// swallow, in case of error method returns null
		}

		return weatherData;
	}

}