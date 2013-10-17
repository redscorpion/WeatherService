package com.tieto.services.weather.service.exception.client;

import com.tieto.services.weather.service.exception.ErrorCodes;

public class InvalidLocationClientException extends ClientException {

	private static final long serialVersionUID = -4499026133501412023L;

	public InvalidLocationClientException() {
		super();
	}

	public InvalidLocationClientException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidLocationClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLocationClientException(String message) {
		super(message);
	}

	public InvalidLocationClientException(Throwable cause) {
		super(cause);
	}

	@Override
	public ErrorCodes getErrorCode() {
		return ErrorCodes.INVALID_LOCATION_EXCEPTION;
	}

}
