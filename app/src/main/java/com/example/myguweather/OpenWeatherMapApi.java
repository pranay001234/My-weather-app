package com.example.myguweather;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenWeatherMapApi implements WeatherApi {
    Context _context;
    RequestQueue queue;


    public OpenWeatherMapApi(Context context) {
        _context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void getWeatherForZipCode(String zipCode, Callback callback) {
        String weather_api_url = _context.getString(R.string.weather_api_url);
        String weather_api_key = _context.getString(R.string.weather_api_key);
        String url = String.format(weather_api_url, zipCode, weather_api_key);
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("example.com response", response);
                                try {
                                    JSONObject jsonWeather = new JSONObject(response);
                                    JSONObject main = jsonWeather.getJSONObject("main");
                                    Double temperature = main.getDouble("temp");
                                    Log.d("temperature", temperature.toString());
                                    CurrentWeather weather = new CurrentWeather();
                                    weather.setTemperature(temperature);

                                    JSONArray weatherArray =
                                            jsonWeather.getJSONArray("weather");
                                    JSONObject weatherObject =
                                            weatherArray.getJSONObject(0);
                                    String condition = weatherObject.getString("main");
                                    weather.setCondition(condition);

                                    callback.success(weather);
                                    // tvTemperature.setText(temperature + "");
                                } catch (JSONException e) {
                                    callback.error(ErrorType.JSON);
                                    Log.d("weatherrequestexception", e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.error(ErrorType.NETWORK);
                    }
                });
        queue.add(stringRequest);
    }






}
