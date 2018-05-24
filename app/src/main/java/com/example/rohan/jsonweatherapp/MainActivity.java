package com.example.rohan.jsonweatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText location;
    TextView weatherInfoBox;

    public void checkWeather(View view){
        String result = "";
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(location.getWindowToken(),0);
        CheckWeather weather = new CheckWeather();

        String place = location.getText().toString();
        String preparedURL = "http://api.openweathermap.org/data/2.5/weather?q=" + place + "&appid=e13132fe2d55423dc4ac0cadf192e23f";
        try {

            result = weather.execute(preparedURL).get();

        } catch (Exception e) {

            e.printStackTrace();

        }
        try {

            JSONObject jsonObjectFromString = new JSONObject(result);

            String weatherInfo = jsonObjectFromString.getString("weather");

            JSONArray arrayofWeather = new JSONArray(weatherInfo);


                JSONObject jsonObject = arrayofWeather.getJSONObject(0);


                weatherInfoBox.setText(jsonObject.getString("main") + " : " + jsonObject.getString("description"));


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public class CheckWeather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {

                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = (EditText)findViewById(R.id.location);
        weatherInfoBox = findViewById(R.id.weatherInfo);
    }
}
