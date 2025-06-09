package com.practice.observerpattern.observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practice.observerpattern.observer.IWeatherAlertObserver;
import com.practice.observerpattern.observer.MobileObserverImpl;
import com.practice.observerpattern.observer.TelevisionObserverImpl;

@ExtendWith(MockitoExtension.class)
public class WeatherChangeObservableImplTest {

	private WeatherChangeObservableImpl weatherChangeObservableImpl;

	@Mock
	private IWeatherAlertObserver observer1;

	@Mock
	private IWeatherAlertObserver observer2;

	@BeforeEach
	void setup() {
		weatherChangeObservableImpl = new WeatherChangeObservableImpl();
	}

	@Test
	void testRegisterAndNotifyObservers() {
		observer1 = new TelevisionObserverImpl(weatherChangeObservableImpl);
		observer2 = new MobileObserverImpl(weatherChangeObservableImpl);
		weatherChangeObservableImpl.register(observer1);
		weatherChangeObservableImpl.register(observer2);

		weatherChangeObservableImpl.setTemperature(25.5);

		verify(observer1, times(1)).update();
		verify(observer2, times(1)).update();
	}
	
	@Test
	void testRemoveObservers() {
		observer1 = new TelevisionObserverImpl(weatherChangeObservableImpl);
		observer2 = new MobileObserverImpl(weatherChangeObservableImpl);
		weatherChangeObservableImpl.register(observer1);
		weatherChangeObservableImpl.register(observer2);

		weatherChangeObservableImpl.remove(observer1);
		weatherChangeObservableImpl.setTemperature(25.5);

		verify(observer1, never()).update();
		verify(observer2, times(1)).update();
	}
	
	@Test
	void testNoTemperatureChange() {
		observer1 = new TelevisionObserverImpl(weatherChangeObservableImpl);
		observer2 = new MobileObserverImpl(weatherChangeObservableImpl);
		weatherChangeObservableImpl.register(observer1);
		weatherChangeObservableImpl.register(observer2);
		weatherChangeObservableImpl.setTemperature(25.5);
		weatherChangeObservableImpl.setTemperature(25.5);
		
		verify(observer1, never()).update();
		verify(observer2, never()).update();
		
	}
	
}
