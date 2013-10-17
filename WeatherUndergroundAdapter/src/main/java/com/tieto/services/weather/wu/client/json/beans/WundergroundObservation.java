package com.tieto.services.weather.wu.client.json.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds following weather data:
 * 
 * Observation location, Observation time, Temperature in Celsius, Humidity,
 * Wind direction, Weather description, Wind description.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WundergroundObservation {

	@JsonProperty("display_location")
	private WundergroundLocation location;

	@JsonProperty("local_epoch")
	private long time;

	@JsonProperty("local_tz_long")
	private String timeZone;

	@JsonProperty("temp_c")
	private String temperature;

	@JsonProperty("relative_humidity")
	private String humidity;

	@JsonProperty("wind_dir")
	private String windDirection;

	@JsonProperty("wind_string")
	private String windDescription;

	@JsonProperty("weather")
	private String weatherDescription;

	public WundergroundLocation getLocation() {
		return location;
	}

	public void setLocation(WundergroundLocation location) {
		this.location = location;
	}

	public long getTime() {
		return time;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public String getTemperature() {
		return temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public String getWindDescription() {
		return windDescription;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	@Override
	public String toString() {
		return "WundergroundObservation [location=" + location + ", time="
				+ time + ", timeZone=" + timeZone + ", temperature="
				+ temperature + ", humidity=" + humidity + ", windDirection="
				+ windDirection + ", windDescription=" + windDescription
				+ ", weatherDescription=" + weatherDescription + "]";
	}

}
