package com.jvetter2.movieclue;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieList {
    String result;

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

    public String downloadContent(String url) {
        DownloadTask task = new DownloadTask();
        try {
            result = task.execute(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
