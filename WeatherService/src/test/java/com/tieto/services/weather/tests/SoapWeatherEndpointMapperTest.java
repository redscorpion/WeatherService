package com.tieto.services.weather.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.tieto.services.weather.endpoint.soap.mapper.impl.SoapWeatherEndpointMapper;
import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.impl.WeatherModelFactory;
import com.tieto.services.weather.response.beans.GetWeatherRequest;
import com.tieto.services.weather.response.beans.GetWeatherResponse;
import com.tieto.services.weather.response.mapper.impl.PropertiesBasedLocationMapper;
import com.tieto.services.weather.service.exception.client.ClientException;

public class SoapWeatherEndpointMapperTest {

	private SoapWeatherEndpointMapper mapper;

	@Before
	public void before() {
		Properties requestLocationMapping = TestUtils
				.createTestRequestLocationMapping();
		Properties responseLocationMapping = TestUtils
				.createTestResponseLocationMapping();

		WeatherModelFactory weatherModelFactory = new WeatherModelFactory();

		mapper = new SoapWeatherEndpointMapper(weatherModelFactory,
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
		GetWeatherRequest request = new GetWeatherRequest();
		request.getLocation().add(TestUtils.CITY_A);
		Collection<ILocation> converted = mapper.mapRequest(request);
		assertNotNull(converted);
		assertEquals(1, converted.size());
		assertEquals(TestUtils.CITY_A_LOCATION, converted.iterator().next());
	}

	@Test
	public void testConvertCityABRequest() throws ClientException {
		GetWeatherRequest request = new GetWeatherRequest();
		request.getLocation().add(TestUtils.CITY_A);
		request.getLocation().add(TestUtils.CITY_B);
		Collection<ILocation> converted = mapper.mapRequest(request);
		assertNotNull(converted);
		assertEquals(2, converted.size());
		assertTrue(converted.contains(TestUtils.CITY_A_LOCATION));
		assertTrue(converted.contains(TestUtils.CITY_B_LOCATION));
	}

	@Test
	public void testConvertEmptyRequest() throws ClientException {
		Collection<ILocation> request = mapper
				.mapRequest(new GetWeatherRequest());
		assertNull(request);
	}

	@Test
	public void testConvertNullRequest() throws ClientException {
		assertEquals(null, mapper.mapRequest((GetWeatherRequest) null));
	}

}
