package com.example.myguweather;


public interface WeatherApi {

    void getWeatherForZipCode(String zipCode, Callback callback);

}
