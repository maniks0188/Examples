# 🌦️ Weather Alert System - Observer Pattern (Java + Spring Boot)

This project demonstrates the **Observer Design Pattern** in Java using a weather alert use case. It allows multiple observers to register themselves to receive notifications when the weather (specifically temperature) changes.

---

## 🔄 Observer Pattern Overview

This implementation follows the **Observer Pattern**, where:
- **Observable** (subject): The weather system that tracks temperature changes.
- **Observers**: Components (users, devices, systems) that want to be alerted when the temperature changes.

---

## 🧩 Interfaces

### `IWeatherObservable`

The observable interface which provides:
- `register(observer)` – Register a new observer.
- `remove(observer)` – Remove an existing observer.
- `notifyObservers()` – Notify all registered observers.
- `getTemperature()` – Get the current temperature.
- `setTemperature(temp)` – Set a new temperature and notify observers if it changes.

> ✅ Author: Manik Sharma

---

### `IWeatherAlertObserver`

The observer interface that must be implemented by any class that wants to receive weather updates.

```java
public interface IWeatherAlertObserver {
    void update();
}

🛠️ Implementation
WeatherChangeObservableImpl
This is the concrete implementation of IWeatherObservable:

Maintains a list of registered observers.

Updates and notifies observers only if the temperature has changed.

✅ Future Enhancements
Add a Spring Boot REST controller to update temperature via HTTP.
Implement additional observer classes (e.g., MobileAlert, EmailAlert).
Use events and asynchronous messaging (e.g., Spring Events or Kafka) for scalability.

