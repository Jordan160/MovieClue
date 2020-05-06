package com.jvetter2.movieclue;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieList {

    //api key 39eded7c924a15985f6ce7c11be25a40

    String result;
    TextView weatherCondition;
    Toast toast;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        weatherCondition = findViewById(R.id.textView);
//        result = null;
//        toast = new Toast(getApplicationContext());
//        downloadContent("hi");
//    }

    public void getWeather(View view) {
        //Button weatherButton = findViewById(R.id.weatherButton);
        //EditText city = findViewById(R.id.cityEditText);
//        String encodedCity = "";
//        try {
//            encodedCity = URLEncoder.encode(city.getText().toString(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        Log.i("Application", city.getText().toString());
//        downloadContent(encodedCity);
//
//        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        mgr.hideSoftInputFromWindow(city.getWindowToken(), 0);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return  result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //weatherCondition.setText(null);

                JSONObject jsonObject = new JSONObject(s);

                String movieTitle = jsonObject.getString("title");
                Log.i("Movie Title", movieTitle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("JSON", s);
        }
    }

    public String downloadContent(String city, Context context) {
        //String encodedURL = "";
        DownloadTask task = new DownloadTask();
        //String url = "https://api.themoviedb.org/3/movie/550?api_key=39eded7c924a15985f6ce7c11be25a40";
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=39eded7c924a15985f6ce7c11be25a40";

        try {
            result = task.execute(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
