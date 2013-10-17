package com.tieto.services.weather.service.update;

public interface IWeatherUpdateScheduler {

	public abstract void setWeatherUpdateTask(Runnable updaterRunnable);

}