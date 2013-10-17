package com.tieto.services.weather.service.update.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tieto.services.weather.service.update.IWeatherUpdateScheduler;

@Component
public class WeatherUpdateScheduler implements IWeatherUpdateScheduler {

	private static final Logger logger = LoggerFactory
			.getLogger(WeatherUpdateScheduler.class);

	private Runnable updaterTask;

	public WeatherUpdateScheduler() {
	}

	@Override
	synchronized public void setWeatherUpdateTask(Runnable updaterTask) {
		if (this.updaterTask != null && updaterTask != null) {
			logger.warn(
					"Updater task is already set! The old task will be lost!",
					new Exception()); // for tracing info
		}
		this.updaterTask = updaterTask;

	}

	@Scheduled(initialDelayString = "${weather.update.initial.delay}", fixedDelayString = "${weather.update.fixed.delay}")
	synchronized public void refreshWeatherData() {
		if (updaterTask != null) {
			updaterTask.run();
		}
	}
}
