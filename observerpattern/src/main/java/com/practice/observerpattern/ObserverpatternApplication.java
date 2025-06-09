package com.practice.observerpattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.practice.observerpattern.observable.IWeatherObservable;
import com.practice.observerpattern.observable.WeatherChangeObservableImpl;
import com.practice.observerpattern.observer.IWeatherAlertObserver;
import com.practice.observerpattern.observer.MobileObserverImpl;
import com.practice.observerpattern.observer.TabletObserverImpl;
import com.practice.observerpattern.observer.TelevisionObserverImpl;

@SpringBootApplication
public class ObserverpatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObserverpatternApplication.class, args);
		
		IWeatherObservable weatherObs = new WeatherChangeObservableImpl();
		
		IWeatherAlertObserver mobileObserver = new MobileObserverImpl(weatherObs);
		IWeatherAlertObserver tabletObserver = new TabletObserverImpl(weatherObs);
		IWeatherAlertObserver televisionObserver = new TelevisionObserverImpl(weatherObs);
		
		weatherObs.register(mobileObserver);
		weatherObs.register(tabletObserver);
		weatherObs.register(televisionObserver);
		
		weatherObs.setTemperature(46.5);
		weatherObs.setTemperature(56.5);
		weatherObs.remove(televisionObserver);
		weatherObs.setTemperature(66.5);
		weatherObs.register(televisionObserver);
		weatherObs.setTemperature(40.5);
		weatherObs.setTemperature(98.5);
		weatherObs.remove(tabletObserver);
		weatherObs.setTemperature(100.5);
	}

}
