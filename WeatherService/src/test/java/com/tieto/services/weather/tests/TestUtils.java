package com.tieto.services.weather.tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.IWeather.WindDirection;
import com.tieto.services.weather.model.IWeatherModelFactory;
import com.tieto.services.weather.model.impl.WeatherModelFactory;
import com.tieto.services.weather.model.provider.IWeatherProvider;
import com.tieto.services.weather.model.validator.IWeatherModelValidator;
import com.tieto.services.weather.response.beans.WeatherDTO;
import com.tieto.services.weather.service.IWeatherService;
import com.tieto.services.weather.service.executor.impl.GetWeatherTaskExecutor;
import com.tieto.services.weather.service.impl.WeatherService;
import com.tieto.services.weather.service.update.impl.WeatherUpdateScheduler;

final class TestUtils {

	public static final String CITY_A = "CityA";
	public static final String CITY_A_STATE = "CAS";
	public static final String CITY_A_COUNTRY = "CAC";
	public static final String CITY_A_WINDDESC = "Tornado";
	public static final WindDirection CITY_A_WINDDIR = WindDirection.W;
	public static final String CITY_A_WEATHER = "Sunny";
	public static final BigDecimal CITY_A_TEMP = new BigDecimal("44.4");
	public static final String CITY_A_HUMIDITY = "55%";
	public static final GregorianCalendar CITY_A_TIME = new GregorianCalendar();

	public static final String CITY_B = "CityB";
	public static final String CITY_B_STATE = "CBS";
	public static final String CITY_B_COUNTRY = "CBC";
	public static final String CITY_B_WINDDESC = "N/A";
	public static final WindDirection CITY_B_WINDDIR = WindDirection.NE;
	public static final String CITY_B_WEATHER = "Icy";
	public static final BigDecimal CITY_B_TEMP = new BigDecimal("-55");
	public static final String CITY_B_HUMIDITY = "77%";
	public static final GregorianCalendar CITY_B_TIME = new GregorianCalendar();

	public static final String UNKNOWN_CITY = "***SOME_CITY***";

	public static final ILocation CITY_A_LOCATION;
	public static final ILocation CITY_B_LOCATION;
	public static final ILocation UNKNOWN_CITY_LOCATION;

	private static final IWeatherModelFactory weatherModelFactory = new WeatherModelFactory();

	static {
		CITY_A_LOCATION = weatherModelFactory.createLocation();
		CITY_A_LOCATION.setCity(CITY_A);
		CITY_A_LOCATION.setState(CITY_A_STATE);
		CITY_A_LOCATION.setCountry(CITY_A_COUNTRY);
		CITY_B_LOCATION = weatherModelFactory.createLocation();
		CITY_B_LOCATION.setCity(CITY_B);
		CITY_B_LOCATION.setState(CITY_B_STATE);
		CITY_B_LOCATION.setCountry(CITY_B_COUNTRY);
		UNKNOWN_CITY_LOCATION = weatherModelFactory.createLocation();
		UNKNOWN_CITY_LOCATION.setCity(UNKNOWN_CITY);
	}

	private TestUtils() {
	}

	static IWeather createWeatherCityA() {
		IWeather weather = weatherModelFactory.createWeather();
		weather.setLocation(CITY_A_LOCATION);
		weather.setRelativeHumidity(CITY_A_HUMIDITY);
		weather.setTemperature(CITY_A_TEMP);
		weather.setWeatherDescription(CITY_A_WEATHER);
		weather.setWindDirection(CITY_A_WINDDIR);
		weather.setWindDescription(CITY_A_WINDDESC);
		weather.setObservationTime(CITY_A_TIME);
		return weather;
	}

	static IWeather createWeatherCityB() {
		IWeather weather = weatherModelFactory.createWeather();
		weather.setLocation(CITY_B_LOCATION);
		weather.setRelativeHumidity(CITY_B_HUMIDITY);
		weather.setTemperature(CITY_B_TEMP);
		weather.setWeatherDescription(CITY_B_WEATHER);
		weather.setWindDirection(CITY_B_WINDDIR);
		weather.setWindDescription(CITY_B_WINDDESC);
		weather.setObservationTime(CITY_B_TIME);
		return weather;
	}

