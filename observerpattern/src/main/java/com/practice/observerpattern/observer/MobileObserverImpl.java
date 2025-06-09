package com.practice.observerpattern.observer;

import com.practice.observerpattern.observable.IWeatherObservable;

public class MobileObserverImpl implements IWeatherAlertObserver{

	private IWeatherObservable observable;
	public MobileObserverImpl(IWeatherObservable obs) {
		this.observable = obs;
	}
	
	@Override
	public void update() {
		System.out.println("Temperature change detected! Updating new temp in Mobile - "+ observable.getTemperature());
	}

}
