package com.tieto.services.weather.model.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.validation.constraints.NotNull;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;

/**
 * Default simple implementation of the {@link IWeather}
 * 
 * @author sirokkam
 * 
 */
public class Weather implements IWeather, Serializable {

	private static final long serialVersionUID = -1164170281475206062L;

	@NotNull
	private ILocation location;

	@NotNull
	private BigDecimal temperature;

	@NotNull
	private String relativeHumidity;

	@NotNull
	private WindDirection windDirection;

	@NotNull
	private String weatherDescription;

	@NotNull
	private String windDescription;

	@NotNull
	private GregorianCalendar observationTime;

	protected Weather() {
	}

	@Override
	public BigDecimal getTemperature() {
		return temperature;
	}

	@Override
	public void setTemperature(BigDecimal value) {
		if (value == null) {
			throw new IllegalArgumentException("Temperature can't be null!");
		}
		this.temperature = value;
	}

	@Override
	public ILocation getLocation() {
		return location;
	}

	@Override
	public void setLocation(ILocation value) {
		if (value == null) {
			throw new IllegalArgumentException("Location can't be null!");
		}
		this.location = value;
	}

	@Override
	public String getRelativeHumidity() {
		return relativeHumidity;
	}

	@Override
	public void setRelativeHumidity(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Humidity can't be null!");
		}
		this.relativeHumidity = value;
	}

	@Override
	public WindDirection getWindDirection() {
		return windDirection;
	}

	@Override
	public void setWindDirection(WindDirection value) {
		if (value == null) {
			throw new IllegalArgumentException("Wind direction can't be null!");
		}
		this.windDirection = value;
	}

	@Override
	public String getWeatherDescription() {
		return weatherDescription;
	}

	@Override
	public void setWeatherDescription(String value) {
		if (value == null) {
			throw new IllegalArgumentException(
					"Weather description can't be null!");
		}
		this.weatherDescription = value;
	}

	@Override
	public String getWindDescription() {
		return windDescription;
	}

	@Override
	public void setWindDescription(String value) {
		this.windDescription = value;
		if (value == null) {
			throw new IllegalArgumentException(
					"Wind description can't be null!");
		}
	}

	@Override
	public GregorianCalendar getObservationTime() {
		return observationTime;
	}

	@Override
	@NotNull
	public void setObservationTime(GregorianCalendar observationTime) {
		this.observationTime = observationTime;
		if (observationTime == null) {
			throw new IllegalArgumentException(
					"Observation time can't be null!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((observationTime == null) ? 0 : observationTime.hashCode());
		result = prime
				* result
				+ ((relativeHumidity == null) ? 0 : relativeHumidity.hashCode());
		result = prime * result
				+ ((temperature == null) ? 0 : temperature.hashCode());
		result = prime
				* result
				+ ((weatherDescription == null) ? 0 : weatherDescription
						.hashCode());
		result = prime * result
				+ ((windDescription == null) ? 0 : windDescription.hashCode());
		result = prime * result
				+ ((windDirection == null) ? 0 : windDirection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weather other = (Weather) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (observationTime == null) {
			if (other.observationTime != null)
				return false;
		} else if (!observationTime.equals(other.observationTime))
			return false;
		if (relativeHumidity == null) {
			if (other.relativeHumidity != null)
				return false;
		} else if (!relativeHumidity.equals(other.relativeHumidity))
			return false;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		if (weatherDescription == null) {
			if (other.weatherDescription != null)
				return false;
		} else if (!weatherDescription.equals(other.weatherDescription))
			return false;
		if (windDescription == null) {
			if (other.windDescription != null)
				return false;
		} else if (!windDescription.equals(other.windDescription))
			return false;
		if (windDirection == null) {
			if (other.windDirection != null)
				return false;
		} else if (!windDirection.equals(other.windDirection))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Weather [temperature=" + temperature + ", location=" + location
				+ ", relativeHumidity=" + relativeHumidity + ", windDirection="
				+ windDirection + ", weatherDescription=" + weatherDescription
				+ ", windDescription=" + windDescription + ", observationTime="
				+ observationTime + "]";
	}

}
