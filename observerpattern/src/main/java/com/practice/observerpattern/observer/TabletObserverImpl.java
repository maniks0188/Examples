package com.practice.observerpattern.observer;

import com.practice.observerpattern.observable.IWeatherObservable;

/**
 * Observer Implementation for Mobile devices.
 * This class should have to business logic implemented in the update method. 
 * It uses constructor injection to gain access to the Observable class.
 * 
 * @author Manik Sharma
 *
 */
public class TabletObserverImpl implements IWeatherAlertObserver{

	private IWeatherObservable observable;
	public TabletObserverImpl(IWeatherObservable obs) {
		this.observable = obs;
	}
	
	@Override
	public void update() {
		System.out.println("Temperature change detected! Updating new temp in Tablet- "+ observable.getTemperature());
	}

}
