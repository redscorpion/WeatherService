package com.tieto.services.weather.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.tieto.services.weather.endpoint.rest.mapper.impl.RestWeatherEndpointMapper;
import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.impl.WeatherModelFactory;
import com.tieto.services.weather.response.beans.GetWeatherResponse;
import com.tieto.services.weather.response.mapper.impl.PropertiesBasedLocationMapper;
import com.tieto.services.weather.service.exception.client.ClientException;

public class RestWeatherEndpointMapperTest {

	private RestWeatherEndpointMapper mapper;

	@Before
	public void before() {

		Properties requestLocationMapping = TestUtils
				.createTestRequestLocationMapping();
		Properties responseLocationMapping = TestUtils
				.createTestResponseLocationMapping();

		WeatherModelFactory weatherModelFactory = new WeatherModelFactory();

		mapper = new RestWeatherEndpointMapper(weatherModelFactory,
				new PropertiesBasedLocationMapper(requestLocationMapping,
						responseLocationMapping, weatherModelFactory));
	}

	@Test
	public void testConvertCityAWeather() throws Exception {
		GetWeatherResponse response = mapper.mapResponse(Collections
				.<IWeather> singletonList(TestUtils.createWeatherCityA()));
		assertNotNull(response);
		assertNotNull(response.getResponse());
		assertEquals(1, response.getResponse().size());
		assertEquals(
				true,
				TestUtils.compare(response.getResponse().get(0),
						TestUtils.createExpectedResponseWeatherCityA()));
	}

	@Test
	public void testConvertCityBWeather() throws Exception {
		GetWeatherResponse response = mapper.mapResponse(Collections
				.<IWeather> singletonList(TestUtils.createWeatherCityB()));
		assertNotNull(response);
		assertNotNull(response.getResponse());
		assertEquals(1, response.getResponse().size());
		assertEquals(
				true,
				TestUtils.compare(response.getResponse().get(0),
						TestUtils.createExpectedResponseWeatherCityB()));
	}

	@Test
	public void testConvertEmptyResponse() {
		GetWeatherResponse response = mapper.mapResponse(Collections
				.<IWeather> emptyList());
		assertNotNull(response);
		assertNotNull(response.getResponse());
		assertEquals(0, response.getResponse().size());
	}

	@Test
	public void testConvertNullResponse() {
		assertEquals(null, mapper.mapResponse(null));
	}

	@Test
	public void testConvertCityARequest() throws ClientException {
		Collection<ILocation> request = mapper.mapRequest(TestUtils.CITY_A);
		assertNotNull(request);
		assertEquals(1, request.size());
		assertEquals(TestUtils.CITY_A_LOCATION, request.iterator().next());
	}

	@Test
	public void testConvertNullCityRequest() throws ClientException {
		assertEquals(null, mapper.mapRequest((String) null));
	}

}
