package com.tieto.services.weather.tests.integration;

import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.HELSINKI_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.RIGA_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.TALLINN_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.UNKNOWN_CITY;
import static com.tieto.services.weather.tests.integration.IntegrationTestUtils.VILNIUS_CITY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.ws.transport.http.HttpTransportConstants;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.tieto.services.weather.endpoint.soap.ISoapWeatherEndpoint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-soap-context.xml" })
public class SoapWeatherServiceWUIntegrationTest {

	private static MessageDispatcherServlet mockServlet;

	@Autowired
	private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;

	@BeforeClass
	public static void createServlet() throws ServletException {
		ServletConfig config = new MockServletConfig(new MockServletContext(),
				"spring-ws");
		mockServlet = new MessageDispatcherServlet();
		mockServlet
				.setContextConfigLocation("classpath:/test-soap-context.xml");
		mockServlet.init(config);
	}

	@AfterClass
	public static void destroyServlet() {
		mockServlet.destroy();
	}

	@Before
	public void createClient() throws ServletException {
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}

	@Test
	public void testGetWeatherWsdl() throws ServletException, IOException,
			ParserConfigurationException, SAXException {
		MockHttpServletRequest request = new MockHttpServletRequest(
				HttpTransportConstants.METHOD_GET,
				ISoapWeatherEndpoint.WSDL_LOCATION);

		MockHttpServletResponse response = new MockHttpServletResponse();
		mockServlet.service(request, response);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		Document result = documentBuilder.parse(new ByteArrayInputStream(
				response.getContentAsByteArray()));
		assertNotNull(result);
	}

	@Test
	public void testFetchHelsinkiWeather() {

		Map<String, String> namespaces = new HashMap<>();
		namespaces.put("n", ISoapWeatherEndpoint.NAMESPACE);

		mockClient
				.sendRequest(withPayload(REQUEST_HELSINKI_WEATHER))
				.andExpect(ResponseMatchers.noFault())
				.andExpect(
						ResponseMatchers
								.xpath("/n:getWeatherResponse/n:response[1]/n:location",
										namespaces).evaluatesTo(HELSINKI_CITY))
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
	}

	@Test
	public void testFetchVilniusHelsinkiWeather() {

		Map<String, String> namespaces = new HashMap<>();
		namespaces.put("n", ISoapWeatherEndpoint.NAMESPACE);

		mockClient
				.sendRequest(withPayload(REQUEST_VILNIUS_HELSINKI_WEATHER))
				.andExpect(ResponseMatchers.noFault())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ HELSINKI_CITY + "\"]", namespaces)
								.exists())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ RIGA_CITY + "\"]", namespaces)
								.doesNotExist())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ VILNIUS_CITY + "\"]", namespaces)
								.exists())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ TALLINN_CITY + "\"]", namespaces)
								.doesNotExist());
	}

	@Test
	public void testFetchAllWeather() {

		Map<String, String> namespaces = new HashMap<>();
		namespaces.put("n", ISoapWeatherEndpoint.NAMESPACE);

		mockClient
				.sendRequest(withPayload(REQUEST_ALL_WEATHER))
				.andExpect(ResponseMatchers.noFault())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ HELSINKI_CITY + "\"]", namespaces)
								.exists())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ RIGA_CITY + "\"]", namespaces)
								.exists())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ VILNIUS_CITY + "\"]", namespaces)
								.exists())
				.andExpect(
						xpath(
								"/n:getWeatherResponse/n:response/n:location[text()=\""
										+ TALLINN_CITY + "\"]", namespaces)
								.exists());

	}

	@Test
	public void testFetchUnknownCity() {

		Map<String, String> namespaces = new HashMap<>();
		namespaces.put("n", ISoapWeatherEndpoint.NAMESPACE);

		mockClient
				.sendRequest(withPayload(REQUEST_UNKNOWN_CITY_WEATHER))
				.andExpect(ResponseMatchers.noFault())
				.andExpect(
						xpath("/n:getWeatherResponse/n:response", namespaces)
								.doesNotExist());
	}

	@Test
	public void testAverageResponseTime() {
		long repeat, duration = 0;

		for (repeat = 0; repeat < 10; repeat++) {
			long start = System.currentTimeMillis();
			mockClient.sendRequest(withPayload(REQUEST_HELSINKI_WEATHER))
					.andExpect(ResponseMatchers.noFault());
			duration += System.currentTimeMillis() - start;
		}

		long avgDuration = duration / repeat;
		assertTrue(avgDuration < 500);
	}

	private static final StringSource REQUEST_UNKNOWN_CITY_WEATHER = new StringSource(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean:"
					+ ISoapWeatherEndpoint.GET_WEATHER_REQUEST
					+ " xmlns:bean=\"" + ISoapWeatherEndpoint.NAMESPACE
					+ "\"><bean:location>" + UNKNOWN_CITY
					+ "</bean:location></bean:"
					+ ISoapWeatherEndpoint.GET_WEATHER_REQUEST + ">");

	private static final StringSource REQUEST_ALL_WEATHER = new StringSource(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean:"
					+ ISoapWeatherEndpoint.GET_WEATHER_REQUEST
					+ " xmlns:bean=\"" + ISoapWeatherEndpoint.NAMESPACE
					+ "\"></bean:" + ISoapWeatherEndpoint.GET_WEATHER_REQUEST
					+ ">");

	private static final StringSource REQUEST_HELSINKI_WEATHER = new StringSource(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean:"
					+ ISoapWeatherEndpoint.GET_WEATHER_REQUEST
					+ " xmlns:bean=\"" + ISoapWeatherEndpoint.NAMESPACE
					+ "\"><bean:location>" + HELSINKI_CITY
					+ "</bean:location></bean:"
					+ ISoapWeatherEndpoint.GET_WEATHER_REQUEST + ">");

	private static final StringSource REQUEST_VILNIUS_HELSINKI_WEATHER = new StringSource(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><bean:"
					+ ISoapWeatherEndpoint.GET_WEATHER_REQUEST
					+ " xmlns:bean=\"" + ISoapWeatherEndpoint.NAMESPACE
					+ "\"><bean:location>" + VILNIUS_CITY
					+ "</bean:location><bean:location>" + HELSINKI_CITY
					+ "</bean:location></bean:"
					+ ISoapWeatherEndpoint.GET_WEATHER_REQUEST + ">");

}
