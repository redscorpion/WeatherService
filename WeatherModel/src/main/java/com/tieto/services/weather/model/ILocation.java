package com.tieto.services.weather.model;

/**
 * Holds location data.
 * 
 * @author sirokkam
 */
public interface ILocation extends Cloneable {

	public String getCity();

	public void setCity(String city);

	public String getState();

	public void setState(String state);

	public String getCountry();

	public void setCountry(String country);

	public ILocation copy();

}