	static WeatherDTO createExpectedResponseWeatherCityA()
			throws DatatypeConfigurationException {
		WeatherDTO weather = new WeatherDTO();
		weather.setLocation(CITY_A);
		weather.setRelativeHumidity(CITY_A_HUMIDITY);
		weather.setTempC(CITY_A_TEMP);
		weather.setWeather(CITY_A_WEATHER);
		weather.setWindDir(CITY_A_WINDDIR.name());
		weather.setWindString(CITY_A_WINDDESC);
		weather.setObservationTime(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(CITY_A_TIME));
		return weather;
	}

	static WeatherDTO createExpectedResponseWeatherCityB()
			throws DatatypeConfigurationException {
		WeatherDTO weather = new WeatherDTO();
		weather.setLocation(CITY_B);
		weather.setRelativeHumidity(CITY_B_HUMIDITY);
		weather.setTempC(CITY_B_TEMP);
		weather.setWeather(CITY_B_WEATHER);
		weather.setWindDir(CITY_B_WINDDIR.name());
		weather.setWindString(CITY_B_WINDDESC);
		weather.setObservationTime(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(CITY_B_TIME));
		return weather;
	}

	static boolean compare(WeatherDTO w1, WeatherDTO w2) {
		if (w1 == null && w2 == null) {
			return true;
		}

		if (w1 == null && w2 != null) {
			return false;
		}

		return w1.equals(w2);
	}

	static Properties createTestRequestLocationMapping() {
		Properties requestLocationMapping = new Properties();

		requestLocationMapping.setProperty(TestUtils.CITY_A,
				TestUtils.CITY_A_COUNTRY + "/" + TestUtils.CITY_A_STATE + "/"
						+ TestUtils.CITY_A);

		requestLocationMapping.setProperty(TestUtils.CITY_B,
				TestUtils.CITY_B_COUNTRY + "/" + TestUtils.CITY_B_STATE + "/"
						+ TestUtils.CITY_B);

		return requestLocationMapping;
	}

	static Properties createTestResponseLocationMapping() {

		Properties responseLocationMapping = new Properties();

		responseLocationMapping.setProperty(TestUtils.CITY_A_COUNTRY + "/"
				+ TestUtils.CITY_A_STATE + "/" + TestUtils.CITY_A,
				TestUtils.CITY_A);

		responseLocationMapping.setProperty(TestUtils.CITY_B_COUNTRY + "/"
				+ TestUtils.CITY_B_STATE + "/" + TestUtils.CITY_B,
				TestUtils.CITY_B);

		return responseLocationMapping;
	}

	static IWeatherService createMockWeatherService() {
		List<IWeatherProvider> providers = new ArrayList<>();
		providers.add(new MockWeatherProvider(TestUtils.createWeatherCityA()));
		providers.add(new MockWeatherProvider(TestUtils.createWeatherCityB()));
		return new WeatherService(providers, null, new MockWeatherValidator(),
				new GetWeatherTaskExecutor(), new WeatherUpdateScheduler());
	}

	private static class MockWeatherValidator implements IWeatherModelValidator {
		@Override
		public Set<ConstraintViolation<IWeather>> validate(IWeather weather)
				throws IllegalArgumentException, ValidationException {
			return new HashSet<>();
		}
	}

	private static class MockWeatherProvider implements IWeatherProvider {

		private final Map<ILocation, IWeather> location2WeatherMap = new LinkedHashMap<>();

		public MockWeatherProvider(final IWeather... weathers) {
			for (IWeather iWeather : weathers) {
				location2WeatherMap.put(iWeather.getLocation(), iWeather);
			}
		}

		@Override
		public Set<ILocation> getKnownLocation() {
			return location2WeatherMap.keySet();
		}

		@Override
		public IWeather getWeather(final ILocation location) {
			return location2WeatherMap.get(location);
		}

	}

}
