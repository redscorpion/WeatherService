package com.tieto.services.weather.service.cache;

/**
 * Cache miss exception.
 * 
 * @author sirokkam
 * 
 */
public class CacheMissException extends Exception {

	private static final long serialVersionUID = 7488751649038121457L;

	public CacheMissException() {
	}

	public CacheMissException(String message) {
		super(message);
	}

	public CacheMissException(Throwable cause) {
		super(cause);
	}

	public CacheMissException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheMissException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
