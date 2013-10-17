package com.tieto.services.weather.service.executor;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;

public interface IGetWeatherTaskExecutor extends ISharedTaskExecutor<ILocation, IWeather> {

}
