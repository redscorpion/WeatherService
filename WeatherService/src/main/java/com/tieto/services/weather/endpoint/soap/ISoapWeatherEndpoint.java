package com.tieto.services.weather.endpoint.soap;

import com.tieto.services.weather.response.beans.GetWeatherRequest;
import com.tieto.services.weather.response.beans.GetWeatherResponse;
import com.tieto.services.weather.service.exception.client.ClientException;

/**
 * Interface for the SOAP weather service.
 * 
 * @author sirokkam
 * 
 */
public interface ISoapWeatherEndpoint {

	public static final String NAMESPACE = "http://services.tieto.com/weather/response/beans";
	public static final String GET_WEATHER_REQUEST = "getWeatherRequest";
	public static final String WSDL_LOCATION = "/weather.wsdl";

	/**
	 * Returns weather data.
	 * 
	 * If the city is not specified then it will return weather data for all
	 * known cities.
	 * 
	 * @throws ClientException
	 */
	public GetWeatherResponse getWeather(GetWeatherRequest request)
			throws ClientException;

}
