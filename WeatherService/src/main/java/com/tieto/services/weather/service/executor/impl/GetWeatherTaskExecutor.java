package com.tieto.services.weather.service.executor.impl;

import org.springframework.stereotype.Component;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.service.executor.IGetWeatherTaskExecutor;

@Component
public class GetWeatherTaskExecutor extends SharedTaskExecutor<ILocation, IWeather>
		implements IGetWeatherTaskExecutor {

}
