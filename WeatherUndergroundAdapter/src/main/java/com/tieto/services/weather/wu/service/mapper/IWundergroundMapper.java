package com.tieto.services.weather.wu.service.mapper;

import java.util.Set;

import com.tieto.services.weather.model.ILocation;
import com.tieto.services.weather.model.IWeather;
import com.tieto.services.weather.model.IWeather.WindDirection;
import com.tieto.services.weather.wu.client.json.beans.WundergroundWeather;

/**
 * Interface for mapper which converts Weather Underground weather data to the
 * {@link IWeather}.
 * 
 * @author sirokkam
 * 
 */
public interface IWundergroundMapper {

	public IWeather mapResponse(WundergroundWeather reponse);

	public String mapRequest(ILocation requestedLocation);

	public Set<ILocation> getKnownLocations();

	/** Wind direction mapping **/
	public enum WindDirectionMapping {
		North(WindDirection.N), NNE(WindDirection.NNE), NE(WindDirection.NE), ENE(
				WindDirection.ENE), East(WindDirection.E), ESE(
				WindDirection.ESE), SE(WindDirection.SE), SSE(WindDirection.SSE), South(
				WindDirection.S), SSW(WindDirection.SSW), SW(WindDirection.SW), WSW(
				WindDirection.WSW), West(WindDirection.W), WNW(
				WindDirection.WNW), NW(WindDirection.NW), NNW(WindDirection.NNW), Variable(
				WindDirection.Variable);

		private final WindDirection mapsTo;

		private WindDirectionMapping(final WindDirection mapsTo) {
			this.mapsTo = mapsTo;
		}

		public WindDirection map() {
			return mapsTo;
		}
	};

}