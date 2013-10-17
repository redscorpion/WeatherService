package com.tieto.services.weather.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.service.IWeatherService;

public class WeatherServiceTest {

	private IWeatherService mockService;

	@Before
	public void before() {
		mockService = TestUtils.createMockWeatherService();
	}

	@Test
	public void testGetAllCities() {
		Collection<ILocation> cities = mockService.getKnownLocations();
		assertNotNull(cities);
		assertEquals(2, cities.size());
		assertEquals(true, cities.contains(TestUtils.CITY_A_LOCATION));
		assertEquals(true, cities.contains(TestUtils.CITY_B_LOCATION));
	}

	@Test
	public void testCityAWeather() {
		Collection<IWeather> weather = mockService.getWeather(Collections
				.<ILocation> singletonList(TestUtils.CITY_A_LOCATION));
		assertNotNull(weather);
		assertEquals(1, weather.size());
		assertFalse(weather.contains(TestUtils.createWeatherCityB()));
		assertTrue(weather.contains(TestUtils.createWeatherCityA()));
	}

	@Test
	public void testCityBWeather() {
		Collection<IWeather> weather = mockService.getWeather(Collections
				.<ILocation> singletonList(TestUtils.CITY_B_LOCATION));
		assertNotNull(weather);
		assertEquals(1, weather.size());
		assertFalse(weather.contains(TestUtils.createWeatherCityA()));
		assertTrue(weather.contains(TestUtils.createWeatherCityB()));
	}

	@Test
	public void testUknownCityWeather() {
		Collection<IWeather> weather = mockService.getWeather(Collections
				.<ILocation> singletonList(TestUtils.UNKNOWN_CITY_LOCATION));
		assertNotNull(weather);
		assertEquals(0, weather.size());
	}

	@Test
	public void testGetCityABWeather() {
		Collection<IWeather> weather = mockService.getWeather(Arrays.asList(
				TestUtils.CITY_A_LOCATION, TestUtils.CITY_B_LOCATION));
		assertNotNull(weather);
		assertEquals(2, weather.size());
		assertTrue(weather.contains(TestUtils.createWeatherCityA()));
		assertTrue(weather.contains(TestUtils.createWeatherCityB()));
	}

	@Test
	public void testGetNoCitiesWeather() {
		Collection<IWeather> weather = mockService.getWeather(Collections
				.<ILocation> emptyList());
		assertNotNull(weather);
		assertEquals(0, weather.size());
	}

	@Test
	public void testGetAllCitiesWeather2() {
		Collection<IWeather> weather = mockService.getWeather(null);
		assertNotNull(weather);
		assertEquals(2, weather.size());
		assertTrue(weather.contains(TestUtils.createWeatherCityA()));
		assertTrue(weather.contains(TestUtils.createWeatherCityB()));
	}

}
