package com.example.myguweather;

public interface Callback {
    void success(CurrentWeather currentWeather);
    void error(ErrorType error);
}
