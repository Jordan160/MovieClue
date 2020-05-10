package com.jvetter2.movieclue;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, String, Bitmap> {

    Context context;

    public ImageDownloader(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            Bitmap pic = Picasso.with(context).load(String.valueOf(url)).get();

//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//
//            InputStream in = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(in);
            return pic;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
