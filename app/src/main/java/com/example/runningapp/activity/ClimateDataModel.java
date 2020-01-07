package com.example.runningapp.activity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ClimateDataModel {

    // TODO: Declare the member variables here
    private double temperature;
    private int condition;
    private String iconName;


    // TODO: Create a WeatherDataModel from a JSON:
    public static ClimateDataModel fromJson(JSONObject jsonObject) {
        ClimateDataModel weatherDataModel = new ClimateDataModel();

        try {
            JSONObject mainData = jsonObject.getJSONObject("main");

            weatherDataModel.temperature = mainData.getDouble("temp");
            weatherDataModel.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherDataModel.iconName = updateWeatherIcon(weatherDataModel.condition);

        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        return weatherDataModel;
    }

    // TODO: Uncomment to this to get the weather image name from the condition:
    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

    // TODO: Create getter methods for temperature, city, and icon name:


    public double getTemperature() {
        return temperature;
    }

    public String getIconName() {
        return iconName;
    }
}
