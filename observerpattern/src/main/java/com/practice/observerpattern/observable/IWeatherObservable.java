package com.practice.observerpattern.observable;

import com.practice.observerpattern.observer.IWeatherAlertObserver;


/**
 * This is the Observable interface that has common method definitions.
 * 
 * register: register a new Observer
 * remove: remove an existing Observer
 * notify: notify all the Observers of any change
 * getTemperature: gives the current value of the temperature
 * setTemerature: sets the value of temperature
 * 
 * @author Manik Sharma
 *
 */
public interface IWeatherObservable {
	
	
	public void register(IWeatherAlertObserver observer);
	public void remove(IWeatherAlertObserver observer);
	public void notifyObservers();
	public Double getTemperature();
	public void setTemperature(Double temp);
	
	
}
