package com.tieto.services.weather.service.exception.server;

import com.tieto.services.weather.service.exception.ErrorCodes;

public class ServerException extends RuntimeException {

	private static final long serialVersionUID = -5113515903388601965L;

	public ServerException() {
		super();
	}

	public ServerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

	public ErrorCodes getErrorCode() {
		return ErrorCodes.TECHNICAL_EXCEPTION;
	}

}
