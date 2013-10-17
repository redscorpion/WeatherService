package com.tieto.services.weather.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.provider.IWeatherProvider;
import com.tieto.services.weather.model.validator.IWeatherModelValidator;
import com.tieto.services.weather.service.IWeatherService;
import com.tieto.services.weather.service.cache.CacheMissException;
import com.tieto.services.weather.service.cache.IWeatherCache;
import com.tieto.services.weather.service.exception.client.ClientException;
import com.tieto.services.weather.service.exception.server.ServerException;
import com.tieto.services.weather.service.executor.IGetWeatherTaskExecutor;
import com.tieto.services.weather.service.executor.ISharedTaskExecutor.ISharedTask;
import com.tieto.services.weather.service.executor.ISharedTaskExecutor.ISharedTaskFactory;
import com.tieto.services.weather.service.update.IWeatherUpdateScheduler;

/**
 * Service which is used from SOAP/REST endpoint to get the weather data.
 * 
 * @author sirokkam
 * 
 */
@Service
public class WeatherService implements IWeatherService {

	private static final Logger logger = LoggerFactory
			.getLogger(WeatherService.class);

	private final List<IWeatherProvider> weatherProviders;

	private final IWeatherCache weatherCache;

	private final IWeatherModelValidator weatherValidator;

	private final IGetWeatherTaskExecutor executor;

	@Autowired
	public WeatherService(final List<IWeatherProvider> weatherProviders,
			final IWeatherCache weatherCache,
			final IWeatherModelValidator weatherValidator,
			final IGetWeatherTaskExecutor executor,
			final IWeatherUpdateScheduler updateScheduler) {

		this.weatherProviders = weatherProviders;
		this.weatherCache = weatherCache;
		this.weatherValidator = weatherValidator;
		this.executor = executor;

		if (updateScheduler != null) {
			updateScheduler.setWeatherUpdateTask(new Runnable() {
				@Override
				public void run() {
					refreshWeatherData();
				}
			});
		}

		logger.info("Weather service initialized.");
	}

	@Override
	public Set<ILocation> getKnownLocations() {
		final Set<ILocation> cities = new LinkedHashSet<>();

		for (IWeatherProvider weatherProvider : weatherProviders) {
			cities.addAll(weatherProvider.getKnownLocation());
		}

		return cities;
	}

	@Override
	public Collection<IWeather> getWeather(final Collection<ILocation> locations) {
		if (locations == null) {
			return getWeather(getKnownLocations(), false);
		} else {
			return getWeather(locations, false);
		}
	}

	protected void refreshWeatherData() {
		if (weatherCache != null) {
			getWeather(getKnownLocations(), true);
		}
	}

	protected Collection<IWeather> getWeather(
			final Collection<ILocation> locations, final boolean forceUpdate) {

		if (locations == null) {
			throw new IllegalArgumentException("Locations cant't be null!");
		}

		final Collection<IWeather> result = new ArrayList<>();

		for (ILocation location : locations) {
			final IWeather weather = getLocationWeather(location, forceUpdate);
			if (weather != null) {
				result.add(weather);
			}
		}

		return result;
	}

	protected IWeather getLocationWeather(final ILocation location,
			final boolean forceUpdate) {

		if (location == null) {
			throw new IllegalArgumentException("Location cant't be null!");
		}

		// first try to get the weather from cache
		if (!forceUpdate && weatherCache != null) {
			try {
				return weatherCache.get(location);
			} catch (CacheMissException e) {
				// swallow, object is not in cache
			}
		}

		final IWeather weather = internalGetLocationWeather(location);

		// update weather cache
		if (weatherCache != null) {
			weatherCache.put(location, weather);
		}

		return weather;
	}

	private IWeather internalGetLocationWeather(final ILocation location)
			throws ServerException {
		final ISharedTask<IWeather> task = executor.runTask(location,
				new ISharedTaskFactory<GetWeatherTask>() {
					@Override
					public GetWeatherTask create() {
						return new GetWeatherTask(location);
					}
				});
		try {
			return task.waitForResult();
		} catch (ExecutionException e) {
			if (e.getCause() instanceof ClientException) {
				throw ((ClientException) e.getCause());
			} else if (e.getCause() instanceof ServerException) {
				throw ((ServerException) e.getCause());
			} else {
				throw new ServerException("Internal error!", e.getCause());
			}
		}
	}

	protected boolean isValid(final IWeather weather) {
		if (weather == null) {
			return false;
		}

		try {
			final Set<ConstraintViolation<IWeather>> validationResult = weatherValidator
					.validate(weather);

			if (!validationResult.isEmpty()) {
				logger.error("Weather validation error!\n" + validationResult);
				return false;
			}
		} catch (IllegalArgumentException | ValidationException e) {
			logger.warn("Weather can't be validated!", e);
		}

		return true;
	}

	private class GetWeatherTask implements Callable<IWeather> {
		private final ILocation location;

		public GetWeatherTask(final ILocation location) {
			this.location = location;
		}

		@Override
		public IWeather call() throws Exception {
			for (IWeatherProvider weatherProvider : weatherProviders) {
				final IWeather weather = weatherProvider.getWeather(location);

				if (isValid(weather)) {
					return weather;
				}

				if (weather != null) {
					logger.error("Weather returned by weather provider '"
							+ weatherProvider.getClass().getSimpleName()
							+ "' is not valid!");
				}
			}

			throw new ServerException(
					"No provider can provide weather for location '"
							+ location.toString() + "'!");
		}
	}
}
