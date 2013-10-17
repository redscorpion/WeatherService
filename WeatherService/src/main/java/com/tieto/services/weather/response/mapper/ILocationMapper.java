package com.tieto.services.weather.response.mapper;

import com.tieto.services.weather.model.ILocation;

/**
 * Interface for weather location mapper.
 * 
 * @author sirokkam
 * 
 */
public interface ILocationMapper {

	public String mapResponseLocation(final ILocation location);

	public ILocation mapRequestLocation(final String locationCode);

}
