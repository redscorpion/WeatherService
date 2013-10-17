package com.tieto.services.weather.model.impl;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.tieto.services.weather.model.ILocation;

/**
 * Default simple implementation of the {@link ILocation}
 * 
 * @author sirokkam
 * 
 */
public class Location implements ILocation, Serializable {

	private static final long serialVersionUID = 9043552917719865094L;

	@NotNull
	private String city = "";

	@NotNull
	private String state = "";

	@NotNull
	private String country = "";

	protected Location() {
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		if (city == null) {
			throw new IllegalArgumentException("City can't be null!");
		}
		this.city = city;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public void setState(String state) {
		if (state == null) {
			throw new IllegalArgumentException("State can't be null!");
		}
		this.state = state;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		if (country == null) {
			throw new IllegalArgumentException("Country can't be null!");
		}
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Location other = (Location) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [city=" + city + ", state=" + state + ", country="
				+ country + "]";
	}

	@Override
	public Location copy() {
		Location copy = new Location();
		copy.city = city;
		copy.country = country;
		copy.state = state;
		return copy;
	}
}
