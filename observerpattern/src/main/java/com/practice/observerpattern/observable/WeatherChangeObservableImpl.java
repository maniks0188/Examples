package com.practice.observerpattern.observable;

import java.util.ArrayList;
import java.util.List;

import com.practice.observerpattern.observer.IWeatherAlertObserver;


/**
 * This is the concrete implementation of the IWeatherObservable.
 * This class is responsible to manage the observers list.
 * This class sets the temperature current value and returns it.
 * 
 * @author Manik Sharma
 *
 */
public class WeatherChangeObservableImpl implements IWeatherObservable{

	private List<IWeatherAlertObserver> listObservers = new ArrayList<IWeatherAlertObserver>();
	private Double temp = 0.0; 
	
	@Override
	public void register(IWeatherAlertObserver observer) {
		listObservers.add(observer);
	}

	@Override
	public void remove(IWeatherAlertObserver observer) {
		listObservers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for(IWeatherAlertObserver obj:listObservers) {
			obj.update();
		}
		
	}

	@Override
	public Double getTemperature() {
		return temp;
	}

	@Override
	public void setTemperature(Double newTemp) {
		if(Double.compare(newTemp, temp) != 0) {
			this.temp = newTemp;
			notifyObservers();
		}
	}

}
