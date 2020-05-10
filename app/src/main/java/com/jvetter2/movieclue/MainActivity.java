package com.jvetter2.movieclue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Movie> mList = new ArrayList<>();
    private MovieArrayAdapter mAdapter;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private TextView welcomeTV;
    private TextView welcomeTV2;
    public static MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;

    public ArrayList<String> upMovieNames = new ArrayList<>();
    public ArrayList<String> upMovieDescriptions = new ArrayList<>();;
    public ArrayList<Bitmap> upMovieImages = new ArrayList<>();
    public ArrayList<String> npMovieNames = new ArrayList<>();
    public ArrayList<String> npMovieDescriptions = new ArrayList<>();
    public ArrayList<Bitmap> npMovieImages = new ArrayList<>();
    public ArrayList<String> pMovieNames = new ArrayList<>();
    public ArrayList<String> pMovieDescriptions = new ArrayList<>();
    public ArrayList<Bitmap> pMovieImages = new ArrayList<>();
    public ArrayList<String> topMovieNames = new ArrayList<>();
    public ArrayList<String> topMovieDescriptions = new ArrayList<>();
    public ArrayList<Bitmap> topMovieImages = new ArrayList<>();
    public String[] urls;
    private HandlerThread thread;
    private int progressStatus = 0;
    public FragmentManager fragmentManager;
    public Fragment fragment;

    public void setUpMovieNames(ArrayList<String> upMovieNames) {
        this.upMovieNames = upMovieNames;
    }

    public void setUpMovieDescriptions(ArrayList<String> upMovieDescriptions) {
        this.upMovieDescriptions = upMovieDescriptions;
    }

    public void setUpMovieImages(ArrayList<Bitmap> upMovieImages) {
        this.upMovieImages = upMovieImages;
    }

    public void setNpMovieNames(ArrayList<String> npMovieNames) {
        this.npMovieNames = npMovieNames;
    }

    public void setNpMovieDescriptions(ArrayList<String> npMovieDescriptions) {
        this.npMovieDescriptions = npMovieDescriptions;
    }

    public void setNpMovieImages(ArrayList<Bitmap> npMovieImages) {
        this.npMovieImages = npMovieImages;
    }

    public void setpMovieNames(ArrayList<String> pMovieNames) {
        this.pMovieNames = pMovieNames;
    }

    public void setpMovieDescriptions(ArrayList<String> pMovieDescriptions) {
        this.pMovieDescriptions = pMovieDescriptions;
    }

    public void setpMovieImages(ArrayList<Bitmap> pMovieImages) {
        this.pMovieImages = pMovieImages;
    }

    public void setTopMovieNames(ArrayList<String> topMovieNames) {
        this.topMovieNames = topMovieNames;
    }

    public void setTopMovieImages(ArrayList<Bitmap> topMovieImages) {
        this.topMovieImages = topMovieImages;
    }

    public void setTopMovieDescriptions(ArrayList<String> topMovieDescriptions) {
        this.topMovieDescriptions = topMovieDescriptions;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        urls = res.getStringArray(R.array.urls);

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Retrieving latest movies...", true);

        Thread thread = new Thread() {
            public void run() {
                while (progressStatus < 20 && npMovieNames!= null && npMovieNames.size() < 10) {
                    progressStatus += 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        };
        thread.start();

        recyclerView = (RecyclerView) findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setNavigationBar();
        setBottomNavigation();
        startMusic();
        fragmentManager = getSupportFragmentManager();

        getMoviesAsync("thread1", upMovieNames, upMovieDescriptions, upMovieImages, urls[0]);
        getMoviesAsync("thread2", npMovieNames, npMovieDescriptions, npMovieImages, urls[1]);
        getMoviesAsync("thread3", pMovieNames, pMovieDescriptions, pMovieImages, urls[2]);
        getMoviesAsync("thread4", topMovieNames, topMovieDescriptions, topMovieImages, urls[3]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMovies(ArrayList<String> movieNames, ArrayList<String> movieDescriptions,
                           ArrayList<Bitmap> movieImages, String url ) {
        MovieList movieList = new MovieList();
        String movie = movieList.downloadContent(url);
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(movie);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < 10; i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                if(null != movieNames && !movieNames.contains(c.getString("title"))) {
                    movieNames.add(i+1 + ". " + c.getString("title"));
                    movieDescriptions.add(c.getString("overview"));
                    getMovieImage(c.getString("backdrop_path"), movieImages);
                }
            }
            if (url.equalsIgnoreCase(getString(R.string.upcoming_url))) {
                setUpMovieNames(movieNames);
                setUpMovieDescriptions(movieDescriptions);
                setUpMovieImages(movieImages);
            } else if (url.equalsIgnoreCase(getString(R.string.now_playing_url))) {
                setNpMovieNames(movieNames);
                setNpMovieDescriptions(movieDescriptions);
                setNpMovieImages(movieImages);
            } else if (url.equalsIgnoreCase(getString(R.string.popular_url))) {
                setpMovieNames(movieNames);
                setpMovieDescriptions(movieDescriptions);
                setpMovieImages(movieImages);
            } else {
                setTopMovieNames(movieNames);
                setTopMovieDescriptions(movieDescriptions);
                setTopMovieImages(movieImages);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMovieImage(String imageURL, ArrayList<Bitmap> movieImages) {
        ImageDownloader task = new ImageDownloader(getApplicationContext());

        try {
            String url = getString(R.string.image_path) + imageURL + "?" + getString(R.string.api_key);
            movieImages.add(task.execute(url).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setNavigationBar() {
        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.now_playing:
                        dl.closeDrawers();
                        setMovies(npMovieNames, npMovieDescriptions, npMovieImages);
                        break;
                    case R.id.upcoming:
                        dl.closeDrawers();
                        setMovies(upMovieNames, upMovieDescriptions, upMovieImages);
                        break;
                    case R.id.popular:
                        dl.closeDrawers();
                        setMovies(pMovieNames, pMovieDescriptions, pMovieImages);
                        break;
                    case R.id.top_rated:
                        dl.closeDrawers();
                        setMovies(topMovieNames, topMovieDescriptions, topMovieImages);
                        break;
                    case R.id.settings:
                        dl.closeDrawers();

                        fragment = SettingsFragment.newInstance();
                        FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                        transaction2.replace(R.id.activity_main, fragment).commit();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv.setVisibility(View.INVISIBLE);
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.now_playing:
                        setMovies(npMovieNames, npMovieDescriptions, npMovieImages);
                        break;
                    case R.id.upcoming:
                        setMovies(upMovieNames, upMovieDescriptions, upMovieImages);
                        break;
                    case R.id.popular:
                        setMovies(pMovieNames, pMovieDescriptions, pMovieImages);
                        break;
                    case R.id.top_rated:
                        setMovies(topMovieNames, topMovieDescriptions, topMovieImages);
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.getMenu().findItem(R.id.now_playing).setChecked(false);
    }

    private void setMovies(ArrayList<String> movieNames, ArrayList<String> movieDescriptions,
                          ArrayList<Bitmap> movieImages) {
        hideWelcomeViews();
        mList.clear();
        for (int i = 0; i < movieImages.size(); i++) {
            Movie movie = new Movie();
            if (i == 9) {
                movie.setDescription(movieDescriptions.get(i) + "\n" + "\n");
            } else {
                movie.setDescription(movieDescriptions.get(i));
            }
            movie.setName(movieNames.get(i));
            movie.setImage(movieImages.get(i));

            mList.add(movie);
        }
        mAdapter = new MovieArrayAdapter(mList, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void getMoviesAsync(String threadName, final ArrayList<String> movieNames,
                                final ArrayList<String> movieDescriptions, final ArrayList<Bitmap> movieImages,
                                final String urlEndpoint){
        thread = new HandlerThread(threadName);
        thread.start();
        Handler handler4 = new Handler(thread.getLooper());
        handler4.post(new Runnable() {
            @Override
            public void run() {
                getMovies(movieNames, movieDescriptions, movieImages, urlEndpoint);
                thread.quitSafely();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            nv.setVisibility(View.VISIBLE);
        } else {
            this.getSupportFragmentManager().popBackStack();
        }
    }

    private void hideWelcomeViews() {
        welcomeTV = findViewById(R.id.welcomeTV);
        welcomeTV2 = findViewById(R.id.welcomeTV2);
        welcomeTV.setVisibility(View.INVISIBLE);
        welcomeTV2.setVisibility(View.INVISIBLE);
    }

    private void startMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.bensoundcreativeminds);
        mediaPlayer.setLooping(true);

        sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        boolean musicPlaying = sharedPreferences.getBoolean("musicPlaying", true);

        if (musicPlaying) {
            mediaPlayer.start();
        }
    }
}