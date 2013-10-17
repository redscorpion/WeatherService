package com.tieto.services.weather.response.mapper.impl;

import java.util.Collection;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.endpoint.soap.mapper.ISoapWeatherEndpointMapper;
import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.IWeatherModelFactory;
import com.tieto.services.weather.response.beans.ErrorDTO;
import com.tieto.services.weather.response.beans.GetWeatherResponse;
import com.tieto.services.weather.response.beans.ObjectFactory;
import com.tieto.services.weather.response.beans.WeatherDTO;
import com.tieto.services.weather.response.mapper.ILocationMapper;
import com.tieto.services.weather.response.mapper.IWeatherEndpointMapper;
import com.tieto.services.weather.service.exception.client.ClientException;
import com.tieto.services.weather.service.exception.server.ServerException;

/**
 * Implementation of {@link ISoapWeatherEndpointMapper}
 * 
 * @author sirokkam
 * 
 */
@Component
public abstract class AbstractWeatherEndpointMapper implements
		IWeatherEndpointMapper {

	protected final Logger logger = LoggerFactory
			.getLogger(AbstractWeatherEndpointMapper.class);

	protected final ObjectFactory responseFactory = new ObjectFactory();

	protected final DatatypeFactory datatypeFactory;

	protected final IWeatherModelFactory weatherModelFactory;

	protected final ILocationMapper locationMapper;

	public AbstractWeatherEndpointMapper(
			final IWeatherModelFactory weatherModelFactory,
			final ILocationMapper locationMapper) {

		this.weatherModelFactory = weatherModelFactory;
		this.locationMapper = locationMapper;

		try {
			this.datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// something really BAD happened!
			throw new RuntimeException(e);
		}
	}

	@Override
	public GetWeatherResponse mapResponse(final Collection<IWeather> response) {

		if (response == null) {
			return null;
		}

		final GetWeatherResponse responseDTO = responseFactory
				.createGetWeatherResponse();

		for (IWeather iWeather : response) {

			if (iWeather == null) {
				continue;
			}

			final GregorianCalendar dateTime = iWeather.getObservationTime();
			final XMLGregorianCalendar xmlDateTime = datatypeFactory
					.newXMLGregorianCalendar(dateTime);

			final String location = mapResponseLocation(iWeather.getLocation());

			if (location == null) {
				logger.error("No location mapping found for location '"
						+ iWeather.getLocation() + "'.");
				continue;
			}

			final WeatherDTO weatherDTO = responseFactory.createWeatherDTO();
			weatherDTO.setLocation(location);
			weatherDTO.setRelativeHumidity(iWeather.getRelativeHumidity());
			weatherDTO.setTempC(iWeather.getTemperature());
			weatherDTO.setWeather(iWeather.getWeatherDescription());
			weatherDTO.setWindDir(mapWindDirection(iWeather));
			weatherDTO.setWindString(iWeather.getWindDescription());
			weatherDTO.setObservationTime(xmlDateTime);

			responseDTO.getResponse().add(weatherDTO);
		}

		return responseDTO;
	}

	@Override
	public GetWeatherResponse mapException(Exception exception) {
		ErrorDTO errorDTO = responseFactory.createErrorDTO();

		if (exception instanceof ClientException) {
			errorDTO.setCode((((ClientException) exception).getErrorCode()
					.toString()));
		} else if (exception instanceof ServerException) {
			errorDTO.setCode((((ServerException) exception).getErrorCode()
					.toString()));
		} else {
			errorDTO.setCode(new ServerException("Unknown server error.")
					.getErrorCode().toString());
		}

		errorDTO.setMessage(exception.getMessage());

		GetWeatherResponse response = responseFactory
				.createGetWeatherResponse();
		response.setError(errorDTO);

		return response;
	}

	protected String mapWindDirection(IWeather iWeather) {
		return iWeather.getWindDirection().name();
	}

	protected String mapResponseLocation(final ILocation location) {
		return locationMapper.mapResponseLocation(location);
	}

	protected ILocation mapRequestLocation(final String locationCode) {
		return locationMapper.mapRequestLocation(locationCode);
	}

}
