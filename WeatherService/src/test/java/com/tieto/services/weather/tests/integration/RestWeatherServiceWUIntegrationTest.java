package com.tieto.services.weather.tests.integration;

import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.APPLICATION_JSON_CHARSET_UTF_8;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.APPLICATION_XML;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.HELSINKI_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.RIGA_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.TALLINN_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.UNKNOWN_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.VILNIUS_CITY;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tieto.services.weather.endpoint.rest.IRestWeatherService;
import com.tieto.services.weather.endpoint.soap.ISoapWeatherEndpoint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-rest-context.xml" })
@WebAppConfiguration
public class RestWeatherServiceWUIntegrationTest {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void testFetchHelsinkiWeatherJSON() throws Exception {
		String city = HELSINKI_CITY;

		Set<String> cities = new HashSet<>();
		cities.add(city);

		mockMvc.perform(
				get(IRestWeatherService.CITY_WEATHER_MAPPING, city).accept(
						MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(APPLICATION_JSON_CHARSET_UTF_8))
				.andExpect(jsonPath("response").exists())
				.andExpect(
						jsonPath("response[0].location").value(
								new CityMatcher(cities)))
				.andExpect(jsonPath("response[0].temp_c").exists())
				.andExpect(jsonPath("response[0].relative_humidity").exists())
				.andExpect(jsonPath("response[0].wind_dir").exists())
				.andExpect(jsonPath("response[0].weather").exists())
				.andExpect(jsonPath("response[0].wind_string").exists());

		assertTrue(cities.isEmpty());
	}

	@Test
	public void testFetchHelsinkiWeatherXML() throws Exception {
		String city = HELSINKI_CITY;

		Set<String> cities = new HashSet<String>();
		cities.add(city);

		Map<String, String> namespaces = new HashMap<String, String>();
		namespaces.put("n", ISoapWeatherEndpoint.NAMESPACE);

		mockMvc.perform(
				get(IRestWeatherService.CITY_WEATHER_MAPPING, city).accept(
						MediaType.APPLICATION_XML))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_XML))
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[1]/n:location",
								namespaces).string(new CityMatcher(cities)))
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[1]/n:temp_c",
								namespaces).exists())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response[1]/n:relative_humidity",
								namespaces).exists())
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[1]/n:wind_dir",
								namespaces).exists())
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[1]/n:weather",
								namespaces).exists())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response[1]/n:wind_string",
								namespaces).exists());

		assertTrue(cities.isEmpty());
	}

	@Test
	public void testFetchAllWeatherJSON() throws Exception {
		Set<String> cities = new HashSet<String>();
		cities.add(VILNIUS_CITY);
		cities.add(RIGA_CITY);
		cities.add(TALLINN_CITY);
		cities.add(HELSINKI_CITY);

		CityMatcher cityMatcher = new CityMatcher(cities);

		mockMvc.perform(
				get(IRestWeatherService.ALL_WEATHER_MAPPING).accept(
						MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(APPLICATION_JSON_CHARSET_UTF_8))
				.andExpect(jsonPath("response[0].location").value(cityMatcher))
				.andExpect(jsonPath("response[1].location").value(cityMatcher))
				.andExpect(jsonPath("response[2].location").value(cityMatcher))
				.andExpect(jsonPath("response[3].location").value(cityMatcher));

		assertTrue(cities.isEmpty());
	}

	@Test
	public void testFetchAllWeatherXML() throws Exception {
		Set<String> cities = new HashSet<String>();
		cities.add(VILNIUS_CITY);
		cities.add(RIGA_CITY);
		cities.add(TALLINN_CITY);
		cities.add(HELSINKI_CITY);

		CityMatcher cityMatcher = new CityMatcher(cities);

		Map<String, String> namespaces = new HashMap<String, String>();
		namespaces.put("n", ISoapWeatherEndpoint.NAMESPACE);

		mockMvc.perform(
				get(IRestWeatherService.ALL_WEATHER_MAPPING).accept(
						MediaType.APPLICATION_XML))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_XML))
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[1]/n:location",
								namespaces).string(cityMatcher))
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[2]/n:location",
								namespaces).string(cityMatcher))
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[3]/n:location",
								namespaces).string(cityMatcher))
				.andExpect(
						xpath("/n:getWeatherResponse/n:response[4]/n:location",
								namespaces).string(cityMatcher));

		assertTrue(cities.isEmpty());
	}

	@Test
	public void testFetchUnknownCityJSON() throws Exception {
		String city = UNKNOWN_CITY;

		mockMvc.perform(
				get(IRestWeatherService.CITY_WEATHER_MAPPING, city).accept(
						MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(APPLICATION_JSON_CHARSET_UTF_8))
				.andExpect(jsonPath("weather").doesNotExist());
	}

	@Test
	public void testFetchUnknownCityXML() throws Exception {
		String city = UNKNOWN_CITY;

		Map<String, String> namespaces = new HashMap<String, String>();
		namespaces.put("n", ISoapWeatherEndpoint.NAMESPACE);

		mockMvc.perform(
				get(IRestWeatherService.CITY_WEATHER_MAPPING, city).accept(
						MediaType.APPLICATION_XML))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_XML))
				.andExpect(
						xpath("/n:getWeatherResponse/n:response", namespaces)
								.doesNotExist());
	}

	@Test
	public void testAverageResponseTimeJSON() throws Exception {
		long repeat, duration = 0;

		for (repeat = 0; repeat < 10; repeat++) {
			long start = System.currentTimeMillis();
			mockMvc.perform(
					get(IRestWeatherService.CITY_WEATHER_MAPPING, HELSINKI_CITY)
							.accept(MediaType.APPLICATION_JSON)).andExpect(
					status().isOk());
			duration += System.currentTimeMillis() - start;
		}

		long avgDuration = duration / repeat;
		assertTrue(avgDuration < 500);
	}

	@Test
	public void testAverageResponseTimeXML() throws Exception {
		long repeat, duration = 0;

		for (repeat = 0; repeat < 10; repeat++) {
			long start = System.currentTimeMillis();
			mockMvc.perform(
					get(IRestWeatherService.CITY_WEATHER_MAPPING, HELSINKI_CITY)
							.accept(MediaType.APPLICATION_XML)).andExpect(
					status().isOk());
			duration += System.currentTimeMillis() - start;
		}

		long avgDuration = duration / repeat;
		assertTrue(avgDuration < 500);
	}

	private static class CityMatcher extends BaseMatcher<String> {

		private final Set<String> cities;

		public CityMatcher(final Set<String> cities) {
			this.cities = cities;
		}

		@Override
		public boolean matches(Object location) {
			return cities.remove(location.toString());
		}

		@Override
		public void describeTo(Description descr) {
			descr.appendText(cities.toString());
		}
	}
}
