package com.tieto.services.weather.wu.client.json.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WundergroundWeather {

	@JsonProperty("current_observation")
	private WundergroundObservation observation;

	public WundergroundObservation getObservation() {
		return observation;
	}

	@Override
	public String toString() {
		return "WundergroundWeather [observation=" + observation + "]";
	}
}
