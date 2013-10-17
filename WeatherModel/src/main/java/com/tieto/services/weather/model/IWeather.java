package com.tieto.services.weather.model;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

/**
 * Holds weather data.
 * 
 * @author sirokkam
 */
public interface IWeather {

	BigDecimal getTemperature();

	/**
	 * Temperature must not be null.
	 * 
	 * @return
	 */
	public void setTemperature(BigDecimal value);

	public ILocation getLocation();

	/**
	 * Location must not be null.
	 * 
	 * @return
	 */
	public void setLocation(ILocation value);

	public String getRelativeHumidity();

	/**
	 * Humidity must not be null.
	 * 
	 * @return
	 */
	public void setRelativeHumidity(String value);

	public WindDirection getWindDirection();

	/**
	 * Wind direction must not be null.
	 * 
	 * @return
	 */
	public void setWindDirection(WindDirection value);

	public String getWeatherDescription();

	/**
	 * Weather description must not be null.
	 * 
	 * @return
	 */
	public void setWeatherDescription(String value);

	public String getWindDescription();

	/**
	 * WindDescription must not be null.
	 * 
	 * @return
	 */
	public void setWindDescription(String value);

	public GregorianCalendar getObservationTime();

	/**
	 * Date must not be null.
	 * 
	 * @return
	 */
	public void setObservationTime(GregorianCalendar observationTime);

	/** Wind directions **/
	public enum WindDirection {
		N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW, Variable
	};

}