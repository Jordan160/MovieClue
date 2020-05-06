package com.jvetter2.movieclue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<Movie> mList = new ArrayList<>();
    private MovieArrayAdapter mAdapter;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Recycler
//        recyclerView = (RecyclerView) findViewById(R.id.movie_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        Movie movie = new Movie();
//        movie.setImage(R.drawable.sonic_movie);
//        movie.setName("Sonic the Hedgehog");
//        movie.setDescription("Based on the global blockbuster videogame franchise from Sega, Sonic the Hedgehog tells the story of the world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. Robotnik and his plans for world domination.");
//        mList.add(movie);
//        movie = new Movie();
//        movie.setImage(R.drawable.sonic_movie);
//        movie.setName("Sonic the Hedgehog2");
//        movie.setDescription("Good movie");
//        mList.add(movie);
//
//        mAdapter = new MovieArrayAdapter(mList, this);
//
//        recyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();

        //

        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        MovieList movieList = new MovieList();
                        String movie = movieList.downloadContent("hi", getApplicationContext());
                        Log.i("movie result: ", movie);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                //JSONObject moviez = (JSONObject) jsonArray.getString(0);
                                //movieDescription = jsonObject.getString("overview");
                                String movieTitle = c.getString("title");
                                String movieDescription = c.getString("overview");
                                String imageURL = c.getString("backdrop_path");
                                updateMovieList(movieTitle, movieDescription, imageURL);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Log.i("Movie Title", movieTitle);

                        //updateMovieList(movieTitle, movieDescription, "img");

//                        TextView textview = findViewById(R.id.);
//                        textview.setText(movie);
                        dl.closeDrawers();
                        break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        dl.closeDrawers();
                        break;
                    case R.id.mycart:
                        Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                        dl.closeDrawers();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(MainActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_nearby:
                        Toast.makeText(MainActivity.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMovieList(String title, String description, String imageURL) {
        Movie movie = new Movie();
        //movie.setImage(R.drawable.sonic_movie);
        movie.setName(title);
        movie.setDescription(description);

        Bitmap myImage = null;
        ImageDownloader task = new ImageDownloader();
//        try {
//            result = task.execute("http://www.posh24.se/kandisar").get();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("Error downloading", "Oofta");
//        }

        try {
            String url = getString(R.string.image_path) + imageURL + "?" + getString(R.string.api_key);
            myImage = task.execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //movie.setImage(getBitmapFromURL("https://image.tmdb.org/t/p/original/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg"));
        movie.setImage(myImage);

        mList.add(movie);

        mAdapter = new MovieArrayAdapter(mList, this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

