package com.tieto.services.weather.wu.client.json.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WundergroundLocation {

	@JsonProperty("city")
	private String city;

	@JsonProperty("country")
	private String country;

	@JsonProperty("state")
	private String state;

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	@Override
	public String toString() {
		return "WundergroundLocation [city=" + city + ", country=" + country
				+ ", state=" + state + "]";
	}

}
