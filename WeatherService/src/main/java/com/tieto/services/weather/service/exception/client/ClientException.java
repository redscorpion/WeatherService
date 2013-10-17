package com.tieto.services.weather.service.exception.client;

import com.tieto.services.weather.service.exception.ErrorCodes;

public class ClientException extends RuntimeException {

	private static final long serialVersionUID = -8136657956637958911L;

	public ClientException() {
		super();
	}

	public ClientException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

	public ErrorCodes getErrorCode() {
		return ErrorCodes.LOGICAL_EXCEPTION;
	}

}
