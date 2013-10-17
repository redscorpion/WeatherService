package com.tieto.services.weather.service.exception;

public enum ErrorCodes {

	LOGICAL_EXCEPTION("LOGICAL_EXCEPTION"), INVALID_LOCATION_EXCEPTION(
			"INVALID_LOCATION_EXCEPTION"), TECHNICAL_EXCEPTION(
			"TECHNICAL_EXCEPTION");

	private final String id;

	private ErrorCodes(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}
